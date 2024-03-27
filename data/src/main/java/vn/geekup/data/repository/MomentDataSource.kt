package vn.geekup.data.repository

import androidx.paging.*
import androidx.room.withTransaction
import kotlinx.coroutines.flow.*
import retrofit2.Response
import vn.geekup.data.model.moment.MomentVO
import vn.geekup.data.remote.PagingByNetworkDataSource
import vn.geekup.data.local.AppDatabase
import vn.geekup.data.local.PagingByLocalDataSource
import vn.geekup.data.model.general.ListResponse
import vn.geekup.data.model.general.ObjectResponse
import vn.geekup.data.service.AliaApiService
import vn.geekup.domain.model.general.RemoteKey
import vn.geekup.domain.model.moment.MomentModel
import vn.geekup.domain.repository.MomentRepository

class MomentDataSource(
    private val db: AppDatabase,
    private val aliaApiService: AliaApiService
) : MomentRepository {

    override suspend fun getPagingFeeds(): Flow<PagingData<MomentModel>> =
        Pager(
            config = PagingConfig(15),
        ) {
            object :
                PagingByNetworkDataSource<MomentVO, MomentModel>() {
                override suspend fun onApi(nextKey: String?): Response<ObjectResponse<ListResponse<MomentVO>>> =
                    aliaApiService.getFlowMomentFeeds(cursor = nextKey)

                override suspend fun processResponse(request: ListResponse<MomentVO>?): ListResponse<MomentModel> {
                    return ListResponse(
                        limit = request?.limit,
                        nextCursor = request?.nextCursor,
                        items = request?.items?.map {
                            it.vo2Model()
                        } as? ArrayList<MomentModel>,
                        metadata = request?.metadata
                    )
                }
            }
        }.flow

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPagingLocalFeeds(): Flow<PagingData<MomentModel>> = Pager(
        config = PagingConfig(25),
        remoteMediator = object : PagingByLocalDataSource<MomentVO, MomentModel, RemoteKey>() {
            override suspend fun onApi(nextKey: String?): Response<ObjectResponse<ListResponse<MomentVO>>> =
                aliaApiService.getFlowMomentFeeds(cursor = nextKey)

            override suspend fun processResponse(request: ListResponse<MomentVO>?): ListResponse<MomentModel> {
                return ListResponse(
                    limit = request?.limit,
                    nextCursor = request?.nextCursor,
                    items = request?.items?.map {
                        it.vo2Model()
                    } as? ArrayList<MomentModel>,
                    metadata = request?.metadata
                )
            }

            override suspend fun getInitKey(): RemoteKey? = db.remoteKeyDao()
                .remoteKeysIdAll().lastOrNull()

            override suspend fun getRemoteKey(
                state: PagingState<Int, MomentModel>
            ): RemoteKey? {
                val repoId =
                    if (state.pages.lastOrNull()?.data?.isEmpty() == true) getInitKey()?.repoId else
                        state.pages
                            .lastOrNull()?.data?.lastOrNull()?.id?.toString()
                return db.withTransaction {
                    db.remoteKeyDao().remoteKeysId(repoId ?: "")
                }
            }

            override suspend fun onRefresh() {
                db.withTransaction {
                    db.remoteKeyDao().delete()
                    db.travelFeedDao().delete()
                }
            }

            override suspend fun onDB(response: ListResponse<MomentModel>?) {
                db.withTransaction {
                    db.remoteKeyDao().insert(
                        RemoteKey(
                            repoId = response?.items?.lastOrNull()?.id?.toString() ?: "",
                            nextKey = response?.nextCursor ?: ""
                        )
                    )
                    db.travelFeedDao().insertAll(response?.items ?: arrayListOf())
                }
            }
        },
        pagingSourceFactory = {
            db.travelFeedDao().getPagingTravelFeeds()
        }
    ).flow

}

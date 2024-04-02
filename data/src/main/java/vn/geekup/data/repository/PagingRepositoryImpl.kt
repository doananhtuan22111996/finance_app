package vn.geekup.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import vn.geekup.data.local.dao.ItemDao
import vn.geekup.data.local.PagingByLocalDataSource
import vn.geekup.data.network.PagingByNetworkDataSource
import vn.geekup.data.model.ItemRaw
import vn.geekup.data.model.ListResponse
import vn.geekup.data.service.ApiService
import vn.geekup.domain.model.ItemModel
import vn.geekup.domain.repository.PagingRepository

class PagingRepositoryImpl(
    private val apiService: ApiService,
    private val itemDao: ItemDao,
) : PagingRepository {

    override suspend fun getPagingNetwork(): Flow<PagingData<ItemModel>> = Pager(
        config = PagingConfig(15),
    ) {
        object : PagingByNetworkDataSource<ItemRaw, ItemModel>() {
            override suspend fun onApi(page: Int?): Response<ListResponse<ItemRaw>> =
                apiService.getPaging(page = page ?: 1)

            override suspend fun processResponse(request: ListResponse<ItemRaw>?): ListResponse<ItemModel> {
                // Save Data to Room
                itemDao.insertAll(request?.data ?: listOf())
                return ListResponse(data = request?.data?.map {
                    it.raw2Model() as ItemModel
                }, metadata = request?.metadata)
            }
        }
    }.flow

    override suspend fun getPagingLocal(): Flow<PagingData<ItemModel>> = Pager(
        config = PagingConfig(15),
    ) {
        object : PagingByLocalDataSource<ItemRaw, ItemModel>() {
            override suspend fun onDatabase(offset: Int?): List<ItemRaw> =
                itemDao.getPagingItems(limit = 15, offset = offset)

            override suspend fun processResponse(request: List<ItemRaw>?): List<ItemModel>? =
                request?.map {
                    it.raw2Model() as ItemModel
                }
        }
    }.flow

    // TODO Paging Local with Room
//    @OptIn(ExperimentalPagingApi::class)
//    override suspend fun getPagingLocal(): Flow<PagingData<ItemModel>> =
//        Pager(config = PagingConfig(25),
//            remoteMediator = object : PagingByLocalDataSource<ItemRaw, ItemModel, RemoteKey>() {
//                override suspend fun onApi(nextKey: String?): Response<ListResponse<ItemRaw>> =
//                    apiService.getPaging(cursor = nextKey)
//
//                override suspend fun processResponse(request: ListResponse<ItemRaw>?): ListResponse<ItemModel> {
//                    return ListResponse(limit = request?.limit,
//                        nextCursor = request?.nextCursor,
//                        items = request?.items?.map {
//                            it.raw2Model()
//                        } as? ArrayList<ItemModel>,
//                        metadata = request?.metadata)
//                }
//
//                override suspend fun getInitKey(): RemoteKey? =
//                    db.remoteKeyDao().remoteKeysIdAll().lastOrNull()
//
//                override suspend fun getRemoteKey(
//                    state: PagingState<Int, ItemModel>
//                ): RemoteKey? {
//                    val repoId =
//                        if (state.pages.lastOrNull()?.data?.isEmpty() == true) getInitKey()?.repoId else state.pages.lastOrNull()?.data?.lastOrNull()?.id?.toString()
//                    return db.withTransaction {
//                        db.remoteKeyDao().remoteKeysId(repoId ?: "")
//                    }
//                }
//
//                override suspend fun onRefresh() {
//                    db.withTransaction {
//                        db.remoteKeyDao().delete()
//                        db.travelFeedDao().delete()
//                    }
//                }
//
//                override suspend fun onDB(response: ListResponse<ItemModel>?) {
//                    db.withTransaction {
//                        db.remoteKeyDao().insert(
//                            RemoteKey(
//                                repoId = response?.items?.lastOrNull()?.id?.toString() ?: "",
//                                nextKey = response?.nextCursor ?: ""
//                            )
//                        )
//                        db.travelFeedDao().insertAll(response?.items ?: arrayListOf())
//                    }
//                }
//            },
//            pagingSourceFactory = {
//                db.itemDao().getPagingItems()
//            }).flow
}

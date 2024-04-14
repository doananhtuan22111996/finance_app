package vn.finance.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import timber.log.Timber
import vn.finance.data.local.dao.ItemDao
import vn.finance.data.local.PagingByLocalDataSource
import vn.finance.data.network.PagingByNetworkDataSource
import vn.finance.data.model.ItemRaw
import vn.finance.data.model.ListResponse
import vn.finance.data.service.ApiService
import vn.finance.domain.model.ItemModel
import vn.finance.domain.repository.PagingRepository

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
                Timber.d("processResponse: ${itemDao.getItems()}")
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
}

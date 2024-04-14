package vn.finance.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.finance.domain.model.ItemModel

interface PagingRepository {

    suspend fun getPagingNetwork(): Flow<PagingData<ItemModel>>

    suspend fun getPagingLocal(): Flow<PagingData<ItemModel>>
}

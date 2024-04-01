package vn.geekup.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.geekup.domain.model.ItemModel

interface PagingRepository {

    suspend fun getPagingNetwork(): Flow<PagingData<ItemModel>>

    // TODO Paging Local with DataBase Room
//    suspend fun getPagingLocal(): Flow<PagingData<ItemModel>>
}

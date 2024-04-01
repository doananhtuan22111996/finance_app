package vn.geekup.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.geekup.domain.model.ItemModel
import vn.geekup.domain.repository.PagingRepository

interface PagingUseCase {

    suspend fun getPagingNetwork(): Flow<PagingData<ItemModel>>

    // TODO Paging Local with Room
//    suspend fun getPagingLocal(): Flow<PagingData<ItemModel>>
}

class PagingUseCaseImpl(private val repository: PagingRepository) : PagingUseCase {

    override suspend fun getPagingNetwork(): Flow<PagingData<ItemModel>> {
        return repository.getPagingNetwork()
    }
}

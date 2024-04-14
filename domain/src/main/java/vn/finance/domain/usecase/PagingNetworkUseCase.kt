package vn.finance.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.finance.domain.model.ItemModel
import vn.finance.domain.repository.PagingRepository

interface PagingNetworkUseCase {

    suspend fun getPagingNetwork(): Flow<PagingData<ItemModel>>
}

class PagingNetworkUseCaseImpl(private val repository: PagingRepository) : PagingNetworkUseCase {

    override suspend fun getPagingNetwork(): Flow<PagingData<ItemModel>> {
        return repository.getPagingNetwork()
    }
}

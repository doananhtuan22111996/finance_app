package vn.geekup.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.geekup.domain.model.ItemModel
import vn.geekup.domain.repository.PagingRepository

interface PagingLocalUseCase {

    suspend fun getPagingLocal(): Flow<PagingData<ItemModel>>

}

class PagingLocalUseCaseImpl(private val repository: PagingRepository) : PagingLocalUseCase {

    override suspend fun getPagingLocal(): Flow<PagingData<ItemModel>> {
        return repository.getPagingLocal()
    }
}

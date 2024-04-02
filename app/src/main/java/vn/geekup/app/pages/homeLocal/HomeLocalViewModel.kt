package vn.geekup.app.pages.homeLocal

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import vn.geekup.app.base.BaseViewModel
import vn.geekup.domain.model.ItemModel
import vn.geekup.domain.usecase.PagingLocalUseCase

class HomeLocalViewModel(private val pagingUseCase: PagingLocalUseCase) : BaseViewModel() {

    suspend fun paging(): Flow<PagingData<ItemModel>> =
        pagingUseCase.getPagingLocal().cachedIn(viewModelScope)
}

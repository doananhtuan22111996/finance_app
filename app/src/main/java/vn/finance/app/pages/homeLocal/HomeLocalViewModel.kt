package vn.finance.app.pages.homeLocal

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import vn.finance.app.base.BaseViewModel
import vn.finance.domain.model.ItemModel
import vn.finance.domain.usecase.PagingLocalUseCase

class HomeLocalViewModel(private val pagingUseCase: PagingLocalUseCase) : BaseViewModel() {

    suspend fun paging(): Flow<PagingData<ItemModel>> =
        pagingUseCase.getPagingLocal().cachedIn(viewModelScope)
}

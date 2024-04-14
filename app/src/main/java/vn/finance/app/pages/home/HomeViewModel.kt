package vn.finance.app.pages.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import vn.finance.app.base.BaseViewModel
import vn.finance.domain.model.ItemModel
import vn.finance.domain.usecase.PagingNetworkUseCase

class HomeViewModel(private val pagingUseCase: PagingNetworkUseCase) : BaseViewModel() {

    suspend fun paging(): Flow<PagingData<ItemModel>> =
        pagingUseCase.getPagingNetwork().cachedIn(viewModelScope)
}

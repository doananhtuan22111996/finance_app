package vn.geekup.app.pages.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import vn.geekup.app.base.BaseViewModel
import vn.geekup.domain.model.ItemModel
import vn.geekup.domain.usecase.PagingUseCase

class HomeViewModel(private val pagingUseCase: PagingUseCase) : BaseViewModel() {

    suspend fun paging(): Flow<PagingData<ItemModel>> =
        pagingUseCase.getPagingNetwork().cachedIn(viewModelScope)
}

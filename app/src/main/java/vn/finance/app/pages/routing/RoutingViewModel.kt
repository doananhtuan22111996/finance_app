package vn.finance.app.pages.routing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.finance.app.base.BaseViewModel
import vn.finance.domain.model.ResultModel
import vn.finance.domain.usecase.SkippedOnBoardingUseCase

class RoutingViewModel(private val useCase: SkippedOnBoardingUseCase) : BaseViewModel() {
    val skipped: MutableLiveData<Boolean?> = MutableLiveData(null)

    fun onSkipped() {
        viewModelScope.launch {
            useCase.execute().collect {
                when (it) {
                    is ResultModel.Success -> skipped.value = it.data ?: false
                    is ResultModel.AppException -> setAppException(it)
                    else -> {
                        // Do nothings
                    }
                }
            }
        }
    }
}
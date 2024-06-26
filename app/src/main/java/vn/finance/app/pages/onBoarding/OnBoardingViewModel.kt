package vn.finance.app.pages.onBoarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.finance.app.base.BaseViewModel
import vn.finance.domain.model.ResultModel
import vn.finance.domain.usecase.SkipOnBoardingUseCase

class OnBoardingViewModel(private val useCase: SkipOnBoardingUseCase) : BaseViewModel() {

    val skipped: MutableLiveData<Boolean> = MutableLiveData(false)

    fun onSkip() {
        viewModelScope.launch {
            useCase.execute().collect {
                when (it) {
                    is ResultModel.Success -> skipped.value = true
                    is ResultModel.AppException -> setAppException(it)
                    else -> {
                        // Do nothings
                    }
                }
            }
        }
    }
}
package vn.finance.app.pages.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.finance.app.base.BaseViewModel
import vn.finance.domain.model.ResultModel
import vn.finance.domain.usecase.LoginUseCase
import vn.finance.domain.usecase.LogoutUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase, private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    val login: MutableLiveData<Boolean> = MutableLiveData(false)

    fun login() {
        viewModelScope.launch {
            loginUseCase.login().collect {
                Timber.i("Thread login: ${Thread.currentThread().name} -- $it")
                when (it) {
                    is ResultModel.Success -> login.value = it.data?.token?.isNotEmpty()

                    is ResultModel.AppException -> setAppException(it)

                    is ResultModel.Done -> setLoadingOverlay(false)

                    else -> setLoadingOverlay(true)
                }
            }
        }
    }
}

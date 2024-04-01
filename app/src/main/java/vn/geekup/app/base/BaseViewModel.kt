package vn.geekup.app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import vn.geekup.domain.model.ResultModel

open class BaseViewModel : ViewModel() {

    val loadingOverlay: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val exception: MutableLiveData<ResultModel.AppException?> = MutableLiveData(null)

    fun setLoadingOverlay(isLoading: Boolean) {
        loadingOverlay.value = isLoading
    }

    fun setAppException(error: ResultModel.AppException?) {
        if (error == null) return
        exception.value = error
        Timber.e("executeException: ${error.message}")
    }
}
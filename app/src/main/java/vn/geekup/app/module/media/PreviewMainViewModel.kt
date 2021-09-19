package vn.geekup.app.module.media

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.geekup.app.base.BaseViewModel
import vn.geekup.app.network.NetworkChange
import vn.geekup.app.utils.KEY_ARGUMENT_IMAGES
import javax.inject.Inject

@HiltViewModel
class PreviewMainViewModel @Inject constructor(networkChange: NetworkChange) :
    BaseViewModel(networkChange) {

    val imagesUrls: MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())

    override fun loadArgumentsBundle(bundle: Bundle?) {
        imagesUrls.value = bundle?.getStringArrayList(KEY_ARGUMENT_IMAGES) ?: arrayListOf()
    }
}
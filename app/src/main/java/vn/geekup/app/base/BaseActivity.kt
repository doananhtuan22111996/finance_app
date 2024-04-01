package vn.geekup.app.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import vn.geekup.app.databinding.ActivityBaseBinding
import vn.geekup.domain.model.ResultModel
import vn.geekup.app.utils.setupViewClickHideKeyBoard

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected abstract val viewModel: VM

    protected lateinit var viewBinding: ActivityBaseBinding
        private set

    abstract fun onInit(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityBaseBinding.inflate(layoutInflater)
        super.setContentView(viewBinding.root)
        window?.setupViewClickHideKeyBoard(viewBinding.root)
        onInit(savedInstanceState)
        bindViewModel()
    }

    @CallSuper
    open fun bindViewModel() {
        viewModel.loadingOverlay.observe(this, this::executeLoadingOverlay)
        viewModel.exception.observe(this, this::executeAppException)
    }

    private fun executeAppException(appException: ResultModel.AppException?) {
        appException?.run {
            Timber.d("executeAppException: ${this.message}")
            Toast.makeText(this@BaseActivity, this.message ?: "", Toast.LENGTH_SHORT).show()
        }
    }

    private fun executeLoadingOverlay(isLoading: Boolean) {
        Timber.d("executeLoadingOverlay: $isLoading")
        viewBinding.layoutLoading.rlProgressbar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}
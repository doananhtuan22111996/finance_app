package vn.geekup.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import vn.geekup.app.utils.setupViewClickHideKeyBoard

abstract class BaseFragment<SharedVM : BaseViewModel, VM : BaseViewModel, VB : ViewBinding> :
    Fragment() {

    protected abstract val sharedViewModel: SharedVM

    protected abstract val viewModel: VM

    protected lateinit var viewBinding: VB

    protected lateinit var navController: NavController

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    abstract fun onInit(view: View, savedInstanceState: Bundle?)

    @CallSuper
    open fun bindViewModel() {
        viewModel.loadingOverlay.observe(viewLifecycleOwner) {
            sharedViewModel.setLoadingOverlay(it)
        }

        viewModel.exception.observe(viewLifecycleOwner) {
            it?.run {
                sharedViewModel.setAppException(this)

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewBinding = bindingInflater.invoke(inflater, container, false)
        (activity as BaseActivity<*>).window?.setupViewClickHideKeyBoard(viewBinding.root)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        onInit(view, savedInstanceState)
        bindViewModel()
    }
}

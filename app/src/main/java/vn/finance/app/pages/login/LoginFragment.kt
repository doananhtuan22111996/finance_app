package vn.finance.app.pages.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.finance.app.R
import vn.finance.app.base.BaseFragment
import vn.finance.app.databinding.FragmentLoginBinding
import vn.finance.app.pages.root.RootViewModel

class LoginFragment : BaseFragment<RootViewModel, LoginViewModel, FragmentLoginBinding>() {

    override val sharedViewModel: RootViewModel by activityViewModel()

    override val viewModel: LoginViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding =
        FragmentLoginBinding::inflate

    override fun onInit(view: View, savedInstanceState: Bundle?) {
        viewBinding.fragment = this
    }

    override fun bindViewModel() {
        super.bindViewModel()
        viewModel.login.observe(this) {
            if (it) {
                navController.navigate(R.id.pushToMainFragment)
            }
        }
    }

    fun onClickLogin() {
        viewModel.login()
    }
}

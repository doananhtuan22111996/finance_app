package vn.geekup.app.pages.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.geekup.app.R
import vn.geekup.app.base.BaseFragment
import vn.geekup.app.databinding.FragmentMainBinding
import vn.geekup.app.pages.root.RootViewModel

class MainFragment : BaseFragment<RootViewModel, MainViewModel, FragmentMainBinding>() {
    override val sharedViewModel: RootViewModel by activityViewModel()

    override val viewModel: MainViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding =
        FragmentMainBinding::inflate

    override fun onInit(view: View, savedInstanceState: Bundle?) {
        viewBinding.fragment = this
        setupMainNavigation()

        viewBinding.bottomNavigation.lnPagingNetwork.setOnClickListener {
            if (navController.currentDestination?.id == R.id.homeFragment) return@setOnClickListener
            navController.navigate(R.id.navigateToHomeNetwork)
        }

        viewBinding.bottomNavigation.lnPagingLocal.setOnClickListener {
            if (navController.currentDestination?.id == R.id.homeLocalFragment) return@setOnClickListener
            navController.navigate(R.id.navigateToHomeLocal)
        }
    }

    private fun setupMainNavigation() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.nav_main)
        // Todo Handle Argument in intent
//    val navArgument = NavArgument.Builder().setDefaultValue().build()
//    graph.addArgument(
//      KEY_ARGUMENT,
//      navArgument
//    ) // This is where you pass the bundle data from Activity to StartDestination
        navHostFragment.navController.graph = graph
    }
}
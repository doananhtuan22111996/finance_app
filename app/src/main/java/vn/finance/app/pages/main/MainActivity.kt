package vn.finance.app.pages.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.finance.app.R
import vn.finance.app.base.BaseActivity
import vn.finance.app.pages.routing.RootEnum
import vn.finance.app.utils.Constants

class MainActivity : BaseActivity<RootViewModel>() {

    override val viewModel: RootViewModel by viewModel()

    private lateinit var navController: NavController

    override fun onInit(savedInstanceState: Bundle?) {
        // TODO check logged in
        with(
            intent.getIntExtra(
                Constants.KEY_ROUTING_NAME, RootEnum.OnBoarding.hashCode()
            )
        ) {
            when (this) {
                RootEnum.Login.hashCode() -> setupRootNavigation(RootEnum.Login)
                RootEnum.Home.hashCode() -> setupRootNavigation(RootEnum.Home)
                else -> setupRootNavigation(RootEnum.OnBoarding)
            }
        }
    }

    private fun setupRootNavigation(root: RootEnum) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.root_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.nav_root)
        // Todo Handle Argument in intent
//    val navArgument = NavArgument.Builder().setDefaultValue().build()
//    graph.addArgument(
//      KEY_ARGUMENT,
//      navArgument
//    ) // This is where you pass the bundle data from Activity to StartDestination
        // This is where you change start Destination
        val destination = when (root) {
            is RootEnum.OnBoarding -> R.id.onBoardingFragment
            is RootEnum.Login -> R.id.loginFragment
            else -> R.id.mainFragment
        }
        graph.setStartDestination(destination)
        navHostFragment.navController.graph = graph
    }
}

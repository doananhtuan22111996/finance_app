package vn.finance.app.pages.root

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.finance.app.R
import vn.finance.app.base.BaseActivity

class RootActivity : BaseActivity<RootViewModel>() {

    sealed class RootNavigation {
        data object Login : RootNavigation()
        data object Main : RootNavigation()
    }

    override val viewModel: RootViewModel by viewModel()

    private lateinit var navController: NavController

    override fun onInit(savedInstanceState: Bundle?) {
        setupRootNavigation(RootNavigation.Login)
    }

    private fun setupRootNavigation(root: RootNavigation) {
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
            is RootNavigation.Login -> R.id.loginFragment
            else -> R.id.mainFragment
        }
        graph.setStartDestination(destination)
        navHostFragment.navController.graph = graph
    }
}

package vn.geekup.app.module.root

import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.geekup.app.R
import vn.geekup.app.base.BaseActivity
import vn.geekup.app.databinding.ActivityRootBinding
import vn.geekup.app.utils.setAppColorStatusBar

class RootActivity : BaseActivity<RootViewModel, ActivityRootBinding>() {

    sealed class RootNavigation {
        object Login : RootNavigation()
        object Main : RootNavigation()
    }

    override val viewModel: RootViewModel by viewModel()

    private lateinit var navController: NavController

    override fun provideViewModelClass(): Class<RootViewModel> {
        return RootViewModel::class.java
    }

    override fun provideViewBinding(parent: ViewGroup): ActivityRootBinding {
        return ActivityRootBinding.inflate(layoutInflater, parent, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppColorStatusBar()
        redirectAuth(false)

    }

    override fun onInitLayout(savedInstanceState: Bundle?) {
        // TODO handle layout
    }

    override fun bindViewModel() {
        super.bindViewModel()

        viewModel.isLoggedIn.observe(this) {
            redirectAuth(it)
        }

    }

    private fun setupRootNavigation(root: RootNavigation) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.root_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.root_nav_graph)
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

    fun redirectMain() {
        redirectAuth(isLoggedIn = true)
    }

    private fun redirectAuth(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            setupRootNavigation(RootNavigation.Main)
        } else {
            setupRootNavigation(RootNavigation.Login)
        }
    }
}

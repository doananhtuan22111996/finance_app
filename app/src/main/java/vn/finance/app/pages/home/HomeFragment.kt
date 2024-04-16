package vn.finance.app.pages.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import vn.finance.app.base.BaseFragment
import vn.finance.app.base.PagingLoadStateAdapter
import vn.finance.app.databinding.FragmentHomeBinding
import vn.finance.app.pages.main.RootViewModel

class HomeFragment : BaseFragment<RootViewModel, HomeViewModel, FragmentHomeBinding>() {

    private lateinit var adapter: HomeAdapter

    override val sharedViewModel: RootViewModel by activityViewModel()

    override val viewModel: HomeViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = HomeAdapter()
    }

    override fun onInit(view: View, savedInstanceState: Bundle?) {
        viewBinding.rvHome.adapter =
            adapter.withLoadStateFooter(PagingLoadStateAdapter(retryFunc = {
                adapter.retry()
            }))
        viewBinding.swRefresh.setOnRefreshListener {
            adapter.refresh()
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()
        lifecycleScope.launch {
            viewModel.paging().collectLatest {
                adapter.submitData(it)
            }

        }
        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadStates ->
                Timber.d("loadStateFlow $loadStates")
                viewBinding.swRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }
    }
}

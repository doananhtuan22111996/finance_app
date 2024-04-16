package vn.finance.app.pages.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.finance.app.R
import vn.finance.app.base.BaseFragment
import vn.finance.app.databinding.FragmentOnboardingBinding
import vn.finance.app.pages.routing.RootViewModel

class OnBoardingFragment :
    BaseFragment<RootViewModel, OnBoardingViewModel, FragmentOnboardingBinding>() {
    override val sharedViewModel: RootViewModel by activityViewModel()

    override val viewModel: OnBoardingViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOnboardingBinding
        get() = FragmentOnboardingBinding::inflate

    private lateinit var adapter: OnBoardingViewPagerAdapter
    private lateinit var pageJob: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        adapter = OnBoardingViewPagerAdapter(
            this, items = listOf(
                OnBoarding(
                    title = getString(R.string.title_1), content = getString(R.string.content_1)
                ), OnBoarding(
                    title = getString(R.string.title_2), content = getString(R.string.content_2)
                ), OnBoarding(
                    title = getString(R.string.title_3), content = getString(R.string.content_3)
                )
            )
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onInit(view: View, savedInstanceState: Bundle?) {
        with(viewBinding.vPager) {
            adapter = this@OnBoardingFragment.adapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    println("PagerView selected: $position")
                }
            })
        }
        viewBinding.dotIndicator.attachTo(viewBinding.vPager)

        pageJob = lifecycleScope.launch(Dispatchers.Main) {
            while (true) {
                println("Pager 2 while: ${viewBinding.vPager.currentItem} --- ${adapter.itemCount}")
                delay(2000L)
                if (viewBinding.vPager.currentItem < adapter.itemCount - 1) {
                    println("Pager 2 changed: ${viewBinding.vPager.currentItem}")
                    viewBinding.vPager.setCurrentItem(viewBinding.vPager.currentItem + 1, true)
                } else if (viewBinding.vPager.currentItem == adapter.itemCount - 1) {
                    println("Pager 2 Reset: ${viewBinding.vPager.currentItem}")
                    // Reset position = 0
                    viewBinding.vPager.setCurrentItem(0, true)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pageJob.cancel()
    }
}

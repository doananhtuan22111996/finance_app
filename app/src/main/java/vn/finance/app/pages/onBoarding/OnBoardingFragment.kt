package vn.finance.app.pages.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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

    private lateinit var viewPager2: ViewPager2

    override fun onInit(view: View, savedInstanceState: Bundle?) {
        viewPager2 = viewBinding.vPager
        viewPager2.adapter = OnBoardingViewPagerAdapter(
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
        viewBinding.dotIndicator.attachTo(viewPager2)
    }
}

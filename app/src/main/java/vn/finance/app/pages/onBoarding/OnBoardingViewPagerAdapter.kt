package vn.finance.app.pages.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.finance.app.utils.Constants

class OnBoarding(val title: String, val content: String)

class OnBoardingViewPagerAdapter(fragment: Fragment, private val items: List<OnBoarding>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        val fragment = OnBoardingItemFragment()
        fragment.arguments = Bundle().apply {
            putString(Constants.KEY_TITLE, items[position].title)
            putString(Constants.KEY_CONTENT, items[position].content)
        }
        return fragment
    }
}
package vn.finance.app.pages.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.finance.app.databinding.ItemOnboardingBinding
import vn.finance.app.utils.Constants

class OnBoardingItemFragment : Fragment() {

    private lateinit var viewBinding: ItemOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewBinding = ItemOnboardingBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(Constants.KEY_TITLE) }?.apply {
            viewBinding.tvTitle.text = getString(Constants.KEY_TITLE)
        }?.takeIf { it.containsKey(Constants.KEY_CONTENT) }?.apply {
            viewBinding.tvContent.text = getString(Constants.KEY_CONTENT)
        }
    }
}

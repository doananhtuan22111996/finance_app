package vn.geekup.app.utils

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import vn.geekup.app.R

// TODO input Generic View Binding
object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["visible"], requireAll = true)
    fun setVisible(textView: AppCompatTextView, visible: Boolean) {
        textView.visibility = if (visible) View.VISIBLE else View.GONE
    }
}

fun View.visible(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

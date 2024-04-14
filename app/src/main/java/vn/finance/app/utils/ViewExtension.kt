package vn.finance.app.utils

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

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

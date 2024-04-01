package vn.geekup.app.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.geekup.app.databinding.ItemPagingStateBinding
import vn.geekup.app.utils.visible

class PagingLoadStateAdapter(private val retryFunc: (() -> Unit)? = null) :
    LoadStateAdapter<PagingLoadStateAdapter.PagingStateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup, loadState: LoadState
    ): PagingStateViewHolder {
        return PagingStateViewHolder(
            ItemPagingStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PagingStateViewHolder, loadState: LoadState) {
        holder.bind()
    }

    inner class PagingStateViewHolder(private val viewBinding: ItemPagingStateBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind() {
            viewBinding.progressBar.visible(loadState is LoadState.Loading)
            viewBinding.lnRetry.visible(loadState is LoadState.Error)
            viewBinding.btnRetry.setOnClickListener {
                retryFunc?.invoke()
            }
            if (loadState is LoadState.Error) {
                viewBinding.tvRetry.text = (loadState as LoadState.Error).error.message
            }

        }
    }
}



package vn.geekup.app.model

sealed class PagingState {
    data object LoadingMore : PagingState()
    data object Loaded : PagingState()
}
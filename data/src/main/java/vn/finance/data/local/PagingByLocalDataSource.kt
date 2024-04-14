package vn.finance.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * A [PagingSource] that uses the before/after keys returned in page requests.
 *
 * @see PagingByLocalDataSource
 */
abstract class PagingByLocalDataSource<RequestType : Any, ResultType : Any>(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) :
    PagingSource<Int, ResultType>() {

    override fun getRefreshKey(state: PagingState<Int, ResultType>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultType> {
        return withContext(dispatcher) {
            try {
                Timber.d("Load: Params: $params - Page: ${params.key}")
                val response = onDatabase(offset = (params.key ?: 0) * params.loadSize)
                Timber.d("PagingByLocalDataSource Success: + $response")
                // TODO remove if Query big data
                delay(2000)
                LoadResult.Page(
                    data = processResponse(response) ?: listOf(),
                    prevKey = null,
                    nextKey = if (response.isNotEmpty()) (params.key ?: 0).plus(1) else null
                )
            } catch (e: Exception) {
                LoadResult.Error(
                    Throwable(
                        e.message ?: "PagingByLocalDataSource somethings wrong!!!"
                    )
                )
            }

        }
    }

    abstract suspend fun onDatabase(offset: Int?): List<RequestType>
    abstract suspend fun processResponse(request: List<RequestType>?): List<ResultType>?
}

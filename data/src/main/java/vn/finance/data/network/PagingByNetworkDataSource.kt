package vn.finance.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import vn.finance.data.model.ListResponse

/**
 * A [PagingSource] that uses the before/after keys returned in page requests.
 *
 * @see PagingByNetworkDataSource
 */
abstract class PagingByNetworkDataSource<RequestType : Any, ResultType : Any>(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) :
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
            val apiResponse = onApi(page = if (params is LoadParams.Append) params.key else null)
            if (apiResponse.isSuccessful) {
                val body = apiResponse.body()
                val response = processResponse(body)
                val metadata = response?.metadata
                Timber.d("PagingByNetworkDataSource Success: + $metadata")
                LoadResult.Page(
                    data = response?.data ?: listOf(), prevKey = null,
                    // TODO Condition nextPage, maybe change by context API
                    nextKey = if (response?.data?.isNotEmpty() == true && metadata != null && (metadata.page!! * metadata.limit!!) <= metadata.total!!) response.metadata.page!! + 1 else null
                )
            } else {
                try {
                    // TODO Paging Netowrk Error, maybe change by context API
                    val result = Gson().fromJson(
                        apiResponse.errorBody()?.string(), ListResponse<ResultType>()::class.java
                    )
                    Timber.e("PagingByNetworkDataSource Failure: $result")
                    LoadResult.Error(
                        Throwable(
                            result.metadata?.message ?: "Network somethings wrong!!!"
                        )
                    )
                } catch (e: Exception) {
                    LoadResult.Error(
                        Throwable("Network somethings wrong!!! --- ${e.message}")
                    )
                }
            }
        }
    }

    abstract suspend fun onApi(page: Int?): Response<ListResponse<RequestType>>
    abstract suspend fun processResponse(request: ListResponse<RequestType>?): ListResponse<ResultType>?
}

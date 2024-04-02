package vn.geekup.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
            val anchorPage = state.closestPageToPosition(anchorPosition)
            Timber.d(
                "anchorPosition: prevKey - ${anchorPage?.prevKey} --- NextKey: ${
                    anchorPage?.nextKey
                }"
            )
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultType> {
        return withContext(dispatcher) {
            try {
                Timber.d("Load: Params: $params - Page: ${params.key}")
                val response = onDatabase(offset = (params.key ?: 0) * params.loadSize)
                Timber.d("PagingByLocalDataSource Success: + $response")
                LoadResult.Page(
                    data = processResponse(response) ?: listOf(), prevKey = null,
                    // TODO Condition nextPage, maybe change by context API
                    nextKey = if (response.isNotEmpty()) (params.key?.plus(1)) else null
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

// TODO Paging local with Room
//
//@OptIn(ExperimentalPagingApi::class)
//abstract class PagingByLocalDataSource<RequestType : Any, ResultType : Any, remote : RemoteKey> :
//    RemoteMediator<Int, ResultType>() {
//
//    abstract suspend fun onApi(page: Int?): Response<ListResponse<RequestType>>
//    abstract suspend fun processResponse(request: ListResponse<RequestType>?): ListResponse<ResultType>?
//    abstract suspend fun getInitKey(): remote?
//    abstract suspend fun getRemoteKey(state: PagingState<Int, ResultType>): remote?
//    abstract suspend fun onRefresh()
//    abstract suspend fun onDB(response: ListResponse<ResultType>?)
//
//    override suspend fun initialize(): InitializeAction {
//        // Require that remote REFRESH is launched on initial load and succeeds before launching
//        // remote PREPEND / APPEND.
//        withContext(Dispatchers.IO) {
//            getInitKey()
//        }
//        return InitializeAction.LAUNCH_INITIAL_REFRESH
//    }
//
//    override suspend fun load(
//        loadType: LoadType, state: PagingState<Int, ResultType>
//    ): MediatorResult {
//        try {
//            return withContext(Dispatchers.IO) {
//                Timber.tag("PagingByLocalDataSource").e(Thread.currentThread().name)
//
//                // Get the closest item from PagingState that we want to load data around.
//                val loadKey = when (loadType) {
//                    LoadType.REFRESH -> null
//                    LoadType.PREPEND -> {
//                        return@withContext MediatorResult.Success(
//                            endOfPaginationReached = true
//                        )
//                    }
//
//                    LoadType.APPEND -> {
//                        // Query DB for SubredditRemoteKey for the subreddit.
//                        // SubredditRemoteKey is a wrapper object we use to keep track of page keys we
//                        // receive from the Reddit API to fetch the next or previous page.
//                        val remoteKey = getRemoteKey(state)
//
//                        // We must explicitly check if the page key is null when appending, since the
//                        // Reddit API informs the end of the list by returning null for page key, but
//                        // passing a null key to Reddit API will fetch the initial page.
//                        if (remoteKey?.nextKey.isNullOrEmpty()) {
//                            return@withContext MediatorResult.Success(endOfPaginationReached = true)
//                        }
//                        remoteKey?.nextKey
//                    }
//                }
//
//                val apiResponse = onApi(loadKey)
//
//                if (apiResponse.isSuccessful) {
//                    val body = apiResponse.body()
//                    when (apiResponse.code()) {
//                        Config.ErrorCode.CODE_200, Config.ErrorCode.CODE_201, Config.ErrorCode.CODE_204 -> {
//                            val data = processResponse(body)
//                            if (loadType == LoadType.REFRESH) {
//                                onRefresh()
//                            }
//                            onDB(data)
//                            MediatorResult.Success(endOfPaginationReached = data?.items?.isEmpty() == true)
//                        }
//
//                        else -> {
//                            val error = ResultModel.ServerErrorException(
//                                code = apiResponse.code(), message = apiResponse.message()
//                            )
//                            MediatorResult.Error(Throwable(error.message))
//                        }
//                    }
//                } else {
//                    Timber.e("PagingByLocalDataSource: ${apiResponse.errorBody()?.toString()}")
//                    try {
//                        val result = Gson().fromJson(
//                            apiResponse.errorBody()?.toString(),
//                            ResultModel.ServerErrorException::class.java
//                        ) ?: ResultModel.ServerErrorException(
//                            code = CODE_999, message = "Network somethings wrong!!!"
//                        )
//                        MediatorResult.Error(Throwable(message = result.message))
//                    } catch (e: Exception) {
//                        MediatorResult.Error(Throwable("Network somethings wrong!!!"))
//                    }
//                }
//            }
//        } catch (e: IOException) {
//            return MediatorResult.Error(e)
//        } catch (e: HttpException) {
//            return MediatorResult.Error(e)
//        }
//    }
//}

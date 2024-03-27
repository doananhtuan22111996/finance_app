package vn.geekup.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import vn.geekup.data.model.general.ListResponse
import vn.geekup.domain.model.general.ResultModel
import vn.geekup.data.Config
import vn.geekup.data.model.general.ObjectResponse
import java.io.IOException

/**
 * A [PagingSource] that uses the before/after keys returned in page requests.
 *
 * @see PagingByNetworkDataSource
 */
abstract class PagingByNetworkDataSource<RequestType : Any, ResultType : Any> :
    PagingSource<String, ResultType>() {

    abstract suspend fun onApi(nextKey: String?): Response<ObjectResponse<ListResponse<RequestType>>>
    abstract suspend fun processResponse(request: ListResponse<RequestType>?): ListResponse<ResultType>?

    override fun getRefreshKey(state: PagingState<String, ResultType>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ResultType> {
        return try {
            withContext(Dispatchers.IO) {
                val apiResponse =
                    onApi(nextKey = if (params is LoadParams.Append) params.key else null)
                if (apiResponse.isSuccessful) {
                    val body = apiResponse.body()
                    when (apiResponse.code()) {
                        Config.ErrorCode.CODE_200, Config.ErrorCode.CODE_201, Config.ErrorCode.CODE_204 -> {
                            val data = processResponse(body?.data)
                            Timber.tag("PagingByKeyDataSource").e(Thread.currentThread().name)
                            LoadResult.Page(
                                data = data?.items ?: arrayListOf(),
                                prevKey = null,
                                nextKey = data?.nextCursor
//                                nextKey = if (data?.items?.isNotEmpty() == true) data.metadata?.nextPage?.toString() else null
                            )
                        }

                        else -> {
                            val error = ResultModel.ServerErrorException(
                                code = apiResponse.code(),
                                message = apiResponse.message()
                            )
                            LoadResult.Error(Throwable(error.message))
                        }
                    }
                } else {
                    Log.e("PagingByKeyDataSource", "${apiResponse.errorBody()?.toString()}")
                    try {
                        val result = Gson().fromJson(
                            apiResponse.errorBody()?.toString(),
                            ResultModel.ServerErrorException::class.java
                        ) ?: null
                        LoadResult.Error(
                            Throwable(
                                result?.message ?: "Network somethings wrong!!!"
                            )
                        )
                    } catch (e: Exception) {
                        LoadResult.Error(
                            Throwable("Network somethings wrong!!! --- ${e.message}")
                        )
                    }
                }
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
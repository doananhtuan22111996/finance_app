package vn.geekup.data.network

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import timber.log.Timber
import vn.geekup.data.Config.ErrorCode.CODE_200
import vn.geekup.data.Config.ErrorCode.CODE_201
import vn.geekup.data.Config.ErrorCode.CODE_204
import vn.geekup.data.Config.ErrorCode.CODE_999
import vn.geekup.data.model.ObjectResponse
import vn.geekup.domain.model.ResultModel

abstract class NetworkBoundService<RequestType, ResultType>(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val tag = NetworkBoundService::class.java.name

    /**
     * Follow Best Practice
     * https://github.com/android/architecture-components-samples/blob/main/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/NetworkBoundResource.kt
     * */
    fun build() = flow {
        emit(ResultModel.Loading)
        emit(
            fetchFromNetwork() ?: ResultModel.AppException(
                code = CODE_999, message = "Network Somethings wrong!"
            )
        )
        emit(ResultModel.Done)
    }.flowOn(dispatcher)

    /**
     * This Func handle response from Network [fetchFromNetwork].
     */
    private suspend fun fetchFromNetwork(): ResultModel<ResultType>? {
        Timber.i(tag, "Fetch data from network")
        val result: ResultModel<ResultType>?
        val apiResponse = onApi()
        if (apiResponse.isSuccessful) {
            val body = apiResponse.body()
            result = when (apiResponse.code()) {
                CODE_200, CODE_201, CODE_204 -> {
                    processResponse(body)
                }

                else -> {
                    ResultModel.AppException(
                        code = apiResponse.code(), message = apiResponse.message()
                    )
                }
            }
        } else {
            result = try {
                Gson().fromJson(
                    apiResponse.errorBody()?.toString(), ResultModel.AppException::class.java
                ) ?: ResultModel.AppException(
                    code = CODE_999, message = "Network Somethings wrong!"
                )
            } catch (e: Exception) {
                ResultModel.AppException(
                    code = CODE_999, message = "Network Somethings wrong! -- ${e.message}"
                )
            }
        }
        return result
    }

    abstract suspend fun onApi(): Response<ObjectResponse<RequestType>>

    abstract suspend fun processResponse(request: ObjectResponse<RequestType>?): ResultModel.Success<ResultType>

}
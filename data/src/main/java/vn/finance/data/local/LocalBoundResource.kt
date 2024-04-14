package vn.finance.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import vn.finance.data.Config.ErrorCode.CODE_999
import vn.finance.domain.model.ResultModel

abstract class LocalBoundResource<RequestType, ResultType> {

    fun build() = flow {
        emit(ResultModel.Loading)
        emit(
            fetchFromDatabase() ?: ResultModel.AppException(
                message = "LocalBoundResource somethings wrong", code = CODE_999
            )
        )
        emit(ResultModel.Done)
    }.flowOn(Dispatchers.IO)


    private suspend fun fetchFromDatabase(): ResultModel<ResultType>? {
        return try {
            val response = onDatabase()
            Timber.d("Data fetched from Database ${if (response != null) "Success" else "Failure"}")
            ResultModel.Success(data = processResponse(response))
        } catch (e: Exception) {
            Timber.e("Data fetched from Database Error: ${e.message}")
            ResultModel.AppException(
                message = e.message, code = CODE_999
            )
        }
    }

    abstract suspend fun onDatabase(): RequestType

    abstract suspend fun processResponse(request: RequestType?): ResultType?
}

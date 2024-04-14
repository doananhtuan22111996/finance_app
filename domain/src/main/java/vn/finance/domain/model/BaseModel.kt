package vn.finance.domain.model

open class BaseModel

sealed class ResultModel<out R> {
    data class Success<out R>(
        val data: R? = null, val limit: Int? = 0, val nextPage: Int? = 0
    ) : ResultModel<R>()

    data class AppException(val code: Int? = 0, val message: String? = "") : ResultModel<Nothing>()

    data object Loading : ResultModel<Nothing>()
    data object Done : ResultModel<Nothing>()
}
package vn.finance.domain.usecase

import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<in Request : Any, out Result> {
    abstract suspend fun execute(vararg params: Request?): Flow<Result>
}

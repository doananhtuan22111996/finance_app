package vn.finance.domain.usecase

import kotlinx.coroutines.flow.Flow
import vn.finance.domain.model.ResultModel
import vn.finance.domain.repository.OnBoardingRepository

class SkippedOnBoardingUseCase(private val repository: OnBoardingRepository) :
    BaseUseCase<Any, ResultModel<Boolean>>() {
    override suspend fun execute(vararg params: Any?): Flow<ResultModel<Boolean>> =
        repository.skipped()
}
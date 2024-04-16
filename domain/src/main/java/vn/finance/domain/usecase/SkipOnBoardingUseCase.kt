package vn.finance.domain.usecase

import kotlinx.coroutines.flow.Flow
import vn.finance.domain.model.ResultModel
import vn.finance.domain.repository.OnBoardingRepository

class SkipOnBoardingUseCase(private val repository: OnBoardingRepository) :
    BaseUseCase<Any, ResultModel<Nothing>>() {

    override suspend fun execute(vararg params: Any?): Flow<ResultModel<Nothing>> =
        repository.skip()
}
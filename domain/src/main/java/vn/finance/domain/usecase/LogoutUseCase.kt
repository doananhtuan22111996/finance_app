package vn.finance.domain.usecase

import kotlinx.coroutines.flow.Flow
import vn.finance.domain.model.ResultModel
import vn.finance.domain.repository.AuthRepository

interface LogoutUseCase {

    suspend fun logout(): Flow<ResultModel<Nothing>>
}

class LogoutUseCaseImpl(private var repository: AuthRepository) : LogoutUseCase {

    override suspend fun logout(): Flow<ResultModel<Nothing>> = repository.logout()
}

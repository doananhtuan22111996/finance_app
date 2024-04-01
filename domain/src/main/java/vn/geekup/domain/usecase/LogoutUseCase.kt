package vn.geekup.domain.usecase

import kotlinx.coroutines.flow.Flow
import vn.geekup.domain.model.ResultModel
import vn.geekup.domain.model.TokenModel
import vn.geekup.domain.repository.AuthRepository

interface LogoutUseCase {

    suspend fun logout(): Flow<ResultModel<Nothing>>
}

class LogoutUseCaseImpl(private var repository: AuthRepository) : LogoutUseCase {

    override suspend fun logout(): Flow<ResultModel<Nothing>> = repository.logout()
}

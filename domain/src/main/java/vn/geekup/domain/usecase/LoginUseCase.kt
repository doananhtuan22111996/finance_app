package vn.geekup.domain.usecase

import kotlinx.coroutines.flow.Flow
import vn.geekup.domain.model.ResultModel
import vn.geekup.domain.model.TokenModel
import vn.geekup.domain.repository.AuthRepository

interface LoginUseCase {

    suspend fun login(): Flow<ResultModel<TokenModel>>
}

class LoginUseCaseImpl(private var repository: AuthRepository) : LoginUseCase {
    override suspend fun login(): Flow<ResultModel<TokenModel>> = repository.login()
}

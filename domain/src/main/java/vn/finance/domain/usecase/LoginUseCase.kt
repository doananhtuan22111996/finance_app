package vn.finance.domain.usecase

import kotlinx.coroutines.flow.Flow
import vn.finance.domain.model.ResultModel
import vn.finance.domain.model.TokenModel
import vn.finance.domain.repository.AuthRepository

interface LoginUseCase {

    suspend fun login(): Flow<ResultModel<TokenModel>>
}

class LoginUseCaseImpl(private var repository: AuthRepository) : LoginUseCase {
    override suspend fun login(): Flow<ResultModel<TokenModel>> = repository.login()
}

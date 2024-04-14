package vn.finance.domain.repository

import kotlinx.coroutines.flow.Flow
import vn.finance.domain.model.ResultModel
import vn.finance.domain.model.TokenModel

interface AuthRepository {

    suspend fun login(): Flow<ResultModel<TokenModel>>

    suspend fun logout(): Flow<ResultModel<Nothing>>

}
package vn.geekup.domain.repository

import kotlinx.coroutines.flow.Flow
import vn.geekup.domain.model.ResultModel
import vn.geekup.domain.model.TokenModel

interface AuthRepository {

    suspend fun login(): Flow<ResultModel<TokenModel>>

    suspend fun logout(): Flow<ResultModel<Nothing>>

}
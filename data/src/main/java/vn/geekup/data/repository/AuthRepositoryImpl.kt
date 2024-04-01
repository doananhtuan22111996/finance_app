package vn.geekup.data.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import vn.geekup.data.Config
import vn.geekup.data.local.PreferenceWrapper
import vn.geekup.data.model.ObjectResponse
import vn.geekup.data.model.TokenRaw
import vn.geekup.data.network.NetworkBoundService
import vn.geekup.data.service.ApiService
import vn.geekup.domain.model.ResultModel
import vn.geekup.domain.model.TokenModel
import vn.geekup.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: ApiService, private val preferenceWrapper: PreferenceWrapper
) : AuthRepository {
    override suspend fun login(): Flow<ResultModel<TokenModel>> =
        object : NetworkBoundService<TokenRaw, TokenModel>() {
            override suspend fun onApi(): Response<ObjectResponse<TokenRaw>> = apiService.login()

            override suspend fun processResponse(request: ObjectResponse<TokenRaw>?): ResultModel.Success<TokenModel> {
                if (request?.data != null) {
                    preferenceWrapper.saveString(
                        Config.SharePreference.KEY_AUTH_TOKEN, request.data.token ?: ""
                    )
                    preferenceWrapper.saveString(
                        Config.SharePreference.KEY_AUTH_REFRESH_TOKEN,
                        request.data.refreshToken ?: ""
                    )
                }
                return ResultModel.Success(data = request?.data?.raw2Model() as? TokenModel)
            }
        }.build()

    override suspend fun logout(): Flow<ResultModel<Nothing>> =
        object : NetworkBoundService<Nothing, Nothing>() {
            override suspend fun onApi(): Response<ObjectResponse<Nothing>> = apiService.logout()

            override suspend fun processResponse(request: ObjectResponse<Nothing>?): ResultModel.Success<Nothing> =
                ResultModel.Success()
        }.build()

//    override suspend fun saveToken(
//        token: String, refreshToken: String
//    ): Flow<ResultModel<Nothing>> {
//        preferenceWrapper.saveString(Config.SharePreference.KEY_AUTH_TOKEN, token)
//        preferenceWrapper.saveString(Config.SharePreference.KEY_AUTH_REFRESH_TOKEN, refreshToken)
//        return emptyFlow()
//    }
}

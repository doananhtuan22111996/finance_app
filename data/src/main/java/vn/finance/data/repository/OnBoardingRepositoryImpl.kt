package vn.finance.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import vn.finance.data.Config
import vn.finance.data.local.PreferenceWrapper
import vn.finance.domain.model.ResultModel
import vn.finance.domain.repository.OnBoardingRepository

class OnBoardingRepositoryImpl(private val preferenceWrapper: PreferenceWrapper) :
    OnBoardingRepository {

    override suspend fun skipped(): Flow<ResultModel<Boolean>> = flow<ResultModel<Boolean>> {
        try {
            emit(
                ResultModel.Success(
                    data = preferenceWrapper.getBoolean(
                        Config.SharePreference.KEY_SKIP, false
                    )
                )
            )
        } catch (e: Exception) {
            emit(ResultModel.AppException(Config.ErrorCode.CODE_999, message = e.message))
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun skip(): Flow<ResultModel<Nothing>> = flow {
        try {
            preferenceWrapper.saveBoolean(Config.SharePreference.KEY_SKIP, true)
            emit(ResultModel.Success())
        } catch (e: Exception) {
            emit(ResultModel.AppException(Config.ErrorCode.CODE_999, message = e.message))
        }
    }.flowOn(Dispatchers.IO)
}
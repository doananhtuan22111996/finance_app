package vn.finance.domain.repository

import kotlinx.coroutines.flow.Flow
import vn.finance.domain.model.ResultModel

interface OnBoardingRepository {
    suspend fun skipped(): Flow<ResultModel<Boolean>>

    suspend fun skip(): Flow<ResultModel<Nothing>>
}
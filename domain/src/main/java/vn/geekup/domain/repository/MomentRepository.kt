package vn.geekup.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.geekup.domain.dto.MomentFeedRequestBody
import vn.geekup.domain.model.general.ResultModel
import vn.geekup.domain.model.moment.MomentModel

interface MomentRepository {

    suspend fun getPagingFeeds(): Flow<PagingData<MomentModel>>

    suspend fun getPagingLocalFeeds(): Flow<PagingData<MomentModel>>
}

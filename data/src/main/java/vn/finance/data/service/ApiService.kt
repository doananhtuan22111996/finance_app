package vn.finance.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import vn.finance.data.model.ItemRaw
import vn.finance.data.model.ListResponse
import vn.finance.data.model.ObjectResponse
import vn.finance.data.model.TokenRaw

interface ApiService {

    @POST("/login")
    suspend fun login(): Response<ObjectResponse<TokenRaw>>

    @POST("/logout")
    suspend fun logout(): Response<ObjectResponse<Nothing>>

    @GET("/paging-page")
    suspend fun getPaging(@Query("page") page: Int = 1): Response<ListResponse<ItemRaw>>
}

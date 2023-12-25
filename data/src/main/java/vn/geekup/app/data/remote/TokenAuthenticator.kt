package vn.geekup.app.data.remote

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.use
import timber.log.Timber
import vn.geekup.app.data.Config
import vn.geekup.app.data.Config.ErrorCode.CODE_200
import vn.geekup.app.data.Config.ErrorCode.CODE_401
import vn.geekup.app.data.local.PreferenceWrapper
import vn.geekup.app.data.model.general.ObjectResponse
import vn.geekup.app.data.model.user.OTableVO

class TokenAuthenticator(
    private val preferenceWrapper: PreferenceWrapper
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == CODE_401) {
            val refreshToken =
                preferenceWrapper.getString(Config.SharePreference.KEY_AUTH_REFRESH_TOKEN, "")
            try {
                refreshToken(refreshToken)
            } catch (e: Exception) {
                Timber.e("Refresh Token Somethings Wrong: ${e.message}")
                return null
            }
        }
        val newToken = preferenceWrapper.getString(Config.SharePreference.KEY_AUTH_TOKEN, "")
        val builder = response.request.newBuilder()
        builder.addHeader("Authorization", "Bearer $newToken")
        return builder.build()
    }

    private fun refreshToken(refreshToken: String) {
        val request = Request.Builder()
            .url("${Config.mainDomain}auth/refresh")
//            .header("Authorizationrefresh", "Bearer $refreshToken")
            .post(refreshToken.toRequestBody())
            .build()
        OkHttpClient().newBuilder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
            .newCall(request).execute().use { response ->
                if (response.isSuccessful && response.code == CODE_200) {
                    val objResponse =
                        Gson().fromJson(response.body.string(), ObjectResponse::class.java)
                    val oTableVO = Gson().fromJson(
                        Gson().toJson(objResponse.data), OTableVO::class.java
                    ) ?: null
                    Timber.e("Refresh Token Success: ${oTableVO?.refreshToken}")
                    preferenceWrapper.saveString(
                        Config.SharePreference.KEY_AUTH_TOKEN,
                        oTableVO?.token ?: ""
                    )
//                    preferenceWrapper.saveString(
//                        Config.SharePreference.KEY_AUTH_REFRESH_TOKEN,
//                        oTableVO?.refreshToken ?: ""
//                    )
                } else {
                    Timber.e("Refresh Token Failure: ${response.message}")
                }
            }
    }
}
package vn.geekup.data.network

import com.google.gson.Gson
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import okio.use
import timber.log.Timber
import vn.geekup.data.Config
import vn.geekup.data.Config.ErrorCode.CODE_200
import vn.geekup.data.Config.ErrorCode.CODE_401
import vn.geekup.data.local.PreferenceWrapper
import vn.geekup.data.model.ObjectResponse
import vn.geekup.data.model.TokenRaw

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
        val request = Request.Builder().url("${Config.mainDomain}/refreshToken")
            .post(refreshToken.toRequestBody()).build()
        OkHttpClient().newBuilder().build().newCall(request).execute().use { response ->
            if (response.isSuccessful && response.code == CODE_200) {
                val objResponse =
                    Gson().fromJson(response.body.string(), ObjectResponse::class.java)
                val tokenRaw = Gson().fromJson(
                    Gson().toJson(objResponse.data), TokenRaw::class.java
                ) ?: null
                Timber.e("Refresh Token Success: ${tokenRaw?.refreshToken}")
                preferenceWrapper.saveString(
                    Config.SharePreference.KEY_AUTH_TOKEN, tokenRaw?.token ?: ""
                )
                preferenceWrapper.saveString(
                    Config.SharePreference.KEY_AUTH_REFRESH_TOKEN, tokenRaw?.refreshToken ?: ""
                )
            } else {
                Timber.e("Refresh Token Failure: ${response.message}")
            }
        }
    }
}
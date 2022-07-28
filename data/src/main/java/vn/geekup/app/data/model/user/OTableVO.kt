package vn.geekup.app.data.model.user

import com.google.gson.annotations.SerializedName
import vn.geekup.app.data.model.general.BaseVO
import vn.geekup.app.domain.model.user.OTableModel

data class OTableVO(
    @SerializedName("access_token")
    val token: String? = "",
    @SerializedName("refresh_token")
    val refreshToken: String? = ""
) : BaseVO<OTableModel>() {

    override fun vo2Model(): OTableModel = OTableModel(
        token = token,
        refreshToken = refreshToken
    )
}
package vn.geekup.app.data.model.user

import vn.geekup.app.data.model.general.BaseVO
import vn.geekup.app.domain.model.user.OTableModel

data class OTableVO(
    val token: String? = "",
    val refreshToken: String? = ""
) : BaseVO<OTableModel>() {

    override fun vo2Model(): OTableModel = OTableModel(
        token = token,
        refreshToken = refreshToken
    )
}
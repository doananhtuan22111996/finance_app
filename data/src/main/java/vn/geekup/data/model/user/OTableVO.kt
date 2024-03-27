package vn.geekup.data.model.user

import vn.geekup.data.model.general.BaseVO
import vn.geekup.domain.model.user.OTableModel

data class OTableVO(
    val token: String? = "",
    val refreshToken: String? = ""
) : BaseVO<OTableModel>() {

    override fun vo2Model(): OTableModel = OTableModel(
        token = token,
        refreshToken = refreshToken
    )
}
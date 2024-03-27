package vn.geekup.domain.model.user

import vn.geekup.domain.model.general.BaseModel

data class OTableModel(
    val token: String? = "",
    val refreshToken: String? = ""
) : BaseModel()
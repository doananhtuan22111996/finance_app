package vn.finance.data.model

import androidx.room.Entity
import vn.finance.domain.model.BaseModel
import vn.finance.domain.model.TokenModel

@Entity
data class TokenRaw(
    val token: String? = "", val refreshToken: String? = ""
) : BaseRaw() {

    override fun raw2Model(): BaseModel = TokenModel(
        token = token, refreshToken = refreshToken
    )
}
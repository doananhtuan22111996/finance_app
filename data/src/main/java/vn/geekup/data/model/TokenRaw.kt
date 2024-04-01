package vn.geekup.data.model

import androidx.room.Entity
import vn.geekup.domain.model.BaseModel
import vn.geekup.domain.model.TokenModel

@Entity
data class TokenRaw(
    val token: String? = "", val refreshToken: String? = ""
) : BaseRaw() {

    override fun raw2Model(): BaseModel = TokenModel(
        token = token, refreshToken = refreshToken
    )
}
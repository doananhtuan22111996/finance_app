package vn.geekup.domain.model

data class TokenModel(
    val token: String? = "", val refreshToken: String? = ""
) : BaseModel()
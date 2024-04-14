package vn.finance.data.model

data class MetaData(
    val status: Boolean? = false,
    val message: String? = "",
    val limit: Int? = 0,
    val page: Int? = 0,
    val total: Int? = 0,
)

data class ObjectResponse<Raw>(
    val data: Raw? = null,
    val metadata: MetaData? = MetaData(),
)

data class ListResponse<Raw>(
    var data: List<Raw>? = listOf(),
    val metadata: MetaData? = MetaData(),
)

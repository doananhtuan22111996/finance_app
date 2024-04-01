package vn.geekup.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.geekup.domain.model.BaseModel

data class ItemModel(
    val id: String,
    val name: String,
) : BaseModel()

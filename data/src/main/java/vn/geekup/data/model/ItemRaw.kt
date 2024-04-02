package vn.geekup.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.geekup.domain.model.BaseModel
import vn.geekup.domain.model.ItemModel

@Entity
data class ItemRaw(
    @PrimaryKey val id: Int = 0,
    val name: String? = "",
) : BaseRaw() {

    override fun raw2Model(): BaseModel {
        return ItemModel(
            id = id,
            name = name ?: "",
        )
    }
}

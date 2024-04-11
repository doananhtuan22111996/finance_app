package vn.geekup.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import vn.geekup.data.model.ItemRaw

@Dao
interface ItemDao : BaseDao<ItemRaw> {

    @Query("SELECT * FROM ItemRaw ORDER BY id ASC")
    fun getItems(): List<ItemRaw>

    @Query("SELECT * FROM ItemRaw ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getPagingItems(limit: Int?, offset: Int?): List<ItemRaw>

    @Query("DELETE FROM ItemRaw")
    suspend fun delete()

}
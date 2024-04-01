package vn.geekup.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import vn.geekup.data.model.ItemRaw

@Dao
interface ItemDao : BaseDao<ItemRaw> {

    @Query("SELECT * FROM ItemRaw")
    fun getItems(): List<ItemRaw>

    @Query("SELECT * FROM ItemRaw ORDER BY `id` DESC")
    fun getPagingItems(): PagingSource<Int, ItemRaw>

    @Query("DELETE FROM ItemRaw")
    suspend fun delete()

}
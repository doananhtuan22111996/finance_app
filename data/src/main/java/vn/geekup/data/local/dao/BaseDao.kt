package vn.geekup.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import vn.geekup.data.model.BaseRaw

@Dao
interface BaseDao<Raw : BaseRaw> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(obj: List<Raw>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: Raw)
}

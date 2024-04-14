package vn.finance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import vn.finance.data.model.BaseRaw

@Dao
interface BaseDao<Raw : BaseRaw> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(obj: List<Raw>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: Raw)
}

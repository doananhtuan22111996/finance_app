package vn.finance.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vn.finance.data.local.dao.ItemDao
import vn.finance.data.model.ItemRaw

@Database(
    entities = [ItemRaw::class], version = AppDatabase.DB_VERSION, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "finance_app.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: build(context).also { instance = it }
        }

        private fun build(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                .build()
// This is migrate function
//                .addMigrations(MIGRATION_1_TO_2).build()
//        private val MIGRATION_1_TO_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//            }
//        }
    }

    abstract fun itemDao(): ItemDao
}
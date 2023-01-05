package ba.etf.rma21.projekat.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ba.etf.rma21.projekat.data.dao.*
import ba.etf.rma21.projekat.data.models.*


@Database(entities = arrayOf(Grupa::class, Predmet::class, Kviz::class, Pitanje::class, Account::class, Odgovor::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun kvizDao() : KvizDao
    abstract fun grupaDao() : GrupaDao
    abstract fun predmetDao() : PredmetDao
    abstract fun pitanjeDao() : PitanjeDao
    abstract fun accountDao() : AccountDao
    abstract fun odgovorDao() : OdgovorDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }

        fun setInstance(appdb: AppDatabase):Unit{
            INSTANCE =appdb
        }


        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "projekat-db"
            ).build()
    }
}
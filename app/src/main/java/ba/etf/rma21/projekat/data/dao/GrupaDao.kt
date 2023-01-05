package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa


@Dao
interface GrupaDao {
    @Query("DELETE FROM GRUPA")
    suspend fun deleteAll()

    @Query("SELECT * FROM GRUPA")
    suspend fun getAll() : List<Grupa>


    @Insert
    suspend fun addAll(groups : List<Grupa>)

}
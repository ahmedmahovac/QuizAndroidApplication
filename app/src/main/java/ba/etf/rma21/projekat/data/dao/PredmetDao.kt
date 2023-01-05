package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet

@Dao
interface PredmetDao {
    @Query("DELETE FROM PREDMET")
    suspend fun deleteAll()

    @Query("SELECT * FROM PREDMET")
    suspend fun getAll() : List<Predmet>


    @Insert
    suspend fun addAll(predmeti : List<Predmet>)
}
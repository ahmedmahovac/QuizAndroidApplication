package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.Predmet

@Dao
interface PitanjeDao {
    @Query("DELETE FROM PITANJE")
    suspend fun deleteAll()

    @Query("SELECT * FROM PITANJE")
    suspend fun getAll() : List<Pitanje>


    @Insert
    suspend fun addAll(pitanja : List<Pitanje>)

    @Query("SELECT * FROM PITANJE WHERE kvizId = :idKviza")
    suspend fun getPitanja(idKviza: Int): List<Pitanje>

    @Query("SELECT kvizId FROM Pitanje WHERE id=:idPitanje")
    suspend fun getKvizIdByPitanjeId(idPitanje: Int) : Int?


}
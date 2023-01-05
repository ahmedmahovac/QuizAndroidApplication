package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz

@Dao
interface KvizDao {
    @Query("DELETE FROM KVIZ")
    suspend fun deleteAll()

    @Query("SELECT * FROM KVIZ")
    suspend fun getAll() : List<Kviz>


    @Insert
    suspend fun addAll(kvizovi : List<Kviz>)

    @Query("UPDATE Kviz SET idKvizTaken = :idKvizTaken WHERE id = :idKviz")
    abstract fun dodajKvizTakenUKviz(idKviz: Int, idKvizTaken: Int)

    @Query("SELECT id FROM Kviz WHERE idKvizTaken = :idKvizTaken")
    fun getKvizIdByKvizTakenIdDB(idKvizTaken: Int) : Int

    @Query("SELECT idKvizTaken FROM Kviz WHERE id = :idKviz")
    suspend fun getKvizTakenIdByKvizIdDB(idKviz: Int): Int

    @Query("SELECT * FROM KVIZ WHERE id = :idKviz")
    suspend fun getKviz(idKviz: Int) : Kviz

    @Query("UPDATE KVIZ SET predan=1 WHERE id = :id")
    fun setPredan(id : Int)
}
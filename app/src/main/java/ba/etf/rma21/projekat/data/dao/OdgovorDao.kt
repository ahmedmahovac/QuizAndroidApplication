package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Odgovor


@Dao
interface OdgovorDao
{
    @Insert
    fun dodajOdgovor(odgovor: Odgovor)

    @Query("DELETE FROM ODGOVOR")
    fun deleteAll()

    @Query("SELECT * FROM ODGOVOR WHERE kvizId = :idKviz")
    abstract fun getOdgovori(idKviz: Int): List<Odgovor>

    @Query("SELECT * FROM ODGOVOR WHERE kvizId = :idKviz AND pitanjeId = :idPitanje")
    fun getOdgovor(idKviz: Int, idPitanje: Int): Odgovor

    @Query("SELECT * FROM ODGOVOR")
    fun getAll() : List<Odgovor>


}
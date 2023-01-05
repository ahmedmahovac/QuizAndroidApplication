package ba.etf.rma21.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Odgovor (@PrimaryKey(autoGenerate = true)  val id : Int?, val odgovoreno : Int, val pitanjeId: Int, val kvizId : Int) {
    override fun toString(): String {
        return "Odgovor(id=$id, odgovoreno=$odgovoreno, pitanjeId=$pitanjeId, kvizId=$kvizId)"
    }
}
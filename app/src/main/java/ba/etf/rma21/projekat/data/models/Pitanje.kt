package ba.etf.rma21.projekat.data.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(primaryKeys = arrayOf("id","kvizId"))
data class Pitanje (
    val id : Int,
    val naziv: String,
    val tekstPitanja: String,
    val opcije: String,
    val tacan: Int,
    var odgovoreno: Boolean = false,
    var odgovor : Int = -1,
    val kvizId : Int
)
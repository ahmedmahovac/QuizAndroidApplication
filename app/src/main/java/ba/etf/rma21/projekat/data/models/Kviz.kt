package ba.etf.rma21.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*



@Entity
data class Kviz(@PrimaryKey val id : Int,
                val naziv: String, val nazivPredmeta: String, val datumPocetka: String, val datumKraj: String,
                var datumRada: String?, val trajanje: Int, val nazivGrupe: String, var osvojeniBodovi: Float?, var predan : Boolean = false, var zaustavljen: Boolean = true, var idKvizTaken : Int? = null)
{
    override fun toString(): String {
        return "Kviz(id=$id, naziv='$naziv', nazivPredmeta='$nazivPredmeta', nazivGrupe='$nazivGrupe')"
    }
}
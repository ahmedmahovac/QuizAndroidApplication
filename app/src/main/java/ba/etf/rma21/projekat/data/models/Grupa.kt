package ba.etf.rma21.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Grupa(val naziv: String, val nazivPredmeta: String, @PrimaryKey val id: Int, val predmetId : Int) {


    override fun toString(): String {
        return naziv
    }
}
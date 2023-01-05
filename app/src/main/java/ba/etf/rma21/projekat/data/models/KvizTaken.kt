package ba.etf.rma21.projekat.data.models

import java.util.*

class KvizTaken(val id : Int, private val student : String, val osvojeniBodovi : Int, val datumRada : String, val KvizId : Int) {
    override fun toString(): String {
        return "KvizTaken(kvizId=$id, studentHash='$student', osvojeniBodovi=$osvojeniBodovi, datumRada=$datumRada)"
    }
}
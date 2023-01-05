package ba.etf.rma21.projekat.data.repositories

import android.accounts.Account
import android.util.Log
import ba.etf.rma21.projekat.data.models.KvizTaken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors

class TakeKvizRepository {
    companion object {
        val formatterDB = SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss")

 // otvarat cemo samo jedan pokusaj za isti kviz
        suspend fun zapocniKviz(idKviza:Int): KvizTaken? {
            var pokusajiDosad = getPocetiKvizovi()
            if(pokusajiDosad == null) {
                pokusajiDosad = listOf()
            }
            val pokusaj = pokusajiDosad.stream().filter{ t->t.KvizId==idKviza}.findAny()
            if(pokusaj?.isPresent!!) return pokusaj.get()
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val url = URL(ApiConfig.baseURL + "/student/${AccountRepository.getHash()}/kviz/${idKviza}")
            val konekcija = url.openConnection() as HttpURLConnection
            konekcija.requestMethod = "POST"
            val data = konekcija.inputStream.bufferedReader().readText() // ovo ce procitat ako je doslo do greske, za sad cu ovo zanemarit
//            Log.d("pracenjeKviz", data + "  dsadsad ${idKviza}")
        //if(data.contains("nije upisan u grupu za kviz") || data.contains("not found")) return null
            val obj = JSONObject(data)
            if(!obj.isNull("id") && !obj.isNull("student") && !obj.isNull("osvojeniBodovi") && !obj.isNull("datumRada"))
            return KvizTaken(obj.getInt("id"),AccountRepository.getHash(), obj.getInt("osvojeniBodovi"), formatterDB.format(formatter.parse(obj.getString("datumRada"))),idKviza)
            else return null
        }


        suspend fun getPocetiKvizovi(): List<KvizTaken>? {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val url = URL(ApiConfig.baseURL + "/student/${AccountRepository.getHash()}/kviztaken")
            val konekcija = url.openConnection() as HttpURLConnection
            val data = konekcija.inputStream.bufferedReader().readText()
        //    Log.d("pracenjeFinalno", data)
            if(data.contains("message")) return null // za nepostojeceg studenta
            val array = JSONArray(data)
            var pocetiKvizovi = mutableListOf<KvizTaken>()
            for(i in 1..array.length()) {
                val obj = array.getJSONObject(i-1)
//                Log.d("pracenjePokusaji", obj.toString())
                pocetiKvizovi.add(KvizTaken(obj.getInt("id"),obj.getString("student"),obj.getInt("osvojeniBodovi"),
                    formatterDB.format(formatter.parse(obj.getString("datumRada"))),obj.getInt("KvizId")))
            }
            if(pocetiKvizovi.size != 0) return pocetiKvizovi
            return null
        }


        suspend fun getPocetiKvizZaId(kvizId : Int):KvizTaken? {
            var poceti = getPocetiKvizovi()
            if(poceti == null) {
                poceti = listOf()
            }
            val pokusaj = poceti.stream().filter{t->t.KvizId == kvizId}.findAny()
            if(pokusaj.isPresent) return pokusaj.get()
            else return null
        }


        suspend fun getPokusajById(id : Int) : KvizTaken? {
            var poceti = getPocetiKvizovi()
            if(poceti == null) {
                poceti = listOf()
            }
            val pokusaj = poceti.stream().filter{t -> t.id == id}.findFirst()
            if(pokusaj.isPresent) return pokusaj.get()
            return null
        }


   }
}
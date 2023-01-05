package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors

class KvizRepository {

    companion object {


        val formatterDB = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }


       suspend fun getAll(): List<Kviz> {
            val url = URL(ApiConfig.baseURL + "/kviz")
            val konekcija = url.openConnection() as HttpURLConnection
            val data = konekcija.inputStream.bufferedReader().readText()
            val array = JSONArray(data)
            var kvizovi = mutableListOf<Kviz>()
            for(i in 1..array.length()) {
//                Log.d("pracenjePravo","uso ${i}")
                val obj = array.getJSONObject(i-1)
                kvizovi.add(getKvizFromJSONObject(obj))
            }

            return kvizovi
        }



        suspend fun getDone(): List<Kviz> {
            return getUpisani().stream().filter { t ->  t.predan == true}.collect(Collectors.toList()) // sta ako je zaustavljen
        }

       suspend fun getFuture(): List<Kviz> {
            val currentTime = Calendar.getInstance().time
            return getUpisani().stream().filter { t ->  formatterDB.parse(t.datumPocetka).after(currentTime)}.collect(Collectors.toList())
        }

        suspend fun getNotTaken(): List<Kviz> {
            val currentTime = Calendar.getInstance().time
            return getUpisani().stream().filter {t -> currentTime.after(formatterDB.parse(t.datumKraj)) && t.predan == false}.collect(Collectors.toList())
        }






        suspend fun getUpisani():List<Kviz> {
            val upisaneGrupe = GrupaRepository.getUpisaneGrupe()
            var upisaniKvizovi = mutableListOf<Kviz>()
            for(i in 1..upisaneGrupe.size) {
                upisaniKvizovi.addAll(getKvizoviZaGrupu(upisaneGrupe[i-1].id))
            }
            return upisaniKvizovi
        }

        suspend fun getKvizoviZaGrupu(id: Int): List<Kviz> {
            val url = URL(ApiConfig.baseURL + "/grupa/${id}/kvizovi")
            val konekcija = url.openConnection() as HttpURLConnection
            val data = konekcija.inputStream.bufferedReader().readText()
            var upisaniKvizovi = mutableListOf<Kviz>()
            if(data.contains("message")) return upisaniKvizovi
            val array = JSONArray(data)
            for(i in 1..array.length()) {
                val obj = array.getJSONObject(i-1)
                upisaniKvizovi.add(getKvizFromJSONObject(obj))
            }
            return upisaniKvizovi
        }


        suspend fun getById(id:Int):Kviz? {
            val url = URL(ApiConfig.baseURL + "/kviz/${id}")
            val konekcija = url.openConnection() as HttpURLConnection
            val data = konekcija.inputStream.bufferedReader().readText()
            val obj = JSONObject(data)
           if(obj.has("message") && !obj.isNull("message") && obj.getString("message").equals("Kviz not found.")) return null
           return getKvizFromJSONObject(obj)
        }



        suspend fun getKvizFromJSONObject(obj : JSONObject) : Kviz {
            val formatter1 = SimpleDateFormat("dd.MM.yyyy")
            val naziv = obj.getString("naziv")
            val trajanje = obj.getInt("trajanje")
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val datumPocetka = formatter.parse(obj.getString("datumPocetak"))
            var datumKraja : String = "2055-05-05T12:00:00"
            if(!obj.isNull("datumKraj")) {
                datumKraja = formatterDB.format(formatter.parse(obj.getString("datumKraj")))
            }

            // ovdje treba pozvat drugu metodu koja ce preko servisa provjerit dal je ovaj kviz radjen
            // i shodno tome dodijelit ove za sada fiktivno postavljene atribute

            val grupa = GrupaRepository.getGrupaByKvizId(obj.getInt("id"))!!
            val predmet = PredmetRepository.getPredmetById(grupa.predmetId)


            var datumRada : String? = null
            var osvojeniBodovi : Float = 0F

            var pokusanBarJednom = false
            var predan = false
            val pokusaj = TakeKvizRepository.getPocetiKvizZaId(obj.getInt("id"))



            if(pokusaj != null) {
                pokusanBarJednom = true
                val odgovori = OdgovorRepository.getOdgovoriKviz(obj.getInt("id"))
                val pitanja = PitanjeKvizRepository.getPitanja(obj.getInt("id"))
                if(odgovori.size == pitanja.size) predan = true // najjednostavnije. Ako smo predali prije odgovaranja
                // na sva pitanja onda ce bit dati svi odgovori kao i da smo
                osvojeniBodovi = pokusaj.osvojeniBodovi.toFloat()
            }


            if(predan && pokusaj != null) {
                datumRada = pokusaj.datumRada
            }
// mozda nece dobro upisat sa toString
            return Kviz(obj.getInt("id"),naziv,predmet?.naziv!!,
                formatterDB.format(datumPocetka), datumKraja,datumRada,trajanje, grupa.naziv, osvojeniBodovi,predan,!predan && pokusanBarJednom)
        }

        fun getKvizoviZaGrupuSamoId(id: Int): List<Int> {
            val url = URL(ApiConfig.baseURL + "/grupa/${id}/kvizovi")
            val konekcija = url.openConnection() as HttpURLConnection
            val data = konekcija.inputStream.bufferedReader().readText()
            var upisaniKvizoviIds = mutableListOf<Int>()
            val array = JSONArray(data)
            for(i in 1..array.length()) {
                val obj = array.getJSONObject(i-1)
                upisaniKvizoviIds.add(obj.getInt("id"))
            }
            return upisaniKvizoviIds
        }



     //   suspend fun getKvizByKvizTaken(kvizTakenId : Int) : Kviz {
     //        val kvizId = TakeKvizRepository.getPocetiKvizovi().stream().filter { t->t.id == kvizTakenId }.findFirst().get().KvizId
     //        return getById(kvizId)!!
     //   }

        suspend fun getUpisaniDB() : List<Kviz> {
            // kad god trebam nesto dobavit iz baze provjerim da li baza raspolaze sa validnim informacijama
            DBRepository.updateNow()
            val db = AppDatabase.getInstance(context)
            return db.kvizDao().getAll()
        }


        suspend fun getDoneDB() : List<Kviz> {
            return getUpisaniDB().stream().filter { t ->  t.predan == true}.collect(Collectors.toList())
        }


        suspend fun getFutureDB(): List<Kviz> {
            val currentTime = Calendar.getInstance().time
            return getUpisaniDB().stream().filter { t ->  formatterDB.parse(t.datumPocetka).after(currentTime)}.collect(Collectors.toList())
        }

        suspend fun getNotTakenDB(): List<Kviz> {
            val currentTime = Calendar.getInstance().time
            return getUpisaniDB().stream().filter {t -> currentTime.after(formatterDB.parse(t.datumKraj)) && t.predan == false}.collect(Collectors.toList())
        }








        suspend fun setPredan(id: Int) {
            AppDatabase.getInstance(context).kvizDao().setPredan(id)
        }


    }
}
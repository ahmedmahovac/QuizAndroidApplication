package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Grupa
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class GrupaRepository {
    companion object {




        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }





     //   fun getGroupsUpisanZaPredmet(nazivPredmeta: String): List<Grupa> {
      //      return groupsUpisan.stream().filter{ t -> t.nazivPredmeta==nazivPredmeta}.collect(Collectors.toList())
      //  }


       suspend fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
            val id = PredmetRepository.getIdPredmetByNaziv(nazivPredmeta)
            if(id == null) return listOf<Grupa>()
            return PredmetIGrupaRepository.getGrupeZaPredmet(id!!).stream().filter{ t -> t.nazivPredmeta==nazivPredmeta}.collect(Collectors.toList())
        }




        suspend fun getAllGroupsJSON() : JSONArray {
            val url = URL(ApiConfig.baseURL + "/grupa")
            val konekcija = url.openConnection() as HttpURLConnection
            val data = konekcija.inputStream.bufferedReader().readText()
            val array = JSONArray(data)
            return array
        }



        suspend fun getAllGroups() : List<Grupa> {
            val array = getAllGroupsJSON()
            var sveGrupe = mutableListOf<Grupa>()
            for(i in 1..array.length()) {
                val obj = array.getJSONObject(i-1)
                sveGrupe.add(getGroupForJSONObject(obj))
            }
            return sveGrupe
        }

        suspend fun getGroupForJSONObject(obj: JSONObject): Grupa {
            return Grupa(obj.getString("naziv"),PredmetRepository.getPredmetById(obj.getInt("PredmetId"))!!.naziv, obj.getInt("id"), obj.getInt("PredmetId"))
        }

        suspend fun getGrupaByName(group: String): Grupa {
            return getAllGroups().stream().filter{t->t.naziv.equals(group)}.findFirst().get()
        }

       suspend fun getUpisaneGrupe() : List<Grupa> {
            val url = URL(ApiConfig.baseURL + "/student/${AccountRepository.getHash()}/grupa")
            val konekcija = url.openConnection() as HttpURLConnection
            val data = konekcija.inputStream.bufferedReader().readText()
            val array = JSONArray(data)
            var upisaneGrupe = mutableListOf<Grupa>()
            for(i in 1..array.length()) {
                val obj = array.getJSONObject(i-1)
                upisaneGrupe.add(getGroupForJSONObject(obj))
            }
           return upisaneGrupe
        }



        suspend fun getUpisaneGrupeDB() : List<Grupa> {
            return AppDatabase.getInstance(context).grupaDao().getAll()
        }




        suspend fun getGroupById(id: Int): Grupa? {
            val url = URL(ApiConfig.baseURL + "/grupa/${id}")
            val konekcija = url.openConnection() as HttpURLConnection
            val data = konekcija.inputStream.bufferedReader().readText()
            val obj = JSONObject(data)
            if(obj.getString("message") != null && obj.getString("message").equals("Grupa not found.")) return null
            return getGroupForJSONObject(obj)
        }

       suspend fun getUpisaneGrupePredmetiId() : List<Int> {
            val ids =  getUpisaneGrupe().stream().map{t -> t.predmetId}.collect(Collectors.toList())
            return ids
        }

       suspend fun getGrupaByKvizId(id: Int) : Grupa? {
            val sveGrupe = getAllGroups()
            for(i in 1..sveGrupe.size) {
                if(KvizRepository.getKvizoviZaGrupuSamoId(sveGrupe[i-1].id).stream().filter{t->t == id}.findAny().isPresent) return sveGrupe[i-1]
            }
           return null
        }


        // odvoji u posebnu funkciju ovo dobijanje json arraya
    }
}
package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class PredmetIGrupaRepository {
    companion object{

        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }


       suspend fun getPredmeti():List<Predmet> {
            return PredmetRepository.getAll()
        }


        suspend fun getGrupe():List<Grupa> {
            return GrupaRepository.getAllGroups()
        }

        suspend fun getGrupeZaPredmet(idPredmeta:Int):List<Grupa> {
            val array = GrupaRepository.getAllGroupsJSON()
            var sveGrupeZaPredmet = mutableListOf<Grupa>()
            for(i in 1..array.length()) {
                val obj = array.getJSONObject(i-1)
               if(obj.getInt("PredmetId")==idPredmeta) {
                   sveGrupeZaPredmet.add(GrupaRepository.getGroupForJSONObject(obj))
               }
            }
            return sveGrupeZaPredmet
        }



        suspend fun getUpisaneGrupeAPI():List<Grupa> {
            return GrupaRepository.getUpisaneGrupe()
        }



        suspend fun upisiUGrupu(idGrupa:Int):Boolean {
            val url = URL(ApiConfig.baseURL + "/grupa/${idGrupa}/student/${AccountRepository.getHash()}")
            val konekcija = url.openConnection() as HttpURLConnection
            konekcija.requestMethod = "POST"
            val data = konekcija.inputStream.bufferedReader().readText()
            if(data.contains("Ne postoji") || data.contains("not found")) return false
            return true
        }





    }
}
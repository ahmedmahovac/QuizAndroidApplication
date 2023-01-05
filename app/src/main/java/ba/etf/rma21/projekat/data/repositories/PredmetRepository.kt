package ba.etf.rma21.projekat.data.repositories

import android.util.Log
import ba.etf.rma21.projekat.data.models.Predmet
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class PredmetRepository {
    companion object {









        suspend fun getAllJSON() : JSONArray {
            val url = URL(ApiConfig.baseURL + "/predmet")
            val konekcija = url.openConnection() as HttpURLConnection
            val data = konekcija.inputStream.bufferedReader().readText()
            val array = JSONArray(data)
            return array
        }


        suspend fun getAll(): List<Predmet> {
            val array = getAllJSON()
            var predmeti = mutableListOf<Predmet>()
            for(i in 1..array.length()) {
                val obj = array.getJSONObject(i-1)
                predmeti.add(Predmet(obj.getString("naziv"),obj.getInt("godina"), obj.getInt("id")))
            }
            return predmeti
        }




      suspend  fun getNeupisaniPredmetiZaGodina(godina : Int) : List<Predmet> {
          return getNeupisaniPredmeti().stream().filter{t->t.godina==godina}.collect(Collectors.toList())
        }



        suspend fun getNeupisaniPredmeti() : List<Predmet> {
            val sviPredmetiJSON = getAllJSON()
            val upisaneGrupePredmetiIds = GrupaRepository.getUpisaneGrupePredmetiId()
            var sviNeupisaniPredmeti = mutableListOf<Predmet>()
            for(i in 1..sviPredmetiJSON.length()) {
                val obj = sviPredmetiJSON.getJSONObject(i-1)
                if(!upisaneGrupePredmetiIds.contains(obj.getInt("id"))) sviNeupisaniPredmeti.add(Predmet(obj.getString("naziv"),obj.getInt("godina"), obj.getInt("id")))
            }
            return sviNeupisaniPredmeti
        }




        suspend fun getPredmetById(id : Int) : Predmet? {
            val url = URL(ApiConfig.baseURL + "/predmet/${id}")
            val konekcija = url.openConnection() as HttpURLConnection
            val data = konekcija.inputStream.bufferedReader().readText()
            val obj = JSONObject(data)
            if(obj.has("message") && obj.getString("message").equals("Predmet not found.")) return null
            return Predmet(obj.getString("naziv"),obj.getInt("godina"), obj.getInt("id"))
        }


        suspend fun getIdPredmetByNaziv(naziv : String) : Int?{
            val array = getAllJSON()
            for(i in 1..array.length()) {
                val obj = array.getJSONObject(i-1)
                if(obj.getString("naziv").equals(naziv)){
               //     Log.d("pracenjePravo", "parsiranje" + obj.getInt("id"))
                    return obj.getInt("id")
                }
            }
            return null
        }

       suspend fun getUpisaniPredmeti(): List<Predmet> {
            val sviPredmetiJSON = getAllJSON()
            val upisaneGrupePredmetiIds = GrupaRepository.getUpisaneGrupePredmetiId()
            var sviUpisaniPredmeti = mutableListOf<Predmet>()
            for(i in 1..sviPredmetiJSON.length()) {
                val obj = sviPredmetiJSON.getJSONObject(i-1)
                if(upisaneGrupePredmetiIds.contains(obj.getInt("id"))) sviUpisaniPredmeti.add(Predmet(obj.getString("naziv"),obj.getInt("godina"), obj.getInt("id")))
            }
            return sviUpisaniPredmeti
        }


    }

}
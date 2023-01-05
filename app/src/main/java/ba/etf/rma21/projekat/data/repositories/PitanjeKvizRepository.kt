package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.PitanjeKviz
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class PitanjeKvizRepository {


    companion object {


        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }

        suspend fun getPitanja(idKviza:Int):List<Pitanje> {
            val url = URL(ApiConfig.baseURL + "/kviz/${idKviza}/pitanja")
            val konekcija = url.openConnection() as HttpURLConnection
            val data = konekcija.inputStream.bufferedReader().readText()
            val array = JSONArray(data)
            var pitanja = mutableListOf<Pitanje>()
            for(i in 1..array.length()) {
                val obj = array.getJSONObject(i-1)
                val opcije = obj.getJSONArray("opcije")
                var pitanjeOpcije = String()
                for(i in 1..opcije.length()) {
                    pitanjeOpcije = pitanjeOpcije + (opcije.getString(i-1))
                    if(i != opcije.length()) pitanjeOpcije = pitanjeOpcije + ","
                }

                val naziv = obj.getString("naziv")
                val tekst = obj.getString("tekstPitanja")
                val tacan = obj.getInt("tacan")
                pitanja.add(Pitanje(obj.getInt("id"),naziv,tekst,pitanjeOpcije,tacan, false, -1, idKviza))
            }

            val odgovori = OdgovorRepository.getOdgovoriKviz(idKviza)
            for(i in 1..odgovori.size) {
                for(j in 1..pitanja.size) {
                    if(pitanja[j-1].id == odgovori[i-1].pitanjeId && odgovori[i-1].odgovoreno != -1) {
                        pitanja[j-1].odgovoreno = true
                        pitanja[j-1].odgovor = odgovori[i-1].odgovoreno
                    }
                }
            }
            return pitanja
        }


        suspend fun getPitanjaDB(idKviza : Int) : List<Pitanje> {
            return AppDatabase.getInstance(context).pitanjeDao().getPitanja(idKviza)
        }




    }
}
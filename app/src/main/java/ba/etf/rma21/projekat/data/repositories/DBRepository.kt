package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma21.projekat.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class DBRepository {

    companion object {
        private lateinit var context: Context
        fun setContext(_context:Context){
            context=_context
        }

        val formatterDB = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        suspend fun updateNow(): Boolean {
            Log.d("pracenjeOvoOno", "uso " + AppDatabase.getInstance(context).accountDao().getLastUpdate() + " "+ AppDatabase.getInstance(context).accountDao().getCount())
            val url = URL(
                "https://rma21-etf.herokuapp.com/account/${AccountRepository.getHash()}/lastUpdate?date=${
                    AppDatabase.getInstance(context).accountDao().getLastUpdate()
                }"
            )
            val konekcija = url.openConnection() as HttpURLConnection
            val data = konekcija.inputStream.bufferedReader().readText()
            val obj = JSONObject(data)
            if(obj.isNull("changed")) return false
            val changed = obj.getBoolean("changed")
            if(changed) {
              updateDB()

            }
            return changed
        }

       suspend   fun updateDB() {
            val db = AppDatabase.getInstance(context)

            Log.d("pracenjeOvoOno", "uslo u if")
            AccountRepository.deleteDBs()

            val upisani = PredmetRepository.getUpisaniPredmeti()
            db.predmetDao().addAll(PredmetRepository.getUpisaniPredmeti())
            db.grupaDao().addAll(PredmetIGrupaRepository.getUpisaneGrupeAPI())
            val upisaniKvizovi = KvizRepository.getUpisani()
            db.kvizDao().addAll(upisaniKvizovi)



            for(i in 1..upisaniKvizovi.size) {
                db.pitanjeDao().addAll(PitanjeKvizRepository.getPitanja(upisaniKvizovi[i-1].id))
            }


            db.accountDao().setLastUpdate(formatterDB.format(Calendar.getInstance().time))
           // Log.d("pracenjeOvoOno", ""  + changed + " " + AppDatabase.getInstance(context).accountDao().getLastUpdate())
            //  Log.d("promjenePracenje", "uso " + changed)
        }


        suspend fun getKvizIdByPitanjeIdDB(idPitanje: Int): Int? {
            return AppDatabase.getInstance(context).pitanjeDao().getKvizIdByPitanjeId(idPitanje)
        }



        suspend fun dodajKvizTaken(idKviz : Int, idKvizTaken : Int) {
            val db = AppDatabase.getInstance(context)
            db.kvizDao().dodajKvizTakenUKviz(idKviz, idKvizTaken)
        }

    }
}
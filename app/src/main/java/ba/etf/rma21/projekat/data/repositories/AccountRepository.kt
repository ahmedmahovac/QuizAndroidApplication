package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Account
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class AccountRepository {


    companion object {

        // necu vise u atributu cuvat nego u bazi
         var acHash: String = "aec32f1c-c193-4c7e-97b3-753109794580"

        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }


        suspend fun postaviHash(acHash: String): Boolean {
            this.acHash = acHash
            val db = AppDatabase.getInstance(context)
            db.accountDao().deleteUser()
            db.accountDao().setAccount(Account(acHash,"1982-05-05T12:00:00"))
            deleteDBs()
            db.odgovorDao().deleteAll()
            // nece uvijek bit ovo moguce mozda pa treba vratit false
            return true
        }


        suspend fun getHash(): String {
            return acHash
        //  return AppDatabase.getInstance(context).accountDao().getHash()
        }

        suspend fun obrisiPodatkeZaKorisnika() {
            val url = URL(ApiConfig.baseURL + "/student/aec32f1c-c193-4c7e-97b3-753109794580/upisugrupeipokusaji")
            val konekcija = url.openConnection() as HttpURLConnection
            konekcija.requestMethod = "DELETE"
            val data = konekcija.inputStream.bufferedReader().readText()
           // val obj = JSONObject(data)
           // Log.d("pracenjePravo", obj.getString("message"))
        }



        suspend fun deleteDBs() {
            val db = AppDatabase.getInstance(context)
            db.predmetDao().deleteAll()
            db.grupaDao().deleteAll()
            db.kvizDao().deleteAll()
            db.pitanjeDao().deleteAll()
        }

    }
}
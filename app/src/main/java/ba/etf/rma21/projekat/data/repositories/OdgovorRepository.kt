package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class OdgovorRepository {
    companion object {

        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }


        suspend fun getOdgovoriKviz(idKviza:Int):List<Odgovor> {
//            Log.d("pracenjeOdgovori", "uso")
            var odgovori = mutableListOf<Odgovor>()

            val pokusaj = TakeKvizRepository.getPocetiKvizZaId(idKviza)
          //  Log.d("pracenjeRandom", "" + pokusaj?.id)fgdfddf

            if(pokusaj != null) {
                val url =
                    URL(ApiConfig.baseURL + "/student/${AccountRepository.getHash()}/kviztaken/${pokusaj?.id}/odgovori")
                val konekcija = url.openConnection() as HttpURLConnection
                val data = konekcija.inputStream.bufferedReader().readText()
                if(data.contains("message")) return odgovori
                val array = JSONArray(data)
                for (i in 1..array.length()) {
                    val obj = array.getJSONObject(i - 1)
                    odgovori.add(
                        Odgovor(
                            0,
                            obj.getInt("odgovoreno"),
                            obj.getInt("PitanjeId"),
                            idKviza
                        )
                    ) // sta za id stavit, ne dobije se u response
                }
            }
            return odgovori
        }

       suspend fun  postaviOdgovorKvizAPI(idKvizTaken:Int,idPitanje:Int,odgovor:Int):Int {
           val url = URL(ApiConfig.baseURL + "/student/${AccountRepository.getHash()}/kviztaken/${idKvizTaken}/odgovor")
           val konekcija = url.openConnection() as HttpURLConnection
           konekcija.requestMethod = "POST"
           konekcija.setRequestProperty("Content-Type", "application/json")
           konekcija.setRequestProperty("Accept", "*/*")
           konekcija.doOutput = true
           konekcija.doInput = true

           // ovdje cemo sracunati bodove dosad na ovom kvizu + bodove koje ce potencijalno donijet ovaj odgovor
           // ovo je sve ok i u slucaju slanja fiktivnog odgovora jer ce samo proc kroz petlju
           var pokusaj = TakeKvizRepository.getPokusajById(idKvizTaken)
           var bodovi = pokusaj?.osvojeniBodovi
           val kviz = KvizRepository.getById(pokusaj?.KvizId!!)
           val pitanja = PitanjeKvizRepository.getPitanja(kviz?.id!!)
           for(i in 1..pitanja.size) {
               if(pitanja[i-1].id == idPitanje && pitanja[i-1].tacan == odgovor) bodovi = bodovi?.plus(((1.0/pitanja.size)*100)?.toInt())
           }


           val dataJSON = JSONObject()
           dataJSON.put("odgovor", odgovor)
           dataJSON.put("pitanje", idPitanje)
           dataJSON.put("bodovi", bodovi) // ovo je problem

         val os =   konekcija.getOutputStream()
           os.use({ os ->
               val input: ByteArray = dataJSON.toString().toByteArray(Charsets.UTF_8)
               os.write(input, 0, input.size)
           })

     val data = konekcija.inputStream.bufferedReader().readText()


       //    Log.d("pracenjeResponse", data)
     //   val response =  konekcija.responseCode // forsira dobijanje response-a i finaliziranje zahtjeva, izgleda da bez tog zahtjev ne radi
     //      os.close()

//           Log.d("pracenjeResponse", "" + response)

           if(JSONObject(data).isNull("odgovoreno") || JSONObject(data).isNull("KvizTakenId")) return -1
               return bodovi!!
        }


// kad otvorim pokusaj dodam u kolonu kviza kvizTakenId
//

        suspend fun  postaviOdgovorKviz(idKvizTaken:Int,idPitanje:Int,odgovor:Int):Int {
            val db = AppDatabase.getInstance(context)
            var idKviz = DBRepository.getKvizIdByPitanjeIdDB(idPitanje)
            if(idKviz == null) idKviz = -1 // ne valja da ovako razgranicim
            val vecPostoji = db.odgovorDao().getOdgovor(idKviz, idPitanje)
            if(vecPostoji == null) {
                db.odgovorDao().dodajOdgovor(Odgovor(null,odgovor,idPitanje,idKviz))
            }

            val pitanja = db.pitanjeDao().getPitanja(idKviz)
            val odgovori = db.odgovorDao().getAll()
            if(idKviz != -1) {
                odgovori.stream().filter{t -> t.kvizId==idKviz}.collect(Collectors.toList())
            }
            var bodovi = 0
            for(i in 1..pitanja.size) {
                for(j in 1..odgovori.size) {
                    if(pitanja[i-1].id == odgovori[j-1].pitanjeId && odgovori[j-1].odgovoreno == pitanja[i-1].tacan) {
                        bodovi = bodovi.plus(((1.0/pitanja.size)*100).toInt())

                    }
                }
            }
            return bodovi
        }


        suspend fun predajOdgovore(idKviz : Int) {
            val db = AppDatabase.getInstance(context)
            val kviz = db.kvizDao().getKviz(idKviz)
            val odgovori = db.odgovorDao().getOdgovori(idKviz)
            for(i in 1..odgovori.size) {
                Log.d("pracenjeOvoOno", odgovori[i-1].toString())
            }

            for(i in 1..odgovori.size) {
                postaviOdgovorKvizAPI(kviz.idKvizTaken!!,odgovori[i-1].pitanjeId,odgovori[i-1].odgovoreno)
            }
        }





    }





}
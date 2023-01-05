package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import android.util.Log
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KFunction2

class SharedViewModel private constructor(private val pokreniAkcijePokusajKviza: KFunction2<KvizTaken, List<Pitanje>, Unit>) {

    private  var izabranaGodina : Int = -1
    private  var izabraniPredmet : Int = -1
    private  var izabranaGrupa  : Int = -1


    private lateinit var trenutniKviz : Kviz



    private var backPressedInPredmetiFragment: Boolean = false


    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    companion object {
        private var instance : SharedViewModel? = null
        fun instance(pokreniAkcijePokusajKviza: KFunction2<KvizTaken, List<Pitanje>, Unit>): SharedViewModel? {
            if(instance==null) instance = SharedViewModel(pokreniAkcijePokusajKviza)
            return instance
        }

        fun instance(): SharedViewModel? {
            return instance
        }
    }


    fun sacuvajOdabirPredmeta(godina: Int, predmet : Int , grupa  : Int){
        izabranaGodina = godina
        izabraniPredmet = predmet
        izabranaGrupa = grupa
    }

    fun getOdabirGodina(): Int {
        return izabranaGodina
    }

    fun getOdabirPredmet(): Int {
        return izabraniPredmet
    }

    fun getOdabirGrupa(): Int{
        return izabranaGrupa
    }

    fun getNazivKviza(): String {
        return trenutniKviz.naziv
    }

    fun getBodovi(): Double {
        return trenutniKviz.osvojeniBodovi?.toDouble()!!
    }

    fun jeLiPredanKviz() : Boolean {
        return trenutniKviz.predan
    }

    fun jeLiZaustavljenKviz() : Boolean {
        return trenutniKviz.zaustavljen
    }


    fun postaviTrenutniKviz(kviz: Kviz) {
        trenutniKviz = kviz
    }

    fun getTrenutniKviz(): Kviz {
        return trenutniKviz
    }

   // fun getBrojTacnih() : Int {
   //     return trenutniKviz.brojTacnihOdgovora
  //  }


   // fun setBrojTacnih(brojTacnih : Int) {
   //     trenutniKviz.brojTacnihOdgovora = brojTacnih
   // }


    fun setBackPressedPredmeti(value : Boolean) {
        backPressedInPredmetiFragment = value
    }

    fun getBackPressedPredmeti() : Boolean {
        return backPressedInPredmetiFragment
    }

    fun obrisiPodatkeZaKorisnika() {
        CoroutineScope(Dispatchers.IO).launch {
            AccountRepository.obrisiPodatkeZaKorisnika()
        }
    }

    fun otvoriPokusaj(kviz : Kviz) {
        trenutniKviz = kviz
        coroutineScope.launch {
            val takenKviz = TakeKvizRepository.zapocniKviz(kviz.id)
            if(takenKviz != null) {
                val pitanja = PitanjeKvizRepository.getPitanjaDB(kviz.id) // posto ga je moguce otvoriti vec je sigurno smjesten u lokalnu bazu tako da postoje pitanja za njega tu
                withContext(Dispatchers.Main) {
                    pokreniAkcijePokusajKviza(takenKviz, pitanja)
                }
            }
        }

    }


     fun registrujOdgovor(idKvizTaken:Int,idPitanje:Int,odgovor:Int) {
        coroutineScope.launch {
            trenutniKviz.osvojeniBodovi = OdgovorRepository.postaviOdgovorKviz(idKvizTaken,idPitanje,odgovor).toFloat()
            Log.d("pracenjeOvoOno", "zavrseno postavljanje odg")
          //  Log.d("pratiJelZavrseno", "fdsfsfdf " + trenutniKviz.osvojeniBodovi)
        }
    }

    fun predajKviz() {
        trenutniKviz.predan = true
    }

    fun postaviHash(context : Context, hash: String) {
        AccountRepository.setContext(context)
        coroutineScope.launch {
            AccountRepository.postaviHash(hash)
        }
    }

    fun setContexts(context : Context) {
        KvizRepository.setContext(context)
        AccountRepository.setContext(context)
        DBRepository.setContext(context)
        OdgovorRepository.setContext(context)
        PredmetIGrupaRepository.setContext(context)
        PitanjeKvizRepository.setContext(context)
        GrupaRepository.setContext(context)
    }

    fun dodajKvizTaken(kvizId: Int, kvizTakenId: Int) {
        coroutineScope.launch {
            DBRepository.dodajKvizTaken(kvizId, kvizTakenId)
        }
    }

    fun posaljiOdgovoreIzBaze() {
        coroutineScope.launch {
            OdgovorRepository?.predajOdgovore(trenutniKviz.id)
        }
    }

    fun forceDBUpdate() {
        coroutineScope.launch {
            DBRepository.updateDB()
        }
    }

    fun predajKvizAPI(pitanja : List<Pitanje>, takenKviz : KvizTaken) {
        coroutineScope.launch {
            for(i in 1..pitanja.size) {
                if(!pitanja[i-1].odgovoreno) {
                    Log.d("pracenjeOvoOno", "neodgovoreno")
                    trenutniKviz.osvojeniBodovi = OdgovorRepository.postaviOdgovorKviz(takenKviz.id,pitanja[i-1].id,-1).toFloat()
                }
            }
            OdgovorRepository.predajOdgovore(trenutniKviz.id)
            // jos dodat u kolonu predan
            KvizRepository.setPredan(trenutniKviz.id)
            DBRepository.updateDB()
        }
    }

}

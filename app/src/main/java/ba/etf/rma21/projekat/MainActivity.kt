package ba.etf.rma21.projekat

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.view.*
import ba.etf.rma21.projekat.viewmodel.KvizoviViewModel
import ba.etf.rma21.projekat.viewmodel.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, FragmentPredmeti.OnCommunicationPredmetiListener,
FragmentKvizovi.OnCommunicationKvizoviListener, FragmentPokusaj.OnCommunicationPokusajListener,
    FragmentPitanje.OnCommunicationPitanjeListener
{

    private lateinit var bottomNavigationView : BottomNavigationView
    private var tag = "nista"


    private var sharedViewModel = SharedViewModel.instance(::pokreniAkcijePokusajKviza)
    private var fragmentKvizovi = FragmentKvizovi.newInstance()
    private var fragmentPredmeti = FragmentPredmeti.newInstance()
    private lateinit var fragmentPokusaj : FragmentPokusaj  // cisto onako , da imam  referencu na ovo uvijek a svaki put cu nanovo stvarat kad otvaram



    override fun onCreate(savedInstanceState: Bundle?) {
   //    sharedViewModel?.obrisiPodatkeZaKorisnika()
        sharedViewModel?.setContexts(applicationContext)
        if(intent != null) {
           //Log.d("pracenjeOnoBas", intent.getStringExtra("payload")!!)
            val hash = intent.getStringExtra("payload")
            // mogu i ovdje kreirat korutinu mada nije neka praksa
           if(hash != null) sharedViewModel?.postaviHash(applicationContext,hash)
            else sharedViewModel?.postaviHash(applicationContext,"aec32f1c-c193-4c7e-97b3-753109794580")
        }
        else sharedViewModel?.postaviHash(applicationContext,"aec32f1c-c193-4c7e-97b3-753109794580")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNav)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        // jer ovo gore ne triggera listener
        openFragmentMainFrame(FragmentKvizovi.newInstance())
    }


    override fun onBackPressed() {
        // samo vodim racuna o transakciji i na osnovu toga koji je trenutno fragment prikazan, ja mijenjam prikazani fragment
        // mogao sam raditi i preko popBackStack, ili preko last fragment iz managera
        // popbackstack bi svakako morao ponovo staviti fragment
        if (tag.equals("nista")) return
        if(tag.equals("predmeti") || tag.equals("poruka")) {
            if(tag.equals("predmeti")) sharedViewModel?.setBackPressedPredmeti(true)
            openFragmentMainFrame(fragmentKvizovi)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.kvizovi -> {
                openFragmentMainFrame(fragmentKvizovi)
                return true
            }
            R.id.predmeti -> {
                fragmentPredmeti = FragmentPredmeti.newInstance()
                openFragmentMainFrame(fragmentPredmeti)
                return true
            }
            R.id.predajKviz -> {
                sharedViewModel?.predajKviz()
                fragmentPokusaj.predajKviz() // postavi fiktivne odgovore i sve ostalo sto je potrebno za predaju
                showMessagePredajKviz()
                showKvizoviPredmetibottomNav()
                return true
            }
            R.id.zaustaviKviz -> {
                openFragmentMainFrame(fragmentKvizovi)
                showKvizoviPredmetibottomNav()
                return true
            }
            else -> {

            }
        }
        return false
    }




    fun openFragmentMainFrame(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            replace(R.id.frameLayoutMain,fragment)
            tag = "poruka"
            if(fragment is FragmentPredmeti) tag = "predmeti"
            else if(fragment is FragmentKvizovi) tag = "kvizovi"
            else if(fragment is FragmentPokusaj) tag = "pokusaj"
            addToBackStack(tag)
            commit()
        }
    }




    override fun showMessageGrupa(grupa: Grupa) {
        val tekst = "Uspješno ste upisani u grupu ${grupa.naziv} predmeta ${grupa.nazivPredmeta}!"
        var fragment = FragmentPoruka.newInstance(tekst)
        openFragmentMainFrame(fragment)
    }

//jhjhk



    override fun otvoriPokusaj(kviz : Kviz) {
        sharedViewModel?.otvoriPokusaj(kviz)
    }

    override fun getBottomNav(): BottomNavigationView {
        return bottomNavigationView
    }

    override fun openFragmentPitanje(pitanje: Pitanje) {
        val fragmentPitanje = FragmentPitanje.newInstance(pitanje)
        openFragmentFramePitanje(fragmentPitanje)
    }

    override fun showMessageResult() {
        showMessagePredajKviz()
    }

    override fun markNumberOfQuestion(pitanje: Pitanje, color: Int) {
        fragmentPokusaj.markNumberOfQuestion(pitanje, color) // drzat ce uvijek referencu ispravnog fragmentPokusaj tj onog koji se prikazuje
    }

    override fun registrujOdgovor(pitanjeId: Int, odgovor: Int) {
        fragmentPokusaj.registrujOdgovor(pitanjeId, odgovor)
    }


    fun showMessagePredajKviz() {
        fragmentPokusaj.addItemResult()
        var fragment = FragmentPoruka.newInstance("Završili ste kviz ${sharedViewModel?.getNazivKviza()} sa tačnosti ${sharedViewModel?.getBodovi()}")
        openFragmentFramePitanje(fragment)
    }

    private fun showKvizoviPredmetibottomNav() {
        bottomNavigationView.menu.findItem(R.id.kvizovi).setVisible(true)
        bottomNavigationView.menu.findItem(R.id.predmeti).setVisible(true)
        bottomNavigationView.menu.findItem(R.id.predajKviz).setVisible(false)
        bottomNavigationView.menu.findItem(R.id.zaustaviKviz).setVisible(false)
    }


    fun openFragmentFramePitanje(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            replace(R.id.framePitanje, fragment)
            addToBackStack(tag)
            commit()
        }
    }


    fun pokreniAkcijePokusajKviza(takenKviz : KvizTaken,  pitanja : List<Pitanje>) {
        sharedViewModel?.dodajKvizTaken(takenKviz.KvizId, takenKviz.id)
        fragmentPokusaj = FragmentPokusaj.newInstance(pitanja)
        fragmentPokusaj.takenKviz = takenKviz
        openFragmentMainFrame(fragmentPokusaj)
    }




}



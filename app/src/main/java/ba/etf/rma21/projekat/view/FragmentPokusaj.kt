package ba.etf.rma21.projekat.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.viewmodel.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class FragmentPokusaj(private var pitanja : List<Pitanje>) : Fragment(), NavigationView.OnNavigationItemSelectedListener {



    companion object {
        fun newInstance(pitanja : List<Pitanje>) = FragmentPokusaj(pitanja)
    }


    lateinit var takenKviz: KvizTaken
    private val sharedViewModel = SharedViewModel.instance()

    private var listener : OnCommunicationPokusajListener? = null

    private lateinit var bottomNav : BottomNavigationView
    private lateinit var navigationQuestions : NavigationView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pokusaj,container,false)
        navigationQuestions = view.findViewById(R.id.navigacijaPitanja)
        navigationQuestions.setNavigationItemSelectedListener(this)
        addQuestions()
        if(!sharedViewModel?.jeLiPredanKviz()!!) setBottomNav()
        else {
            addItemResult()
        }
        setColorOfNumbers()
        return view
    }

    private fun setColorOfNumbers() {
        if(!sharedViewModel?.jeLiPredanKviz()!! && !sharedViewModel?.jeLiZaustavljenKviz()!!) return
        for(pitanje in pitanja) {
            if(!pitanje.odgovoreno) continue
            else if(pitanje.tacan.equals(pitanje.odgovor))
            markNumberOfQuestion(pitanje, Color.parseColor("#3DDC84"))
            else {
                markNumberOfQuestion(pitanje, Color.parseColor("#DB4F3D"))
            }
        }
    }

    private fun addQuestions() {
        val menu  = navigationQuestions.menu
        for(i in 1..pitanja.size) {
            menu.add(R.id.group,i-1,i-1, ""+i)
        }
    }

    private fun setBottomNav() {
        bottomNav.menu.findItem(R.id.kvizovi).setVisible(false)
        bottomNav.menu.findItem(R.id.predmeti).setVisible(false)
        bottomNav.menu.findItem(R.id.predajKviz).setVisible(true)
        bottomNav.menu.findItem(R.id.zaustaviKviz).setVisible(true)
    }


    interface OnCommunicationPokusajListener {
        fun getBottomNav() : BottomNavigationView
        fun openFragmentPitanje(pitanje: Pitanje)
        fun showMessageResult()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnCommunicationPokusajListener) {
            listener = context
            bottomNav = listener?.getBottomNav()!!
        }
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.title!="Rezultat")
        listener?.openFragmentPitanje(pitanja[item.itemId])
        else {
            listener?.showMessageResult()
        }
        return true
    }


    fun markNumberOfQuestion(pitanje: Pitanje, color: Int) {
        val number = pitanja.indexOf(pitanje)
        val item = navigationQuestions.menu.get(number)
        val s = SpannableString(item.title)
        s.setSpan( ForegroundColorSpan(color), 0, s.length, 0)
        item.title = s
    }



    fun addItemResult() {
       if(navigationQuestions.menu.findItem(pitanja.size)==null) navigationQuestions.menu.add(R.id.group, pitanja.size,pitanja.size, "Rezultat")
    }

    fun registrujOdgovor(pitanjeId: Int, odgovor: Int) {
        sharedViewModel?.registrujOdgovor(takenKviz.id, pitanjeId, odgovor)
    }

    fun predajKviz() {
        sharedViewModel?.predajKvizAPI(pitanja, takenKviz)
    }


}

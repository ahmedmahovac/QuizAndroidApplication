package ba.etf.rma21.projekat.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.SharedViewModel

class FragmentPitanje(private var pitanje : Pitanje) : Fragment(),
    AdapterView.OnItemClickListener {

    companion object {
        fun newInstance(pitanje: Pitanje) = FragmentPitanje(pitanje)
    }


    private lateinit var tekstPitanja : TextView
    private lateinit var odgovoriLista : ListView


    private var sharedViewModel = SharedViewModel.instance()


    private  var listener : OnCommunicationPitanjeListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnCommunicationPitanjeListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pitanje,container,false)
        tekstPitanja = view.findViewById(R.id.tekstPitanja)
        odgovoriLista = view.findViewById(R.id.odgovoriLista)

        odgovoriLista.adapter = ArrayAdapter<String>(inflater.context,android.R.layout.simple_list_item_1,pitanje.opcije.split(","))
        tekstPitanja.text = pitanje.tekstPitanja
        odgovoriLista.onItemClickListener = this
        if(pitanje.odgovoreno) {
           odgovoriLista.post {
               odgovoriLista.performItemClick(odgovoriLista.getChildAt(pitanje.odgovor),pitanje.odgovor,odgovoriLista.adapter.getItemId(pitanje.odgovor))
           }
            odgovoriLista.isEnabled = false
        }
        else if(pitanje.odgovoreno == false && sharedViewModel?.jeLiPredanKviz()==true) {
            odgovoriLista.isEnabled = false
        }


        return view
    }





    interface OnCommunicationPitanjeListener {
        fun markNumberOfQuestion(pitanje: Pitanje , color: Int)
        fun registrujOdgovor(pitanjeId: Int, odgovor: Int)
    }




    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(pitanje.tacan.equals(position)) {
            view?.setBackgroundColor(Color.parseColor("#3DDC84"))
            listener?.markNumberOfQuestion(pitanje,Color.parseColor("#3DDC84"))
           // if(!pitanje.odgovoreno) sharedViewModel?.setBrojTacnih(sharedViewModel?.getBrojTacnih()?.plus(1)!!)
        }
        else {
            view?.setBackgroundColor(Color.parseColor("#DB4F3D"))
            parent?.get(pitanje.tacan)?.setBackgroundColor(Color.parseColor("#3DDC84"))
            listener?.markNumberOfQuestion(pitanje,Color.parseColor("#DB4F3D"))
        }
      if(!pitanje.odgovoreno) {
          listener?.registrujOdgovor(pitanje.id,position)
          pitanje.odgovoreno = true
          pitanje.odgovor = position
      } // mozda ovo ne trebam pozivat svaki put jer ovako pamtim odgovore a svaki put ce dobavljat podatke


        odgovoriLista.setEnabled(false)
    }



}
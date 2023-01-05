package ba.etf.rma21.projekat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.viewmodel.SharedViewModel
import ba.etf.rma21.projekat.viewmodel.UpisPredmetViewModel
import java.util.stream.Collectors

class FragmentPredmeti : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener{
    private  lateinit var spinnerSubject : Spinner
    private  lateinit var spinnerYear : Spinner
    private  lateinit var spinnerGroup : Spinner
    private lateinit var btnSubmit : Button

    private var predmetiViewModel = UpisPredmetViewModel(::pokreniAkcijeOdabirGodine, ::pokreniAkcijeOdabirPredmeta, ::pokreniAkcijeUpisanUGrupu)
    private var sharedViewModel = SharedViewModel.instance()

    companion object {
        fun newInstance() = FragmentPredmeti()
    }




    var spinnerYearData = listOf("1","2","3","4","5")
    var spinnerSubjectData = mutableListOf<String>()
    var spinnerGroupData = mutableListOf<String>()
    private lateinit var adapterSpinnerYear : ArrayAdapter<String>
    private lateinit var adapterSpinnerSubject : ArrayAdapter<String>
    private lateinit var adapterSpinnerGroup : ArrayAdapter<String>




    private var listener : OnCommunicationPredmetiListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnCommunicationPredmetiListener) {
            listener = context
        }
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }






    interface OnCommunicationPredmetiListener {
        fun showMessageGrupa(grupa: Grupa)
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      var view = inflater.inflate(R.layout.fragment_upis_predmet, container, false)

        spinnerYear = view.findViewById(R.id.odabirGodina)
        spinnerSubject = view.findViewById(R.id.odabirPredmet)
        spinnerGroup = view.findViewById(R.id.odabirGrupa)

        btnSubmit = view.findViewById(R.id.dodajPredmetDugme)

        // definisanje adaptera
        adapterSpinnerYear = ArrayAdapter(inflater.context, R.layout.my_spinner_item_layout, spinnerYearData)
        adapterSpinnerSubject = ArrayAdapter(inflater.context, R.layout.my_spinner_item_layout, spinnerSubjectData)
        adapterSpinnerGroup = ArrayAdapter(inflater.context, R.layout.my_spinner_item_layout, spinnerGroupData)

        // postavljanje adaptera
        spinnerYear.adapter = adapterSpinnerYear
        spinnerSubject.adapter = adapterSpinnerSubject
        spinnerGroup.adapter = adapterSpinnerGroup

        spinnerYear.onItemSelectedListener = this
        spinnerSubject.onItemSelectedListener = this
        spinnerGroup.onItemSelectedListener = this
        btnSubmit.setOnClickListener(this)


       if(sharedViewModel?.getBackPressedPredmeti()!!){
        postaviOdabire()
       }


        btnSubmit.setClickable(false)


        return view
    }

    private fun postaviOdabire() {
        spinnerYear.setSelection(sharedViewModel?.getOdabirGodina()!!, true)
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val id = parent?.id
        when(id) {
            R.id.odabirGodina -> {
                val value = parent?.getItemAtPosition(position).toString().toInt()
                spinnerSubjectData.clear()
                predmetiViewModel.neupisaniPredmetiZaGodina(value) // pokrenute korutine
                btnSubmit.setClickable(false)
            }
            R.id.odabirPredmet -> {
                spinnerGroupData.clear()
                predmetiViewModel.grupeZaPredmet(parent?.getItemAtPosition(position).toString()) // pokrenuta korutina
                btnSubmit.setClickable(false)
            }
            R.id.odabirGrupa -> {
                if(!spinnerGroup.selectedItem.toString().equals("Izaberi"))
                    btnSubmit.setClickable(true)
            }
            else -> {

            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.dodajPredmetDugme -> {
                if(spinnerYear.selectedItem != null && spinnerSubject.selectedItem!=null && spinnerGroup.selectedItem!=null) {
                    predmetiViewModel.upisiUGrupu(spinnerGroup.selectedItem.toString()) // pokrece se korutina
                    val naziv = spinnerGroup.selectedItem
                }
            }
            else -> {
            }
        }
    }
// /grupa/{gid}/student/{id}

    override fun onPause() {
        super.onPause()

       if(sharedViewModel?.getBackPressedPredmeti()!!) {
           sharedViewModel?.sacuvajOdabirPredmeta(spinnerYear.selectedItemPosition,spinnerSubject.selectedItemPosition,spinnerGroup.selectedItemPosition)
       }

    }




    fun pokreniAkcijeOdabirGodine(predmeti : List<Predmet>) {
        spinnerSubjectData.add("Izaberi")
        spinnerSubject.setSelection(0) // osigurava
        val predmetiZaGodinu = predmeti.stream().map{ t -> t.toString()}.collect(
            Collectors.toList())
        spinnerSubjectData.addAll(predmetiZaGodinu)
        adapterSpinnerSubject.notifyDataSetChanged()
        spinnerGroupData.clear()
        adapterSpinnerGroup.notifyDataSetChanged()
        if(sharedViewModel?.getBackPressedPredmeti()!!){
            spinnerSubject.setSelection(sharedViewModel?.getOdabirPredmet()!!,true)
        }
    }


    fun pokreniAkcijeOdabirPredmeta(grupe : List<Grupa>) {
        spinnerGroupData.add("Izaberi")
        spinnerGroup.setSelection(0)
        spinnerGroupData.addAll(grupe.stream().map{ t->t.toString()}.collect(Collectors.toList()))
        adapterSpinnerGroup.notifyDataSetChanged()
        if(sharedViewModel?.getBackPressedPredmeti()!!){
            spinnerGroup.setSelection(sharedViewModel?.getOdabirGrupa()!!,true)
            sharedViewModel?.setBackPressedPredmeti(false)!!
        }
    }


    fun pokreniAkcijeUpisanUGrupu(grupa : Grupa) {
        listener?.showMessageGrupa(grupa)
    }



}
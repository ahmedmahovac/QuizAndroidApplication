package ba.etf.rma21.projekat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.viewmodel.KvizoviViewModel

class FragmentKvizovi : Fragment(){

    private lateinit var recyclerViewQuizzes : RecyclerView
    private lateinit var spinnerOptions : Spinner





    private lateinit var adapter : RecyclerViewQuizAdapter


    private val kvizoviViewModel = KvizoviViewModel(::postaviKvizove)


    companion object {
        fun newInstance() = FragmentKvizovi()
    }





    private var listener : OnCommunicationKvizoviListener? = null


    interface OnCommunicationKvizoviListener {
        fun otvoriPokusaj(kviz : Kviz)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnCommunicationKvizoviListener) listener = context
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }




    fun actionOnSelectedItemSpinner(option : String) {
        if(option=="Svi moji kvizovi") kvizoviViewModel.getQuizzesUser()
        else if(option=="Svi kvizovi") kvizoviViewModel.getQuizzesAll()
        else if(option=="Urađeni kvizovi") kvizoviViewModel.getQuizzesDone()
        else if(option=="Budući kvizovi") kvizoviViewModel.getQuizzesUpcoming()
        else if(option=="Prošli kvizovi") kvizoviViewModel.getQuizzesNotTaken()
    }










    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_kvizovi, container, false)
        super.onCreate(savedInstanceState)

        adapter = RecyclerViewQuizAdapter(::onItemClick)

        spinnerOptions = view.findViewById(R.id.filterKvizova)
        spinnerOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val option = parent?.getItemAtPosition(position).toString()
                actionOnSelectedItemSpinner(option)
            }
        }

        recyclerViewQuizzes = view.findViewById(R.id.listaKvizova)
        recyclerViewQuizzes.layoutManager = GridLayoutManager(inflater.context, 2, RecyclerView.VERTICAL, false)
        recyclerViewQuizzes.adapter = adapter
      //  kvizoviViewModel.getQuizzesUser()
      //  spinnerOptions.setSelection()
        return view

    }

     fun onItemClick(kviz : Kviz) {
         listener?.otvoriPokusaj(kviz)
     }





    fun postaviKvizove(result : List<Kviz>) {
        adapter.setItems(result)
    }







}
package ba.etf.rma21.projekat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R

class FragmentPoruka(private val tekst : String) : Fragment() {
    private lateinit var tvPoruka : TextView



    companion object {
        fun newInstance(tekst : String) = FragmentPoruka(tekst)
    }





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_poruka,container,false)
        tvPoruka = view.findViewById(R.id.tvPoruka)
        tvPoruka.text = tekst
        return view
    }




}
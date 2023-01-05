package ba.etf.rma21.projekat.view


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import java.text.SimpleDateFormat
import java.util.*

class RecyclerViewQuizAdapter(
     private var onItemClick: (kviz: Kviz) -> Unit
) : RecyclerView.Adapter<RecyclerViewQuizAdapter.MyViewHolder>(){
    private var quizzes = mutableListOf<Kviz>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var newView : View = LayoutInflater.from(parent.context).inflate(R.layout.element_layout_kvizovi, parent, false)
        return MyViewHolder(newView)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentQuiz = quizzes[position]
        holder.textViewSubject.text = currentQuiz.nazivPredmeta
        holder.textViewName.text = currentQuiz.naziv
        holder.textViewDuration.text = currentQuiz.trajanje.toString() + " min"
        holder.setImageIndicatorAndDate(currentQuiz)
        holder.itemView.setOnClickListener {
            onItemClick(currentQuiz)
        }
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
         var textViewSubject = view.findViewById<TextView>(R.id.textViewSubject)
         var textViewName = view.findViewById<TextView>(R.id.textViewName)
         var textViewDate = view.findViewById<TextView>(R.id.textViewDate)
         var textViewDuration = view.findViewById<TextView>(R.id.textViewDuration)
         var imageViewIndicator = view.findViewById<ImageView>(R.id.imageViewIndicator)
         var textViewOsvojeniBodovi = view.findViewById<TextView>(R.id.textViewOsvojeniBodovi)


        fun setImageIndicatorAndDate(currentQuiz: Kviz) {
            val formatter = SimpleDateFormat("dd.MM.yyyy")
            val formatterDB = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val currentTime = Calendar.getInstance().time
            // ovako uvijek prikazuje status u skladu sa trenutnim vremenom, a da sam dodao kao atribute kviza onda bi se jednom postavilo i tjt
            Log.d("pracenjeKviza", currentQuiz.toString())
            if(formatterDB.parse(currentQuiz.datumPocetka).after(currentTime)) {
                imageViewIndicator.setImageResource(R.drawable.zuta)
                textViewDate.text = formatter.format(formatterDB.parse(currentQuiz.datumPocetka))
                textViewOsvojeniBodovi.text = ""
            }
            else if(checkIfActive(currentQuiz)) {
                imageViewIndicator.setImageResource(R.drawable.zelena)
                textViewDate.text = formatter.format(formatterDB.parse(currentQuiz.datumKraj))
                textViewOsvojeniBodovi.text = ""
            }
            else if(currentTime.after(formatterDB.parse(currentQuiz.datumKraj)) && !currentQuiz.predan) {
                imageViewIndicator.setImageResource(R.drawable.crvena)
                textViewDate.text = formatter.format(formatterDB.parse(currentQuiz.datumKraj))
                textViewOsvojeniBodovi.text = ""
            }
            else {
                imageViewIndicator.setImageResource(R.drawable.plava)
                textViewDate.text = formatter.format(formatterDB.parse(currentQuiz.datumRada))
                textViewOsvojeniBodovi.text = currentQuiz.osvojeniBodovi.toString()
            }
        }




    }


     fun setItems(quizzes : List<Kviz>)  {
        this.quizzes = quizzes as MutableList<Kviz>
        notifyDataSetChanged()
    }



    companion object {
        fun checkIfActive(currentQuiz: Kviz): Boolean {
            val formatterDB = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val currentTime = Calendar.getInstance().time
            return currentTime.after(formatterDB.parse(currentQuiz.datumPocetka)) && currentTime.before(formatterDB.parse(currentQuiz.datumKraj)) && !currentQuiz.predan
        }
    }


}
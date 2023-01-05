package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import kotlinx.coroutines.*
import kotlin.reflect.KFunction1

class KvizoviViewModel() {
    private lateinit var postaviKvizove : KFunction1<List<Kviz>, Unit>
    constructor(postaviKvizove: KFunction1<List<Kviz>, Unit>) : this(){
        this.postaviKvizove = postaviKvizove
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO) // zajednicki scope svih korutina vezanih za ovaj viewmodel
    // sve korutine pokrecemo unutar ovog scopea



    fun getQuizzesAll()  {
        coroutineScope.launch {
            val result = KvizRepository.getAll()
            withContext(Dispatchers.Main) {
                postaviKvizove(result)
            }
        }
    }

    fun getQuizzesUpcoming() {
        coroutineScope.launch {
            val result = KvizRepository.getFutureDB()
            withContext(Dispatchers.Main) {
                postaviKvizove(result)
            }
        }
    }

    fun getQuizzesUser() {
        coroutineScope.launch {
            val result = KvizRepository.getUpisaniDB()
            withContext(Dispatchers.Main) {
                postaviKvizove(result)
            }
        }
    }

    fun getQuizzesDone() {
        coroutineScope.launch {
            val result = KvizRepository.getDoneDB()
            withContext(Dispatchers.Main) {
                postaviKvizove(result)
            }
        }
    }

    fun getQuizzesNotTaken() {
        coroutineScope.launch {
            val result = KvizRepository.getNotTakenDB()
            withContext(Dispatchers.Main) {
                postaviKvizove(result)
            }
        }
    }




}
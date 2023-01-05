package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpisPredmetViewModel(
    private val pokreniAkcijeOdabirGodine: (predmeti: List<Predmet>) -> Unit,
    private val pokreniAkcijeOdabirPredmeta: (grupe: List<Grupa>) -> Unit,
    private val pokreniAkcijeUpisanUGrupu: (grupa: Grupa) -> Unit
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    fun neupisaniPredmetiZaGodina(godina  : Int) {
        coroutineScope.launch {
            val neupisaniPredmeti = PredmetRepository.getNeupisaniPredmetiZaGodina(godina)
            withContext(Dispatchers.Main) {
                // pozovi funkciju koja ce uticati na UI
                pokreniAkcijeOdabirGodine(neupisaniPredmeti)
            }
        }
    }

    fun grupeZaPredmet(predmet : String)  {
        coroutineScope.launch {
            val grupe = GrupaRepository.getGroupsByPredmet(predmet)
            withContext(Dispatchers.Main) {
                pokreniAkcijeOdabirPredmeta(grupe)
            }
        }
    }

    fun upisiPredmet(predmet: String) {
          //  val predmet = PredmetRepository.getPredmetByNaziv(predmet)
           // PredmetRepository.upisaniPredmeti.add(predmet)
           // PredmetRepository.neupisaniPredmeti.remove(predmet)
    }

    /*fun upisiGrupaIKvizovi(group: String) {
            val grupa = GrupaRepository.getGrupaByName(group)
            GrupaRepository.groupsUpisan.add(grupa)
            GrupaRepository.groupsNeupisan.remove(grupa)
            val quizzesForGroup = KvizRepository.getQuizzesForGroup(grupa)
            for(currentQuiz in quizzesForGroup) {
                val currentTime = Calendar.getInstance().time
                if(currentQuiz.datumPocetka.after(currentTime)) {
                    KvizRepository.quizzesFuture.add(currentQuiz)
                }
                else if(currentQuiz.osvojeniBodovi != null) {
                    KvizRepository.quizzesDone.add(currentQuiz)
                }
                else if(currentTime.after(currentQuiz.datumKraj) && currentQuiz.osvojeniBodovi==null) {
                    KvizRepository.quizzesNotTaken.add(currentQuiz)
                }
            }
            KvizRepository.quizzesMy.addAll(quizzesForGroup)
    }

     */


    fun upisiUGrupu(group : String) {
        coroutineScope.launch {
           val grupa = GrupaRepository.getGrupaByName(group) // ovo bi trebalo jos malo provjerit jer moze bit vise grupa istog naziva unutar vise predmeta
            PredmetIGrupaRepository.upisiUGrupu(grupa.id) // ovo bih mogo nekako kesirat jer mi nece prikazat grupu koja nije vec dobavljena pa da je samo povucem iz viewmodela
            withContext(Dispatchers.Main) {
                pokreniAkcijeUpisanUGrupu(grupa)
            }
        }
    }

/*
    fun getGrupa(naziv: String): Grupa {
        return GrupaRepository.getGrupaByName(naziv)
    }

*/
}
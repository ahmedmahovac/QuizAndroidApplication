


package ba.etf.rma21.projekat
/*
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.viewmodel.KvizoviViewModel
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*

import org.junit.Assert.*
import java.text.SimpleDateFormat

class MainViewModelTest {

    private val viewModel = KvizoviViewModel(::postaviSveKvizove)
/*
    @Test
    fun testQuizzesAll() {
        val quizzes = viewModel.getQuizzesAll()
        assertEquals(quizzes.size, 7)
        assertThat(quizzes, hasItem<Kviz>(hasProperty("naziv",Is("Kviz 1"))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("nazivPredmeta",Is("ORM"))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("trajanje",Is(5))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("nazivGrupe",Is("Grupa 1 IF1"))))
    }
*/


    @Test
    fun testQuizzesUser() {
        val quizzes = viewModel.getQuizzesUser()
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        assertEquals(quizzes.size, 1)
        assertThat(quizzes, hasItem<Kviz>(hasProperty("naziv",Is("Kviz 1"))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("nazivPredmeta",Is("RA"))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("trajanje",Is(5))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("nazivGrupe",Is("Grupa 1 RA"))))
    }


    @Test
    fun testQuizzesDone() {
        val quizzes = viewModel.getQuizzesDone()
        KvizRepository.quizzesDone.add(KvizRepository.quizzesAll[3])
        assertEquals(quizzes.size, 1)
        assertThat(quizzes, hasItem<Kviz>(hasProperty("naziv",Is("Kviz 1 ooad"))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("nazivPredmeta",Is("OOAD"))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("trajanje",Is(5))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("nazivGrupe",Is("Grupa 1 OOAD"))))
    }


    @Test
    fun testQuizzesUpcoming() {
        val quizzes = viewModel.getQuizzesUpcoming()
        KvizRepository.quizzesFuture.add(KvizRepository.quizzesAll[2])
        assertEquals(quizzes.size, 1)
        assertThat(quizzes, hasItem<Kviz>(hasProperty("naziv",Is("Kviz 1 fizika"))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("nazivPredmeta",Is("IF1"))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("trajanje",Is(5))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("nazivGrupe",Is("Grupa 2 IF1"))))
    }



    @Test
    fun testQuizzesNotTaken() {
        val quizzes = viewModel.getQuizzesNotTaken()
        KvizRepository.quizzesNotTaken.add(KvizRepository.quizzesAll[1])
        assertEquals(quizzes.size, 1)
        assertThat(quizzes, hasItem<Kviz>(hasProperty("naziv",Is("Kviz 1"))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("nazivPredmeta",Is("IF1"))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("trajanje",Is(5))))
        assertThat(quizzes, hasItem<Kviz>(hasProperty("nazivGrupe",Is("Grupa 1 IF1"))))
    }




}

 */
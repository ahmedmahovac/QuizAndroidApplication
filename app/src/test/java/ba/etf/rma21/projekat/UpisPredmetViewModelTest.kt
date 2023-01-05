
/*

package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import ba.etf.rma21.projekat.viewmodel.UpisPredmetViewModel
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert.assertEquals

import org.hamcrest.CoreMatchers.`is` as Is

class UpisPredmetViewModelTest {

    private val viewModel = UpisPredmetViewModel(
        ::pokreniAkcijeOdabirGodine,
        ::pokreniAkcijeOdabirPredmeta
    )

     @Test
     fun neupisaniPredmetiZaGodinaTest() {
         val predmeti = viewModel.neupisaniPredmetiZaGodina(1)
         assertEquals(predmeti.size, 1)
         assertThat(predmeti, hasItem(hasProperty("naziv",Is("IF1"))))
         assertThat(predmeti, hasItem(hasProperty("godina",Is(1))))
     }

    @Test
    fun grupeZaPredmetTest() {
        val predmeti = viewModel.grupeZaPredmet("IF1")
        assertEquals(predmeti.size, 2)
        assertThat(predmeti, hasItem(hasProperty("naziv",Is("Grupa 1 IF1"))))
        assertThat(predmeti, hasItem(hasProperty("nazivPredmeta",Is("IF1"))))
    }



    @Test
    fun upisiPredmetGrupaKvizoviTest() {
        viewModel.upisiPredmet("OOAD")
        viewModel.upisiGrupaIKvizovi("Grupa 1 OOAD")
        val predmeti = PredmetRepository.getUpisani()
        val grupe = GrupaRepository.groupsUpisan
        val kvizovi = KvizRepository.quizzesMy
        assertEquals(predmeti.size, grupe.size)
        assertThat(predmeti, hasItem(hasProperty("naziv",Is("OOAD"))))
        assertThat(grupe, hasItem(hasProperty("naziv",Is("Grupa 1 OOAD"))))
        assertThat(grupe, hasItem(hasProperty("nazivPredmeta",Is("OOAD"))))
        assertEquals(kvizovi.size, 2)
        assertThat(kvizovi, hasItem<Kviz>(hasProperty("naziv",Is("Kviz 1 ooad"))))
        assertThat(kvizovi, hasItem<Kviz>(hasProperty("nazivPredmeta",Is("OOAD"))))
        assertThat(kvizovi, hasItem<Kviz>(hasProperty("trajanje",Is(5))))
        assertThat(kvizovi, hasItem<Kviz>(hasProperty("nazivGrupe",Is("Grupa 1 OOAD"))))
    }








}

 */
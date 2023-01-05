package ba.etf.rma21.projekat
/*
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith




@RunWith(AndroidJUnit4::class)
class MySpirala2AndroidTest {
    @get:Rule
    val intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)


    @Test
    fun testZadatak1() {
        onView(withId(R.id.predmeti)).perform(click())
        onView(withId(R.id.odabirGodina)).perform(click())
        onView(withText("2")).perform(click())
        val predmet = PredmetRepository.getNeupisaniPredmetiZaGodina(2).first()
        val grupa = GrupaRepository.getGroupsByPredmet(predmet.naziv).first()
        onView(withId(R.id.odabirPredmet)).perform(click())
        onView(withText(predmet.naziv)).perform(click())
        pressBack()
        onView(withId(R.id.predmeti)).perform(click())
        onView(withId(R.id.odabirGodina)).check(matches(withSpinnerText("2")))
        onView(withId(R.id.odabirPredmet)).check(matches(withSpinnerText(predmet.naziv)))
        onView(withId(R.id.odabirGrupa)).check(matches(withSpinnerText("Izaberi")))
        onView(withId(R.id.odabirGrupa)).perform(click())
        onView(withText(grupa.naziv)).perform(click())
        onView(withId(R.id.dodajPredmetDugme)).perform(click())
        onView(withId(R.id.tvPoruka)).check(matches(withText("Uspješno ste upisani u grupu ${grupa.naziv} predmeta ${predmet.naziv}!")))
        pressBack()
        onView(withId(R.id.filterKvizova)).perform(click())
        onView(withText("Svi moji kvizovi")).perform(click())
        onView(withText(predmet.naziv)).perform(click())

    }


    @Test
    fun testZadatak2() {
        onView(withId(R.id.listaKvizova)).check(matches(isDisplayed()))
        onView(withId(R.id.listaKvizova)).perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        onView(withId(R.id.bottomNav)).check(matches(isDisplayed()))
        onView(withId(R.id.navigacijaPitanja)).check(matches(isDisplayed()))
        onView(withId(R.id.predajKviz)).check(matches(isDisplayed()))
        onView(withId(R.id.zaustaviKviz)).check(matches(isDisplayed()))
        val kviz = KvizRepository.getMyKvizes().first()
        val pitanja = PitanjeKvizRepository.getPitanja(kviz.naziv,kviz.nazivPredmeta)
        var redniBroj : Int = 0
        for(pitanje in pitanja) {
            val odgovori = pitanje.opcije
            onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(redniBroj))
            onView(withText(pitanje.tekst)).check(matches(isDisplayed()))
            for(odgovor in odgovori) {
                onView(withText(odgovor)).check(matches(isDisplayed()))
            }
            onView(withText(odgovori[0])).perform(click())
            redniBroj++
        }
        onView(withId(R.id.zaustaviKviz)).perform(click())
        onView(withId(R.id.listaKvizova)).perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        redniBroj = 0
        for(pitanje in pitanja) {
            onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(redniBroj))
            onView(withId(R.id.odgovoriLista)).check(matches(isClickable()))
            redniBroj++
        }
        onView(withId(R.id.predajKviz)).perform(click())
        onView(withText("Završili ste kviz ${kviz.naziv} sa tačnosti ${kviz.brojTacnihOdgovora.toFloat()/pitanja.size}")).check(matches(isDisplayed()))
        onView(withId(R.id.kvizovi)).check(matches(isDisplayed()))
        onView(withId(R.id.kvizovi)).perform(click())
        onView(withId(R.id.listaKvizova)).perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        redniBroj = 0
        for(pitanje in pitanja) {
            onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(redniBroj))
            onView(withId(R.id.odgovoriLista)).check(matches(isClickable()))
            redniBroj++
        }
        onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(redniBroj))
        onView(withText("Završili ste kviz ${kviz.naziv} sa tačnosti ${kviz.brojTacnihOdgovora.toFloat()/pitanja.size}")).check(matches(isDisplayed()))

        KvizRepository.refreshRepo()

    }





}

*/
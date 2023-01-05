package ba.etf.rma21.projekat

/*

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma21.projekat.UtilTestClass.Companion.hasItemCount
import ba.etf.rma21.projekat.UtilTestClass.Companion.itemTest
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import org.hamcrest.CoreMatchers.*
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PocetniTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun postojiSveNaPocetnoj() {

        onView(withId(R.id.filterKvizova)).check(matches(isDisplayed()))
        onView(withId(R.id.listaKvizova)).check(matches(isDisplayed()))
        onView(withId(R.id.bottomNav)).check(matches(isDisplayed()))

        var listaOdabira = listOf<String>("Svi moji kvizovi", "Svi kvizovi", "Urađeni kvizovi", "Budući kvizovi", "Prošli kvizovi")

        for (odabir in listaOdabira) {
            onView(withId(R.id.filterKvizova)).perform(click())
            onData(allOf(Is(instanceOf(String::class.java)), Is(odabir))).perform(click())
        }

    }

    @Test
    fun popuniKvizoveGetDone() {

        onView(withId(R.id.filterKvizova)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), Is("Svi moji kvizovi"))).perform(click())
        val kvizovi = KvizRepository.getMyKvizes()
        onView(withId(R.id.listaKvizova)).check(hasItemCount(kvizovi.size))
        for (kviz in kvizovi) {
            itemTest(R.id.listaKvizova, kviz)
        }

    }

    @Test
    fun godineTest() {
        onView(withId(R.id.predmeti)).perform(click())
        var listaOdabira = listOf<String>("1", "2", "3", "4", "5")
        for (odabir in listaOdabira) {
            onView(withId(R.id.odabirGodina)).perform(click())
            onData(allOf(Is(instanceOf(String::class.java)), Is(odabir))).perform(click())
        }
    }

}

 */
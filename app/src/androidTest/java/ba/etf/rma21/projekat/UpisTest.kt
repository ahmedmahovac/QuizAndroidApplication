package ba.etf.rma21.projekat
/*
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma21.projekat.UtilTestClass.Companion.hasItemCount
import ba.etf.rma21.projekat.UtilTestClass.Companion.itemTest
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UpisTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun upisTest() {

        Espresso.onView(ViewMatchers.withId(R.id.filterKvizova)).perform(ViewActions.click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("Svi moji kvizovi")
            )
        ).perform(ViewActions.click())
        val kvizoviPrije = KvizRepository.getMyKvizes()
        val kvizoviPrijeSize = kvizoviPrije.size
        Espresso.onView(ViewMatchers.withId(R.id.listaKvizova)).check(hasItemCount(kvizoviPrije.size))
        for (kviz in kvizoviPrije) {
            itemTest(R.id.listaKvizova, kviz)
        }
        Espresso.onView(ViewMatchers.withId(R.id.predmeti)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.odabirGodina)).perform(ViewActions.click())
        val nedodjeljeniKvizovi = KvizRepository.getAll().minus(KvizRepository.getMyKvizes())
        val nedodjeljeniPredmeti = PredmetRepository.getAll().minus(PredmetRepository.getUpisani())

        var grupaVrijednost = ""
        var predmetNaziv = ""
        var godinaVrijednost = -1
        for (nk in nedodjeljeniKvizovi) {
            for (np in nedodjeljeniPredmeti) {
                if (nk.nazivPredmeta == np.naziv) {
                    grupaVrijednost = nk.nazivGrupe
                    godinaVrijednost = np.godina
                    predmetNaziv = np.naziv

                }
            }
        }
        ViewMatchers.assertThat(
            "Nema neupisanih predmeta sa kvizovima",
            godinaVrijednost,
            CoreMatchers.not(CoreMatchers.`is`(-1))
        )

        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`(godinaVrijednost.toString())
            )
        ).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.odabirPredmet)).perform(ViewActions.click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`(predmetNaziv)
            )
        ).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.odabirGrupa)).perform(ViewActions.click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`(grupaVrijednost)
            )
        ).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.dodajPredmetDugme)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withSubstring("Uspješno ste upisani u grupu"))
        Espresso.onView(ViewMatchers.withId(R.id.kvizovi)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.filterKvizova)).perform(ViewActions.click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("Svi moji kvizovi")
            )
        ).perform(ViewActions.click())
        val kvizoviPoslije = KvizRepository.getMyKvizes()

        Espresso.onView(ViewMatchers.withId(R.id.listaKvizova)).check(hasItemCount(kvizoviPoslije.size))
        for (kviz in kvizoviPoslije) {
            itemTest(R.id.listaKvizova, kviz)
        }

        ViewMatchers.assertThat(
            "Nije dodan kviz nakon upisanog predmeta ",
            kvizoviPrijeSize,
            CoreMatchers.`is`(Matchers.lessThan(kvizoviPoslije.size))
        )

    }
}

 */
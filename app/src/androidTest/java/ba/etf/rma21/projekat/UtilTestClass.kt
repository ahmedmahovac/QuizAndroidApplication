
/*
package ba.etf.rma21.projekat




import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import ba.etf.rma21.projekat.data.models.Kviz
import junit.framework.Assert
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers

class UtilTestClass {
    companion object{
        fun hasItemCount(n: Int) = object : ViewAssertion {
            override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
                if (noViewFoundException != null) {
                    throw noViewFoundException
                }
                assertTrue("View nije tipa RecyclerView", view is RecyclerView)
                var rv: RecyclerView = view as RecyclerView
                ViewMatchers.assertThat(
                    "GetItemCount RecyclerView broj elementa: ",
                    rv.adapter?.itemCount,
                    CoreMatchers.`is`(n)
                )
            }

        }

        fun itemTest(id:Int, k: Kviz){
            Espresso.onView(ViewMatchers.withId(R.id.listaKvizova)).perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    CoreMatchers.allOf(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(k.naziv)),
                        ViewMatchers.hasDescendant(ViewMatchers.withText(k.nazivPredmeta))
                    )
                )
            )
        }
    }
}

 */
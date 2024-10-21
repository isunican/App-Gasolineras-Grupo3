package es.unican.gasolineras.ConsultarRepostaje;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertEquals;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DatabaseFunction;
import es.unican.gasolineras.repository.IGasolinerasRepository;
import es.unican.gasolineras.repository.RepostajeDAO;

@UninstallModules(RepositoriesModule.class)
@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
public class ConsultarRepostajeDatosAusentesUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<MainView> activityRule = new ActivityScenarioRule<>(MainView.class);

    // I need the context to access resources, such as the json with test gas stations
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    // Mock repository that provides data from a JSON file instead of downloading it from the internet.
    @BindValue
    final IGasolinerasRepository repository = getTestRepository(context, R.raw.gasolineras_ccaa_06);

    @Test
    public void DatosAusentesContext(){
        AppDatabase bd = DatabaseFunction.getDatabase(ApplicationProvider.getApplicationContext());
        RepostajeDAO dao = bd.repostajeDao();
        dao.eliminarRepostajes();
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onData(hasToString(containsString("Historial repostaje"))).perform(click());
        onView(withId(R.id.tvRepostajesMes)).check(matches(withText("0")));
        onView(withId(R.id.tvPrecioMedioLitro)).check(matches(withText("0.00")));
        onView(withId(R.id.tvAcumuladoMes)).check(matches(withText("0.00")));
        onView(withId(R.id.lvRepostajes)).check(matches(hasChildCount(0)));
    }
}

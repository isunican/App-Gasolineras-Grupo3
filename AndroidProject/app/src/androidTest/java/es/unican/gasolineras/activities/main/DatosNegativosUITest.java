package es.unican.gasolineras.activities.main;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
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
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.IGasolinerasRepository;
import es.unican.gasolineras.utils.Matchers;

@UninstallModules(RepositoriesModule.class)
@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
public class DatosNegativosUITest {

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
    public void DatosNegativosContext(){
        // Abrir el menÃº de opciones y pulsar la opcion correspondiente a registrar un descuento
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onData(hasToString(containsString("Registrar descuento"))).perform(click());
        // Abrir la lista desplegable y seleccionar la marca "Repsol"
        onView(withId(R.id.spMarcas)).perform(click());
        // Seleccionar el texto de la opciÃ³n "Repsol"
        onView(withText("Repsol")).perform(click());
        onView(withId(R.id.etDescuento)).perform(click());
        onView(withId(R.id.etDescuento)).perform(typeText("-5"));
        Espresso.pressBackUnconditionally();
        // Pulsar el botÃ³n guardar
        onView(withId(R.id.btnGuardar)).perform(click());
        onView(withId(R.id.tvError2)).check(matches(withText("Error: El valor debe ser positivo")));
        onView(withId(R.id.etDescuento)).check(matches(new Matchers.DrawableMatcher(R.drawable.border_red)));
    }
}


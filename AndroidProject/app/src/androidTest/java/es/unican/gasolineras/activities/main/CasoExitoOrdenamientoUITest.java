package es.unican.gasolineras.activities.main;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;


import android.content.Context;

import androidx.test.espresso.DataInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.R;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@UninstallModules(RepositoriesModule.class)
@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
public class CasoExitoOrdenamientoUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<MainView> activityRule = new ActivityScenarioRule<>(MainView.class);


    private static List<Gasolinera> getGasolineras() {

        List<Gasolinera> listaGasolineras = new ArrayList<>();

        Gasolinera g1;
        Gasolinera g2;
        Gasolinera g3;

        g1 = new Gasolinera();
        g1.setRotulo("PetroPrix");
        g1.setGasolina95E5(1.5);
        g1.setGasoleoA(1.39);
        listaGasolineras.add(g1);

        g2 = new Gasolinera();
        g2.setRotulo("Repsol");
        g2.setGasolina95E5(1.35);
        g2.setGasoleoA(1.31);
        listaGasolineras.add(g2);

        g3 = new Gasolinera();
        g3.setRotulo("Avia");
        g3.setGasolina95E5(1.4);
        g3.setGasoleoA(1.28);
        listaGasolineras.add(g3);

        return listaGasolineras;
    }

    @BindValue
    final IGasolinerasRepository repository = getTestRepository(getGasolineras());

    @Test
    public void CasoExitoContext() {

        onView(withId(R.id.OrdenarItem)).perform(click());

        onView(withId(R.id.btnGasolina)).perform(click());

        onView(withId(R.id.btnOrdenar))
                .check(matches(isDisplayed()))
                .perform(click());

        DataInteraction primeraGas = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(0);
        primeraGas.onChildView(withId(R.id.tv95)).check(matches(withText("1.35")));

        DataInteraction segundaGas = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(1);
        segundaGas.onChildView(withId(R.id.tv95)).check(matches(withText("1.4")));

        DataInteraction terceraGas = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(2);
        terceraGas.onChildView(withId(R.id.tv95)).check(matches(withText("1.5")));

    }
}

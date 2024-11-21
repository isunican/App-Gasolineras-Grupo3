package es.unican.gasolineras.activities.main;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.isA;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
public class CasoExitoCompararPrecioUITest {

    @Rule(order = 0)  // El orden de ejecución de la regla Hilt
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<MainView> activityRule = new ActivityScenarioRule<>(MainView.class);

    // Método para devolver una lista de gasolineras de ejemplo
    private static List<Gasolinera> getGasolineras() {
        List<Gasolinera> listaGasolineras = new ArrayList<>();

        Gasolinera g1 = new Gasolinera();
        g1.setRotulo("PetroPrix");
        g1.setGasolina95E5(1.5);
        g1.setGasoleoA(1.39);
        listaGasolineras.add(g1);

        Gasolinera g2 = new Gasolinera();
        g2.setRotulo("Repsol");
        g2.setGasolina95E5(1.35);
        g2.setGasoleoA(1.31);
        listaGasolineras.add(g2);

        Gasolinera g3 = new Gasolinera();
        g3.setRotulo("Avia");
        g3.setGasolina95E5(1.4);
        g3.setGasoleoA(1.28);
        listaGasolineras.add(g3);

        return listaGasolineras;
    }

    // Repositorio simulado
    @BindValue
    final IGasolinerasRepository repository = getTestRepository(getGasolineras());

    @Test
    public void CasoExitoContext() {
        // Comprobar que la lista de gasolineras está visible
        onView(withId(R.id.lvStations)).check(matches(isDisplayed()));

        // Obtener los datos de la primera gasolinera
        Gasolinera primeraGasolinera = getGasolineras().get(0);
        String rotuloEsperado = primeraGasolinera.getRotulo();

        // Seleccionar la primera gasolinera en el ListView
        onData(isA(Gasolinera.class)) // Aseguramos que es una Gasolinera
                .inAdapterView(withId(R.id.lvStations))
                .atPosition(0) // Seleccionar la primera posición
                .perform(click());

        // Comprobar que los detalles de la gasolinera seleccionada son visibles
        onView(withId(R.id.tvRotulo)).check(matches(isDisplayed()));
        onView(withId(R.id.tvHoy)).check(matches(isDisplayed()));
        onView(withId(R.id.tvHoy2)).check(matches(isDisplayed()));

        // Verificar que los datos mostrados coinciden con los valores esperados
        String precioGasolinaEsperado = String.format("%.2f", primeraGasolinera.getGasolina95E5());
        String precioDieselEsperado = String.format("%.2f", primeraGasolinera.getGasoleoA());

        // Comprobar que el rotulo y los precios son correctos
        onView(withId(R.id.tvRotulo)).check(matches(withText(rotuloEsperado)));
    }
}
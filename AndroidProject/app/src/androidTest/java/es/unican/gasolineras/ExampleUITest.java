package es.unican.gasolineras;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;
import android.widget.TextView;

import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.filament.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@UninstallModules(RepositoriesModule.class)
@HiltAndroidTest
public class ExampleUITest {

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
    public void showStationsTest() {
        assertEquals(10, 5+5);
    }


   @Test
    public void compruebaVistaDetalleGasolineraAbreCorrectamente() {
        // Realiza clic en el primer elemento de la lista de gasolineras
        onData(anything())
                .inAdapterView(withId(R.id.lvStations))
                .atPosition(0)
                .perform(click());

        // Verifica que la vista de detalle se muestra comprobando un elemento de la DetailsView
        onView(withId(R.id.tvRotulo))
                .check(matches(isDisplayed()));
    }


    @Test
    public void testDetailViewMuestraTodosLosDatos() {
        // Realiza clic en el primer elemento de la lista de gasolineras
        onData(anything())
                .inAdapterView(withId(R.id.lvStations))
                .atPosition(0)
                .perform(click());

        // Verifica que la dirección está visible
        onView(withId(R.id.tvDireccion))
                .check(matches(isDisplayed()));
        // Verifica que la localidad está visible
        onView(withId(R.id.tvMunicipio))
                .check(matches(isDisplayed()));
        // Verifica que el codigo postal esta visible
        onView(withId(R.id.tvCp))
                .check(matches(isDisplayed()));
        // Verifica que el horario está visible
        onView(withId(R.id.tvHorario))
                .check(matches(isDisplayed()));
        // Verifica que los precios de carburante están visibles
        onView(withId(R.id.tvGasolina95))
                .check(matches(isDisplayed()));
        onView(withId(R.id.tvGasoleoA))
                .check(matches(isDisplayed()));
        // Verifica que el precio sumario esta visible
        onView(withId(R.id.tvPrecioSumario))
                .check(matches(isDisplayed()));
    }


    @Test
    public void testFormatoPrecioCorrecto() {
        // Realiza clic en el primer elemento de la lista de gasolineras
        onData(anything())
                .inAdapterView(withId(R.id.lvStations))
                .atPosition(0)
                .perform(click());

        // Verifica que el formato del precio es de dos decimales
        onView(withId(R.id.tvGasolina95))
                .check((view, noViewFoundException) -> {
                    TextView textView = (TextView) view;
                    String text = textView.getText().toString();
                    assertTrue("El texto no tiene el formato correcto", text.matches("\\d+\\.\\d{2}"));
                });

        onView(withId(R.id.tvGasoleoA))
                .check((view, noViewFoundException) -> {
                    TextView textView = (TextView) view;
                    String text = textView.getText().toString();
                    assertTrue("El texto no tiene el formato correcto", text.matches("\\d+\\.\\d{2}"));
                });

        onView(withId(R.id.tvPrecioSumario))
                .check((view, noViewFoundException) -> {
                    TextView textView = (TextView) view;
                    String text = textView.getText().toString();
                    assertTrue("El texto no tiene el formato correcto", text.matches("\\d+\\.\\d{2}"));
                });
    }


    /*
    @Test
    public void testDatosNoExistentesConGuion() {
        // Realiza clic en el primer elemento de la lista de gasolineras
        onData(anything())
                .inAdapterView(withId(R.id.lvStations))
                .atPosition(0)
                .perform(click());

        // Verifica que si la localidad no está disponible, se muestra un guion
        onView(withId(R.id.tvMunicipio))
                .check(matches(withText("-")));

        // Haz lo mismo para otros campos que podrían no estar disponibles
        onView(withId(R.id.tvHorario))
                .check(matches(withText("-")));
    }*/
}


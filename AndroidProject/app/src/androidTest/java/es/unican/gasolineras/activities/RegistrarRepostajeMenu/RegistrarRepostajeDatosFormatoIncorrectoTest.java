package es.unican.gasolineras.activities.RegistrarRepostajeMenu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static es.unican.gasolineras.utils.Matchers.DrawableMatcher;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;
import android.graphics.Color;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.HasBackgroundMatcher;
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
import es.unican.gasolineras.activities.RegistrarRepostajeMenu.RegistrarView;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.IGasolinerasRepository;
import es.unican.gasolineras.utils.Matchers;

@UninstallModules(RepositoriesModule.class)
@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
public class RegistrarRepostajeDatosFormatoIncorrectoTest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<RegistrarView> activityRule = new ActivityScenarioRule<>(RegistrarView.class);

    // I need the context to access resources, such as the json with test gas stations
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    // Mock repository that provides data from a JSON file instead of downloading it from the internet.
    @BindValue
    final IGasolinerasRepository repository = getTestRepository(context, R.raw.gasolineras_ccaa_06);

    @Test
    public void datosFormatoIncorrectoContext() {
        onView(withId(R.id.textLitros)).perform(click());
        onView(withId(R.id.textLitros)).perform(typeText("Veinte"));
        Espresso.pressBackUnconditionally();
        onView(withId(R.id.textPrecioTotal)).perform(click());
        onView(withId(R.id.textPrecioTotal)).perform(typeText("20"));
        Espresso.pressBackUnconditionally();


        onView(withId(R.id.btnGuardar)).perform(click());

        onView(withId(R.id.tvError)).check(matches(withText("Error: Los datos introducidos no son válidos")));

        onView(withId(R.id.textLitros)).check(matches(new DrawableMatcher(R.drawable.border_red)));


    }

}

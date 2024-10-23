package es.unican.gasolineras.utils;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;

import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Custom matchers for Espresso tests.
 */
public class Matchers {

    public static Matcher<View> withBackgroundColor(final int expectedColor) {
        return new BoundedMatcher<View, View>(View.class) {

            public void describeTo(Description description) {
                description.appendText("with background color: ");
            }

            public boolean matchesSafely(View view) {
                int currentColor = ((ColorDrawable) view.getBackground()).getColor();
                return currentColor == expectedColor;
            }
        };
    }


}

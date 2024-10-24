package es.unican.gasolineras.utils;

import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;

/**
 * Custom matchers for Espresso tests.
 */
public class Matchers {

    public static class DrawableMatcher extends TypeSafeMatcher<View> {
        private final int expectedResId;

        public DrawableMatcher(@DrawableRes int expectedResId) {
            super(View.class);
            this.expectedResId = expectedResId;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("with background resource id: " + expectedResId);
        }

        @Override
        protected boolean matchesSafely(View view) {
            if (expectedResId == -1) {
                return view.getBackground() == null;
            }

            Drawable expectedDrawable = ResourcesCompat.getDrawable(view.getResources(), expectedResId, null);
            if (expectedDrawable == null) {
                return false;
            }

            Drawable actualDrawable = view.getBackground();
            if (actualDrawable == null) {
                return false;
            }

            // Compara los drawables asegurándose de que ambos estén bien definidos.
            return actualDrawable.getConstantState().equals(expectedDrawable.getConstantState());
        }
    }


}

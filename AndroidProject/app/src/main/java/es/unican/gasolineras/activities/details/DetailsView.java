package es.unican.gasolineras.activities.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.parceler.Parcels;

import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Gasolinera;

/**
 * View that shows the details of one gas station. Since this view does not have business logic,
 * it can be implemented as an activity directly, without the MVP pattern.
 */
public class DetailsView extends AppCompatActivity {

    /** Key for the intent that contains the gas station */
    public static final String INTENT_STATION = "INTENT_STATION";

    /**
     * @see AppCompatActivity#onCreate(Bundle)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        // The default theme does not include a toolbar.
        // In this app the toolbar is explicitly declared in the layout
        // Set this toolbar as the activity ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        assert bar != null;  // to avoid warning in the line below
        bar.setDisplayHomeAsUpEnabled(true);  // show back button in action bar

        // Link to view elements
        ImageView ivRotulo = findViewById(R.id.ivRotulo);
        TextView tvRotulo = findViewById(R.id.tvRotulo);
        TextView tvMunicipio = findViewById(R.id.tvMunicipio);
        TextView tvCP = findViewById(R.id.tvCP);
        TextView tvHorario = findViewById(R.id.tvHorario);
        TextView tvGasoleoA = findViewById(R.id.tvGasoleoA);
        TextView tvGasolina95E5 = findViewById(R.id.tvGasolina95E5);
        TextView tvPrecioSumario = findViewById(R.id.tvPrecioSumario);

        // Get Gas Station from the intent that triggered this activity
        Gasolinera gasolinera = Parcels.unwrap(getIntent().getExtras().getParcelable(INTENT_STATION));

        // Set logo
        @SuppressLint("DiscouragedApi") int imageID =
                getResources().getIdentifier("generic", "drawable", getPackageName());
        ivRotulo.setImageResource(imageID);

        // Set Texts
        tvRotulo.setText(gasolinera.getRotulo());
        tvMunicipio.setText(gasolinera.getMunicipio());
        tvCP.setText(gasolinera.getCp());
        tvHorario.setText(gasolinera.getHorario());
        tvGasoleoA.setText(String.valueOf(gasolinera.getGasoleoA()));
        tvGasolina95E5.setText(String.valueOf(gasolinera.getGasolina95E5()));
        tvPrecioSumario.setText(String.valueOf(calcularPrecioSumario(gasolinera)));
    }

    /**
     * @see AppCompatActivity#onOptionsItemSelected(MenuItem)
     * @param item The menu item that was selected.
     *
     * @return true if we are handling the selection
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public double calcularPrecioSumario(Gasolinera gasolinera) {
        double precioSumario;
        precioSumario = ((2 * gasolinera.getGasolina95E5()) + (gasolinera.getGasoleoA())) / 3;
        return precioSumario;
    }
}
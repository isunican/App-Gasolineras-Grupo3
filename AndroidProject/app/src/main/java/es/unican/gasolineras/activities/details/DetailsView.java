package es.unican.gasolineras.activities.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.parceler.Parcels;

import javax.inject.Inject;

import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.IGasolinerasRepository;


/**
 * View that shows the details of one gas station. Since this view does not have business logic,
 * it can be implemented as an activity directly, without the MVP pattern.
 */
public class DetailsView extends AppCompatActivity implements IDetails.View {

    /** Key for the intent that contains the gas station */
    public static final String INTENT_STATION = "INTENT_STATION";
    private DetailsPresenter presenter;

    /** The repository to access the data. This is automatically injected by Hilt in this class */
    @Inject
    IGasolinerasRepository repository;

    // UI Elements for displaying prices
    private TextView tvPrecioGasolina95Hoy;
    private TextView tvPrecioGasolina95SemanaPasada;
    private TextView tvPrecioDieselHoy;
    private TextView tvPrecioDieselSemanaPasada;
    private TextView tvDiferenciaGasolina95;
    private TextView tvDiferenciaDiesel;


    @Override
    public void init() {
        presenter.calcularTextoComparacion();
    }

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
        TextView tvDireccion = findViewById(R.id.tvDireccion);
        TextView tvHorario = findViewById(R.id.tvHorario);
        tvPrecioGasolina95Hoy = findViewById(R.id.tvPrecioGasolina95Hoy);
        tvPrecioGasolina95SemanaPasada = findViewById(R.id.tvPrecioGasolina95SemanaPasada);
        tvPrecioDieselHoy = findViewById(R.id.tvPrecioDieselHoy);
        tvPrecioDieselSemanaPasada = findViewById(R.id.tvPrecioDieselSemanaPasada);
        tvDiferenciaGasolina95 = findViewById(R.id.tvDiferenciaGasolina95);
        tvDiferenciaDiesel = findViewById(R.id.tvDiferenciaDiesel);



        // Get Gas Station from the intent that triggered this activity
        Gasolinera gasolinera = Parcels.unwrap(getIntent().getExtras().getParcelable(INTENT_STATION));

        // Set logo
        @SuppressLint("DiscouragedApi") int imageID =
                getResources().getIdentifier("generic", "drawable", getPackageName());

        ivRotulo.setImageResource(imageID);

        // Set Texts
        tvRotulo.setText(gasolinera.getRotulo());
        tvMunicipio.setText(gasolinera.getMunicipio());
        tvDireccion.setText(gasolinera.getCp());
        tvHorario.setText(gasolinera.getHorario());



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

    @Override
    public IGasolinerasRepository getGasolinerasRepository() {
        return repository;
    }

    // Display the updated price information in the view
    @Override
    public void mostrarPrecioGasolina95Hoy(String precioHoy) {
        tvPrecioGasolina95Hoy.setText(precioHoy);
    }

    @Override
    public void mostrarPrecioGasolina95SemanaPasada(String precioSemanaPasada) {
        tvPrecioGasolina95SemanaPasada.setText(precioSemanaPasada);
    }

    @Override
    public void mostrarPrecioDieselHoy(String precioHoy) {
        tvPrecioDieselHoy.setText(precioHoy);
    }

    @Override
    public void mostrarPrecioDieselSemanaPasada(String precioSemanaPasada) {
        tvPrecioDieselSemanaPasada.setText(precioSemanaPasada);

    }

    @Override
    public void mostrarError(String mensaje) {
        // Show error message (implement appropriate error handling for your app, like a Toast or Snackbar)
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarTextoComparacionDiesel(String textoGasoleoA) {
        tvDiferenciaDiesel.setText(textoGasoleoA);
    }

    @Override
    public void mostrarTextoComparacionGasolina95(String textoGasolina95) {
        tvDiferenciaGasolina95.setText(textoGasolina95);
    }

}
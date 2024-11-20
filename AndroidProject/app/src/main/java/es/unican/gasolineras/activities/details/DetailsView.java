package es.unican.gasolineras.activities.details;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.parceler.Parcels;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DatabaseFunction;
import es.unican.gasolineras.repository.IGasolinerasRepository;


/**
 * View that shows the details of one gas station. Since this view does not have business logic,
 * it can be implemented as an activity directly, without the MVP pattern.
 */
@AndroidEntryPoint
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
    private TextView tvDiaSemanaPasada;
    private TextView tvDiaSemanaPasada2;
    private TextView tvDiferenciaGasolina95;
    private TextView tvDiferenciaDiesel;
    private TextView tvNoDisponibleGasolina95;
    private TextView tvNoDisponibleDiesel;
    private TextView tvHoy;
    private TextView tvHoy2;
    Gasolinera gasolinera;



    /**
     * @see AppCompatActivity#onCreate(Bundle)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        // Get Gas Station from the intent that triggered this activity
        gasolinera = Parcels.unwrap(getIntent().getExtras().getParcelable(INTENT_STATION));

        // Instantiate presenter and initialize the view
        AppDatabase db = DatabaseFunction.getDatabase(this);
        presenter = new DetailsPresenter(gasolinera,db.descuentoDao());
        presenter.init(this);

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
        tvDiaSemanaPasada = findViewById(R.id.tvDiaSemanaPasada);
        tvDiaSemanaPasada2 = findViewById(R.id.tvDiaSemanaPasada2);
        tvDiferenciaGasolina95 = findViewById(R.id.tvDiferenciaGasolina95);
        tvDiferenciaDiesel = findViewById(R.id.tvDiferenciaDiesel);
        tvNoDisponibleGasolina95 = findViewById(R.id.tvNoDisponibleGasolina95);
        tvNoDisponibleDiesel = findViewById(R.id.tvNoDisponibleDiesel);
        tvHoy = findViewById(R.id.tvHoy);
        tvHoy2 = findViewById(R.id.tvHoy2);


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


    /**
     * @see IDetails.View#getGasolinerasRepository()
     */
    @Override
    public IGasolinerasRepository getGasolinerasRepository() {
        return repository;
    }

    /**
     * @see IDetails.View#mostrarPreciosSemanaPasada(double, double, String, double, double)
     *
     * @param precioSemanaPasadaGasolina95 precio de gasolina de la gasolinera seleccionada
     * @param precioSemanaPasadaDiesel precio de disel de la gasolinera seleccionada
     * @param diferenciaGasolina diferencia de gasolina actual y de la semana anterior
     * @param diferenciaDiesel diferencia del precio disel actual y de la semana anterior
     * @param dia dia de la semana en el que se consulta la gasolina
     */
    @Override
    public void mostrarPreciosSemanaPasada(double precioSemanaPasadaDiesel , double precioSemanaPasadaGasolina95, String dia,double diferenciaGasolina, double diferenciaDiesel) {
        // Se comprueba si hay disponibilidad del precio de la gasolina
        if (precioSemanaPasadaGasolina95 != 0.0) {

            tvPrecioGasolina95SemanaPasada.setText(String.format("%.2f", precioSemanaPasadaGasolina95));
            tvPrecioGasolina95SemanaPasada.setVisibility(View.VISIBLE);

            // Mostrar la diferencia de la gasolina
            String diferenciaTextoGasolina = String.format("%.2f", diferenciaGasolina);
            if (diferenciaGasolina > 0) {
                // Si la diferencia es positiva, agregar el signo '+' y los paréntesis
                tvDiferenciaGasolina95.setText("(+ " + diferenciaTextoGasolina + ")");
            } else if (diferenciaGasolina < 0) {
                // Si la diferencia es negativa, solo mostrar el valor con el signo negativo
                tvDiferenciaGasolina95.setText("(- " + diferenciaTextoGasolina.substring(1) + ")");
            } else {
                // Si la diferencia es cero, no se muestra nada
                tvDiferenciaGasolina95.setText("");
            }

            tvDiferenciaGasolina95.setVisibility(View.VISIBLE);
            // Se muestra el dia de la semana
            tvDiaSemanaPasada.setText(dia + " pasado:") ;
            tvDiaSemanaPasada.setVisibility(View.VISIBLE);
        }
        else {
            tvPrecioGasolina95SemanaPasada.setVisibility(View.GONE);
        }

        // Se comprueba si hay disponibilidad del precio del diesel
        if (precioSemanaPasadaDiesel != 0.0) {

            tvPrecioDieselSemanaPasada.setText(String.format("%.2f", precioSemanaPasadaDiesel));
            tvPrecioDieselSemanaPasada.setVisibility(View.VISIBLE);

            // Mostrar la diferencia de disel
            String diferenciaTextoDiesel = String.format("%.2f", diferenciaDiesel);
            if (diferenciaDiesel > 0) {
                tvDiferenciaDiesel.setText("(+ " + diferenciaTextoDiesel + ")");
            } else if (diferenciaDiesel < 0) {
                // Si la diferencia es negativa, mostramos el valor con el signo negativo
                tvDiferenciaDiesel.setText("(- " + diferenciaTextoDiesel.substring(1) + ")");
            } else {
                // Si la diferencia es cero, mostramos ""
                tvDiferenciaDiesel.setText("");
            }

            tvDiferenciaDiesel.setVisibility(View.VISIBLE);
            // Se muestra el dia de la semana
            tvDiaSemanaPasada2.setText(dia + " pasado:") ;
            tvDiaSemanaPasada2.setVisibility(View.VISIBLE);
        }

        else {
            tvPrecioDieselSemanaPasada.setVisibility(View.GONE);
        }

    }


    /**
     * @see IDetails.View#mostrarPreciosActuales(double, double)
     *
     * @param precioGasolina precio de gasolina de la gasolinera seleccionada
     * @param precioDiesel precio de disel de la gasolinera seleccionada
     *

     */
    @Override
    public void mostrarPreciosActuales(double precioGasolina, double precioDiesel) {
        // Verificar si el precio de la gasolina 95 es 0.0
        if (gasolinera.getGasolina95E5() != 0.0) {
            tvPrecioGasolina95Hoy.setText(String.format("%.2f", precioGasolina));
            tvPrecioGasolina95Hoy.setVisibility(View.VISIBLE);
        } else {
            // En el caso de que el precio de la gasolina sea 0.0 significa
            // que no hay datos registrados y por tanto no esta disponible.
            tvNoDisponibleGasolina95.setText("(No disponible)");
            tvNoDisponibleGasolina95.setVisibility(View.VISIBLE);
            tvHoy.setVisibility(View.GONE);
        }

        // Verificar si el precio de diésel es 0.0
        if (gasolinera.getGasoleoA() != 0.0) {
            tvPrecioDieselHoy.setText(String.format("%.2f", precioDiesel));
            tvPrecioDieselHoy.setVisibility(View.VISIBLE);

        } else {
            // En el caso de que el precio del diesel sea 0.0 significa
            // que no hay datos registrados y por tanto no esta disponible.
            tvNoDisponibleDiesel.setText("(No disponible)");
            tvNoDisponibleDiesel.setVisibility(View.VISIBLE);
            tvHoy2.setVisibility(View.GONE);

        }
    }


    /**
     * @see IDetails.View#mostrarError
     *
     * @param mensaje the error message to show
     */
    @Override
    public void mostrarError(String mensaje) {
        // Show error message (implement appropriate error handling for your app, like a Toast or Snackbar)
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

    }


}
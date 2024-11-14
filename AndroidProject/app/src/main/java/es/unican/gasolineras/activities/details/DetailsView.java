package es.unican.gasolineras.activities.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DatabaseFunction;
import es.unican.gasolineras.repository.GasolinerasRepository;
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
    Gasolinera gasolinera;



    @Override
    public void init() {

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
        tvDiaSemanaPasada = findViewById(R.id.tvDiaSemanaPasada);
        tvDiaSemanaPasada2 = findViewById(R.id.tvDiaSemanaPasada2);
        tvDiferenciaGasolina95 = findViewById(R.id.tvDiferenciaGasolina95);
        tvDiferenciaDiesel = findViewById(R.id.tvDiferenciaDiesel);
        tvNoDisponibleGasolina95 = findViewById(R.id.tvNoDisponibleGasolina95);
        tvNoDisponibleDiesel = findViewById(R.id.tvNoDisponibleDiesel);


        // Get Gas Station from the intent that triggered this activity
        gasolinera = Parcels.unwrap(getIntent().getExtras().getParcelable(INTENT_STATION));

        // Set logo
        @SuppressLint("DiscouragedApi") int imageID =
                getResources().getIdentifier("generic", "drawable", getPackageName());

        ivRotulo.setImageResource(imageID);

        // Set Texts
        tvRotulo.setText(gasolinera.getRotulo());
        tvMunicipio.setText(gasolinera.getMunicipio());
        tvDireccion.setText(gasolinera.getCp());
        tvHorario.setText(gasolinera.getHorario());

        AppDatabase db = DatabaseFunction.getDatabase(this);
        presenter = new DetailsPresenter(gasolinera,db.descuentoDao());
        presenter.init(this);

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

    public void mostrarPreciosActuales(Descuento descuento) {

        if (descuento == null ) {
            tvPrecioGasolina95Hoy.setText(String.format("%.2f",gasolinera.getGasolina95E5()));
            tvPrecioDieselHoy.setText(String.format("%.2f",gasolinera.getGasoleoA()));
        }
        else {
            double precioGasolina = gasolinera.getGasolina95E5() * (1 - descuento.getDescuento() / 100);
            tvPrecioGasolina95Hoy.setText(String.format("%.2f",precioGasolina));
            double precioDiesel = gasolinera.getGasoleoA() * (1 - descuento.getDescuento() / 100);
            tvPrecioDieselHoy.setText(String.format("%.2f",precioDiesel));
        }

    }


    @Override
    public void mostrarPrecioGasolina95SemanaPasada(double precioSemanaPasada,Descuento descuento) {

        double precioConDescuento = gasolinera.getGasolina95E5();
        if (descuento != null) {
            precioConDescuento = precioConDescuento * (1 - descuento.getDescuento() / 100); // Precio con descuento
        }

        tvPrecioGasolina95SemanaPasada.setText(String.format("%.2f", precioSemanaPasada));
        tvPrecioGasolina95SemanaPasada.setVisibility(View.VISIBLE);

        // Si hay descuento, calculamos la diferencia con el precio con descuento
        double diferenciaGasolina = precioSemanaPasada - precioConDescuento;

        if (gasolinera.getGasolina95E5() == 0) {
            tvNoDisponibleGasolina95.setText("No disponible");
            tvNoDisponibleGasolina95.setVisibility(View.VISIBLE);
        } else {
            // Formateamos la diferencia con el signo adecuado y entre paréntesis
            String diferenciaTexto = String.format("%.2f", diferenciaGasolina);
            if (diferenciaGasolina > 0) {
                // Si la diferencia es positiva, agregar el signo '+' y los paréntesis
                tvDiferenciaGasolina95.setText("(+ " + diferenciaTexto + ")");
            } else if (diferenciaGasolina < 0) {
                // Si la diferencia es negativa, solo mostrar el valor con el signo negativo
                tvDiferenciaGasolina95.setText("(- " + diferenciaTexto.substring(1) + ")");
            } else {
                // Si la diferencia es cero, mostrar simplemente "0"
                tvDiferenciaGasolina95.setText("");
            }

            tvDiferenciaGasolina95.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void mostrarPrecioDieselSemanaPasada(double precioSemanaPasada,Descuento descuento) {

        double precioConDescuento = gasolinera.getGasoleoA();
        if (descuento != null) {
            precioConDescuento = precioConDescuento * (1 - descuento.getDescuento() / 100); // Precio con descuento
        }

        tvPrecioDieselSemanaPasada.setText(String.format("%.2f", precioSemanaPasada));
        tvPrecioDieselSemanaPasada.setVisibility(View.VISIBLE);

        // Si hay descuento, calculamos la diferencia con el precio con descuento
        double diferenciaDiesel = precioSemanaPasada - precioConDescuento;

        // Verificamos si el precio es 0, en ese caso mostramos "(No disponible)"
        if (gasolinera.getGasoleoA() == 0) {
            tvNoDisponibleDiesel.setText("No disponible");
            tvNoDisponibleDiesel.setVisibility(View.VISIBLE);
        } else {
            // Si la diferencia es mayor a 0, mostramos la diferencia con "+" y entre paréntesis
            String diferenciaTexto = String.format("%.2f", diferenciaDiesel);
            if (diferenciaDiesel > 0) {
                tvDiferenciaDiesel.setText("(+ " + diferenciaTexto + ")");
            } else if (diferenciaDiesel < 0) {
                // Si la diferencia es negativa, mostramos el valor con el signo negativo
                tvDiferenciaDiesel.setText("(- " + diferenciaTexto.substring(1) + ")");
            } else {
                // Si la diferencia es cero, mostramos "(0.00)"
                tvDiferenciaDiesel.setText("(0.00)");
            }
        }

        tvDiferenciaDiesel.setVisibility(View.VISIBLE);
    }


    @Override
    public void mostrarDiaDeLaSemana(String texto) {
        tvDiaSemanaPasada.setText(texto + " pasado:") ;
        tvDiaSemanaPasada.setVisibility(View.VISIBLE);
        tvDiaSemanaPasada2.setText(texto + " pasado:") ;
        tvDiaSemanaPasada2.setVisibility(View.VISIBLE);
    }

    @Override
    public void mostrarError(String mensaje) {
        // Show error message (implement appropriate error handling for your app, like a Toast or Snackbar)
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

    }


}
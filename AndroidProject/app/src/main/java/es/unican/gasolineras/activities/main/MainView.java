package es.unican.gasolineras.activities.main;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.ConsultarRepostaje.ConsultarView;
import es.unican.gasolineras.activities.RegistrarDescuentoEnMarca.RegistrarDescuentoView;
import es.unican.gasolineras.activities.RegistrarRepostajeMenu.RegistrarView;
import es.unican.gasolineras.activities.info.InfoView;
import es.unican.gasolineras.activities.details.DetailsView;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.IGasolinerasRepository;


/**
 * The main view of the application. It shows a list of gas stations.
 */
@AndroidEntryPoint
public class MainView extends AppCompatActivity implements IMainContract.View {

    /** The presenter of this view */
    private MainPresenter presenter;

    /** The repository to access the data. This is automatically injected by Hilt in this class */
    @Inject
    IGasolinerasRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The default theme does not include a toolbar.
        // In this app the toolbar is explicitly declared in the layout
        // Set this toolbar as the activity ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // instantiate presenter and launch initial business logic
        presenter = new MainPresenter();
        presenter.init(this);
    }



    /**
     * This creates the menu that is shown in the action bar (the upper toolbar)
     * @param menu The options menu in which you place your items.
     *
     * @return true because we are defining a new menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * This is called when an item in the action bar menu is selected.
     * @param item The menu item that was selected.
     *
     * @return true if we have handled the selection
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuItemInfo) {
            presenter.onMenuInfoClicked();
            return true;
        }
        if (itemId == R.id.RegistrarRepostajeItem) {
            presenter.onMenuRegistrarClicked();
            return true;
        }
        if (itemId == R.id.ConsultarRepostajeItem) {
            presenter.onMenuConsultarClicked();
            return true;
        }
        if(itemId == R.id.menuItemDescuento) {
            presenter.onMenuDescuentoClicked();
            return true;
        }
        if (itemId == R.id.FiltrarMunicipiosItem) {

            // Acceder al array de municipios desde los recursos
            final String[] municipios = getResources().getStringArray(R.array.municipiosArray);


            final Spinner spinner = new Spinner(this);


            // Crear un TextView para mostrar el texto "Municipio:"
            TextView txtMunicipio = new TextView(this);
            txtMunicipio.setText("Municipio:");  // Texto que se mostrará antes del Spinner
            txtMunicipio.setPadding(0, 0, 0, 10);  // Añadir un poco de padding debajo para espacio

            // Crear el ArrayAdapter para el Spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, municipios);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            // Crear el layout para el AlertDialog
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);  // Colocar los elementos en columna (vertical)
            layout.setPadding(40, 40, 40, 40);  // Añadir algo de padding alrededor

            // Configurar márgenes para el spinner
            LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            spinnerParams.setMargins(0, 0, 0, 20);  // Márgenes en la parte inferior
            spinner.setLayoutParams(spinnerParams);

            // Configurar márgenes para el TextView (Municipio:)
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textParams.setMargins(0, 0, 0, 10);  // Márgenes en la parte inferior (espacio entre el texto y el spinner)
            txtMunicipio.setLayoutParams(textParams);

            // Botón Filtrar
            Button btnFiltrar = new Button(this);
            btnFiltrar.setText("Filtrar");
            LinearLayout.LayoutParams filterBtnParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            filterBtnParams.setMargins(0, 20, 0, 0);  // Márgenes en la parte superior
            btnFiltrar.setLayoutParams(filterBtnParams);

            // Botón Cancelar
            Button btnCancelar = new Button(this);
            btnCancelar.setText("Cancelar");
            LinearLayout.LayoutParams cancelBtnParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            cancelBtnParams.setMargins(0, 20, 0, 0);  // Márgenes en la parte superior
            btnCancelar.setLayoutParams(cancelBtnParams);

            // Añadir el TextView y el Spinner al layout
            layout.addView(txtMunicipio);
            layout.addView(spinner);
            layout.addView(btnFiltrar);
            layout.addView(btnCancelar);

            // Crear el AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Filtrar");
            builder.setView(layout);  // Usamos el layout que contiene el TextView, Spinner y los botones

            // No se añade lógica a los botones (sin OnClickListener)

            // Crear y mostrar el dialog
            builder.create().show();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * @see IMainContract.View#init()
     */
    @Override
    public void init() {
        // initialize on click listeners (when clicking on a station in the list)
        ListView list = findViewById(R.id.lvStations);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Gasolinera station = (Gasolinera) parent.getItemAtPosition(position);
            presenter.onStationClicked(station);
        });
    }

    /**
     * @see IMainContract.View#getGasolinerasRepository()
     * @return the repository to access the data
     */
    @Override
    public IGasolinerasRepository getGasolinerasRepository() {
        return repository;
    }

    /**
     * @see IMainContract.View#showStations(List) 
     * @param stations the list of charging stations
     */
    @Override
    public void showStations(List<Gasolinera> stations) {
        ListView list = findViewById(R.id.lvStations);
        GasolinerasArrayAdapter adapter = new GasolinerasArrayAdapter(this, stations);
        list.setAdapter(adapter);
    }

    /**
     * @see IMainContract.View#showLoadCorrect(int)
     * @param stations
     */
    @Override
    public void showLoadCorrect(int stations) {
        Toast.makeText(this, "Cargadas " + stations + " gasolineras", Toast.LENGTH_SHORT).show();
    }

    /**
     * @see IMainContract.View#showLoadError()
     */
    @Override
    public void showLoadError() {
        Toast.makeText(this, "Error cargando las gasolineras", Toast.LENGTH_SHORT).show();
    }

    /**
     * @see IMainContract.View#showStationDetails(Gasolinera)
     * @param station the charging station
     */
    @Override
    public void showStationDetails(Gasolinera station) {
        Intent intent = new Intent(this, DetailsView.class);
        intent.putExtra(DetailsView.INTENT_STATION, Parcels.wrap(station));
        startActivity(intent);
    }

    /**
     * @see IMainContract.View#showInfoActivity()
     */
    @Override
    public void showInfoActivity() {
        Intent intent = new Intent(this, InfoView.class);
        startActivity(intent);
    }

    /**
     * @see IMainContract.View#showRegistrarActivity()
     */
    @Override
    public void showRegistrarActivity() {
        Intent intent = new Intent(this, RegistrarView.class);
        startActivity(intent);
    }


    /**
     * @see IMainContract.View#showConsultarActivity()
     */
    @Override
    public void showConsultarActivity() {
        Intent intent = new Intent(this, ConsultarView.class);
        startActivity(intent);
    }

    /**
     * @see IMainContract.View#showDescuentoActivity()
     */
    @Override
    public void showDescuentoActivity() {
        Intent intent = new Intent(this, RegistrarDescuentoView.class);
        startActivity(intent);
    }

    @Override
    public void mostrarErrorNoGaolinerasEnMunicipio(String s) {
        
    }

    @Override
    public void showBtnCancelarFiltro() {
    }
}
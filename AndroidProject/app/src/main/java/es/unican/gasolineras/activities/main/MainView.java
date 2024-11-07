package es.unican.gasolineras.activities.main;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.ConsultarRepostaje.ConsultarView;
import es.unican.gasolineras.activities.RegistrarDescuentoEnMarca.RegistrarDescuentoView;
import es.unican.gasolineras.activities.RegistrarRepostajeMenu.RegistrarView;
import es.unican.gasolineras.activities.info.InfoView;
import es.unican.gasolineras.activities.details.DetailsView;
import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DatabaseFunction;
import es.unican.gasolineras.repository.DescuentoDAO;
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

    //Database which is used in the app
    public AppDatabase db;

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
        db = DatabaseFunction.getDatabase(this);
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

            View dialogView = getLayoutInflater().inflate(R.layout.activity_filtrar, null);

            Spinner spinner = dialogView.findViewById(R.id.spMunicipios);
            Button btnFiltrar = dialogView.findViewById(R.id.btnFiltrar);
            Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);

            String[] municipios = getResources().getStringArray(R.array.municipiosArray);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, municipios);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            String filtro = presenter.hayFiltroActivado();
            if (filtro != null) {
                spinner.setSelection(Arrays.asList(municipios).indexOf(filtro));
            } else {
                spinner.setSelection(Arrays.asList(municipios).indexOf("Mostrar todos"));
            }

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Filtrar")
                    .setView(dialogView) // Agrega el layout personalizado al diálogo
                    .create();


            btnFiltrar.setOnClickListener(v -> {
                String municipioSeleccionado = spinner.getSelectedItem().toString();
                presenter.onBtnFiltrarClicked(municipioSeleccionado);
                dialog.dismiss();
            });

            btnCancelar.setOnClickListener(v -> dialog.dismiss());

            dialog.show();
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
        GasolinerasArrayAdapter adapter = new GasolinerasArrayAdapter(this, stations, db.descuentoDao());
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

    /**
     * @see IMainContract.View#mostrarErrorNoGasolinerasEnMunicipio(String)
     * @param mensajeError the error message to show
     */
    @Override
    public void mostrarErrorNoGasolinerasEnMunicipio(String mensajeError) {
        DescuentoDAO descuentoDAO = db.descuentoDao();
        // Crear una lista temporal que contenga solo el mensaje de error
        List<Gasolinera> emptyMessage = new ArrayList<>();
        Gasolinera gasolinera = new Gasolinera();
        gasolinera.setDireccion(mensajeError);
        gasolinera.setError(true);
        emptyMessage.add(gasolinera);  // Usa el mensaje que se pasa como argumento

        ListView list = findViewById(R.id.lvStations);
        GasolinerasArrayAdapter adapter = new GasolinerasArrayAdapter(this, emptyMessage, descuentoDAO);
        list.setAdapter(adapter);
    }

    /**
     * @see IMainContract.View#showBtnCancelarFiltro()
     */
    @Override
    public void showBtnCancelarFiltro() {
        Intent intent = new Intent(MainView.this, MainView.class);
        startActivity(intent);
    }
}
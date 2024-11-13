package es.unican.gasolineras.activities.main;

import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.IDCCAAs;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;

/**
 * The presenter of the main activity of the application. It controls {@link MainView}
 */
public class MainPresenter implements IMainContract.Presenter {

    /** The view that is controlled by this presenter */
    private IMainContract.View view;

    List<Gasolinera> gasolineras;
    List<Gasolinera> listaGasolineras = new ArrayList<>();

    public Boolean filtroActivado = false;
    public String filtroActual;

    /**
     * @see IMainContract.Presenter#init(IMainContract.View)
     * @param view the view to control
     */
    @Override
    public void init(IMainContract.View view) {
        this.view = view;
        this.view.init();
        load();
    }

    /**
     * @see IMainContract.Presenter#onStationClicked(Gasolinera)
     * @param station the station that has been clicked
     */
    @Override
    public void onStationClicked(Gasolinera station) {
        view.showStationDetails(station);
    }

    /**
     * @see IMainContract.Presenter#onMenuInfoClicked()
     */
    @Override
    public void onMenuInfoClicked() {
        view.showInfoActivity();
    }

    /**
     * @see IMainContract.Presenter#onMenuRegistrarClicked()
     */
    @Override
    public void onMenuRegistrarClicked() { view.showRegistrarActivity();}

    /**
     * @see IMainContract.Presenter#onMenuConsultarClicked()
     */
    @Override
    public void onMenuConsultarClicked() {
        view.showConsultarActivity();
    }

    /**
     * @see IMainContract.Presenter#onMenuDescuentoClicked()
     */
    @Override
    public void onMenuDescuentoClicked()  { view.showDescuentoActivity();}

    /**
     * @see IMainContract.Presenter#onBtnFiltrarClicked(String)
     * @param municipio el municipio a aplicar como filtro
     */
    @Override
    public void onBtnFiltrarClicked(String municipio) {


        List<Gasolinera> listaFiltrada = new ArrayList<>();

        for (Gasolinera gasolinera : listaGasolineras) {
            if (gasolinera.getMunicipio().equals(municipio) || municipio.equals("Mostrar todos")) {
                listaFiltrada.add(gasolinera);
            }
        }

        if (!municipio.equals("Mostrar todos")) {
            filtroActual = activarFiltro(municipio);
        }
        else {
            filtroActivado = false;
        }

        if (listaFiltrada.isEmpty()) {

            view.mostrarErrorNoGasolinerasEnMunicipio("Error: No existen gasolineras \n con el filtro aplicado");
            return;
        }

        view.showStations(listaFiltrada);


    }

    /**
     * @see IMainContract.Presenter#onBtnCancelarFiltroClicked()
     */
    @Override
    public void onBtnCancelarFiltroClicked() {
        view.showBtnCancelarFiltro();
    }



    /**
     * Loads the gas stations from the repository, and sends them to the view
     */
    private void load() {
        IGasolinerasRepository repository = view.getGasolinerasRepository();

        ICallBack callBack = new ICallBack() {

            @Override
            public void onSuccess(List<Gasolinera> stations) {
                gasolineras = stations;
                view.showStations(stations);
                view.showLoadCorrect(stations.size());
                listaGasolineras.addAll(stations);
            }

            @Override
            public void onFailure(Throwable e) {
                view.showLoadError();
                view.showLoadError();
            }
        };

        repository.requestGasolineras(callBack, IDCCAAs.CANTABRIA.id);
    }

    /**
     * Activa el filtro sobre un municipio dado
     * @param municipio municipio sobre el que activar el filtro
     * @return el municipio sobre el que se ha filtrado
     */
    public String activarFiltro(String municipio) {
        filtroActivado = true;
        return municipio;
    }

    /**
     * Comprueba si hay un filtro activado
     * @return el filtro que hay activado o null si no hay ninguno
     */
    public String hayFiltroActivado() {
        if (filtroActivado) {
            return filtroActual;
        }
        else {
            return null;
        }
    }
}

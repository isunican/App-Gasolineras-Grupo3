package es.unican.gasolineras.activities.main;

import java.util.ArrayList;
import java.util.List;

import es.unican.gasolineras.activities.RegistrarRepostajeMenu.IRegistrar;
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

    private List<Gasolinera> gasolineras;
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
    public void onBtnFiltrarClicked(String municipio) {

        List<Gasolinera> listaFiltrada = new ArrayList<>();

        for (Gasolinera gasolinera : listaGasolineras) {
            if (gasolinera.getMunicipio().equals(municipio) || municipio.equals("Mostrar todos")) {
                listaFiltrada.add(gasolinera);
            }
        }

        if (listaFiltrada.isEmpty()) {
            view.mostrarErrorNoGaolinerasEnMunicipio("Error: No exiten gasolineras con el filtro aplicado");
            return;
        }

        view.showStations(listaFiltrada);
        filtroActual = activarFiltro(municipio);


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

    public String activarFiltro(String municipio) {

        filtroActivado = true;
        return municipio;
    }

    public String hayFiltroActivado() {
        if (filtroActivado) {
            return filtroActual;
        }
        else {
            return null;
        }
    }
}

package es.unican.gasolineras.activities.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.unican.gasolineras.model.Descuento;
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
    List<Gasolinera> listaFiltrada;

    public Boolean filtroActivado = false;
    public String filtroActual;

    public Boolean ordenamientoActivado = false;
    public String ordenamientoActual;

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

        listaFiltrada = new ArrayList<>();

        for (Gasolinera gasolinera : listaGasolineras) {
            if (gasolinera.getMunicipio().equals(municipio) || municipio.equals("Mostrar todos")) {
                listaFiltrada.add(gasolinera);
            }
        }

        if (listaFiltrada.isEmpty()) {

            view.mostrarErrorNoGasolinerasEnMunicipio("Error: No existen gasolineras \n con el filtro aplicado");
            return;
        }

        filtroActivado = false;

        if (!municipio.equals("Mostrar todos")) {
            filtroActual = activarFiltro(municipio);
        }

        String tipoCombustible = hayOrdenamientoActivado();
        if(tipoCombustible == null) {
            view.showStations(listaFiltrada);
            return;

        }

        onBtnOrdenarClicked(tipoCombustible);


    }

    /**
     * @see IMainContract.Presenter#onBtnCancelarFiltroClicked()
     */
    @Override
    public void onBtnCancelarFiltroClicked() {
        view.showBtnCancelarFiltro();
    }

    /**
     * @see IMainContract.Presenter#onBtnOrdenarClicked(String)
     */
    @Override
    public void onBtnOrdenarClicked(String tipoCombustible) {

        List<Gasolinera> copiaGasolineras;

        if(hayFiltroActivado() != null) {
            copiaGasolineras = new ArrayList<>(listaFiltrada);
        } else {
            copiaGasolineras = new ArrayList<>(listaGasolineras);
        }


        copiaGasolineras.removeIf(gasolinera -> {
            if (tipoCombustible.equals("Gasolina")) {
                return gasolinera.getGasolina95E5() == 0; // Eliminar si el precio de gasolina es 0
            } else {
                return gasolinera.getGasoleoA() == 0; // Eliminar si el precio de diésel es 0
            }
        });
        Collections.sort(copiaGasolineras,(g1, g2) -> {
            double preciog1 = calcularPrecioConDescuento(g1, tipoCombustible);
            double preciog2 = calcularPrecioConDescuento(g2, tipoCombustible);
            return Double.compare(preciog1, preciog2);
        });

        ordenamientoActual = tipoCombustible;
        activarOrdenamiento();
        view.showStations(copiaGasolineras);

    }


    /**
     * Calcula el precio de un tipo de combustible de una gasolinera
     * teniendo en cuenta si tiene un descuento aplicado
     * @param g1 la gasolinera de la que calcular el precio
     * @param tipoCombustible tipo de combustible del que se quiere el precio
     * @return el precio con el descuento aplicado si le hubiera
     */
    public double calcularPrecioConDescuento(Gasolinera g1, String tipoCombustible) {
        double precio;
        Descuento descuento = view.getDescuentoDatabase().descuentoPorMarca(g1.getRotulo());
        double descuentoPorcentaje = 0.0;
        if (descuento != null) {
            descuentoPorcentaje = descuento.descuento;
        }

        if (tipoCombustible.equals("Gasolina")) {
            precio = g1.getGasolina95E5();

        } else {
            precio = g1.getGasoleoA();
        }

        if (descuentoPorcentaje > 0) {
            precio = precio - ((precio * descuentoPorcentaje) / 100);
        }

        return precio;
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

    /**
     * Comprueba si hay ordenamiento
     * @return null si no esta activado o
     * el tipo de combustible por el que se esta ordenando
     */
    public String hayOrdenamientoActivado() {
        if (ordenamientoActivado) {
            return ordenamientoActual;
        }
        else {
            return null;
        }
    }

    /**
     * Activa el ordenamiento
     */
    public void activarOrdenamiento() {
        ordenamientoActivado = true;
    }
}

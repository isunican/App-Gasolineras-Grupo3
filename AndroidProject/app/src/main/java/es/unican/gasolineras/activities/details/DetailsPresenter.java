package es.unican.gasolineras.activities.details;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;


import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.IDCCAAs;
import es.unican.gasolineras.repository.DescuentoDAO;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;


public class DetailsPresenter implements  IDetails.Presenter {

    /** The view that is controlled by this presenter */
    private IDetails.View view;
    private DescuentoDAO descuentoDAO;
    IGasolinerasRepository repository;
    Gasolinera gasolinera;


    public DetailsPresenter(Gasolinera gas,DescuentoDAO descuentoDAO) {
        this.gasolinera = gas;
        this.descuentoDAO = descuentoDAO;
    }

    /**
     * @see IDetails.Presenter#init(IDetails.View)
     * @param view the view to control
     */
    @Override
    public void init(IDetails.View view) {
        this.view = view;
        this.repository = view.getGasolinerasRepository();
        load();
    }

    /**
     * Carga los datos a mostrar desde la vista
     */
    private void load() {

        // Recoger la fecha actual y la fecha de la semana pasada
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaSemanaPasada = fechaActual.minusWeeks(1);

        // Obtener precios de la semana pasada
        requestGasolineras(fechaSemanaPasada, new ICallBack() {
            @Override
            public void onSuccess(List<Gasolinera> estacionesSemanaPasada) {
                // Filtrar la gasolinera seleccionada en la respuesta
                Gasolinera gasolineraSemanaPasada = estacionesSemanaPasada.stream()
                        .filter(g -> g.getId().equals(gasolinera.getId()))
                        .findFirst()
                        .orElse(null);

                // Comprobar si la gasolinera tiene un descueto registrado
                Descuento d = descuentoDAO.descuentoPorMarca(gasolinera.getRotulo());
                // Se recogen los precios actuales de la gasolinera
                double precioGasolina = gasolinera.getGasolina95E5();
                double precioDiesel = gasolinera.getGasoleoA();

                if (gasolineraSemanaPasada != null) {
                    double precioSemanaAnteriorGasoleoA = gasolineraSemanaPasada.getGasoleoA();
                    double precioSemanaAnteriorGasolina95 = gasolineraSemanaPasada.getGasolina95E5();
                    // Mostrar los precios actuales en la vista
                    if (d != null) {
                         precioGasolina = gasolinera.getGasolina95E5() * (1 - d.getDescuento() / 100);
                         precioDiesel = gasolinera.getGasoleoA() * (1 - d.getDescuento() / 100);
                    }
                    view.mostrarPreciosActuales(precioGasolina, precioDiesel);
                    String dia = obtenerNombreDelDia(fechaActual);
                    // Calculo de la diferencia
                    // Si hay descuento, calculamos la diferencia con el precio con descuento
                    double diferenciaGasolina = precioSemanaAnteriorGasolina95 - precioGasolina;
                    double diferenciaDiesel = precioSemanaAnteriorGasoleoA - precioDiesel;

                    // Mostrar los precios de la semana pasada en la vista
                    view.mostrarPreciosSemanaPasada(precioSemanaAnteriorGasoleoA,precioSemanaAnteriorGasolina95,dia,diferenciaGasolina,diferenciaDiesel);
                }
                else {
                    // En el caso de que la gasolinera seleccionada no tenga datos para la semana pasada
                    // unicamente se muestran los precios acuales.
                    view.mostrarPreciosActuales(precioGasolina, precioDiesel);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                view.mostrarError("Error al cargar datos de la semana pasada");
            }
        });
    }


    /**
     * Obtiene el nombre del dia de la semana desde el que se consulta el precio de la gasolinera.
     * @param fechaActual fecha del dia en el que se consulta
     */
    private String obtenerNombreDelDia(LocalDate fechaActual) {
        String dia = fechaActual.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        return dia.substring(0, 1).toUpperCase() + dia.substring(1).toLowerCase();
    }


    /**
     * Realiza la llamada correspondiente al historico de gasolineras con la fecha determinada
     * @param fecha fecha del dia en el que se consulta
     * @param callBack llamada al callBack
     */
    private void requestGasolineras(LocalDate fecha, ICallBack callBack) {
        repository.requestGasolinerasHistoricoFechas(callBack, IDCCAAs.CANTABRIA.id, fecha);
    }


}

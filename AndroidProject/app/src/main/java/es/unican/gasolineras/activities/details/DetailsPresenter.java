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
                Descuento d = descuentoDAO.descuentoPorMarca(gasolinera.getRotulo());
                if (gasolineraSemanaPasada != null) {
                    double precioSemanaAnteriorGasoleoA = gasolineraSemanaPasada.getGasoleoA();
                    double precioSemanaAnteriorGasolina95 = gasolineraSemanaPasada.getGasolina95E5();

                    view.mostrarPreciosActuales(d);
                    String dia = obtenerNombreDelDia(fechaActual);
                    // Mostrar los precios de la semana pasada en la vista
                    view.mostrarPrecioDieselSemanaPasada(precioSemanaAnteriorGasoleoA ,d,dia);
                    view.mostrarPrecioGasolina95SemanaPasada(precioSemanaAnteriorGasolina95 ,d,dia);

                }
                else {
                    view.mostrarPreciosActuales(d);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                view.mostrarError("Error al cargar datos de la semana pasada");
            }
        });
    }

    public String obtenerNombreDelDia(LocalDate fechaActual) {
        String dia = fechaActual.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        return dia.substring(0, 1).toUpperCase() + dia.substring(1).toLowerCase();
    }

    private void requestGasolineras(LocalDate fecha, ICallBack callBack) {
        repository.requestGasolinerasHistoricoFechas(callBack, IDCCAAs.CANTABRIA.id, fecha);
    }


}

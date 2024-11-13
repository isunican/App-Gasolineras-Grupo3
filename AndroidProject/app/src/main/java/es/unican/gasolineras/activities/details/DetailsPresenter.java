package es.unican.gasolineras.activities.details;

import java.time.LocalDate;
import java.util.List;


import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.IDCCAAs;
import es.unican.gasolineras.repository.DescuentoDAO;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;


public class DetailsPresenter implements  IDetails.Presenter {


    private IDetails.View view;
    private DescuentoDAO descuentoDAO;
    IGasolinerasRepository repository = view.getGasolinerasRepository();


    /**
     * @see IDetails.Presenter#init(IDetails.View)
     * @param view the view to control
     */
    @Override
    public void init(IDetails.View view) {

        this.view = view;
        this.view.init();
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
                double precioSemanaAnteriorGasoleoA = obtenerPrecioConDescuento("GasoleoA", estacionesSemanaPasada);
                double precioSemanaAnteriorGasolina95 = obtenerPrecioConDescuento("Gasolina95", estacionesSemanaPasada);

                // Mostrar precios de la semana pasada
                mostrarPreciosSemanaPasada(precioSemanaAnteriorGasoleoA, precioSemanaAnteriorGasolina95);

                // Luego cargar los precios de la fecha actual
                requestGasolineras(fechaActual, new ICallBack() {
                    @Override
                    public void onSuccess(List<Gasolinera> estacionesActuales) {
                        double precioActualGasoleoA = obtenerPrecioConDescuento("GasoleoA", estacionesActuales);
                        double precioActualGasolina95 = obtenerPrecioConDescuento("Gasolina95", estacionesActuales);

                        mostrarPreciosActuales(precioActualGasoleoA, precioActualGasolina95,
                                precioSemanaAnteriorGasoleoA, precioSemanaAnteriorGasolina95);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        view.mostrarError("Error al cargar datos de la fecha actual");
                    }
                });
            }

            @Override
            public void onFailure(Throwable e) {
                view.mostrarError("Error al cargar datos de la semana pasada");
            }
        });
    }

    private void requestGasolineras(LocalDate fecha, ICallBack callBack) {
        repository.requestGasolinerasHistoricoFechas(callBack, IDCCAAs.CANTABRIA.id, fecha);
    }

    private void mostrarPreciosSemanaPasada(double precioSemanaAnteriorGasoleoA, double precioSemanaAnteriorGasolina95) {
        view.mostrarPrecioDieselSemanaPasada(String.format("%.2f", precioSemanaAnteriorGasoleoA));
        view.mostrarPrecioGasolina95SemanaPasada(String.format("%.2f", precioSemanaAnteriorGasolina95));
    }

    private void mostrarPreciosActuales(double precioActualGasoleoA, double precioActualGasolina95,
                                        double precioSemanaAnteriorGasoleoA, double precioSemanaAnteriorGasolina95) {
        String textoGasoleoA = calcularTextoComparacion(precioActualGasoleoA, precioSemanaAnteriorGasoleoA);
        String textoGasolina95 = calcularTextoComparacion(precioActualGasolina95, precioSemanaAnteriorGasolina95);

        view.mostrarPrecioDieselHoy(String.format("%.2f", precioActualGasoleoA));
        view.mostrarPrecioGasolina95Hoy(String.format("%.2f", precioActualGasolina95));
        view.mostrarTextoComparacionDiesel(textoGasoleoA);
        view.mostrarTextoComparacionGasolina95(textoGasolina95);
    }

    /**
     * Calcula el texto de comparación entre el precio actual y el de la semana pasada.
     *
     * @param precioActual Precio actual.
     * @param precioSemanaAnterior Precio de la semana pasada.
     * @return Texto de comparación.
     */
    public String calcularTextoComparacion(double precioActual, double precioSemanaAnterior) {
        if (precioActual == precioSemanaAnterior) {
            return String.format("%.2f €", precioActual);
        } else {
            double diferencia = precioActual - precioSemanaAnterior;
            String simbolo = (diferencia > 0) ? "+" : "-";
            return String.format("%.2f € (%s%.2f)", precioActual, simbolo, Math.abs(diferencia));
        }
    }



    private double obtenerPrecioConDescuento(String tipoCombustible, List<Gasolinera> estaciones) {
        double precio = 0.0;
        // Aquí calculas o extraes el precio del tipo de combustible (e.g., "GasoleoA" o "Gasolina95") de las estaciones
        // Implementación detallada depende de la estructura de Gasolinera
        return precio;
    }


}

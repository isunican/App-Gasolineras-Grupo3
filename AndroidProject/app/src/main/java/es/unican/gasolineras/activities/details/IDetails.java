package es.unican.gasolineras.activities.details;


import android.view.MenuItem;

import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.IGasolinerasRepository;

public interface IDetails {

    /**
     * Methods that must be implemented in the Details Presenter.
     * Only the View should call these methods.
     */
    public interface Presenter {
        /**
         * Links the presenter with its view.
         * Only the View should call this method
         * @param view
         */
        public void init(View view);
    }


    public interface View {

        /**
         * Return true if we are handling the selection
         */
        boolean onOptionsItemSelected(MenuItem item);

        /**
         * Devuelve el repositorio
         */
        IGasolinerasRepository getGasolinerasRepository();

        /**
         * Muestra los precios de gasolina de la semana pasada de la gasolinera seleccionada.
         * @param precioSemanaPasadaGasolina95 precio de la semana pasada para la gasolinera
         * @param precioSemanaPasadaDiesel precio de la semana pasada para la gasolinera
         * @param dia dia de la semana en el que se muestra el precio.
         */
        void mostrarPreciosSemanaPasada(double precioSemanaPasadaDiesel, double precioSemanaPasadaGasolina95,String dia,double diferenciaGasolina, double diferenciaDiesel);

        /**
         * Shows an error message on the screen.
         * @param mensaje the error message to show
         */
        void mostrarError(String mensaje);

        /**
         * Muestra los precios actuales de la gasolinera seleccionada.
         * @param precioGasolinaActual
         * @param precioActualDiesel
         */
        void mostrarPreciosActuales(double precioGasolinaActual, double precioActualDiesel);

    }
}

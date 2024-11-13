package es.unican.gasolineras.activities.details;

import android.os.Bundle;
import android.view.MenuItem;

import es.unican.gasolineras.activities.ConsultarRepostaje.IConsultar;
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
        public void init(IDetails.View view);
    }


    public interface View {
        /**
         * Initialize the view. Typically this should initialize all the listeners in the view.
         * Only the Presenter should call this method
         */
        public void init();


        boolean onOptionsItemSelected(MenuItem item);


        public IGasolinerasRepository getGasolinerasRepository();

        void mostrarPrecioGasolina95Hoy(String precioHoy);

        void mostrarPrecioGasolina95SemanaPasada(String precioSemanaPasada);

        void mostrarPrecioDieselHoy(String precioHoy);

        void mostrarPrecioDieselSemanaPasada(String precioSemanaPasada);

        void mostrarTextoComparacionDiesel(String textoGasoleoA);

        void mostrarTextoComparacionGasolina95(String textoGasolina95);

        void mostrarError(String mensaje);

    }
}

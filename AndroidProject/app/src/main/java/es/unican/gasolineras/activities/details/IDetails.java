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
        public void init(IDetails.View view);
    }


    public interface View {
        /**
         * Initialize the view. Typically this should initialize all the listeners in the view.
         * Only the Presenter should call this method
         */
        public void init();


        boolean onOptionsItemSelected(MenuItem item);


        IGasolinerasRepository getGasolinerasRepository();


        void mostrarPrecioGasolina95SemanaPasada(double precioSemanaPasada, Descuento descuento);


        void mostrarPrecioDieselSemanaPasada(double precioSemanaPasada, Descuento descuento);

        void mostrarDiaDeLaSemana(String texto);


        void mostrarError(String mensaje);

        void mostrarPreciosActuales(Descuento descuento);

    }
}

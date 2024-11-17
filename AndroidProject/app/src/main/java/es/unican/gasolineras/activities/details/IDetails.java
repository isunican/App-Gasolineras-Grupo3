package es.unican.gasolineras.activities.details;

import android.os.Bundle;
import android.view.MenuItem;

import es.unican.gasolineras.activities.ConsultarRepostaje.IConsultar;
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

        public void init();

        boolean onOptionsItemSelected(MenuItem item);

        IGasolinerasRepository getGasolinerasRepository();

        void mostrarPrecioGasolina95SemanaPasada(double precioSemanaPasada, Descuento descuento,String dia);

        void mostrarPrecioDieselSemanaPasada(double precioSemanaPasada, Descuento descuento,String dia);

        void mostrarError(String mensaje);

        void mostrarPreciosActuales(Descuento descuento);

    }
}

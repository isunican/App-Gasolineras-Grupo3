package es.unican.gasolineras.activities.RegistrarDescuentoEnMarca;

import es.unican.gasolineras.activities.ConsultarRepostaje.IConsultar;

public interface IRegistrarDescuento {

    /**
     * Methods that must be implemented in the Main Presenter.
     * Only the View should call these methods.
     */
    public interface Presenter {
        /**
         * Links the presenter with its view.
         * Only the View should call this method
         * @param view
         */
        public void init(View view);

        /**
         * Handles the process of validating and registering the discount
         * Called when the user clicks the save button.
         */
        void onBtnGuardarClicked(String marca, int descuento);

        /**
         * Llamado cuando se pulsa el boton de cancelar
         */
        void onBtnCancelarClicked();
    }

    public interface View {
        /**
         * Initialize the view. Typically this should initialize all the listeners in the view.
         * Only the Presenter should call this method
         */
        public void init();

        /**
         * Muestra si el repostaje se ha realizado correctamente o si hay algun problema
         * @param marca marca a la que aplica el descuento
         * @param descuento cantidad en % a descontar
         */
        void showBtnGuardar(String marca, int descuento);

        void showBtnCancelar();

        void mostrarError(String mensajeError, boolean errorDescuento);
    }


}

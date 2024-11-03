package es.unican.gasolineras.activities.RegistrarDescuentoEnMarca;


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
        void onBtnGuardarClicked(String marca, Integer descuento);

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
         * Muestra si hay algun problema o vuelve al main si se ha realizado correctamente
         * @param marca marca a la que aplica el descuento
         * @param descuento cantidad en % a descontar
         */
        void showBtnGuardar(String marca, Integer descuento);

        /**
         * Vuelve al main cuando se pulsa cancelar
         */
        void showBtnCancelar();

        /**
         * Shows an error message on the screen.
         * @param mensajeError the error message to show
         * @param errorDescuento a boolean which tells you if there is an error with that insert
         */
        void mostrarError(String mensajeError, boolean errorDescuento);
    }


}

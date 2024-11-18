package es.unican.gasolineras.activities.RegistrarRepostajeMenu;


public interface IRegistrar {

    public interface Presenter {

        /**
         * Links the presenter with its view.
         * Only the View should call this method
         * @param view
         */
        void init(View view);

        /**
         * Handles the process of validating and registering the repostaje
         * Called when the user clicks the save button.
         */
        void onBtnGuardarClicked(String litros, String precioTotal);

        /**
         * Llamado cuando se pulsa el boton de cancelar
         */
        void onBtnCancelarClicked();
    }

    public interface View {

        /**
         * Initializes the view.
         */
        void init();

        /**
         * Shows an error message on the screen.
         * @param mensajeError the error message to show
         * @param errorLitros a boolean which tells you if there is an error with that insert
         * @param errorPrecioTotal a boolean which tells you if there is an error with that insert
         */
        void mostrarError(String mensajeError, boolean errorLitros, boolean errorPrecioTotal);

        /**
         * Muestra si el repostaje se ha realizado correctamente o si hay algun problema
         * @param litros
         * @param precioTotal
         */
        void showBtnGuardar(String litros, String precioTotal);

        /**
         * Vuelve al main cuando se pulsa cancelar
         */
        void showBtnCancelar();
    }
}

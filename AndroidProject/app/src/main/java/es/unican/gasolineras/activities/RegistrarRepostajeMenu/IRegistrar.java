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
         *
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
         */
        void mostrarError(String mensajeError);


        void showBtnGuardar(String litros, String precioTotal);

        void showBtnCancelar();
    }
}

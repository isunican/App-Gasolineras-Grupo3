package es.unican.gasolineras.activities.RegistrarRepostajeMenu;

public class RegistrarPresenter implements IRegistrar.Presenter {

    /** The view that is controlled by this presenter */
    private IRegistrar.View view;

    @Override
    public void init(IRegistrar.View view) {
        this.view = view;
        this.view.init();
    }

    /**
     * Handles the validation and registration logic.
     * If the data is valid, it shows a confirmation dialog,
     * otherwise, it shows an error message.
     */
    @Override
    public void onGuardarRepostaje(String litrosStr, String precioTotalStr) {
        if (litrosStr.isEmpty() || precioTotalStr.isEmpty()) {
            view.mostrarError("Error: Los campos no deben estar vacíos");
            return;
        }

        try {
            double litros = Double.parseDouble(litrosStr);
            double precioTotal = Double.parseDouble(precioTotalStr);

            if (litros <= 0 || precioTotal <= 0) {
                view.mostrarError("Error: Los valores deben ser positivos");
                return;
            }

            // Si los datos son correctos, mostrar la confirmación
            view.mostrarConfirmacion("Registro de repostaje exitoso");

        } catch (NumberFormatException e) {
            view.mostrarError("Error: Los datos introducidos no son válidos");
        }
    }
}

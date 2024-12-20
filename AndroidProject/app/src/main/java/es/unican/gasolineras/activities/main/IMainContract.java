package es.unican.gasolineras.activities.main;

import java.util.List;

import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.DescuentoDAO;
import es.unican.gasolineras.repository.IGasolinerasRepository;

/**
 * The Presenter-View contract for the Main activity.
 * The Main activity shows a list of gas stations.
 */
public interface IMainContract {

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
         * The presenter is informed that a gas station has been clicked
         * Only the View should call this method
         * @param station the station that has been clicked
         */
        public void onStationClicked(Gasolinera station);

        /**
         * The presenter is informed that the Info item in the menu has been clicked
         * Only the View should call this method
         */
        public void onMenuInfoClicked();

        /**
         * The presenter is informed that the Info item in the menu has been clicked
         * Only the View should call this method
         */
        public void onMenuRegistrarClicked();


        /**
         * The presenter is informed that the Info item in the menu has been clicked
         * Only the View should call this method
         */
        public void onMenuConsultarClicked();

        /**
         * The presenter is informed that the Info item in the menu has been clicked
         * Only the View should call this method
         */
        public void onMenuDescuentoClicked();

        /**
         * Handles the process of filtered of the gas stations
         * Called when the user clicks the filter button.
         * Give the View the list already filtered
         * Only the View should call this method
         * @param municipio the municipality to be used on the filter
         */
        public void onBtnFiltrarClicked(String municipio);


        /**
         * Called when the user clicks the cancel button on the filter pop-up
         * Cancels the filter
         * Only the View should call this method
         */
        public void onBtnCancelarFiltroClicked();

        /**
         * Handles the process of ordered of the gas stations
         * Called when the user clicks the order button.
         * Give the View the list already ordered
         * Only the View should call this method
         *
         * @param tipoCompustible el tipo de combustible sobre el que ordenar
         */
        public void onBtnOrdenarClicked(String tipoCompustible);
    }

    /**
     * Methods that must be implemented in the Main View.
     * Only the Presenter should call these methods.
     */
    public interface View {

        /**
         * Initialize the view. Typically this should initialize all the listeners in the view.
         * Only the Presenter should call this method
         */
        public void init();

        /**
         * Returns a repository that can be called by the Presenter to retrieve gas stations.
         * This method must be located in the view because Android resources must be accessed
         * in order to instantiate a repository (for example Internet Access). This requires
         * dependencies to Android. We want to keep the Presenter free of Android dependencies,
         * therefore the Presenter should be unable to instantiate repositories and must rely on
         * the view to create the repository.
         * Only the Presenter should call this method
         * @return
         */
        public IGasolinerasRepository getGasolinerasRepository();

        /**
         * Retorna la dao que almacena los descuentos, sera llamado por el presenter
         * para acceder a los descuentos a la hora de ordenar las gasolineras.
         * @return
         */
        public DescuentoDAO getDescuentoDatabase();

        /**
         * The view is requested to display the given list of gas stations.
         * Only the Presenter should call this method
         * @param stations the list of charging stations
         */
        public void showStations(List<Gasolinera> stations);

        /**
         * The view is requested to display a notification indicating  that the gas
         * stations were loaded correctly.
         * Only the Presenter should call this method
         * @param stations
         */
        public void showLoadCorrect(int stations);

        /**
         * The view is requested to display a notificacion indicating that the gas
         * stations were not loaded correctly.
         * Only the Presenter should call this method
         */
        public void showLoadError();

        /**
         * The view is requested to display the detailed view of the given gas station.
         * Only the Presenter should call this method
         * @param station the charging station
         */
        public void showStationDetails(Gasolinera station);

        /**
         * The view is requested to open the info activity.
         * Only the Presenter should call this method
         */
        public void showInfoActivity();

        /**
         * The view is requested to open the registrar repostaje activity.
         * Only the Presenter should call this method
         */
        public void showRegistrarActivity();

        /**
         * The view is requested to open the consultar repostaje activity.
         * Only the Presenter should call this method
         */
        public void showConsultarActivity();

        /**
         * The view is requested to open the registrar descuento activity.
         * Only the Presenter should call this method
         */
        public void showDescuentoActivity();

        /**
         * Shows an error message on the screen.
         * @param mensajeError the error message to show
         */
        public void mostrarErrorNoGasolinerasEnMunicipio(String mensajeError);


        /**
         * Closes the filter pop-up
         */
        public void showBtnCancelarFiltro();
    }
}

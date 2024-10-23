package es.unican.gasolineras.activities.ConsultarRepostaje;

import java.util.List;
import es.unican.gasolineras.model.Repostaje;


public interface IConsultar {

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
    }

    public interface View {
        /**
         * Initialize the view. Typically this should initialize all the listeners in the view.
         * Only the Presenter should call this method
         */
        public void init();

        /**
         * Muestra los repostajes realizados
         * @param repostajes the list of charging stations
         */
        public void showRepostajes(List<Repostaje> repostajes);

        /**
         * Muestra los calculos sobre el mes actual de los repostajes
         */
        public void showCalculosRepostajes();

        /**
         * The view is requested to display a notification indicating  that the refueling
         * were loaded correctly.
         * Only the Presenter should call this method
         * @param repostajes
         */
        public void showLoadCorrect(int repostajes);

        /**
         * The view is requested to display a notificacion indicating that the refueling
         * were not loaded correctly.
         * Only the Presenter should call this method
         */
        public void showLoadError();

    }
}

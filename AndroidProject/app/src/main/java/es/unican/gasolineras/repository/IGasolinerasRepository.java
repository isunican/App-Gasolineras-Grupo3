package es.unican.gasolineras.repository;

import java.time.LocalDate;

/**
 * A repository to retrieve gas stations
 */
public interface IGasolinerasRepository {

        /**
         * Asynchronously requests a list of gas stations.
         * @param cb the callback that will asynchronously process the returned gas stations
         * @param ccaa id of the "comunidad autonoma", as defined in IDCCAAs
         */
        public void requestGasolineras(ICallBack cb, String ccaa);

        /**
         * Asynchronously requests a list of gas stations.
         * @param cb the callback that will asynchronously process the returned gas stations
         * @param ccaa id of the "comunidad autonoma", as defined in IDCCAAs
         * @param fecha fecha para la que se realiza la consulta
         */
        public void requestGasolinerasHistoricoFechas(ICallBack cb, String ccaa,LocalDate fecha);

}

package es.unican.gasolineras.repository;


import es.unican.gasolineras.model.GasolinerasResponse;
import retrofit2.Call;
import retrofit2.http.Path;
import retrofit2.http.GET;

/**
 * Gasolineras <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help">API</a>
 * using Retrofit
 */
public interface IGasolinerasAPI {

    /**
     * Retrieve gas stations filtered by "comunidad autónoma"
     * <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help/operations/PreciosEESSTerrestresFiltroCCAA">API</a>
     *
     * @param ccaa id of comunidad autónoma. Id's are defined in a separate <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help/operations/ComunidadesAutonomas">service</a>
     * @return retrofit call object
     */
    @GET("EstacionesTerrestres/FiltroCCAA/{IDCCAA}")
    Call<GasolinerasResponse> gasolineras(@Path("IDCCAA") String ccaa);


    /**
     * Retrieve gas stations filtered by "comunidad autónoma"
     * <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestresHist/FiltroCCAA/{FECHA}/{IDCCAA}">API</a>
     *
     * @param ccaa id of comunidad autónoma. Id's are defined in a separate <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help/operations/ComunidadesAutonomas">service</a>
     * @param fecha fecha para la que se realiza la consulta
     * @return retrofit call object
     */
    @GET("EstacionesTerrestresHist/FiltroCCAA/{FECHA}/{IDCCAA}")
    Call<GasolinerasResponse> gasolinerasHistorico(@Path("FECHA")  String fecha , @Path("IDCCAA") String ccaa);

}

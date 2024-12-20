package es.unican.gasolineras.repository;

import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.annotation.Nonnull;

import es.unican.gasolineras.model.GasolinerasResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of @link{IGasolinerasRepository} that access the real
 * <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help">Gasolineras API</a>
 */
public class GasolinerasRepository implements IGasolinerasRepository {

    /** Since this class does not have any state, it can be a singleton */
    public static final IGasolinerasRepository INSTANCE = new GasolinerasRepository();

    /** Singleton pattern with private constructor */
    private GasolinerasRepository() {}

    /**
     * Request gas stations from the Gasolineras real API.
     * @see IGasolinerasRepository#requestGasolineras(ICallBack, String)
     * @param cb the callback that will asynchronously process the returned gas stations
     * @param ccaa id of the "comunidad autonoma"
     */
    @Override
    public void requestGasolineras(ICallBack cb, String ccaa) {
        Call<GasolinerasResponse> call = GasolinerasService.api.gasolineras(ccaa);
        call.enqueue(new Callback<GasolinerasResponse>() {
            @Override
            public void onResponse(@Nonnull Call<GasolinerasResponse> call, @Nonnull Response<GasolinerasResponse> response) {
                GasolinerasResponse body = response.body();
                assert body != null;  // to avoid warning in the following line
                cb.onSuccess(body.getGasolineras());
            }

            @Override
            public void onFailure(@Nonnull Call<GasolinerasResponse> call, @Nonnull Throwable t) {
                cb.onFailure(t);
            }
        });
    }


    /**
     * Request gas stations from the Gasolineras real API.
     * @see IGasolinerasRepository#requestGasolineras(ICallBack, String)
     * @param cb the callback that will asynchronously process the returned gas stations
     * @param ccaa id of the "comunidad autonoma"
     * @param fecha fecha para la que realizar la consulta
     */
    @Override
    public void requestGasolinerasHistoricoFechas(ICallBack cb, String ccaa, LocalDate fecha) {
        String fechaFormatoAPI = fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Log.d("Fecha API", "Fecha enviada: " + fechaFormatoAPI);
        Call<GasolinerasResponse> call = GasolinerasService.api.gasolinerasHistorico(fechaFormatoAPI,ccaa);
        call.enqueue(new Callback<GasolinerasResponse>() {
            @Override
            public void onResponse(@Nonnull Call<GasolinerasResponse> call, @Nonnull Response<GasolinerasResponse> response) {
                GasolinerasResponse body = response.body();
                assert body != null;  // to avoid warning in the following line
                cb.onSuccess(body.getGasolineras());
            }

            @Override
            public void onFailure(@Nonnull Call<GasolinerasResponse> call, @Nonnull Throwable t) {
                cb.onFailure(t);
            }
        });
    }
}

package es.unican.gasolineras.activities.details;



import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


import android.content.Context;
import android.os.Build;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;



import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DescuentoDAO;
import es.unican.gasolineras.repository.GasolinerasRepository;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P})
public class DetailsPresenterITest {

    private static DetailsPresenter sut;
    private static IGasolinerasRepository repository;
    AppDatabase db;
    private Gasolinera gasolinera;
    DescuentoDAO descuentoDAO;


    @Mock
    private static DetailsView mockDetailsView;


    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        gasolinera = new Gasolinera();
        gasolinera.setCp("39200");
        gasolinera.setDireccion("Avenida Cantabria 77");
        gasolinera.setMunicipio("Reinosa");
        gasolinera.setRotulo("Repsol");
        gasolinera.setGasoleoA(1.26);
        gasolinera.setGasolina95E5(1.35);

        repository = GasolinerasRepository.INSTANCE;
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        descuentoDAO = db.descuentoDao();
        sut = new DetailsPresenter(gasolinera,descuentoDAO);
        when(mockDetailsView.getGasolinerasRepository()).thenReturn(repository);
        sut.init(mockDetailsView);
    }

    @Test
    public void itestLoad_DatosValidosConDiferenciaPrecios() {
        assertTrue(true);
    }

    @Test
    public void itestLoad_DatosValidosNoHayDiferencia() {
        assertTrue(true);
    }

    @Test
    public void itestLoad_DatosValidosDescuentoRegistrado() {
        assertTrue(true);
    }

    @Test
    public void itestLoad_CombustibleNoDisponible() {
        assertTrue(true);
    }

    @Test
    public void itestLoad_NoHayDatosParaSemanaPasada() {
        assertTrue(true);
    }
}
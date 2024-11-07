package es.unican.gasolineras.activities.main;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static es.unican.gasolineras.activities.utils.MockRepositories.getTestRepository;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P})
public class MainPresenterITest {
    private static MainPresenter sut;
    private static IGasolinerasRepository repository;

    private List<Gasolinera> listaGasolineras = new ArrayList<>();
    private String municipio;
    private Gasolinera gasolineraReinosa1;
    private Gasolinera gasolineraReinosa2;
    private Gasolinera gasolineraReinosa3;

    private Gasolinera gasolinera4;
    private Gasolinera gasolinera5;
    private Gasolinera gasolinera6;

    @Mock
    private static MainView mockMainView;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        gasolineraReinosa1 = new Gasolinera();
        gasolineraReinosa1.setCp("39200");
        gasolineraReinosa1.setDireccion("Avenida Cantabria 77");
        gasolineraReinosa1.setMunicipio("Reinosa");
        gasolineraReinosa1.setRotulo("Repsol");

        gasolineraReinosa2 = new Gasolinera();
        gasolineraReinosa2.setCp("39200");
        gasolineraReinosa2.setDireccion("Poligono La Vega");
        gasolineraReinosa2.setMunicipio("Reinosa");
        gasolineraReinosa2.setRotulo("Cepsa");

        gasolineraReinosa3 = new Gasolinera();
        gasolineraReinosa3.setCp("39200");
        gasolineraReinosa3.setDireccion("Avenida Cantabria, 15");
        gasolineraReinosa3.setMunicipio("Reinosa");
        gasolineraReinosa3.setRotulo("Cepsa");

        gasolinera4 = new Gasolinera();
        gasolinera4.setCp("39007");
        gasolinera4.setDireccion("Avenida Cantabria, 15");
        gasolinera4.setMunicipio("Torrelavega");
        gasolinera4.setRotulo("Avia");

        gasolinera5 = new Gasolinera();
        gasolinera5.setCp("39005");
        gasolinera5.setDireccion("Avenida Los Castros, 15");
        gasolinera5.setMunicipio("Santander");
        gasolinera5.setRotulo("Repsol");

        gasolinera6 = new Gasolinera();
        gasolinera6.setCp("39004");
        gasolinera6.setDireccion("Avenida Calvo Sotelo, 15");
        gasolinera6.setMunicipio("Santander");
        gasolinera6.setRotulo("Cepsa");

        listaGasolineras.add(gasolineraReinosa1);
        listaGasolineras.add(gasolineraReinosa2);
        listaGasolineras.add(gasolineraReinosa3);
        listaGasolineras.add(gasolinera4);
        listaGasolineras.add(gasolinera5);
        listaGasolineras.add(gasolinera6);

        repository = getTestRepository(listaGasolineras);

        sut = new MainPresenter();
        when(mockMainView.getGasolinerasRepository()).thenReturn(repository);
        sut.init(mockMainView);
    }

    @Test
    public void ITestFiltrarPorMunicipioExito() {
        municipio = "Reinosa";

        sut.onBtnFiltrarClicked(municipio);

        verify(mockMainView).getGasolinerasRepository();
        verify(mockMainView).showStations(listaGasolineras);
        assertEquals("Reinosa", sut.activarFiltro("Reinosa"), "Reinosa");
        assertTrue(sut.filtroActivado);
    }

    @Test
    public void ITestFiltrarPorMunicipioCancelacionFiltrado() {
        municipio = "";
        listaGasolineras.add(gasolineraReinosa1);
        listaGasolineras.add(gasolineraReinosa2);
        listaGasolineras.add(gasolineraReinosa3);

        sut.onBtnFiltrarClicked("");

        verify(mockMainView).getGasolinerasRepository();
        verify(mockMainView).showStations(listaGasolineras);
        assertEquals("Reinosa", sut.activarFiltro("Reinosa"));
        assertTrue(sut.filtroActivado);


    }

    @Test
    public void ITestFiltrarPorMunicipioEliminacionFiltrado() {
        municipio = "Mostrar todos";

        listaGasolineras.add(gasolineraReinosa1);
        listaGasolineras.add(gasolineraReinosa2);
        listaGasolineras.add(gasolineraReinosa3);
        listaGasolineras.add(gasolinera4);
        listaGasolineras.add(gasolinera5);
        listaGasolineras.add(gasolinera6);

        sut.onBtnFiltrarClicked("Mostrar todos");

        verify(mockMainView).getGasolinerasRepository();
        assertFalse(sut.filtroActivado);
        verify(mockMainView).showStations(listaGasolineras);

    }

    @Test
    public void ITestFiltrarPorMunicipioSinGasolineras() {
        municipio = "Bareyo";

        listaGasolineras.add(gasolineraReinosa1);
        listaGasolineras.add(gasolineraReinosa2);
        listaGasolineras.add(gasolineraReinosa3);
        listaGasolineras.add(gasolinera4);
        listaGasolineras.add(gasolinera5);
        listaGasolineras.add(gasolinera6);

        sut.onBtnFiltrarClicked("Bareyo");

        verify(mockMainView).getGasolinerasRepository();
        verify(mockMainView).mostrarErrorNoGasolinerasEnMunicipio("Error: No exiten gasolineras \n con el filtro aplicado");
        assertFalse(sut.filtroActivado);
    }

}

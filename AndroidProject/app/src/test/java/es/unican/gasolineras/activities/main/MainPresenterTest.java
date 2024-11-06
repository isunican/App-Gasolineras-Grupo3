package es.unican.gasolineras.activities.main;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import static es.unican.gasolineras.activities.utils.MockRepositories.getTestRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.IGasolinerasRepository;

public class MainPresenterTest {

    private List<Gasolinera> listaGasolineras = new LinkedList<Gasolinera>();
    private static MainPresenter sut;
    private Gasolinera gasolineraReinosa1;
    private Gasolinera gasolineraReinosa2;
    private Gasolinera gasolineraReinosa3;

    private Gasolinera gasolinera4;
    private Gasolinera gasolinera5;
    private Gasolinera gasolinera6;

    @Mock
    private static IMainContract.View mockView;

    @Mock
    private IGasolinerasRepository mockRepository;

    @Before
    public void inicializa(){

        MockitoAnnotations.openMocks(this);

        sut = new MainPresenter();
        sut.init(mockView);
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

        mockRepository = getTestRepository(listaGasolineras);

    }

    @Test
    public void testFiltrarPorMunicipioExito() {

        listaGasolineras.add(gasolineraReinosa1);
        listaGasolineras.add(gasolineraReinosa2);
        listaGasolineras.add(gasolineraReinosa3);

        sut.onBtnFiltrarClicked("Reinosa");

        verify(mockView).showStations(listaGasolineras);
        verify(sut).activarFiltro("Reinosa");
        verify(sut).hayFiltroActivado();

    }

    @Test
    public void testFiltrarPorMunicipioSinMunicipio() {

        listaGasolineras.add(gasolineraReinosa1);
        listaGasolineras.add(gasolineraReinosa2);
        listaGasolineras.add(gasolineraReinosa3);

        sut.onBtnFiltrarClicked("");

        verify(mockView).showStations(listaGasolineras);
        verify(sut).activarFiltro("Reinosa");
        verify(sut).hayFiltroActivado();

    }

    @Test
    public void testFiltrarPorMunicipioCancelacionFiltrado() {

        listaGasolineras.add(gasolineraReinosa1);
        listaGasolineras.add(gasolineraReinosa2);
        listaGasolineras.add(gasolineraReinosa3);
        listaGasolineras.add(gasolinera4);
        listaGasolineras.add(gasolinera5);
        listaGasolineras.add(gasolinera6);

        sut.onBtnFiltrarClicked("Bareyo");

        verify(mockView).mostrarErrorNoGaolinerasEnMunicipio("Error: No exiten gasolineras con el filtro aplicado");
        verify(sut).hayFiltroActivado();

    }

    @Test
    public void testFiltrarPorMunicipioEliminacionFiltrado() {

        listaGasolineras.add(gasolineraReinosa1);
        listaGasolineras.add(gasolineraReinosa2);
        listaGasolineras.add(gasolineraReinosa3);
        listaGasolineras.add(gasolinera4);
        listaGasolineras.add(gasolinera5);
        listaGasolineras.add(gasolinera6);

        sut.onBtnFiltrarClicked("Mostrar todos");

        assertFalse(sut.filtroActivado);
        verify(mockView).showStations(listaGasolineras);

    }
}

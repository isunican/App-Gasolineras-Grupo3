package es.unican.gasolineras.activities.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import static es.unican.gasolineras.activities.utils.MockRepositories.getTestRepository;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.DescuentoDAO;
import es.unican.gasolineras.repository.IGasolinerasRepository;

public class MainPresenterTest {

    private List<Gasolinera> listaGasolineras = new ArrayList<>();
    private static MainPresenter sut;
    private Gasolinera gasolinera1;
    private Gasolinera gasolinera2;
    private Gasolinera gasolinera3;

    private Gasolinera gasolinera4;
    private Gasolinera gasolinera5;
    private Gasolinera gasolinera6;
    private Gasolinera gasolinera7;
    private Gasolinera gasA;
    private Gasolinera gasB;

    @Mock
    private static IMainContract.View mockView;

    @Mock
    private IGasolinerasRepository mockRepository;

    @Mock
    private DescuentoDAO mockDescuentoDAO;

    @Before
    public void inicializa(){

        MockitoAnnotations.openMocks(this);

        gasolinera1 = new Gasolinera();
        gasolinera1.setMunicipio("Reinosa");
        gasolinera1.setCp("39200");
        gasolinera1.setDireccion("Calle 1");
        gasolinera1.setRotulo("Repsol");

        gasolinera2 = new Gasolinera();
        gasolinera2.setMunicipio("Reinosa");
        gasolinera2.setCp("39200");
        gasolinera2.setDireccion("Calle 2");
        gasolinera2.setRotulo("Cepsa");

        gasolinera3 = new Gasolinera();
        gasolinera3.setMunicipio("Reinosa");
        gasolinera3.setCp("39200");
        gasolinera3.setDireccion("Calle 3");
        gasolinera3.setRotulo("Cepsa");

        gasolinera4 = new Gasolinera();
        gasolinera4.setMunicipio("Alfoz de Lloredo");
        gasolinera4.setCp("39100");
        gasolinera4.setDireccion("Calle 1");
        gasolinera4.setRotulo("Repsol");
        gasolinera4.setGasoleoA(2);
        gasolinera4.setGasolina95E5(1.25);

        gasolinera5 = new Gasolinera();
        gasolinera5.setMunicipio("Ampuero");
        gasolinera5.setCp("39300");
        gasolinera5.setDireccion("Calle 1");
        gasolinera5.setRotulo("Repsol");
        gasolinera5.setGasoleoA(1.25);
        gasolinera5.setGasolina95E5(0.0);

        gasolinera6 = new Gasolinera();
        gasolinera6.setMunicipio("Ampuero");
        gasolinera6.setCp("39300");
        gasolinera6.setDireccion("Calle 2");
        gasolinera6.setRotulo("Cepsa");
        gasolinera6.setGasoleoA(1.12);
        gasolinera6.setGasolina95E5(1.1);

        gasolinera7 = new Gasolinera();
        gasolinera7.setMunicipio("Anievas");
        gasolinera7.setCp("39400");
        gasolinera7.setDireccion("Calle 1");
        gasolinera7.setRotulo("Avia");
        gasolinera7.setGasoleoA(1.36);
        gasolinera7.setGasolina95E5(1.74);

        gasA = new Gasolinera();
        gasA.setRotulo("Repsol");
        gasA.setGasolina95E5(1.49);
        gasA.setGasoleoA(1.33);

        gasB = new Gasolinera();
        gasB.setRotulo("Cepsa");
        gasB.setGasolina95E5(1.33);
        gasB.setGasoleoA(1.25);

        listaGasolineras.add(gasolinera1);
        listaGasolineras.add(gasolinera2);
        listaGasolineras.add(gasolinera3);
        listaGasolineras.add(gasolinera4);
        listaGasolineras.add(gasolinera5);
        listaGasolineras.add(gasolinera6);
        listaGasolineras.add(gasolinera7);

        mockRepository = getTestRepository(listaGasolineras);

        sut = new MainPresenter();
        when(mockView.getGasolinerasRepository()).thenReturn(mockRepository);
        when(mockView.getDescuentoDatabase()).thenReturn(mockDescuentoDAO);
        sut.init(mockView);

    }

    @Test
    public void onBtnFiltrarClickedTest() {
        //Caso exito
        sut.onBtnFiltrarClicked("Reinosa");

        List<Gasolinera> listaEsperada = List.of(
                gasolinera1,
                gasolinera2,
                gasolinera3
        );

        verify(mockRepository).requestGasolineras(any(), any());
        verify(mockView).showStations(listaEsperada);
        assertEquals("Reinosa", sut.activarFiltro("Reinosa"));
        assertEquals("Reinosa", sut.hayFiltroActivado());

        //Municipio sin gasolinera
        sut.onBtnFiltrarClicked("Bareyo");

        verify(mockRepository).requestGasolineras(any(), any());
        assertEquals("Bareyo",sut.hayFiltroActivado());
        assertEquals("Bareyo",sut.activarFiltro("Bareyo"));
        verify(mockView).mostrarErrorNoGasolinerasEnMunicipio("Error: No existen gasolineras \n con el filtro aplicado");

        //EliminacionFiltrado
        sut.onBtnFiltrarClicked("Mostrar todos");

        List<Gasolinera> listaEsperada1 = List.of(
                gasolinera1,
                gasolinera2,
                gasolinera3,
                gasolinera4,
                gasolinera5,
                gasolinera6,
                gasolinera7
        );

        verify(mockRepository).requestGasolineras(any(), any());
        assertNull(sut.hayFiltroActivado());
        verify(mockView, times(2)).showStations(listaEsperada1);

    }

    @Test
    public void onBtnOrdenarClickedSinFiltroSinDescuentoTest() {

        sut.onBtnOrdenarClicked("Gasolina");

        List<Gasolinera> lista = List.of(
                gasolinera6,
                gasolinera4,
                gasolinera7
        );

        verify(mockView).showStations(lista);
        verify(mockRepository).requestGasolineras(any(), any());
        assertTrue(sut.activarOrdenamiento());
        assertEquals("Gasolina", sut.hayOrdenamientoActivado());

    }

    @Test
    public void onBtnOrdenarClickedConFiltroSinDescuentosTest() {
        sut.onBtnFiltrarClicked("Ampuero");
        sut.onBtnOrdenarClicked("Diesel");

        List<Gasolinera> lista = List.of(
                gasolinera6,
                gasolinera5
        );

        verify(mockView).showStations(lista);
        verify(mockRepository).requestGasolineras(any(), any());
        assertTrue(sut.activarOrdenamiento());
        assertEquals("Diesel", sut.hayOrdenamientoActivado());

    }

    @Test
    public void onBtnOrdenarClickedSinFiltroConDescuentosTest() {

        Descuento descuento = new Descuento();
        descuento.setDescuento(100);

        Descuento descuento2 = new Descuento();
        descuento2.setDescuento(50);

        when(mockView.getDescuentoDatabase().descuentoPorMarca("Avia")).thenReturn(descuento);
        when(mockView.getDescuentoDatabase().descuentoPorMarca("Repsol")).thenReturn(descuento2);

        sut.onBtnOrdenarClicked("Gasolina");

        List<Gasolinera> lista = List.of(
                gasolinera7,
                gasolinera4,
                gasolinera6
        );

        verify(mockView).showStations(lista);
        verify(mockRepository).requestGasolineras(any(), any());
        assertTrue(sut.activarOrdenamiento());
        assertEquals("Gasolina", sut.hayOrdenamientoActivado());
    }

    @Test
    public void onBtnOrdenarClickedConFiltroSinGasolinerasSinDescuentosTest() {

        sut.onBtnFiltrarClicked("Bareyo");
        sut.onBtnOrdenarClicked("Gasolina");

        //Cargado inicial de las gasolineras en la vista principal
        verify(mockView, times(1)).showStations(any());

        verify(mockRepository).requestGasolineras(any(), any());
        assertEquals("Bareyo",sut.hayFiltroActivado());
        verify(mockView, times(2)).mostrarErrorNoGasolinerasEnMunicipio("Error: No existen gasolineras \n con el filtro aplicado");
        assertNull(sut.hayOrdenamientoActivado());
    }

    @Test
    public void calcularPrecioConDescuentoSinDescuentoGasolinaTest() {

        double resultado = sut.calcularPrecioConDescuento(gasA, "Gasolina");
        assertEquals(1.49, resultado, 0.01);
    }

    @Test
    public void calcularPrecioConDescuentoConDescuentoDieselTest() {
        Descuento descuento = new Descuento();
        descuento.setDescuento(10);

        when(mockView.getDescuentoDatabase().descuentoPorMarca("Cepsa")).thenReturn(descuento);

        double resultado = sut.calcularPrecioConDescuento(gasB, "Diesel");
        assertEquals(1.12, resultado, 0.01);
    }

    @Test
    public void calcularPrecioConDescuentoConDescuentoGasolinaTest() {
        Descuento descuento = new Descuento();
        descuento.setDescuento(10);

        when(mockView.getDescuentoDatabase().descuentoPorMarca("Cepsa")).thenReturn(descuento);

        double resultado = sut.calcularPrecioConDescuento(gasB, "Gasolina");
        assertEquals(1.19, resultado, 0.01);
    }

    @Test
    public void calcularPrecioConDescuentoSinDescuentoDieselTest() {

        double resultado = sut.calcularPrecioConDescuento(gasA, "Diesel");
        assertEquals(1.33, resultado, 0.01);
    }

    @Test
    public void calcularPrecioConDescuentoErrorBBDDTest() {

        // Simular un error en el acceso a la base de datos
        when(mockView.getDescuentoDatabase()).thenThrow(new RuntimeException("Error al acceder a la base de datos"));

        assertThrows(RuntimeException.class, () -> sut.calcularPrecioConDescuento(gasA, "Gasolina"));

    }




}

package es.unican.gasolineras.activities.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
        gasolinera1.setRotulo("Repsol");

        gasolinera2 = new Gasolinera();
        gasolinera2.setMunicipio("Reinosa");
        gasolinera2.setRotulo("Cepsa");

        gasolinera3 = new Gasolinera();
        gasolinera3.setMunicipio("Reinosa");
        gasolinera3.setRotulo("Cepsa");

        gasolinera4 = new Gasolinera();
        gasolinera4.setMunicipio("Alfoz de Lloredo");
        gasolinera4.setRotulo("Repsol");
        gasolinera4.setGasoleoA(2);
        gasolinera4.setGasolina95E5(1.25);

        gasolinera5 = new Gasolinera();
        gasolinera5.setMunicipio("Ampuero");
        gasolinera5.setRotulo("Repsol");
        gasolinera5.setGasoleoA(1.25);
        gasolinera5.setGasolina95E5(0.0);

        gasolinera6 = new Gasolinera();
        gasolinera6.setMunicipio("Ampuero");
        gasolinera6.setRotulo("Cepsa");
        gasolinera6.setGasoleoA(1.12);
        gasolinera6.setGasolina95E5(1.1);

        gasolinera7 = new Gasolinera();
        gasolinera7.setMunicipio("Anievas");
        gasolinera7.setRotulo("Avia");
        gasolinera7.setGasoleoA(1.36);
        gasolinera7.setGasolina95E5(1.74);

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
    public void testFiltrarPorMunicipioExito() {

        sut.onBtnFiltrarClicked("Reinosa");

        List<Gasolinera> listaEsperada = List.of(
                gasolinera1,
                gasolinera2,
                gasolinera3
        );

        verify(mockRepository).requestGasolineras(any(), any());
        verify(mockView).showStations(listaEsperada);
        assertEquals(sut.activarFiltro("Reinosa"), "Reinosa");
        assertEquals("Reinosa", sut.hayFiltroActivado());
    }
    /*
    @Test
    public void testFiltrarPorMunicipioCancelacionFiltrado() {

        List<Gasolinera> listaEsperada = List.of(
                gasolinera1,
                gasolinera2,
                gasolinera3
        );

        verify(mockRepository).requestGasolineras(any(), any());
        verify(mockView).showStations(listaEsperada);
        assertEquals(sut.activarFiltro("Reinosa"), "Reinosa");
        assertEquals("Reinosa", sut.hayFiltroActivado());

    }*/

    /**@Test
    public void testFiltrarPorMunicipioSinGasolineras() {

        sut.onBtnFiltrarClicked("Bareyo");

        verify(mockRepository).requestGasolineras(any(), any());
        assertEquals("Bareyo",sut.hayFiltroActivado());
        verify(mockView).mostrarErrorNoGasolinerasEnMunicipio("Error: No existen gasolineras \n con el filtro aplicado");

    }*/

    @Test
    public void testFiltrarPorMunicipioEliminacionFiltrado() {

        sut.onBtnFiltrarClicked("Mostrar todos");

        List<Gasolinera> listaEsperada = List.of(
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
        verify(mockView, times(2)).showStations(listaEsperada);

    }

    //Los siguientes cuatro test comprueban el funcionameinto
    // del metodo onBtnOrdenarClicked(tipoCombustible)
    @Test
    public void testOrdenarPorPrecioSinFiltroSinDescuentos() {

        sut.onBtnOrdenarClicked("Gasolina");

        List<Gasolinera> lista = List.of(
                gasolinera6,
                gasolinera4,
                gasolinera7
        );

        verify(mockView).showStations(lista);
        verify(mockRepository).requestGasolineras(any(), "06");
        assertTrue(sut.activarOrdenamiento());
        assertEquals("Gasolina", sut.hayOrdenamientoActivado());

    }

    @Test
    public void testOrdenarPorPrecioConFiltroSinDescuentos() {
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
    public void testOrdenarPorPrecioSinFiltroConDescuentos() {

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
    public void testOrdenarPorPrecioConFiltroSinGasolinerasSinDescuentos() {

        sut.onBtnFiltrarClicked("Bareyo");
        sut.onBtnOrdenarClicked("Gasolina");

        //Cargado inicial de las gasolineras en la vista principal
        verify(mockView, times(1)).showStations(any());

        verify(mockRepository).requestGasolineras(any(), any());
        assertEquals("Bareyo",sut.hayFiltroActivado());
        verify(mockView, times(2)).mostrarErrorNoGasolinerasEnMunicipio("Error: No existen gasolineras \n con el filtro aplicado");
        assertNull(sut.hayOrdenamientoActivado());
    }

    //A partir de aqui del metodo calcularPrecioConDescuento

    @Test
    public void testOrdenarPorPrecioCalculoSinDescuentoGasolina() {
        Descuento descuento = new Descuento();
        descuento.setDescuento(10);

        when(mockView.getDescuentoDatabase().descuentoPorMarca("Cepsa")).thenReturn(descuento);

        Gasolinera gasA = new Gasolinera();
        gasA.setRotulo("Repsol");
        gasA.setGasolina95E5(1.49);
        gasA.setGasoleoA(1.33);

        double resultado = sut.calcularPrecioConDescuento(gasA, "Gasolina");
        assertEquals(1.49, resultado, 0.01);
    }

    @Test
    public void testOrdenarPorPrecioCalculoConDescuentoDiesel() {
        Descuento descuento = new Descuento();
        descuento.setDescuento(10);

        when(mockView.getDescuentoDatabase().descuentoPorMarca("Cepsa")).thenReturn(descuento);

        Gasolinera gasB = new Gasolinera();
        gasB.setRotulo("Cepsa");
        gasB.setGasolina95E5(1.33);
        gasB.setGasoleoA(1.25);

        double resultado = sut.calcularPrecioConDescuento(gasB, "Diesel");
        assertEquals(1.12, resultado, 0.01);
    }

    @Test
    public void testOrdenarPorPrecioCalculoConDescuentoGasolina() {
        Descuento descuento = new Descuento();
        descuento.setDescuento(10);

        when(mockView.getDescuentoDatabase().descuentoPorMarca("Cepsa")).thenReturn(descuento);

        Gasolinera gasB = new Gasolinera();
        gasB.setRotulo("Cepsa");
        gasB.setGasolina95E5(1.33);
        gasB.setGasoleoA(1.25);

        double resultado = sut.calcularPrecioConDescuento(gasB, "Gasolina");
        assertEquals(1.19, resultado, 0.01);
    }

    @Test
    public void testOrdenarPorPrecioCalculoSinDescuentoDiesel() {
        Descuento descuento = new Descuento();
        descuento.setDescuento(10);

        when(mockView.getDescuentoDatabase().descuentoPorMarca("Cepsa")).thenReturn(descuento);

        Gasolinera gasA = new Gasolinera();
        gasA.setRotulo("Repsol");
        gasA.setGasolina95E5(1.49);
        gasA.setGasoleoA(1.33);

        double resultado = sut.calcularPrecioConDescuento(gasA, "Diesel");
        assertEquals(1.33, resultado, 0.01);
    }


}

package es.unican.gasolineras.activities.RegistrarDescuentoEnMarca;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import android.database.sqlite.SQLiteException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;


import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.DescuentoDAO;


@RunWith(RobolectricTestRunner.class)
public class RegistrarDescuentoPresenterTest {

    private static RegistrarDescuentoPresenter sut;

    private String marca;
    private double descuento;


    @Mock
    private static DescuentoDAO mockDescuentoDAO;

    @Mock
    private static RegistrarDescuentoView mockRegistrarDescuentoView;

    @Before
    public void inicializa(){

        MockitoAnnotations.openMocks(this);

        sut = new RegistrarDescuentoPresenter(mockDescuentoDAO);
        sut.init(mockRegistrarDescuentoView);
    }

    @Test
    public void testRegistrarDescuentoExito() {

        marca = "Repsol";
        descuento = 20;

        // Llamar al método onBtnGuardarClicked con los valores de entrada
        sut.onBtnGuardarClicked(marca, descuento);

        // Capturar el objeto Descuento que se pasa al método actualizaDescuento
        ArgumentCaptor<Descuento> captor = ArgumentCaptor.forClass(Descuento.class);
        verify(mockDescuentoDAO).registrarDescuento(captor.capture());

        // Obtener el valor capturado
        Descuento capturedDescuento = captor.getValue();

        // Verificar que el descuento capturado tenga los valores correctos
        assertEquals("Repsol", capturedDescuento.getMarca());
        assertEquals(20, capturedDescuento.getDescuento());

        verify(mockRegistrarDescuentoView).showBtnGuardar(marca, descuento);

    }


    @Test
    public void testRegistrarDescuentoCero() {

        // Definir los valores de marca y descuento
        marca = "Repsol";
        descuento = 0;

        // Llamar al método onBtnGuardarClicked con los valores de entrada
        sut.onBtnGuardarClicked(marca, descuento);

        // Capturar el objeto Descuento que se pasa al método actualizaDescuento
        ArgumentCaptor<Descuento> captor = ArgumentCaptor.forClass(Descuento.class);
        verify(mockDescuentoDAO).registrarDescuento(captor.capture());

        // Obtener el valor capturado
        Descuento capturedDescuento = captor.getValue();

        // Verificar que el descuento capturado tenga los valores correctos
        assertEquals("Repsol", capturedDescuento.getMarca());
        assertEquals(0, capturedDescuento.getDescuento());  // El descuento es 0, por lo que no necesitamos precisión decimal

        // Verificar que la vista haya mostrado el botón de guardar con los valores correctos
        verify(mockRegistrarDescuentoView).showBtnGuardar(marca, descuento);
    }


    @Test
    public void testRegistrarDescuentoEnMarcaYaRegistrada() {

        marca = "Repsol";
        descuento = 15;

        // Simular que ya existe un descuento para la marca.
        Descuento descuentoExistente = new Descuento();
        descuentoExistente.setMarca(marca);
        descuentoExistente.setDescuento(20);  // Suponiendo que el descuento existente es 20

        // Configurar el mock para devolver el descuento existente cuando se llame a descuentoPorMarca
        when(mockDescuentoDAO.descuentoPorMarca(marca.toUpperCase())).thenReturn(descuentoExistente);

        sut.onBtnGuardarClicked(marca, descuento);


        ArgumentCaptor<Descuento> captor = ArgumentCaptor.forClass(Descuento.class);
        verify(mockDescuentoDAO).actualizaDescuento(captor.capture());


        Descuento capturedDescuento = captor.getValue();
        assertEquals("Repsol", capturedDescuento.getMarca());
        assertEquals(15, capturedDescuento.getDescuento());


        verify(mockRegistrarDescuentoView).showBtnGuardar(marca, descuento);

    }




    @Test
    public void testRegistrarDescuentoValorNegativo() {

        marca = "Repsol";
        descuento = -30;

        sut.onBtnGuardarClicked(marca, descuento);

        verify(mockRegistrarDescuentoView).mostrarError("Error: El valor debe ser positivo", true);
        verify(mockDescuentoDAO, never()).registrarDescuento(any());
    }

    @Test
    public void testRegistrarDescuentoValorFormatoIncorrecto() {

        marca = "Repsol";
        descuento = 130;

        sut.onBtnGuardarClicked(marca, descuento);

        verify(mockRegistrarDescuentoView).mostrarError("Error: El valor debe estar entre 0 y 100", true);
        verify(mockDescuentoDAO, never()).registrarDescuento(any());
    }


    @Test
    public void testRegistrarDescuentoErrorBD() {

        marca = "Repsol";
        descuento = 15;

        // Se simula un error en la base de datos
        doThrow(new SQLiteException()).when(mockDescuentoDAO).registrarDescuento(any());

        sut.onBtnGuardarClicked(marca, descuento);

        verify(mockRegistrarDescuentoView).mostrarError("Error al registrar el descuento en la base de datos", false);

    }

}


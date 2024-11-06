package es.unican.gasolineras.activities.RegistrarDescuentoEnMarca;


import static org.mockito.Mockito.verify;

import android.content.Context;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DatabaseFunction;


@RunWith(RobolectricTestRunner.class)
public class RegistrarDescuentoPresenterITest {

    private static RegistrarDescuentoPresenter sut;

    private String marca;
    private double descuento;

    @Mock
    private static RegistrarDescuentoView mockRegistrarDescuentoView;

    @Before
    public void inicializa(){

        MockitoAnnotations.openMocks(this);
        Context context = ApplicationProvider.getApplicationContext();

        AppDatabase db = DatabaseFunction.getDatabase(context);
        sut = new RegistrarDescuentoPresenter(db.descuentoDao());
        sut.init(mockRegistrarDescuentoView);
    }

    @Test
    public void itestRegistrarDescuentoExito() {

        marca = "Repsol";
        descuento = 20;

        sut.onBtnGuardarClicked(marca, descuento);


        verify(mockRegistrarDescuentoView).showBtnGuardar(marca, descuento);
    }


    @Test
    public void itestRegistrarDescuentoCero() {

        marca = "Repsol";
        descuento = 0;

        sut.onBtnGuardarClicked(marca, descuento);

        verify(mockRegistrarDescuentoView).showBtnGuardar(marca, descuento);

    }

    @Test
    public void itestRegistrarDescuentoEnMarcaYaRegistrada() {


        marca = "Repsol";
        descuento = 20;

        sut.onBtnGuardarClicked(marca, descuento);

        verify(mockRegistrarDescuentoView).showBtnGuardar(marca, descuento);

    }



    @Test
    public void itestRegistrarDescuentoValorNegativo() {
        
        marca = "Repsol";
        descuento = -30;

        sut.onBtnGuardarClicked(marca, descuento);

        verify(mockRegistrarDescuentoView).mostrarError("Error: El valor debe ser positivo", true);

    }

    @Test
    public void itestRegistrarDescuentoValorFormatoIncorrecto() {



        marca = "Repsol";
        descuento = 130;

        sut.onBtnGuardarClicked(marca, descuento);

        verify(mockRegistrarDescuentoView).mostrarError("Error: El valor debe estar entre 0 y 100", true);

    }



}


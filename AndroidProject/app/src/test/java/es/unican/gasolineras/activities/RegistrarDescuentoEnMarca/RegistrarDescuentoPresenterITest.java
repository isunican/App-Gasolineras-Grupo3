package es.unican.gasolineras.activities.RegistrarDescuentoEnMarca;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import android.content.Context;


import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;


import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DatabaseFunction;
import es.unican.gasolineras.repository.DescuentoDAO;


@RunWith(RobolectricTestRunner.class)
public class RegistrarDescuentoPresenterITest {

    private RegistrarDescuentoPresenter sut;
    AppDatabase db;
    private String marca;
    private double descuento;

    @Mock
    private RegistrarDescuentoView mockRegistrarDescuentoView;

    @Before
    public void inicializa(){

        MockitoAnnotations.openMocks(this);
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "database-name")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        DescuentoDAO descuentoDAO = db.descuentoDao();

        sut = new RegistrarDescuentoPresenter(descuentoDAO);
        sut.init(mockRegistrarDescuentoView);
        db.descuentoDao().eliminarDescuentos();
    }


    @Test
    public void itestRegistrarDescuentoExito() {

        marca = "Repsol";
        descuento = 20;

        sut.onBtnGuardarClicked(marca, descuento);

        //Se comprueba en la base de datos que el descuento se ha añadido
        Descuento descuentoGuardado = db.descuentoDao().descuentoPorMarca(marca);

        assertEquals(descuento, descuentoGuardado.getDescuento(), "El descuento guardado no coincide con el esperado.");

        verify(mockRegistrarDescuentoView).showBtnGuardar(marca, descuento);
    }


    @Test
    public void itestRegistrarDescuentoCero() {

        marca = "Repsol";
        descuento = 0;

        sut.onBtnGuardarClicked(marca, descuento);

        //Se comprueba en la base de datos que el descuento se ha añadido
        Descuento descuentoGuardado = db.descuentoDao().descuentoPorMarca(marca);

        assertEquals(descuento, descuentoGuardado.getDescuento(), "El descuento guardado no coincide con el esperado.");


        verify(mockRegistrarDescuentoView).showBtnGuardar(marca, descuento);

    }

    @Test
    public void itestRegistrarDescuentoEnMarcaYaRegistrada() {


        marca = "Repsol";
        descuento = 20;

        sut.onBtnGuardarClicked(marca, descuento);

        //Se comprueba en la base de datos que el descuento se ha añadido
        Descuento descuentoGuardado = db.descuentoDao().descuentoPorMarca(marca);

        assertEquals(descuento, descuentoGuardado.getDescuento(), "El descuento guardado no coincide con el esperado.");


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


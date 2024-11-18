package es.unican.gasolineras.activities.RegistrarDescuentoEnMarca;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import android.content.Context;


import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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


    @ParameterizedTest
    @CsvSource({
            "Repsol, 20, 20", // Test case 1: Successful registration with a valid discount
            "Repsol, 0, 0",   // Test case 2: Registration with a 0% discount
            "Repsol, 15, 15"  // Test case 3: Update discount for an already registered brand
    })
    public void itestRegistrarDescuento(String marca, double descuentoInput, double descuentoExpected) {
        // Act: Attempt to save the discount
        sut.onBtnGuardarClicked(marca, descuentoInput);

        // Assert: Verify the saved discount in the database
        Descuento descuentoGuardado = db.descuentoDao().descuentoPorMarca(marca);
        assertEquals(descuentoExpected, descuentoGuardado.getDescuento(),
                "El descuento guardado no coincide con el esperado.");

        // Verify that the view was notified of success
        verify(mockRegistrarDescuentoView).showBtnGuardar(marca, descuentoExpected);
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


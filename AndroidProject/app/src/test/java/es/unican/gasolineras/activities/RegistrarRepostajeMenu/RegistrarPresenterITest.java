package es.unican.gasolineras.activities.RegistrarRepostajeMenu;

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
@Config(sdk = {Build.VERSION_CODES.P}) // P corresponds to API level 28
public class RegistrarPresenterITest {

    private static RegistrarPresenter sut;

    private String litros;
    private String precioTotal;

    @Mock
    private static RegistrarView mockRegistrarView;

    @Before
    public void inicializa(){

        MockitoAnnotations.openMocks(this);
        Context context = ApplicationProvider.getApplicationContext();

        AppDatabase db = DatabaseFunction.getDatabase(context);
        sut = new RegistrarPresenter(db.repostajeDao());
        sut.init(mockRegistrarView);
    }

    @Test
    public void itestRegistrarRepostajeExito() {

        litros = "20";
        precioTotal = "30";

        sut.onBtnGuardarClicked(litros, precioTotal);

        verify(mockRegistrarView).showBtnGuardar(litros, precioTotal);

    }

    @Test
    public void itestRegistrarRepostajeValoresFormatoIncorrecto() {

        litros = "Veinte";
        precioTotal = "50";

        sut.onBtnGuardarClicked(litros, precioTotal);

        verify(mockRegistrarView).mostrarError("Error: Los datos introducidos no son válidos", true, false);

    }

    @Test
    public void itestRegistrarRepostajeValoresNegativos() {

        litros = "-50";
        precioTotal = "50";

        sut.onBtnGuardarClicked(litros, precioTotal);

        verify(mockRegistrarView).mostrarError("Error: Los valores deben ser positivos", true, false);
    }

    @Test
    public void itestRegistrarRepostajeValoresVacios() {

        litros = "";
        precioTotal = "";

        sut.onBtnGuardarClicked(litros, precioTotal);

        verify(mockRegistrarView).mostrarError("Error: Los campos no deben estar vacíos", true, true);
    }

}

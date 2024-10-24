package es.unican.gasolineras.RegistrarRepostajeMenu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

import es.unican.gasolineras.activities.RegistrarRepostajeMenu.RegistrarPresenter;
import es.unican.gasolineras.activities.RegistrarRepostajeMenu.RegistrarView;
import es.unican.gasolineras.model.Repostaje;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DatabaseFunction;
import es.unican.gasolineras.repository.RepostajeDAO;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class RegistrarRepostajeITest {

    private static RegistrarPresenter sut;
    //private static RepostajeDAO respostajeDAO;

    private String litros;
    private String precioTotal;

    @Mock
    private static RegistrarView mockRegistrarView;

    @Before
    public void inicializa(){

        Context context = ApplicationProvider.getApplicationContext();

        AppDatabase db = DatabaseFunction.getDatabase(context);
        sut = new RegistrarPresenter(db.repostajeDao());

        MockitoAnnotations.openMocks(this);
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

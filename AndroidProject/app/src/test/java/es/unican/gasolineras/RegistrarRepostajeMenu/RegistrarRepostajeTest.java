package es.unican.gasolineras.RegistrarRepostajeMenu;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.unican.gasolineras.activities.RegistrarRepostajeMenu.RegistrarPresenter;
import es.unican.gasolineras.activities.RegistrarRepostajeMenu.RegistrarView;
import es.unican.gasolineras.model.Repostaje;
import es.unican.gasolineras.repository.RepostajeDAO;

public class RegistrarRepostajeTest {

    private static RegistrarPresenter sut;
    private String litros;
    private String precioTotal;

    @Mock
    private static RepostajeDAO mockRepostajeDAO;

    @Mock
    private static RegistrarView mockRegistrarView;

    @Before
    public void inicializa(){

        MockitoAnnotations.openMocks(this);

        sut = new RegistrarPresenter(mockRepostajeDAO);
        sut.init(mockRegistrarView);
    }

    @Test
    public void testRegistrarRepostajeExito() {

        litros = "20";
        precioTotal = "30";

        sut.onBtnGuardarClicked(litros, precioTotal);

        verify(mockRepostajeDAO).registrarRepostaje(any(Repostaje.class));
        verify(mockRegistrarView).showBtnGuardar(litros, precioTotal);

    }

    @Test
    public void testRegistrarRepostajeValoresFormatoIncorrecto() {

        litros = "Veinte";
        precioTotal = "50";

        sut.onBtnGuardarClicked(litros, precioTotal);

        verify(mockRegistrarView).mostrarError("Error: Los datos introducidos no son válidos", true, false);
        verify(mockRepostajeDAO, never()).registrarRepostaje(any());
    }

    @Test
    public void testRegistrarRepostajeValoresNegativos() {

        litros = "-50";
        precioTotal = "50";

        sut.onBtnGuardarClicked(litros, precioTotal);

        verify(mockRegistrarView).mostrarError("Error: Los valores deben ser positivos", true, false);
        verify(mockRepostajeDAO, never()).registrarRepostaje(any());
    }

    @Test
    public void testRegistrarRepostajeValoresVacios() {

        litros = "";
        precioTotal = "";

        sut.onBtnGuardarClicked(litros, precioTotal);

        verify(mockRegistrarView).mostrarError("Error: Los campos no deben estar vacíos", true, true);
        verify(mockRepostajeDAO, never()).registrarRepostaje(any());
    }


}

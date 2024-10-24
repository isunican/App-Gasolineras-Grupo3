package es.unican.gasolineras.RegistrarRepostajeMenu;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.unican.gasolineras.activities.RegistrarRepostajeMenu.RegistrarPresenter;
import es.unican.gasolineras.model.Repostaje;
import es.unican.gasolineras.repository.RepostajeDAO;

public class RegistrarRepostajeTest {

    private static RegistrarPresenter sut;
    private String litros;
    private String precioTotal;

    @Mock
    private static RepostajeDAO mockRepostajeDAO;

    @BeforeEach
    public void inicializa(){

        MockitoAnnotations.openMocks(this);

        sut = new RegistrarPresenter(mockRepostajeDAO);
    }

    @Test
    public void testRegistrarRepostajeExito() {

        litros = "20";
        precioTotal = "30";
        Repostaje repostaje = new Repostaje();
        repostaje.setLitros(Double.parseDouble(litros));
        repostaje.setPrecioTotal(Double.parseDouble(precioTotal));

        Repostaje repostajeIntroducido = sut.onBtnGuardarClicked(litros, precioTotal);

        verify(mockRepostajeDAO).registrarRepostaje(repostajeIntroducido);
        assertEquals(repostaje, repostajeIntroducido);

    }

    @Test
    public void testRegistrarRepostajeValoresFormatoIncorrecto() {

        litros = "Veinte";
        precioTotal = "50";

        assertThrows(OperacionNoValidaException.class, () -> {
            Repostaje repostaje = sut.onBtnGuardarClicked(litros, precioTotal);
        });

        assertNull(repostaje);
        verify(mockRepostajeDAO, never()).registrarRepostaje(any());
    }

    @Test
    public void testRegistrarRepostajeValoresNegativos() {

        litros = "-50";
        precioTotal = "50";

        assertThrows(OperacionNoValidaException.class, () -> {
            Repostaje repostaje = sut.onBtnGuardarClicked(litros, precioTotal);
        });

        assertNull(repostaje);
        verify(mockRepostajeDAO, never()).registrarRepostaje(any());
    }

    @Test
    public void testRegistrarRepostajeValoresVacios() {

        litros = "";
        precioTotal = "";

        assertThrows(OperacionNoValidaException.class, () -> {
            Repostaje repostaje = sut.onBtnGuardarClicked(litros, precioTotal);
        });

        assertNull(repostaje);
        verify(mockRepostajeDAO, never()).registrarRepostaje(any());
    }


}

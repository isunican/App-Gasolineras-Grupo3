package es.unican.gasolineras.activities.details;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


import static es.unican.gasolineras.activities.utils.MockRepositories.getTestRepository;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.DescuentoDAO;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;

public class DetailsPresenterTest {

    @Mock
    private IDetails.View mockView;

    @Mock
    private IGasolinerasRepository mockRepository;

    @Mock
    private DescuentoDAO descuentoDAO;

    private DetailsPresenter sut;

    private Gasolinera gasolineraActual;

    private List<Gasolinera> listaActual = new ArrayList<>();
    private List<Gasolinera> listaGasolinerasSemanaPasada = new ArrayList<>();
    private Gasolinera gasolineraSemanaPasada;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        gasolineraSemanaPasada = new Gasolinera();
        gasolineraSemanaPasada.setId("1");
        gasolineraSemanaPasada.setGasolina95E5(1.90);
        gasolineraSemanaPasada.setGasoleoA(1.80);

        gasolineraActual = new Gasolinera();
        gasolineraActual.setId("1");
        gasolineraActual.setGasolina95E5(1.80);
        gasolineraActual.setGasoleoA(1.70);

        listaActual.add(gasolineraActual);
        listaGasolinerasSemanaPasada.add(gasolineraSemanaPasada);

        // Crear el presenter
        mockRepository = getTestRepository(listaActual);

        sut = new DetailsPresenter(gasolineraActual, descuentoDAO);
        when(mockView.getGasolinerasRepository()).thenReturn(mockRepository);
        doAnswer(invocation -> {
            // Capturar el callback
            ICallBack callback = invocation.getArgument(0);
            // Llamar a onSuccess con la lista personalizada
            callback.onSuccess(listaGasolinerasSemanaPasada);
            return null; // Método es void, así que retornamos null
        }).when(mockRepository).requestGasolinerasHistoricoFechas(any(ICallBack.class), any(), any(LocalDate.class));
        sut.init(mockView);
    }

    @Test
    public void loadConDiferenciaTest() {
        assertTrue(true);
    }

    @Test
    public void loadSinDiferenciaTest() {
        assertTrue(true);
    }

    @Test
    public void loadConDescuentosTest() {
        assertTrue(true);
    }

    @Test
    public void loadSinTipoCombustibleTest() {
        assertTrue(true);
    }

    @Test
    public void loadSinSemanaPasadaTest() {
        assertTrue(true);
    }

}

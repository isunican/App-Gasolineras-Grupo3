package es.unican.gasolineras.activities.ConsultarRepostaje;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import es.unican.gasolineras.model.Repostaje;
import es.unican.gasolineras.repository.RepostajeDAO;

@RunWith(RobolectricTestRunner.class)
public class ConsultarPresenterTest {

    Repostaje repostaje1, repostaje2, repostaje3;
    double res0, res1, res2, res3;


    @Mock
    private RepostajeDAO mockRepostajeDAO;

    private ConsultarPresenter consultarPresenter;

    //Obtenemos el mes y el año actual
    YearMonth mesActual = YearMonth.now();
    LocalDate fechaIni = mesActual.atDay(1);
    LocalDate fechaFin = mesActual.atEndOfMonth();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String fechaIniStr = fechaIni.format(formatter);
    String fechaFinStr = fechaFin.format(formatter);

    @Before
    public void inicializa() throws Exception {
        MockitoAnnotations.openMocks(this);
        consultarPresenter = new ConsultarPresenter(mockRepostajeDAO);

        //Inicializar los repostajes con los valores adecuados
        repostaje1 = new Repostaje();
        repostaje1.setPrecioTotal(30.0);
        repostaje1.setLitros(25.40);
        repostaje2 = new Repostaje();
        repostaje2.setPrecioTotal(12.34);
        repostaje2.setLitros(7.86);
        repostaje3 = new Repostaje();
        repostaje3.setPrecioTotal(56.78);
        repostaje3.setLitros(47.33);

    }

    //Test que comprueba el funcionamiento del metodo calcularAcumuladoMes()
    @Test
    public void calcularAcumuladoMesTest(){
        Context context = ApplicationProvider.getApplicationContext();

        //repostajesMes contiene una lista vacía ([]).
        when(mockRepostajeDAO.repostajesPorRangoDeFechas(fechaIniStr, fechaFinStr))
                .thenReturn(List.of());
        res0 = consultarPresenter.calcularAcumuladoMes();
        assertEquals(0.0, res0);

        //repostajesMes contiene una lista con un objeto Repostaje(precioTotal = 30.0).
        when(mockRepostajeDAO.repostajesPorRangoDeFechas(fechaIniStr, fechaFinStr))
                .thenReturn(List.of(repostaje1));
        res1 = consultarPresenter.calcularAcumuladoMes();
        assertEquals(30.0, res1);

        //repostajesMes contiene una lista con dos objetos de tipo Repostaje:
        //Repostaje(precioTotal = 30.0), Repostaje(precioTotal = 12.34).
        when(mockRepostajeDAO.repostajesPorRangoDeFechas(fechaIniStr, fechaFinStr))
                .thenReturn(List.of(repostaje1, repostaje2));
        res2 = consultarPresenter.calcularAcumuladoMes();
        assertEquals(42.34, res2);


        //repostajesMes contiene una lista con tres objetos de tipo Repostaje:
        //Repostaje(precioTotal = 30.0), Repostaje(precioTotal = 12.34), Repostaje(precioTotal = 56.78).
        when(mockRepostajeDAO.repostajesPorRangoDeFechas(fechaIniStr, fechaFinStr))
                .thenReturn(List.of(repostaje1, repostaje2, repostaje3));
        res3 = consultarPresenter.calcularAcumuladoMes();
        assertEquals(99.12, res3);
    }


    //Test que comprueba el funcionamiento del metodo calcularPrecioMedioLitro()
    @Test
    public void calcularPrecioMedioLitroTest(){
        Context context = ApplicationProvider.getApplicationContext();

        //repostajesMesAnterior contiene una lista vacía ([]).
        when(mockRepostajeDAO.repostajesPorRangoDeFechas(fechaIniStr, fechaFinStr))
                .thenReturn(List.of());
        res0 = consultarPresenter.calcularPrecioMedioLitro();
        assertEquals(0.0, res0);

        //repostajesMesAnterior contiene una lista con un objeto
        //Repostaje(precioTotal = 30.0, litros = 25.40).
        when(mockRepostajeDAO.repostajesPorRangoDeFechas(fechaIniStr, fechaFinStr))
                .thenReturn(List.of(repostaje1));
        res1 = consultarPresenter.calcularPrecioMedioLitro();
        assertEquals(1.18, res1, 0.01);

        //repostajesMesAnterior contiene una lista con dos objetos de tipo Repostaje:
        //Repostaje(precioTotal = 30.0, litros = 25.40), Repostaje(precioTotal = 12.34, litros = 7.86).
        when(mockRepostajeDAO.repostajesPorRangoDeFechas(fechaIniStr, fechaFinStr))
                .thenReturn(List.of(repostaje1, repostaje2));
        res2 = consultarPresenter.calcularPrecioMedioLitro();
        assertEquals(1.27, res2, 0.01);

        //repostajesMesAnterior contiene una lista con tres objetos de tipo Repostaje:
        //Repostaje(precioTotal = 30.0, litros = 25.40), Repostaje(precioTotal = 12.34, litros = 7.86),
        //Repostaje(precioTotal = 56.78, litros = 47.33).
        when(mockRepostajeDAO.repostajesPorRangoDeFechas(fechaIniStr, fechaFinStr))
                .thenReturn(List.of(repostaje1, repostaje2, repostaje3));
        res3 = consultarPresenter.calcularPrecioMedioLitro();
        assertEquals(1.22, res3, 0.01);
    }
}

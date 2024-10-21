package es.unican.gasolineras.activities.ConsultarRepostaje;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.Repostaje;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DatabaseFunction;
import es.unican.gasolineras.repository.RepostajeDAO;

public class ConsultarPresenter implements  IConsultar.Presenter {

    private IConsultar.View view;
    private RepostajeDAO repostajeDAO;

    public ConsultarPresenter(RepostajeDAO repostajeDAO) {
        this.repostajeDAO = repostajeDAO;
    }


    /**
     * @see IConsultar.Presenter#init(IConsultar.View)
     * @param view the view to control
     */
    @Override
    public void init(IConsultar.View view) {

        this.view = view;
        this.view.init();
        load();
    }


    @Override
    public double calcularPrecioMedioLitro() {
        List<Repostaje> repostajesMesAnterior = obtenerRepostajesDelMes();

        if(repostajesMesAnterior.isEmpty()) {
            return 0;
        }

        double sumaPreciosPonderados = 0;
        double sumaLitros = 0;

        for (Repostaje repostaje : repostajesMesAnterior) {
            double precioPorLitro = repostaje.getPrecioTotal() / repostaje.getLitros();
            sumaPreciosPonderados += precioPorLitro * repostaje.getLitros();
            sumaLitros += repostaje.getLitros();
        }
        return sumaPreciosPonderados / sumaLitros;
    }

    @Override
    public List<Repostaje> obtenerRepostajesDelMes() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);  // Primer día del mes actual
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String inicioDelMes = sdf.format(calendar.getTime());

        // Establecer el último día del mes actual
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String finDelMes = sdf.format(calendar.getTime());

        // Obtener los repostajes del mes actual desde el DAO utilizando el rango de fechas
        return repostajeDAO.repostajesPorRangoDeFechas(inicioDelMes, finDelMes);

    }

    @Override
    public double calcularAcumuladoMes() {
        List<Repostaje> repostajesMes = obtenerRepostajesDelMes();

        if(repostajesMes.isEmpty()) {
            return 0;
        }

        double totalAcumulado = 0;

        for (Repostaje repostaje : repostajesMes) {
            totalAcumulado += repostaje.getPrecioTotal();
        }

        return totalAcumulado;
    }

    private void load() {


        List<Repostaje> repostajes = repostajeDAO.repostajes();

        try {
            view.showRepostajes(repostajes);
            view.showLoadCorrect(repostajes.size());
        } catch (Throwable e) {
            view.showLoadError();
        }
    }
}
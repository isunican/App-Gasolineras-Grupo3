package es.unican.gasolineras.activities.RegistrarRepostajeMenu;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.unican.gasolineras.model.Repostaje;
import es.unican.gasolineras.repository.RepostajeDAO;

public class RegistrarPresenter implements IRegistrar.Presenter {

    /** The view that is controlled by this presenter */
    private IRegistrar.View view;
    private RepostajeDAO repostajeDAO;

    public RegistrarPresenter(RepostajeDAO repostajeDAO) {
        this.repostajeDAO = repostajeDAO;
    }

    @Override
    public void init(IRegistrar.View view) {
        this.view = view;
        this.view.init();
    }

    @Override
    public void onBtnGuardarClicked(String litros, String precioTotal) {
        // Validación de los datos de entrada
        if (litros.isEmpty() || precioTotal.isEmpty()) {
            view.mostrarError("Error: Los campos no deben estar vacíos");
            return;
        }

        try {
            // Conversión de litros y precio total
            double litrosNum = Double.parseDouble(litros);
            double precioTotalNum = Double.parseDouble(precioTotal);

            // Validar que los valores sean positivos
            if (litrosNum <= 0 || precioTotalNum <= 0) {
                view.mostrarError("Error: Los valores deben ser positivos");
                return;
            }

            // Crear el objeto Repostaje
            Repostaje repostaje = new Repostaje();
            repostaje.setLitros(litrosNum);
            repostaje.setPrecioTotal(precioTotalNum);

            // Asignar la fecha actual al repostaje
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaActual = sdf.format(Calendar.getInstance());
            repostaje.setFechaRepostaje(fechaActual);

            repostajeDAO.registrarRepostaje(repostaje);
            
            view.showBtnGuardar(litros, precioTotal);

        } catch (NumberFormatException e) {
            // Manejo de excepciones si los valores ingresados no son válidos
            view.mostrarError("Error: Los datos introducidos no son válidos");
        }
    }

    @Override
    public void onBtnCancelarClicked() {
        view.showBtnCancelar();
    }
}

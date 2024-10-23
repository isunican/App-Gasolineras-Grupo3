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
    //private int uid = 1;

    public RegistrarPresenter(RepostajeDAO repostajeDAO) {
        this.repostajeDAO = repostajeDAO;
    }

    /**
     * @see IRegistrar.Presenter#init(IRegistrar.View)
     * @param view vista a iniciar
     */
    @Override
    public void init(IRegistrar.View view) {
        this.view = view;
        this.view.init();
    }

    /**
     * @see IRegistrar.Presenter#onBtnGuardarClicked(String, String)
     * @param litros litros a guardar
     * @param precioTotal precioTotal del repostaje a anhadir
     */
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
            //repostaje.setUid(uid);
            repostaje.setLitros(litrosNum);
            repostaje.setPrecioTotal(precioTotalNum);
            //uid++;


            // Asignar la fecha actual al repostaje
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaActual = sdf.format(Calendar.getInstance().getTime());
            repostaje.setFechaRepostaje(fechaActual);

            try {
                repostajeDAO.registrarRepostaje(repostaje);
                view.showBtnGuardar(litros, precioTotal);

            } catch (Exception e) {
                view.mostrarError("Error al registrar el repostaje en la base de datos");
            }

        } catch (NumberFormatException e) {
            // Manejo de excepciones si los valores ingresados no son válidos
            view.mostrarError("Error: Los datos introducidos no son válidos");
        }
    }

    /**
     * @see IRegistrar.Presenter#onBtnCancelarClicked()
     */
    @Override
    public void onBtnCancelarClicked() {
        view.showBtnCancelar();
    }
}

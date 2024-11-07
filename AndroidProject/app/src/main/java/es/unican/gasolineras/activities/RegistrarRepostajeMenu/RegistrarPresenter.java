package es.unican.gasolineras.activities.RegistrarRepostajeMenu;


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

        boolean errorLitros;
        boolean errorPrecioTotal;

        // Validación de los datos de entrada
        if (litros.isEmpty() || precioTotal.isEmpty()) {
            errorLitros = litros.isEmpty();
            errorPrecioTotal = precioTotal.isEmpty();
            view.mostrarError("Error: Los campos no deben estar vacíos", errorLitros, errorPrecioTotal);
            return;

        }

        try {
            // Conversión de litros y precio total
            double litrosNum = Double.parseDouble(litros.replace(",","."));
            double precioTotalNum = Double.parseDouble(precioTotal.replace(",","."));

            // Validar que los valores sean positivos
            if (litrosNum <= 0 || precioTotalNum <= 0) {
                errorLitros = litrosNum <= 0;
                errorPrecioTotal = precioTotalNum <= 0;
                view.mostrarError("Error: Los valores deben ser positivos", errorLitros, errorPrecioTotal);
                return;
            }

            // Crear el objeto Repostaje
            Repostaje repostaje = new Repostaje();
            repostaje.setLitros(litrosNum);
            repostaje.setPrecioTotal(precioTotalNum);


            // Asignar la fecha actual al repostaje
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaActual = sdf.format(Calendar.getInstance().getTime());
            repostaje.setFechaRepostaje(fechaActual);

            registrarRepostaje(repostaje, litros, precioTotal);

        } catch (NumberFormatException e) {
            // Manejo de excepciones si los valores ingresados no son válidos
            errorLitros = false;
            errorPrecioTotal = false;

            try{
                Double.parseDouble(litros.replace(",","."));
            }catch(NumberFormatException ex) {
                errorLitros = true;
            }

            try{
                Double.parseDouble(precioTotal.replace(",","."));
            }catch(NumberFormatException ex) {
                errorPrecioTotal = true;
            }
            view.mostrarError("Error: Los datos introducidos no son válidos", errorLitros, errorPrecioTotal);
        }

    }

    /**
     * @see IRegistrar.Presenter#onBtnCancelarClicked()
     */
    @Override
    public void onBtnCancelarClicked() {
        view.showBtnCancelar();
    }

    /**
     * Registra un repostaje
     * @param repostaje el repostaje a registrar
     * @param litros litros del repostaje
     * @param precioTotal precio del repostaje
     */
    private void registrarRepostaje(Repostaje repostaje, String litros, String precioTotal) {
        try {
            repostajeDAO.registrarRepostaje(repostaje);
            view.showBtnGuardar(litros, precioTotal);
        } catch (Exception e) {
            view.mostrarError("Error al registrar el repostaje en la base de datos", false, false);
        }
    }
}

package es.unican.gasolineras.activities.RegistrarDescuentoEnMarca;

import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;

import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.DescuentoDAO;

public class RegistrarDescuentoPresenter implements IRegistrarDescuento.Presenter{

    private IRegistrarDescuento.View view;
    private DescuentoDAO descuentoDAO;

    public RegistrarDescuentoPresenter(DescuentoDAO descuentoDAO) {
        this.descuentoDAO = descuentoDAO;
    }

    /**
     * @see IRegistrarDescuento.Presenter#init(IRegistrarDescuento.View)
     * @param view the view to control
     */
    @Override
    public void init(IRegistrarDescuento.View view){
        this.view = view;
        this.view.init();
    }

    /**
     * @see IRegistrarDescuento.Presenter#onBtnCancelarClicked()
     */
    public void onBtnCancelarClicked() {
        view.showBtnCancelar();
    }

    /**
     * @see IRegistrarDescuento.Presenter#onBtnGuardarClicked(String, double)
     * @param marca marca a la que aplicar el filtro
     * @param descuento descuento que se aplica
     */
    public void onBtnGuardarClicked(String marca, double descuento) {
        boolean errorDescuento;

        //Validar que el valor sea positivo
        if(descuento < 0){
            errorDescuento = true;
            view.mostrarError("Error: El valor debe ser positivo", errorDescuento);
            return;
        }

        //Validar que el valor sea menor o igual a 100
        if(descuento > 100){
            errorDescuento = true;
            view.mostrarError("Error: El valor debe estar entre 0 y 100", errorDescuento);
            return;
        }

        try {
            //Se busca si ya existe un descuento para esa marca
            Descuento d = descuentoDAO.descuentoPorMarca(marca.toUpperCase());
            //Si no existe se crea y anhade
            if (d == null){
                //Crear el objeto descuento
                Descuento desc = new Descuento();
                desc.setMarca(marca);
                desc.setDescuento(descuento);
                descuentoDAO.registrarDescuento(desc);
            //Si existe se actualiza
            } else{
                d.setMarca(marca);
                d.setDescuento(descuento);
                descuentoDAO.actualizaDescuento(d);
            }

            view.showBtnGuardar(marca, descuento);
        }catch (SQLiteException e) {
            view.mostrarError("Error al registrar el descuento en la base de datos", false);
        }

    }
}

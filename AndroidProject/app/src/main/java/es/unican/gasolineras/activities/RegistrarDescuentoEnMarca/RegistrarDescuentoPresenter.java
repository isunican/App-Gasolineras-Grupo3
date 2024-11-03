package es.unican.gasolineras.activities.RegistrarDescuentoEnMarca;

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
     * @see IRegistrarDescuento.Presenter#onBtnGuardarClicked(String, Integer)
     * @param marca marca a la que aplicar el filtro
     * @param descuento descuento que se aplica
     */
    public void onBtnGuardarClicked(String marca, Integer descuento) {
        boolean errorDescuento;

        if (descuento == null){
            errorDescuento = true;
            view.mostrarError("Error: El campo no debe estar vacio", errorDescuento);
            return;
        }

        //Validar que el valor sea positivo
        if(descuento < 0){
            errorDescuento = true;
            view.mostrarError("Error: El valor debe ser positivo", errorDescuento);
            return;
        }

        //Validar que el valor sea menor o igual a 0
        if(descuento > 100){
            errorDescuento = true;
            view.mostrarError("Error: El valor debe ser menor que 100", errorDescuento);
            return;
        }

        //Crear el objeto descuento
        Descuento desc = new Descuento();
        desc.setMarca(marca);
        desc.setDescuento(descuento);

        try {
            descuentoDAO.registrarDescuento(desc);
            view.showBtnGuardar(marca, descuento);
        }catch (Exception e){
            view.mostrarError("Error al registrar el descuento en la base de datos", false);
        }

    }
}

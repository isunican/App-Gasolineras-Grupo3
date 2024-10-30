package es.unican.gasolineras.activities.RegistrarDescuentoEnMarca;

import java.util.List;

import es.unican.gasolineras.activities.ConsultarRepostaje.IConsultar;
import es.unican.gasolineras.activities.RegistrarRepostajeMenu.IRegistrar;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.DescuentoDAO;
import es.unican.gasolineras.repository.RepostajeDAO;

public class RegistrarDescuentoPresenter implements IRegistrarDescuento.Presenter{

    private IRegistrarDescuento.View view;
    private DescuentoDAO descuentoDAO;

    public RegistrarDescuentoPresenter(DescuentoDAO descuentoDAO) {
        this.descuentoDAO = descuentoDAO;
    }

    /**
     * @see IRegistrarDescuento.Presenter#init(IResgistarDescuento.View)
     * @param view the view to control
     */
    @Override
    public void init(IRegistrarDescuento.View view){

        this.view = view;
        this.view.init();
        load();

    }

    private void load(){

    }


    /**
     * @see IRegistrar.Presenter#onBtnGuardarClicked(String, String)
     * @param marca marca a la que aplicar el filtro
     * @param descuento descuento que se aplica
     */
    public void onBtnGuardarClicked(String marca, int descuento) {
    }

    /**
     * @see IRegistrar.Presenter#onBtnCancelarClicked()
     */
    public void onBtnCancelarClicked() {
        view.showBtnCancelar();
    }
}

package es.unican.gasolineras.activities.ConsultarRepostaje;

import java.util.List;

import es.unican.gasolineras.model.Repostaje;

public interface IConsultar {

    /**
     * Calcula el precio por litro pagado en un repostaje
     * @return el precio por litro
     */
    public double calcularPrecioLitro();

    /**
     * Calcula el precio medio por litro pagado en el mes actual
     * @return
     */
    public double calcularPrecioMedioLitro();


    /**
     * Muestra los repostajes realizados
     * @param repostajes the list of charging stations
     */
    public void showRepostajes(List<Repostaje> repostajes);

    public void showLoadCorrect(int repostajes);
    public void showLoadError();
    /**
     *
     * @return
     */
    public double calcularAcumuladoMes();
}

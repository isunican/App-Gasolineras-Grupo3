package es.unican.gasolineras.activities.ConsultarRepostaje;

import java.util.List;

import es.unican.gasolineras.model.Repostaje;

public interface IConsultar {

    /**
     * Calcula el precio medio por litro pagado en el mes actual
     * @return
     */
    public double calcularPrecioMedioLitro();

    public List<Repostaje> obtenerRepostajesDelMes();
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

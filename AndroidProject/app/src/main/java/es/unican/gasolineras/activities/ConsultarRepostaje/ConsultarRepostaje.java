package es.unican.gasolineras.activities.ConsultarRepostaje;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.unican.gasolineras.GasolinerasApp;
import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.model.Repostaje;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.RepostajeDAO;

public class ConsultarRepostaje extends AppCompatActivity implements IConsultar {

    /**
     * @see AppCompatActivity#onCreate(Bundle)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_repostaje_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        assert bar != null;  // to avoid warning in the line below
        bar.setDisplayHomeAsUpEnabled(true);  // show back button in action bar

        TextView repostajesMes = findViewById(R.id.tvRepostajesMes);
        TextView precioMedioLitro = findViewById(R.id.tvPrecioMedioLitro);
        TextView acumuladoMes = findViewById(R.id.tvAcumuladoMes);

        repostajesMes.setText(String.valueOf(obtenerRepostajesDelMes()));
        precioMedioLitro.setText(String.valueOf(calcularPrecioMedioLitro()));
        acumuladoMes.setText(String.valueOf(calcularAcumuladoMes()));

        load();

    }

    @Override
    public double calcularPrecioMedioLitro() {
        List<Repostaje> repostajesMesAnterior = obtenerRepostajesDelMes();

        double sumaPreciosPonderados = 0;
        double sumaLitros = 0;

        for (Repostaje repostaje : repostajesMesAnterior) {
            double precioPorLitro = repostaje.getPrecioTotal() / repostaje.getLitros();
            sumaPreciosPonderados += precioPorLitro * repostaje.getLitros();
            sumaLitros += repostaje.getLitros();
        }

        // Evitar división por cero
        if (sumaLitros == 0) {
            return 0;
        }

        // Retornar el precio medio ponderado por los litros repostados
        return sumaPreciosPonderados / sumaLitros;
    }

    @Override
    public double calcularAcumuladoMes() {
        // Obtener la lista de repostajes del mes anterior
        List<Repostaje> repostajes = obtenerRepostajesDelMes();  // Método que devuelve los repostajes del mes anterior
        double totalAcumulado = 0;

        // Iterar sobre los repostajes del mes anterior para sumar el precio total de cada repostaje
        for (Repostaje repostaje : repostajes) {
            totalAcumulado += repostaje.getPrecioTotal();
        }

        // Retorna el acumulado total del mes
        return totalAcumulado;
    }

    @Override
    public List<Repostaje> obtenerRepostajesDelMes() {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());

        // Obtener el mes en formato 'YYYY-MM'
        String mesAnterior = sdf.format(calendar.getTime());

        // Obtener los repostajes del mes anterior desde el DAO
        return GasolinerasApp.getDatabase().repostajeDao().repostajesPorMes(mesAnterior);
    }

    /**
     * Muestra los repostajes realizados
     * @param repostajes the list of charging stations
     */
    @Override
    public void showRepostajes(List<Repostaje> repostajes) {
        ListView list = findViewById(R.id.lvRepostajes);
        RepostajesArrayAdapter adapter = new RepostajesArrayAdapter(this, repostajes);
        list.setAdapter(adapter);
    }

    /**
     *
     * @param
     */
    @Override
    public void showLoadCorrect(int repostajes) {
        Toast.makeText(this, "Cargados " + repostajes + " repostajes", Toast.LENGTH_SHORT).show();
    }

    /**
     * @see IMainContract.View#showLoadError()
     */
    @Override
    public void showLoadError() {
        Toast.makeText(this, "Error cargando los repostajes", Toast.LENGTH_SHORT).show();
    }

    private void load() {

        RepostajeDAO repostajeDao = GasolinerasApp.getDatabase().repostajeDao();
        List<Repostaje> repostajes = repostajeDao.repostajes();

        try {
            showRepostajes(repostajes);
            showLoadCorrect(repostajes.size());
        } catch (Throwable e) {
            showLoadError();
        }
    }
}
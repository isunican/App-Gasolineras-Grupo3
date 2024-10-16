package es.unican.gasolineras.activities.ConsultarRepostaje;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.IDCCAAs;
import es.unican.gasolineras.model.Repostaje;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;
import es.unican.gasolineras.repository.RepostajeDAO;

public class ConsultarRepostaje extends AppCompatActivity implements IConsultar {

    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
            AppDatabase.class, "database-name").build();


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

        load();

    }

    @Override
    public double calcularPrecioLitro() {

        return 0;
    }

    @Override
    public double calcularPrecioMedioLitro() {
        return 0;
    }

    @Override
    public double calcularAcumuladoMes() {
        return 0;
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
        RepostajeDAO repostajeDao = db.repostajeDao();
        List<Repostaje> repostajes = repostajeDao.repostajes();

        try {
            showRepostajes(repostajes);
            showLoadCorrect(repostajes.size());
        } catch (Throwable e) {
            showLoadError();
        }
    }
}

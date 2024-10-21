package es.unican.gasolineras.activities.ConsultarRepostaje;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.unican.gasolineras.R;

import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.Repostaje;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DatabaseFunction;
import es.unican.gasolineras.repository.RepostajeDAO;

public class ConsultarView extends AppCompatActivity implements IConsultar.View {


    private ConsultarPresenter presenter;
    public AppDatabase db;
    /**
     * @see AppCompatActivity#onCreate(Bundle)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_consulta_repostaje_view);
        } catch (Exception e) {
            // Si ocurre una excepción, muestra el mensaje de error
            new AlertDialog.Builder(ConsultarView.this)
                    .setTitle("Error")
                    .setMessage(getString(R.string.error_acceso_bbdd))
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Puedes agregar alguna acción adicional si es necesario
                        Intent intent = new Intent(ConsultarView.this, MainView.class);
                        startActivity(intent);
                    })
                    .show();
        }
        db = DatabaseFunction.getDatabase(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new ConsultarPresenter(db.repostajeDao());
        presenter.init(this);

    }

    @Override
    public void init() {
        TextView repostajesMes = findViewById(R.id.tvRepostajesMes);
        TextView precioMedioLitro = findViewById(R.id.tvPrecioMedioLitro);
        TextView acumuladoMes = findViewById(R.id.tvAcumuladoMes);

        repostajesMes.setText(String.valueOf(presenter.obtenerRepostajesDelMes().size()));
        precioMedioLitro.setText(String.format(Locale.getDefault(),"%.2f", presenter.calcularPrecioMedioLitro()));
        acumuladoMes.setText(String.format(Locale.getDefault(),"%.2f", presenter.calcularAcumuladoMes()));

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

}

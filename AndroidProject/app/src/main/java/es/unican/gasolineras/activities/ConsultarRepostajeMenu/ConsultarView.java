package es.unican.gasolineras.activities.ConsultarRepostajeMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.RegistrarRepostajeMenu.RegistrarView;
import es.unican.gasolineras.activities.main.MainView;

public class ConsultarView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            // setContentView(R.layout.activity_consulta_repostaje_view);
        } catch (Exception e) {
            // Si ocurre una excepción, muestra el mensaje de error
            new AlertDialog.Builder(ConsultarView.this)
                    .setTitle("Error")
                    .setMessage(getString(R.string.error_acceso_bbdd))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Puedes agregar alguna acción adicional si es necesario
                            Intent intent = new Intent(ConsultarView.this, MainView.class);
                            startActivity(intent);
                        }
                    })
                    .show();
        }

    }
}

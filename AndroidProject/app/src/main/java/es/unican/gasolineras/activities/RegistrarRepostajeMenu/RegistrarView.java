package es.unican.gasolineras.activities.RegistrarRepostajeMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.main.MainView;

public class RegistrarView extends AppCompatActivity {

    private EditText textLitros, textPrecioTotal;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_repostaje);

        // Encuentra el botón por su ID
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        // Establece un listener para el botón
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    new AlertDialog.Builder(RegistrarView.this)
                            .setTitle("Confirmación")
                            .setMessage(getString(R.string.registro_exito))
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //setContentView(R.layout.activity_consulta_repostaje_view);

                                }
                            })
                            .show();

                } catch (Exception e) {
                    // Si ocurre una excepción, muestra el mensaje de error
                    new AlertDialog.Builder(RegistrarView.this)
                            .setTitle("Error")
                            .setMessage(getString(R.string.error_acceso_bbdd))
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Puedes agregar alguna acción adicional si es necesario
                                    Intent intent = new Intent(RegistrarView.this, MainView.class);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                Intent intent = new Intent(RegistrarView.this, MainView.class);
                startActivity(intent);
            }
        });

    }

}

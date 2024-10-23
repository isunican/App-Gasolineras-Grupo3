package es.unican.gasolineras.activities.RegistrarRepostajeMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import androidx.appcompat.app.AppCompatActivity;


import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.main.MainView;

public class RegistrarView extends AppCompatActivity implements IRegistrar.View {

    private RegistrarPresenter presenter;
    private EditText textLitros, textPrecioTotal;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_repostaje);

        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnCancelar = findViewById(R.id.btnCancelar);
        
        // Establece un listener para el botón
        btnGuardar.setOnClickListener(view -> {
                    try {
                        new AlertDialog.Builder(RegistrarView.this)
                                .setTitle("Confirmación")
                                .setMessage(getString(R.string.registro_exito))
                                .setPositiveButton("OK", (dialog, which) -> {
                                    setContentView(R.layout.activity_consulta_repostaje_view);
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
                });
        // Listener para el botón Cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrarView.this, MainView.class);
                startActivity(intent);
            }
        });

        // Instantiate presenter and initialize the view
        presenter = new RegistrarPresenter();
        presenter.init(this);
    }

    @Override
    public void init() {
        // Encuentra las vistas por su ID

        textLitros = findViewById(R.id.textLitros);
        textPrecioTotal = findViewById(R.id.textPrecioTotal);
        tvError = findViewById(R.id.tvError);


        // Listener para el botón Guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String litros = textLitros.getText().toString();
                String precioTotal = textPrecioTotal.getText().toString();

                // Delegar la validación y el registro al presenter
                presenter.onGuardarRepostaje(litros, precioTotal);
            }
        });
    }

    @Override
    public void mostrarError(String mensajeError) {
        tvError.setText(mensajeError);
        tvError.setVisibility(View.VISIBLE); // Muestra el TextView del error
    }

    @Override
    public void mostrarConfirmacion(String mensajeConfirmacion) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage(mensajeConfirmacion)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Regresar a la página principal
                        Intent intent = new Intent(RegistrarView.this, MainView.class);
                        startActivity(intent);
                    }
                })
                .show();
    }
}

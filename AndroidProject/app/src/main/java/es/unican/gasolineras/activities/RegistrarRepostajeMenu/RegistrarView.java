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
import androidx.appcompat.widget.Toolbar;
import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.ConsultarRepostaje.ConsultarView;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DatabaseFunction;

public class RegistrarView extends AppCompatActivity implements IRegistrar.View {

    private RegistrarPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_repostaje);

        // The default theme does not include a toolbar.
        // In this app the toolbar is explicitly declared in the layout
        // Set this toolbar as the activity ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        AppDatabase db = DatabaseFunction.getDatabase(this);

        // Instantiate presenter and initialize the view
        presenter = new RegistrarPresenter(db.repostajeDao());
        presenter.init(this);
    }

    /**
     * @see IRegistrar.View#init()
     */
    @Override
    public void init() {

        EditText editTextLitros = findViewById(R.id.textLitros);
        EditText editTextPrecioTotal = findViewById(R.id.textPrecioTotal);

        Button buttonGuardar = findViewById(R.id.btnGuardar);
        buttonGuardar.setOnClickListener(v -> {
            // Lógica para obtener los valores de litros y precioTotal
            String litros = editTextLitros.getText().toString() ;  // Obtén el valor de la forma adecuada
            String precioTotal = editTextPrecioTotal.getText().toString(); // Obtén el valor de la forma adecuada
            presenter.onBtnGuardarClicked(litros, precioTotal); // Llama al método con los parámetros
        });

        Button buttonCancelar = findViewById(R.id.btnCancelar);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBtnCancelarClicked();
            }
        });
    }


    /**
     *
     * @see IRegistrar.View#mostrarError(String, boolean, boolean)
     *
     * @param mensajeError the error message to show
     */
    @Override
    public void mostrarError(String mensajeError, boolean errorLitros, boolean errorPrecioTotal) {
        TextView tvError = findViewById(R.id.tvError);
        tvError.setText(mensajeError);
        tvError.setVisibility(View.VISIBLE); // Muestra el TextView del error

        EditText editTextLitros = findViewById(R.id.textLitros);
        EditText editTextPrecioTotal = findViewById(R.id.textPrecioTotal);

        //Cambiar el fondo del campo correspondiente a rojo si hay un error
        if(errorLitros) {
            editTextLitros.setBackgroundResource(R.drawable.border_red);
        }else {
            editTextLitros.setBackgroundResource(R.drawable.border_default);
        }

        if(errorPrecioTotal) {
            editTextPrecioTotal.setBackgroundResource(R.drawable.border_red);
        }else {
            editTextPrecioTotal.setBackgroundResource(R.drawable.border_default);
        }
    }

    /**
     * @see IRegistrar.View#showBtnGuardar(String, String)
     */
    @Override
    public void showBtnGuardar(String litros, String precioTotal) {

        try {
            new AlertDialog.Builder(RegistrarView.this)
                    .setTitle("Confirmación")
                    .setMessage(getString(R.string.registro_exito))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Puedes agregar alguna acción adicional si es necesario
                            Intent intent = new Intent(RegistrarView.this, ConsultarView.class);
                            startActivity(intent);
                        }
                    })
                    .show();

        } catch (Exception e) {

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

    /**
     * @see IRegistrar.View#showBtnCancelar()
     */
    @Override
    public void showBtnCancelar() {

        Intent intent = new Intent(RegistrarView.this, MainView.class);
        startActivity(intent);

    }


}

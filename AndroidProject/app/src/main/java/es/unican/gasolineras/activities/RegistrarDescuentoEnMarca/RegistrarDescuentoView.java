package es.unican.gasolineras.activities.RegistrarDescuentoEnMarca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.RegistrarRepostajeMenu.IRegistrar;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DatabaseFunction;

public class RegistrarDescuentoView extends AppCompatActivity implements IRegistrarDescuento.View {

    private RegistrarDescuentoPresenter presenter;

    /**
     * @see AppCompatActivity#onCreate(Bundle)
     * @param savedInstanceState
     */
    @Override
    public void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_descuento_view);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registrar descuento");

        Spinner spn = findViewById(R.id.spMarcas);
        // Configuramos el Spinner
        // Tomamos sus valores posibles del array de strings "marcasArray", definido
        // en string.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.marcasArray,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);

        AppDatabase db = DatabaseFunction.getDatabase(this);

        presenter = new RegistrarDescuentoPresenter(db.descuentoDao());
        presenter.init(this);
    }

    /**
     * @see IRegistrarDescuento.View#init()
     */
    @Override
    public void init(){
        Spinner spMarca = findViewById(R.id.spMarcas);
        EditText textDescuento = findViewById(R.id.etDescuento);

        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String marca = spMarca.toString();
                int descuento = Integer.parseInt(textDescuento.getText().toString());
                presenter.onBtnGuardarClicked(marca, descuento);
            }
        });

        Button btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                presenter.onBtnCancelarClicked();
            }
        });
    }

    /**
     * @see IRegistrar.View#showBtnGuardar(String, String)
     */
    @Override
    public void showBtnGuardar(String marca, int descuento) {
        try {
            new AlertDialog.Builder(RegistrarDescuentoView.this)
                    .setMessage(getString(R.string.registro_descuento_exito))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(RegistrarDescuentoView.this, MainView.class);
                            startActivity(intent);
                        }
                    })
                    .show();

        } catch (Exception e) {

            new AlertDialog.Builder(RegistrarDescuentoView.this)
                    .setTitle("Error")
                    .setMessage(getString(R.string.error_acceso_bbdd))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Puedes agregar alguna acción adicional si es necesario
                            Intent intent = new Intent(RegistrarDescuentoView.this, MainView.class);
                            startActivity(intent);
                        }
                    })
                    .show();
        }

    }

    @Override
    public void showBtnCancelar() {

    }

    /**
     *
     * @see IRegistrarDescuento.View#mostrarError(String mensajeError, boolean errorDescuento)
     *
     * @param mensajeError the error message to show
     */
    @Override
    public void mostrarError(String mensajeError, boolean errorDescuento){
        TextView tvError = findViewById(R.id.tvError2);
        tvError.setText(mensajeError);
        tvError.setVisibility(View.VISIBLE);

        EditText etDescuento = findViewById(R.id.etDescuento);
        if(errorDescuento) {
            etDescuento.setBackgroundResource(R.drawable.border_red);
        }else {
            etDescuento.setBackgroundResource(R.drawable.border_default);
        }
    }

}

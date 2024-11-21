package es.unican.gasolineras.activities.RegistrarDescuentoEnMarca;

import android.app.AlertDialog;
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
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DatabaseFunction;

public class RegistrarDescuentoView extends AppCompatActivity implements IRegistrarDescuento.View {

    private RegistrarDescuentoPresenter presenter;

    /**
     * @see AppCompatActivity#onCreate(Bundle)
     * @param SavedInstanceState
     */
    @Override
    public void onCreate (Bundle SavedInstanceState){

        super.onCreate(SavedInstanceState);
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
        Spinner spMarcas = findViewById(R.id.spMarcas);
        EditText textDescuento = findViewById(R.id.etDescuento);

        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(v -> {
            String marca = spMarcas.getSelectedItem().toString().toUpperCase();
            String descuentoStr = textDescuento.getText().toString().trim();
            //Se comprueba que el campo del descuento no este vacio
            if(descuentoStr.isEmpty()){
                mostrarError("Los campos no deben estar vacios", true);
                return;
            }
            try {
                double descuento = Double.parseDouble(descuentoStr);
                presenter.onBtnGuardarClicked(marca, descuento);
            }catch(NumberFormatException e){
                mostrarError("El valor del campo debe ser un número entero", true);
            }
        });

        Button btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> presenter.onBtnCancelarClicked());
    }


    /**
     * @see IRegistrarDescuento.View#showBtnGuardar(String, double)
     */
    @Override
    public void showBtnGuardar(String marca, double descuento) {
        try {
            new AlertDialog.Builder(RegistrarDescuentoView.this)
                    .setMessage(getString(R.string.registro_descuento_exito))
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .show();

        } catch (Exception e) {
            new AlertDialog.Builder(RegistrarDescuentoView.this)
                    .setTitle("Error")
                    .setMessage(getString(R.string.error_acceso_bbdd))
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .show();
        }
    }

    /**
     * @see IRegistrarDescuento.View#showBtnCancelar()
     */
    @Override
    public void showBtnCancelar(){
        finish();
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

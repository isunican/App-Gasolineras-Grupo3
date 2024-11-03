package es.unican.gasolineras.activities.RegistrarDescuentoEnMarca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.main.MainView;
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

        // The default theme does not include a toolbar.
        // In this app the toolbar is explicitly declared in the layout
        // Set this toolbar as the activity ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("Registrar descuento");
        setSupportActionBar(toolbar);

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
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String marca = spMarcas.getSelectedItem().toString();
                String descuentoStr = textDescuento.getText().toString().trim();
                if(descuentoStr.isEmpty()){
                    mostrarError("Los campos no deben estar vacios", true);
                    return;
                }
                try {
                    Integer descuento = Integer.parseInt(descuentoStr);
                    presenter.onBtnGuardarClicked(marca, descuento);
                }catch(NumberFormatException e){
                    mostrarError("El valor del campo debe ser un número entero", true);
                }
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
     * @see IRegistrarDescuento.View#showBtnGuardar(String, Integer)
     */
    @Override
    public void showBtnGuardar(String marca, Integer descuento) {
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
                            Intent intent = new Intent(RegistrarDescuentoView.this, MainView.class);
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    }

    /**
     * @see IRegistrarDescuento.View#showBtnCancelar()
     */
    @Override
    public void showBtnCancelar(){
        Intent intent = new Intent(RegistrarDescuentoView.this, MainView.class);
        startActivity(intent);
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

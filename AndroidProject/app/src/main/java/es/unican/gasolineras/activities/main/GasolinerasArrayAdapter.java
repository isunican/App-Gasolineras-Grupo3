package es.unican.gasolineras.activities.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.DescuentoDAO;

/**
 * Adapter that renders the gas stations in each row of a ListView
 */
public class GasolinerasArrayAdapter extends BaseAdapter {

    /** The list of gas stations to render */
    private final List<Gasolinera> gasolineras;

    /** Context of the application */
    private final Context context;

    private DescuentoDAO descuentoDAO;

    /**
     * Constructs an adapter to handle a list of gasolineras
     * @param context the application context
     * @param objects the list of gas stations
     */
    public GasolinerasArrayAdapter(@NonNull Context context, @NonNull List<Gasolinera> objects, DescuentoDAO descuentoDAO) {
        // we know the parameters are not null because of the @NonNull annotation
        this.gasolineras = objects;
        this.context = context;
        this.descuentoDAO = descuentoDAO;
    }

    @Override
    public int getCount() {
        return gasolineras.size();
    }

    @Override
    public Object getItem(int position) {
        return gasolineras.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("DiscouragedApi")  // to remove warnings about using getIdentifier
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Gasolinera gasolinera = (Gasolinera) getItem(position);
        Descuento descuento = descuentoDAO.descuentoPorMarca(gasolinera.getRotulo().toUpperCase());

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_main_list_item, parent, false);
        }

        if (gasolinera.isError()) {

            // Mostrar solo el mensaje de error en el TextView de la dirección
            setLogo(convertView, gasolinera);
            setAddress(convertView, gasolinera);
            TextView addressTextView = convertView.findViewById(R.id.tvAddress);
            addressTextView.setTextColor(Color.RED);               // Cambiar color a rojo
            addressTextView.setGravity(Gravity.CENTER);            // Centrar el texto
            addressTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            addressTextView.setTextSize(18);
            addressTextView.setMaxLines(2);                         // Limitar a dos líneas
            addressTextView.setEllipsize(TextUtils.TruncateAt.END); // Elipsis si el texto es muy largo
            addressTextView.setLineSpacing(0, 1.2f);

        }

        else {

            setLogo(convertView, gasolinera);
            setName(convertView, gasolinera);
            setAddress(convertView, gasolinera);
            setGasolina95Price(convertView, gasolinera, descuento);
            setDieselAPrice(convertView, gasolinera, descuento);

        }

        return convertView;
    }


    private void setLogo(View convertView, Gasolinera gasolinera) {
        String rotulo = gasolinera.getRotulo().toLowerCase();
        int imageID = context.getResources().getIdentifier(rotulo, "drawable", context.getPackageName());

        // If the rotulo is only numbers or imageID is 0, fall back to a generic logo
        if (imageID == 0 || TextUtils.isDigitsOnly(rotulo)) {
            imageID = context.getResources().getIdentifier("generic", "drawable", context.getPackageName());
        }

        if (imageID != 0) {
            ImageView view = convertView.findViewById(R.id.ivLogo);
            view.setImageResource(imageID);
        }
    }

    private void setName(View convertView, Gasolinera gasolinera) {
        TextView tv = convertView.findViewById(R.id.tvFecha);
        tv.setText(gasolinera.getRotulo());
    }

    private void setAddress(View convertView, Gasolinera gasolinera) {
        TextView tv = convertView.findViewById(R.id.tvAddress);
        tv.setText(gasolinera.getDireccion());
    }

    private void setGasolina95Price(View convertView, Gasolinera gasolinera, Descuento descuento) {
        double descuentoPorcentaje = 0;
        double precio;
        TextView tvLabel = convertView.findViewById(R.id.tv95Label);
        String label = context.getResources().getString(R.string.gasolina95label);
        tvLabel.setText(String.format("%s:", label));

        TextView tv = convertView.findViewById(R.id.tv95);
        // se comprueba que haya un porcentaje asignado
        if (descuento != null){
            descuentoPorcentaje = descuento.getDescuento();
        }else {
            descuentoPorcentaje = 0;
        }
        //si el descuento es distinto de cero se calcula el nuevo precio y se cambia de color al texto
        if (descuentoPorcentaje != 0){
            precio = calcularPrecioConDescuento(gasolinera.getGasolina95E5(), descuentoPorcentaje);
            tv.setText(String.format(Locale.getDefault(), "%.2f", precio));
            tv.setTextColor(context.getResources().getColor(R.color.text_green));
        // si el descuento es 0 entonces se deja el valor obtenido de la API y se pone con el color por defecto
        } else {
            tv.setText(String.valueOf(gasolinera.getGasolina95E5()));
            tv.setTextColor(context.getResources().getColor(R.color.text_default));
        }

    }

    private void setDieselAPrice(View convertView, Gasolinera gasolinera, Descuento descuento) {
        double descuentoPorcentaje = 0;
        double precio;
        TextView tvLabel = convertView.findViewById(R.id.tvDieselALabel);
        String label = context.getResources().getString(R.string.dieselAlabel);
        tvLabel.setText(String.format("%s:", label));

        TextView tv = convertView.findViewById(R.id.tvDieselA);
        // se comprueba que haya un porcentaje asignado
        if (descuento != null){
            descuentoPorcentaje = descuento.getDescuento();
        } else {
            descuentoPorcentaje = 0;
        }
        //si el descuento es distinto de cero se calcula el nuevo precio y se cambia de color al texto
        if (descuentoPorcentaje != 0){
            precio = calcularPrecioConDescuento(gasolinera.getGasoleoA(), descuentoPorcentaje);
            tv.setText(String.format(Locale.getDefault(), "%.2f", precio));
            tv.setTextColor(context.getResources().getColor(R.color.text_green));
        // si el descuento es 0 entonces se deja el valor obtenido de la API y se pone con el color por defecto
        } else {
            tv.setText(String.valueOf(gasolinera.getGasoleoA()));
            tv.setTextColor(context.getResources().getColor(R.color.text_default));
        }
    }

    /**
     * Calcula el precio con el descuento asignado a la marca
     * @param precio precio obtenido de la API
     * @param descuento porcentaje de descuento a aplicar
     * @return el precio actualizado con el descuento
     */
    public double calcularPrecioConDescuento(double precio, double descuento){
        double precioNuevo;
        precioNuevo = precio - ((precio * descuento) / 100);
        return precioNuevo;
    }
}

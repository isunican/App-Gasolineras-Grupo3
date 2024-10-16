package es.unican.gasolineras.activities.ConsultarRepostaje;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Repostaje;

public class RepostajesArrayAdapter extends BaseAdapter {

    private final List<Repostaje> repostajes;
    private final Context context;

    /**
     * Contruye un adapater para mostrar la lista de repostajes
     * @param repostajes
     * @param context
     */
    public RepostajesArrayAdapter(@NonNull Context context, @NonNull List<Repostaje> repostajes) {
        this.repostajes = repostajes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return repostajes.size();
    }

    @Override
    public Object getItem(int position) {
        return repostajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"DiscouragedApi", "DefaultLocale"})
    // to remove warnings about using getIdentifier
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Repostaje repostaje = (Repostaje) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_consultar_list_item, parent, false);
        }

        //fecha
        {
            TextView tv = convertView.findViewById(R.id.tvFecha);
            tv.setText(repostaje.getFechaAnhadido());
        }

        //precio por litro
        {
            TextView tvLabel = convertView.findViewById(R.id.tvPrecioLitroLabel);
            String label = "Precio por litro";
            tvLabel.setText(String.format("%s:", label));

            TextView tv = convertView.findViewById(R.id.tvPreciolitro);
            tv.setText(String.valueOf(repostaje.getLitros()));
        }

        //litros
        {
            TextView tvLabel = convertView.findViewById(R.id.tvLitrosLabel);
            String label = "Litros";
            tvLabel.setText(String.format("%s:", label));

            TextView tv = convertView.findViewById(R.id.tvLitros);
            tv.setText(String.valueOf(repostaje.getLitros()));
        }

        //precio total
        {
            TextView tvLabel = convertView.findViewById(R.id.tvPrecioTotalLabel);
            String label = "Precio total";
            tvLabel.setText(String.format("%s:", label));

            TextView tv = convertView.findViewById(R.id.tvPrecioTotal);
            tv.setText(String.valueOf(repostaje.getPrecioTotal()));
        }

        return convertView;
    }
}

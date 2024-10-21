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
                    .inflate(R.layout.activity_consulta_repostaje_list_item_view, parent, false);
        }


        setFecha(convertView, repostaje);
        setPrecioPorLitro(convertView, repostaje);
        setLitros(convertView, repostaje);
        setPrecioTotal(convertView, repostaje);


        return convertView;
    }


    private void setFecha(View convertView, Repostaje repostaje) {
        TextView tv = convertView.findViewById(R.id.tvFecha);
        tv.setText(repostaje.getFechaRepostaje());
    }

    private void setPrecioPorLitro(View convertView, Repostaje repostaje) {
        TextView tv = convertView.findViewById(R.id.tvPrecioPorLitroNum);
        tv.setText(String.valueOf(repostaje.getPrecioTotal() / repostaje.getLitros()));
    }

    private void setLitros(View convertView, Repostaje repostaje) {
        TextView tv = convertView.findViewById(R.id.tvLitrosNum);
        tv.setText(String.valueOf(repostaje.getLitros()));
    }

    private void setPrecioTotal(View convertView, Repostaje repostaje) {
        TextView tv = convertView.findViewById(R.id.tvPrecioTotalNum);
        tv.setText(String.valueOf(repostaje.getPrecioTotal()));
    }
}

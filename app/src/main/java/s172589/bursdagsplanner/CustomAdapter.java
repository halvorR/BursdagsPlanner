package s172589.bursdagsplanner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Roger on 18.10.2015.
 */
public class CustomAdapter extends ArrayAdapter<Kontakt> {
    int r;
    public CustomAdapter(Context context, List<Kontakt> kontaktliste, int resource) {
        super(context, 0, kontaktliste);
        r = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Kontakt k = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(r, parent, false);
        }
        TextView navneFelt = (TextView) convertView.findViewById(R.id.navneFelt);
        TextView telefonFelt = (TextView) convertView.findViewById(R.id.telefonFelt);
        TextView datoFelt = (TextView) convertView.findViewById(R.id.datoFelt);

        telefonFelt.setText(Integer.toString(k.getTlf()));
        navneFelt.setText(k.getNavn());
        datoFelt.setText(k.getDato());

        return convertView;
    }
}

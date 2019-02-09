package pl.mylittleworld.contraction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ContractionListAdapter extends ArrayAdapter<Contraction> {

    public ContractionListAdapter(Context context, List<Contraction> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contraction contraction = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contraction_row, parent, false);
        }
        TextView start = convertView.findViewById(R.id.start);
        TextView stop = convertView.findViewById(R.id.stop);
        TextView duration = convertView.findViewById(R.id.duration);
        TextView date = convertView.findViewById(R.id.date);
        TextView timeBetween = convertView.findViewById(R.id.time_between);


        start.setText(contraction.getStart().toString());
        stop.setText(contraction.getStop().toString());
        duration.setText(contraction.getDuration().toString());
        date.setText(contraction.getDate().toString());


        return super.getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}

package pl.mylittleworld.contraction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import pl.mylittleworld.contraction.database.Contraction;

class ContractionListAdapter extends ArrayAdapter<Contraction> {

    private final Control control;
    private static final DateTimeFormatter contractionTimeFormat = DateTimeFormatter.ofPattern("kk: mm : ss");
    private static final DateTimeFormatter dataTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public ContractionListAdapter(@NonNull Context context, @NonNull Control control, List<Contraction> objects) {
        super(context, 0, objects);
        this.control = control;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Contraction contraction = getItem(position);

        if (contraction != null) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.contraction_row, parent, false);
            }
            TextView start = convertView.findViewById(R.id.start);
            TextView stop = convertView.findViewById(R.id.stop);
            TextView duration = convertView.findViewById(R.id.duration);
            TextView date = convertView.findViewById(R.id.date);
            TextView timeBetween = convertView.findViewById(R.id.time_between);


            start.setText(contraction.getStart().format(contractionTimeFormat));
            stop.setText(contraction.getStop().format(contractionTimeFormat));

            String durationAsText = getDurationString(R.string.duration, contraction.getDuration().getSeconds() / 60, contraction.getDuration().getSeconds() % 60);
            duration.setText(durationAsText);
            date.setText(dataTimeFormat.format(contraction.getDate()));

            if (getCount() > position + 1) {
                LocalTime start1 = contraction.getStart();
                LocalTime start2 = (getItem(position + 1)).getStart();
                Duration between = Duration.between(start1, start2);

                timeBetween.setText(getDurationString(R.string.between, between.getSeconds() / 60, between.getSeconds() % 60));
            }

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    control.userWantsToDeleteThisItem(contraction);
                    return true;
                }
            });


            return convertView;
        }
        return super.getView(position, convertView, parent);
    }

    private String getDurationString(int id, long minutes, long seconds) {
        return getContext().getString(id, minutes, seconds);

    }


}

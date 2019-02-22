package pl.mylittleworld.contraction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


import pl.mylittleworld.contraction.database.Contraction;

class ContractionListAdapter extends ArrayAdapter<Contraction> {

    @NonNull
    private final Control control;
    private static final DateTimeFormatter contractionTimeFormat = DateTimeFormatter.ofPattern("kk: mm : ss");
    private static final DateTimeFormatter dataTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    ContractionListAdapter(@NonNull Context context, @NonNull Control control, List<Contraction> objects) {
        super(context, 0, objects);
        if (control != null) {
            this.control = control;
        } else {
            throw new IllegalArgumentException("Control cannot be null");
        }
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
            ImageView divider = convertView.findViewById(R.id.divider);


            start.setText(contraction.getStart().format(contractionTimeFormat));
            stop.setText(contraction.getStop().format(contractionTimeFormat));

            String durationAsText = getDurationString(R.string.duration, contraction.getDuration().getSeconds() / 60, contraction.getDuration().getSeconds() % 60);
            duration.setText(durationAsText);
            date.setText(dataTimeFormat.format(contraction.getDate()));

            if (getCount() > position + 1) {
                Contraction nextContraction = getItem(position + 1);

                if (nextContraction != null) {

                    LocalTime start1 = contraction.getStop();
                    LocalTime start2 = nextContraction.getStart();
                    Duration between;
                    if (contraction.getDate().equals(nextContraction.getDate())) {
                        between = Duration.between(start1, start2);
                        timeBetween.setText(getDurationString(R.string.between, between.getSeconds() / 60, between.getSeconds() % 60));
                        divider.setVisibility(View.GONE);
                    } else if (isNextDate(contraction.getDate(), nextContraction.getDate())) {
                        between = Duration.between(start1, LocalTime.MAX);
                        between.plus(Duration.between(LocalTime.MIDNIGHT, start2));
                        timeBetween.setText(getDurationString(R.string.between, between.getSeconds() / 60, between.getSeconds() % 60));
                        divider.setVisibility(View.VISIBLE);
                    } else {
                        divider.setVisibility(View.VISIBLE);
                    }

                }
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

    static boolean isNextDate(LocalDate date1, LocalDate date2) {
        if (date1.getDayOfYear() + 1 == date2.getDayOfYear()) {
            return true;
            //December and January
        } else if (date1.getMonthValue() == 12 && date2.getMonthValue() == 1) {
            //31th and 1st
            if (date1.getDayOfMonth() == 31 && date2.getDayOfMonth() == 1) {
                //year1 +1 == year2
                if (date1.getYear() + 1 == date2.getYear()) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getDurationString(int id, long minutes, long seconds) {
        return getContext().getString(id, minutes, seconds);

    }


}

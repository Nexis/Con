package pl.mylittleworld.contraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import pl.mylittleworld.contraction.database.Contraction;
import pl.mylittleworld.contraction.database.DataBaseAccessor;

public class MainActivity extends AppCompatActivity implements DataAccessor.DataAccessListener {

    private ArrayList<Contraction> contractionsList;
    private Contraction currentContraction = null;
    private ArrayAdapter arrayAdapter;
    private DataAccessor dataAccessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataAccessor=new DataBaseAccessor(this);
        dataAccessor.getAllContractions(this);

        contractionsList = new ArrayList<>();

        arrayAdapter = new ContractionListAdapter(this, contractionsList);

        ((ListView) findViewById(R.id.contraction_list)).setAdapter(arrayAdapter);

        findViewById(R.id.contraction_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentContraction == null) {
                    currentContraction = new Contraction();
                    currentContraction.startContraction();
                } else {
                    currentContraction.stopContraction();
                    contractionsList.add(currentContraction);
                    dataAccessor.addContraction(currentContraction);
                    currentContraction = null;
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onActionDone(ArrayList<Contraction> contractions) {
        contractionsList.addAll(contractions);
        arrayAdapter.notifyDataSetChanged();
    }
}

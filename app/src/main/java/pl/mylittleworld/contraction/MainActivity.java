package pl.mylittleworld.contraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import pl.mylittleworld.contraction.database.Contraction;
import pl.mylittleworld.contraction.database.DataBaseAccessor;

public class MainActivity extends AppCompatActivity implements DataAccessor.DataAccessListener, Control{

    private ArrayList<Contraction> contractionsList;
    private Contraction currentContraction = null;
    private ArrayAdapter arrayAdapter;
    private DataAccessor dataAccessor;
    private Button restart;
    private Button contractionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataAccessor=new DataBaseAccessor(this);
        dataAccessor.getAllContractions(this);

        contractionsList = new ArrayList<>();

        arrayAdapter = new ContractionListAdapter(this,this, contractionsList);

        ((ListView) findViewById(R.id.contraction_list)).setAdapter(arrayAdapter);

        restart= findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentContraction=contractionsList.get(contractionsList.size()-1);
                contractionsList.remove(currentContraction);
                arrayAdapter.notifyDataSetChanged();
                restart.setEnabled(false);
                contractionButton.setText(R.string.stop);
            }
        });

        contractionButton=findViewById(R.id.contraction_button);
        contractionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentContraction == null) {
                    currentContraction = new Contraction();
                    currentContraction.startContraction();
                   contractionButton.setText(R.string.stop);
                    restart.setEnabled(false);


                } else {
                    currentContraction.stopContraction();
                    contractionsList.add(currentContraction);
                    dataAccessor.addContraction(currentContraction);
                    currentContraction = null;
                    arrayAdapter.notifyDataSetChanged();
                    contractionButton.setText(R.string.start);
                    restart.setEnabled(true);
                }
            }
        });


    }

    @Override
    public void onActionDone(ArrayList<Contraction> contractions) {
        contractionsList.addAll(contractions);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void userWantsToDeleteThisItem(Contraction contraction) {
        dataAccessor.deleteContraction(contraction);
        contractionsList.remove(contraction);
        arrayAdapter.notifyDataSetChanged();
    }
}

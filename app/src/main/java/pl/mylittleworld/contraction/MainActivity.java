package pl.mylittleworld.contraction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import pl.mylittleworld.contraction.database.Contraction;
import pl.mylittleworld.contraction.database.DataBaseAccessor;

public class MainActivity extends AppCompatActivity implements DataAccessor.DataAccessListener, Control {

    private ArrayList<Contraction> contractionsList;
    private Contraction currentContraction = null;
    private ArrayAdapter arrayAdapter;
    private DataAccessor dataAccessor;
    private ImageButton restart;
    private Button contractionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataAccessor = new DataBaseAccessor(this);
        dataAccessor.getAllContractions(this);

        contractionsList = new ArrayList<>();

        arrayAdapter = new ContractionListAdapter(this, this, contractionsList);

        ((ListView) findViewById(R.id.contraction_list)).setAdapter(arrayAdapter);

        restart = findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentContraction = contractionsList.get(contractionsList.size() - 1);
                contractionsList.remove(currentContraction);
                dataAccessor.deleteContraction(currentContraction);
                arrayAdapter.notifyDataSetChanged();
                restart.setEnabled(false);
                restart.setBackgroundColor(getColor(R.color.notEnable));
                contractionButton.setText(R.string.stop);
            }
        });

        contractionButton = findViewById(R.id.contraction_button);
        contractionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentContraction == null) {
                    currentContraction = new Contraction();
                    currentContraction.startContraction();
                    contractionButton.setText(R.string.stop);
                    restart.setEnabled(false);
                    restart.setBackgroundColor(getColor(R.color.notEnable));


                } else {
                    currentContraction.stopContraction();
                    contractionsList.add(currentContraction);
                    dataAccessor.addContraction(currentContraction);
                    currentContraction = null;
                    arrayAdapter.notifyDataSetChanged();
                    contractionButton.setText(R.string.start);
                    restart.setEnabled(true);
                    restart.setBackgroundColor(getColor(R.color.colorPrimary));
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.hospital_info:
                Intent intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                return true;
            case R.id.app_info:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActionDone(ArrayList<Contraction> contractions) {
        if (isFinishing() || isDestroyed()) return;
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

package pl.mylittleworld.contraction.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.mylittleworld.contraction.DataAccessor;

public class DataBaseAccessor implements DataAccessor {

    public static DataBase dataBase;

    public DataBaseAccessor(@NonNull Context context) {
        dataBase = Room.databaseBuilder(context, DataBase.class, "ContractionDatabase").build();
    }


    @Override
    public void addContraction(Contraction contraction) {
        new AddTask().execute(contraction);
    }

    @Override
    public void deleteContraction(Contraction contraction) {
        new DeleteTask().execute(contraction);
    }

    @Override
    public void updateContraction(Contraction contraction) {
        new UpdateTask().execute(contraction);
    }

    @Override
    public void getAllContractions(DataAccessListener dataAccessListener) {
        new GetAllContractionsTask().execute(dataAccessListener);
    }

    private static class AddTask extends AsyncTask<Contraction, Void, Void> {

        @Override
        protected Void doInBackground(Contraction... contraction) {
            dataBase.getDao().insert(contraction);
            return null;
        }

    }

    private static class UpdateTask extends AsyncTask<Contraction, Void, Void> {

        @Override
        protected Void doInBackground(Contraction... contraction) {
            dataBase.getDao().update(contraction);
            return null;
        }

    }

    private static class DeleteTask extends AsyncTask<Contraction, Void, Void> {

        @Override
        protected Void doInBackground(Contraction... contraction) {
            dataBase.getDao().delete(contraction);
            return null;
        }

    }

    private static class GetAllContractionsTask extends AsyncTask<DataAccessListener, Void, ArrayList<Contraction>> {

        private List<DataAccessListener> dataAccessListeners;

        @Override
        protected ArrayList<Contraction> doInBackground(DataAccessListener... listeners) {
            dataAccessListeners=new ArrayList<>();
            Collections.addAll(dataAccessListeners,listeners);
           return new ArrayList<>(dataBase.getDao().getAllContractions());
        }

        @Override
        protected void onPostExecute(ArrayList<Contraction> contractions) {
            super.onPostExecute(contractions);
            for(DataAccessListener listener: dataAccessListeners){
                listener.onActionDone(contractions);
            }

        }
    }
}

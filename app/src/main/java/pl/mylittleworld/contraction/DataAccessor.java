package pl.mylittleworld.contraction;

import java.util.ArrayList;

import pl.mylittleworld.contraction.database.Contraction;

public interface DataAccessor {

    interface DataAccessListener{
        void onActionDone(ArrayList<Contraction> contractions);
    }
    void addContraction(Contraction contraction);
    void deleteContraction(Contraction contraction);
    void updateContraction(Contraction contraction);

    void getAllContractions(DataAccessListener dataAccessListener);


}

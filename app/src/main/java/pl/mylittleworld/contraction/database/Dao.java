package pl.mylittleworld.contraction.database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@android.arch.persistence.room.Dao
public interface Dao {

    @Insert
    void insert(Contraction ... contraction);

    @Delete
    void delete(Contraction ... contraction);

    @Update
    void update(Contraction ... contraction);

    @Query("SELECT * FROM Contraction")
    List<Contraction> getAllContractions();
}

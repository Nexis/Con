package pl.mylittleworld.contraction.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {Contraction.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class DataBase extends RoomDatabase {
    public abstract Dao getDao();
}

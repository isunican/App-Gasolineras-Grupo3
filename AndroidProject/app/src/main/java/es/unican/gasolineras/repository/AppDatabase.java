package es.unican.gasolineras.repository;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unican.gasolineras.model.Repostaje;
import lombok.Getter;

@Database(entities = {Repostaje.class, }, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RepostajeDAO repostajeDao();
    public abstract DescuentoDAO descuentoDao();

}

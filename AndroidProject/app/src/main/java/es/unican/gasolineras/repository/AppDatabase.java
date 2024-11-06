package es.unican.gasolineras.repository;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unican.gasolineras.model.Repostaje;
import es.unican.gasolineras.model.Descuento;
import lombok.Getter;

@Database(entities = {Repostaje.class, Descuento.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RepostajeDAO repostajeDao();
    public abstract DescuentoDAO descuentoDao();

}

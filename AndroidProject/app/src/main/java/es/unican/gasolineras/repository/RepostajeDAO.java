package es.unican.gasolineras.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unican.gasolineras.model.Repostaje;

@Dao
public interface RepostajeDAO {
    @Query("SELECT * FROM repostaje")
    List<Repostaje> repostajes();

    @Query("SELECT * FROM repostaje where fecha_repostaje = getdate()")
    Repostaje repostajePorFecha(String fecha);

    @Insert
    void registrarRepostaje(Repostaje r);

    @Delete
    void eliminarRepostaje(Repostaje r);

}

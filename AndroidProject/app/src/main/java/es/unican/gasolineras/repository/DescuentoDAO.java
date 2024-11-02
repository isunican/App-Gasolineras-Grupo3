package es.unican.gasolineras.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unican.gasolineras.model.Descuento;


@Dao
public interface DescuentoDAO {

    @Query("SELECT * FROM Descuento")
    List<Descuento> descuentos();

    @Insert
    void registrarDescuento(Descuento d);

    @Delete
    void eliminarDescuento(Descuento d);

}

package es.unican.gasolineras.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unican.gasolineras.model.Descuento;


@Dao
public interface DescuentoDAO {

    @Query("SELECT * FROM Descuento")
    List<Descuento> descuentos();

    @Query("SELECT * FROM Descuento d WHERE d.marca = :marca")
    Descuento descuentoPorMarca(String marca);

    @Insert
    void registrarDescuento(Descuento d);

    @Delete
    void eliminarDescuento(Descuento d);

    @Update
    void actualizaDescuento(Descuento d);

    @Query("DELETE from Descuento")
    void eliminarDescuentos();

}

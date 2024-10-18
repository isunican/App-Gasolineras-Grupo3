package es.unican.gasolineras.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;


/**
 * Repostaje
 * Recoge las propiedades que se guardan de un repostaje
 */
@Getter
@Entity
public class Repostaje {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "fecha_repostaje")
    public String fechaRepostaje;

    @ColumnInfo(name = "precio_total")
    public double precioTotal;

    @ColumnInfo(name = "litros")
    public double litros;

}

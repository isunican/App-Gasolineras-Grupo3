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
    protected String fechaRepostaje;

    @ColumnInfo(name = "precio_total")
    protected double precioTotal;

    @ColumnInfo(name = "litros")
    protected double litros;

}
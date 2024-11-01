package es.unican.gasolineras.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

/**
 * Descuento
 * Recoge las propiedades que se guardan de un descuento
 */
@Getter
@Setter
@Entity
public class Descuento {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "marca")
    public String marca;

    @ColumnInfo(name = "descuento")
    public double descuento;
}

package es.unican.gasolineras.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

/**
 * A Gas Station.
 *
 * Properties are defined in the <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help/operations/PreciosEESSTerrestres#response-json">API</a>
 *
 * The #SerializedName annotation is a GSON annotation that defines the name of the property
 * as defined in the json response.
 *
 * Getters are automatically generated at compile time by Lombok.
 */
@Parcel
@Getter
@Setter
public class Gasolinera {

    @SerializedName("IDEESS")                       protected String id;


    @SerializedName("Rótulo")                       protected String rotulo;
    @SerializedName("C.P.")                         protected String cp;
    @SerializedName("Dirección")                    protected String direccion;
    @SerializedName("Municipio")                    protected String municipio;
    @SerializedName("Horario")                      protected String horario;

    @SerializedName("Precio Gasoleo A")             protected double gasoleoA;
    @SerializedName("Precio Gasolina 95 E5")        protected double gasolina95E5;


    public String getRotulo() {
        if (rotulo == null) {
            rotulo = "-";
        }
        return this.rotulo;
    }

    public String  getMunicipio() {
        if (municipio == null) {
            municipio = "-";
        }
        return this.municipio;
    }

    public String  getCp() {
        if (cp == null) {
            cp = "-";
        }
        return this.cp;
    }

    public String  getHorario() {
        if (horario == null) {
            horario = "-";
        }
        return this.horario;
    }

    public double getGasoleoA() {
        return Double.parseDouble(String.format("%.2f", gasoleoA));
    }

    public double getGasolina95E5() {
        return Double.parseDouble(String.format("%.2f",gasolina95E5));
    }
}
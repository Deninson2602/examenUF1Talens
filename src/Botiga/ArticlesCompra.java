package Botiga;

import java.io.Serializable;

public class ArticlesCompra implements Serializable {
    private String descripcion;
    private double cantidad;
    private String unidad;
    private String seccion;
    private double precio;

    // Constructor por defecto
    public ArticlesCompra() {}

    // Constructor con todos los atributos
    public ArticlesCompra(String descripcion, double cantidad, String unidad, String seccion, double precio) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.seccion = seccion;
        this.precio = precio;
    }

    // Getter y Setter para descripcion
    public String getDescripcion() {
        return this.descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Getter y Setter para cantidad
    public double getCantidad() {
        return this.cantidad;
    }
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    // Getter y Setter para unidad
    public String getUnidad() {
        return this.unidad;
    }
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    // Getter y Setter para seccion
    public String getSeccion() {
        return this.seccion;
    }
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    // Getter y Setter para seccion

    public double getPrecio() {return precio;}

    public void setPrecio(double precio) {this.precio = precio;}
}

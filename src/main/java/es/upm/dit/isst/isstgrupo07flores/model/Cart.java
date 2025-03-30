package es.upm.dit.isst.isstgrupo07flores.model;

import es.upm.dit.isst.isstgrupo07flores.model.Producto;

public class Cart {
    private Producto producto;

    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void clear() {
        this.producto = null;
    }
}
package es.upm.dit.isst.isstgrupo07flores.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Producto producto;
    private List<Flor> flores;
    private BigDecimal precioTotal;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Flor> getFlores() {
        return flores;
    }

    public void setFlores(List<Flor> flores) {
        this.flores = flores;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void addFlor(Flor flor) {
        if (this.flores == null) {
            this.flores = new ArrayList<>();
        }
        this.flores.add(flor);
    }

    public void clear() {
        this.producto = null;
        this.flores = null;
    }
}
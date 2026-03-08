package com.tienda.abarrotes.model;

public class Producto {

    private int idProducto;
    private String codigoProducto;
    private String codigoBarras;
    private String nombre;
    private String categoria;
    private String marca;
    private String unidadMedida;
    private double precioCompra;
    private double precioVenta;
    private int stockAlmacen;
    private int stockTienda;
    private int stockMinimo;
    private String estado;
    private String fechaRegistro;

    public Producto() {
    }

    public Producto(int idProducto, String codigoProducto, String codigoBarras, String nombre,
                    String categoria, String marca, String unidadMedida, double precioCompra,
                    double precioVenta, int stockAlmacen, int stockTienda, int stockMinimo,
                    String estado, String fechaRegistro) {
        this.idProducto = idProducto;
        this.codigoProducto = codigoProducto;
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.categoria = categoria;
        this.marca = marca;
        this.unidadMedida = unidadMedida;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stockAlmacen = stockAlmacen;
        this.stockTienda = stockTienda;
        this.stockMinimo = stockMinimo;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStockAlmacen() {
        return stockAlmacen;
    }

    public void setStockAlmacen(int stockAlmacen) {
        this.stockAlmacen = stockAlmacen;
    }

    public int getStockTienda() {
        return stockTienda;
    }

    public void setStockTienda(int stockTienda) {
        this.stockTienda = stockTienda;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
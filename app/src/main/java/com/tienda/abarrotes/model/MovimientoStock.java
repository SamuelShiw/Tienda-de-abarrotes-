package com.tienda.abarrotes.model;

public class MovimientoStock {

    private int idMovimiento;
    private int idProducto;
    private String tipoMovimiento;
    private int cantidad;
    private String origen;
    private String destino;
    private String fecha;
    private String hora;
    private int idTrabajador;
    private String observacion;

    public MovimientoStock() {
    }

    public MovimientoStock(int idMovimiento, int idProducto, String tipoMovimiento, int cantidad,
                           String origen, String destino, String fecha, String hora,
                           int idTrabajador, String observacion) {
        this.idMovimiento = idMovimiento;
        this.idProducto = idProducto;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.hora = hora;
        this.idTrabajador = idTrabajador;
        this.observacion = observacion;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
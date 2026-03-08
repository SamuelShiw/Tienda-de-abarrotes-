package com.tienda.abarrotes.model;

public class Venta {

    private int idVenta;
    private int idCaja;
    private int idCajero;
    private String tipoComprobante;
    private String numeroComprobante;
    private String clienteNombre;
    private String clienteDocumento;
    private double subtotal;
    private double igv;
    private double total;
    private String fecha;
    private String hora;

    public Venta() {
    }

    public Venta(int idVenta, int idCaja, int idCajero, String tipoComprobante,
                 String numeroComprobante, String clienteNombre, String clienteDocumento,
                 double subtotal, double igv, double total, String fecha, String hora) {
        this.idVenta = idVenta;
        this.idCaja = idCaja;
        this.idCajero = idCajero;
        this.tipoComprobante = tipoComprobante;
        this.numeroComprobante = numeroComprobante;
        this.clienteNombre = clienteNombre;
        this.clienteDocumento = clienteDocumento;
        this.subtotal = subtotal;
        this.igv = igv;
        this.total = total;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteDocumento() {
        return clienteDocumento;
    }

    public void setClienteDocumento(String clienteDocumento) {
        this.clienteDocumento = clienteDocumento;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
}
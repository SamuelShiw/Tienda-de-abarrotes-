package com.tienda.abarrotes.model;

public class Caja {

    private int idCaja;
    private int idCajero;
    private String fechaApertura;
    private String horaApertura;
    private double montoInicial;
    private String estado;
    private String fechaCierre;
    private String horaCierre;
    private double montoFinal;
    private double totalVentas;

    public Caja() {
    }

    public Caja(int idCaja, int idCajero, String fechaApertura, String horaApertura,
                double montoInicial, String estado, String fechaCierre, String horaCierre,
                double montoFinal, double totalVentas) {
        this.idCaja = idCaja;
        this.idCajero = idCajero;
        this.fechaApertura = fechaApertura;
        this.horaApertura = horaApertura;
        this.montoInicial = montoInicial;
        this.estado = estado;
        this.fechaCierre = fechaCierre;
        this.horaCierre = horaCierre;
        this.montoFinal = montoFinal;
        this.totalVentas = totalVentas;
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

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public double getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(double montoInicial) {
        this.montoInicial = montoInicial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public double getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(double montoFinal) {
        this.montoFinal = montoFinal;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }
}
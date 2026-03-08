package com.tienda.abarrotes.model;

public class Trabajador {

    private int idTrabajador;
    private String fotoUri;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String usuario;
    private String contrasena;
    private String rol;
    private String estado;
    private String qrDni;
    private String fechaRegistro;

    public Trabajador() {
    }

    public Trabajador(int idTrabajador, String fotoUri, String nombres, String apellidos,
                      String dni, String telefono, String usuario, String contrasena,
                      String rol, String estado, String qrDni, String fechaRegistro) {
        this.idTrabajador = idTrabajador;
        this.fotoUri = fotoUri;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estado = estado;
        this.qrDni = qrDni;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getFotoUri() {
        return fotoUri;
    }

    public void setFotoUri(String fotoUri) {
        this.fotoUri = fotoUri;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getQrDni() {
        return qrDni;
    }

    public void setQrDni(String qrDni) {
        this.qrDni = qrDni;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombreCompleto() {
        return (nombres == null ? "" : nombres) + " " + (apellidos == null ? "" : apellidos);
    }
}
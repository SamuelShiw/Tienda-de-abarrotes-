package com.tienda.abarrotes.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import com.tienda.abarrotes.data.repository.TrabajadorRepository;
import com.tienda.abarrotes.model.Trabajador;
import com.tienda.abarrotes.utils.AppConstants;
import com.tienda.abarrotes.utils.CodeGenerator;
import com.tienda.abarrotes.utils.DateTimeUtils;
import com.tienda.abarrotes.utils.SecurityUtils;

import java.util.List;

public class TrabajadorViewModel {

    private final TrabajadorRepository repository;

    public TrabajadorViewModel(Context context) {
        this.repository = new TrabajadorRepository(context);
    }

    public List<Trabajador> listarTrabajadores() {
        return repository.listarTrabajadores();
    }

    public Trabajador obtenerTrabajadorPorId(int idTrabajador) {
        return repository.obtenerTrabajadorPorId(idTrabajador);
    }

    public String validarDatos(String nombres, String apellidos, String dni, String telefono,
                               String usuario, String contrasena, String rol) {

        if (TextUtils.isEmpty(nombres)) return "Ingresa los nombres";
        if (TextUtils.isEmpty(apellidos)) return "Ingresa los apellidos";
        if (TextUtils.isEmpty(dni)) return "Ingresa el DNI";
        if (dni.trim().length() != 8) return "El DNI debe tener 8 dígitos";
        if (TextUtils.isEmpty(telefono)) return "Ingresa el teléfono";
        if (TextUtils.isEmpty(usuario)) return "Ingresa el usuario";
        if (TextUtils.isEmpty(contrasena)) return "Ingresa la contraseña";
        if (contrasena.trim().length() < 6) return "La contraseña debe tener al menos 6 caracteres";
        if (TextUtils.isEmpty(rol)) return "Selecciona un rol";

        return null;
    }

    public String registrarTrabajador(String fotoUri, String nombres, String apellidos, String dni,
                                      String telefono, String usuario, String contrasena, String rol) {

        if (repository.existeDni(dni.trim())) {
            return "Ya existe un trabajador con ese DNI";
        }

        if (repository.existeUsuario(usuario.trim())) {
            return "Ese usuario ya está en uso";
        }

        Trabajador trabajador = new Trabajador();
        trabajador.setFotoUri(fotoUri == null ? "" : fotoUri.trim());
        trabajador.setNombres(nombres.trim());
        trabajador.setApellidos(apellidos.trim());
        trabajador.setDni(dni.trim());
        trabajador.setTelefono(telefono.trim());
        trabajador.setUsuario(usuario.trim());
        trabajador.setContrasena(SecurityUtils.hashPassword(contrasena.trim()));
        trabajador.setRol(rol.trim());
        trabajador.setEstado(AppConstants.ESTADO_ACTIVO);
        trabajador.setFechaRegistro(DateTimeUtils.getCurrentDate());

        String qrContent = CodeGenerator.generateWorkerQrContent(trabajador);
        trabajador.setQrDni(qrContent);

        long resultado = repository.registrarTrabajador(trabajador);
        return resultado > 0 ? null : "No se pudo registrar el trabajador";
    }

    public String actualizarTrabajador(int idTrabajador, String fotoUri, String nombres, String apellidos,
                                       String dni, String telefono, String usuario, String contrasena,
                                       String rol, String estado) {

        Trabajador actual = repository.obtenerTrabajadorPorId(idTrabajador);
        if (actual == null) {
            return "No se encontró el trabajador";
        }

        Trabajador otroPorUsuario = repository.obtenerTrabajadorPorUsuario(usuario.trim());
        if (otroPorUsuario != null && otroPorUsuario.getIdTrabajador() != idTrabajador) {
            return "Ese usuario ya pertenece a otro trabajador";
        }

        if (!actual.getDni().equals(dni.trim()) && repository.existeDni(dni.trim())) {
            return "Ya existe otro trabajador con ese DNI";
        }

        actual.setFotoUri(fotoUri == null ? "" : fotoUri.trim());
        actual.setNombres(nombres.trim());
        actual.setApellidos(apellidos.trim());
        actual.setDni(dni.trim());
        actual.setTelefono(telefono.trim());
        actual.setUsuario(usuario.trim());
        actual.setRol(rol.trim());
        actual.setEstado(estado.trim());

        if (!TextUtils.isEmpty(contrasena)) {
            actual.setContrasena(SecurityUtils.hashPassword(contrasena.trim()));
        }

        actual.setQrDni(CodeGenerator.generateWorkerQrContent(actual));

        boolean actualizado = repository.actualizarTrabajador(actual);
        return actualizado ? null : "No se pudo actualizar el trabajador";
    }

    public boolean desactivarTrabajador(int idTrabajador) {
        return repository.cambiarEstadoTrabajador(idTrabajador, AppConstants.ESTADO_INACTIVO);
    }
}
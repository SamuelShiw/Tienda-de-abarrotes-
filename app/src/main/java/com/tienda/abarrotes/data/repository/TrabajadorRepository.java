package com.tienda.abarrotes.data.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.TrabajadorDao;
import com.tienda.abarrotes.model.Trabajador;
import com.tienda.abarrotes.utils.AppConstants;
import com.tienda.abarrotes.utils.SecurityUtils;

import java.util.List;

public class TrabajadorRepository {

    private final TrabajadorDao trabajadorDao;

    public TrabajadorRepository(Context context) {
        this.trabajadorDao = new TrabajadorDao(context);
    }

    public void asegurarUsuariosBase() {
        trabajadorDao.insertarUsuariosBaseSiNoExisten();
    }

    public Trabajador login(String usuario, String contrasena) {
        Trabajador trabajador = trabajadorDao.obtenerPorUsuario(usuario);

        if (trabajador == null) {
            return null;
        }

        if (!AppConstants.ESTADO_ACTIVO.equalsIgnoreCase(trabajador.getEstado())) {
            return null;
        }

        boolean passwordValido = SecurityUtils.verifyPassword(contrasena, trabajador.getContrasena());
        return passwordValido ? trabajador : null;
    }

    public long registrarTrabajador(Trabajador trabajador) {
        return trabajadorDao.insertar(trabajador);
    }

    public boolean actualizarTrabajador(Trabajador trabajador) {
        return trabajadorDao.actualizar(trabajador);
    }

    public boolean cambiarEstadoTrabajador(int idTrabajador, String estado) {
        return trabajadorDao.cambiarEstado(idTrabajador, estado);
    }

    public Trabajador obtenerTrabajadorPorId(int idTrabajador) {
        return trabajadorDao.obtenerPorId(idTrabajador);
    }

    public Trabajador obtenerTrabajadorPorUsuario(String usuario) {
        return trabajadorDao.obtenerPorUsuario(usuario);
    }

    public List<Trabajador> listarTrabajadores() {
        return trabajadorDao.listarTodos();
    }

    public boolean existeDni(String dni) {
        return trabajadorDao.existeDni(dni);
    }

    public boolean existeUsuario(String usuario) {
        return trabajadorDao.existeUsuario(usuario);
    }
}
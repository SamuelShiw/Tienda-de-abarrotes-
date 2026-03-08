package com.tienda.abarrotes.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import com.tienda.abarrotes.data.repository.TrabajadorRepository;
import com.tienda.abarrotes.model.Trabajador;

public class LoginViewModel {

    private final TrabajadorRepository trabajadorRepository;

    public LoginViewModel(Context context) {
        this.trabajadorRepository = new TrabajadorRepository(context);
    }

    public void prepararDatosIniciales() {
        trabajadorRepository.asegurarUsuariosBase();
    }

    public String validarCampos(String usuario, String contrasena) {
        if (TextUtils.isEmpty(usuario) && TextUtils.isEmpty(contrasena)) {
            return "Ingresa usuario y contraseña";
        }

        if (TextUtils.isEmpty(usuario)) {
            return "Ingresa el usuario";
        }

        if (TextUtils.isEmpty(contrasena)) {
            return "Ingresa la contraseña";
        }

        return null;
    }

    public Trabajador autenticar(String usuario, String contrasena) {
        return trabajadorRepository.login(usuario.trim(), contrasena.trim());
    }
}
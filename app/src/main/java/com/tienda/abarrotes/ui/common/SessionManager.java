package com.tienda.abarrotes.ui.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.tienda.abarrotes.model.Trabajador;
import com.tienda.abarrotes.utils.AppConstants;

public class SessionManager {

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences(AppConstants.PREF_SESSION, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void guardarSesion(Trabajador trabajador) {
        editor.putBoolean(AppConstants.KEY_SESSION_LOGGED, true);
        editor.putInt(AppConstants.KEY_SESSION_ID_TRABAJADOR, trabajador.getIdTrabajador());
        editor.putString(AppConstants.KEY_SESSION_USUARIO, trabajador.getUsuario());
        editor.putString(AppConstants.KEY_SESSION_ROL, trabajador.getRol());
        editor.apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(AppConstants.KEY_SESSION_LOGGED, false);
    }

    public int getIdTrabajador() {
        return preferences.getInt(AppConstants.KEY_SESSION_ID_TRABAJADOR, -1);
    }

    public String getUsuario() {
        return preferences.getString(AppConstants.KEY_SESSION_USUARIO, "");
    }

    public String getRol() {
        return preferences.getString(AppConstants.KEY_SESSION_ROL, "");
    }

    public void cerrarSesion() {
        editor.clear();
        editor.apply();
    }
}
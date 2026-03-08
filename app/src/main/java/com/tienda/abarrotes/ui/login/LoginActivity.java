package com.tienda.abarrotes.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Trabajador;
import com.tienda.abarrotes.ui.admin.AdminDashboardActivity;
import com.tienda.abarrotes.ui.cajero.CajeroDashboardActivity;
import com.tienda.abarrotes.ui.common.SessionManager;
import com.tienda.abarrotes.ui.reponedor.ReponedorDashboardActivity;
import com.tienda.abarrotes.utils.AppConstants;
import com.tienda.abarrotes.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContrasena;
    private Button btnLogin;
    private TextView tvCredencialesDemo;

    private LoginViewModel loginViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new LoginViewModel(this);
        sessionManager = new SessionManager(this);

        initViews();
        loginViewModel.prepararDatosIniciales();
        validarSesionExistente();
        configurarEventos();
    }

    private void initViews() {
        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnLogin = findViewById(R.id.btnLogin);
        tvCredencialesDemo = findViewById(R.id.tvCredencialesDemo);

        tvCredencialesDemo.setText(
                "Demo:\n" +
                        "Admin: admin / admin123\n" +
                        "Cajero: cajero / cajero123\n" +
                        "Reponedor: reponedor / reponedor123"
        );
    }

    private void validarSesionExistente() {
        if (sessionManager.isLoggedIn()) {
            redirigirSegunRol(sessionManager.getRol());
        }
    }

    private void configurarEventos() {
        btnLogin.setOnClickListener(v -> iniciarSesion());
    }

    private void iniciarSesion() {
        String usuario = etUsuario.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();

        String error = loginViewModel.validarCampos(usuario, contrasena);
        if (error != null) {
            mostrarMensaje(error);
            return;
        }

        Trabajador trabajador = loginViewModel.autenticar(usuario, contrasena);
        if (trabajador == null) {
            mostrarMensaje("Credenciales inválidas o usuario inactivo");
            return;
        }

        sessionManager.guardarSesion(trabajador);
        mostrarMensaje("Bienvenido, " + trabajador.getNombres());
        redirigirSegunRol(trabajador.getRol());
    }

    private void redirigirSegunRol(String rol) {
        Intent intent;

        switch (rol) {
            case AppConstants.ROL_ADMINISTRADOR:
                intent = new Intent(this, AdminDashboardActivity.class);
                break;

            case AppConstants.ROL_REPONEDOR:
                intent = new Intent(this, ReponedorDashboardActivity.class);
                break;

            case AppConstants.ROL_CAJERO:
                intent = new Intent(this, CajeroDashboardActivity.class);
                break;

            default:
                mostrarMensaje("Rol no reconocido");
                return;
        }

        startActivity(intent);
        finish();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
package com.tienda.abarrotes.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.ui.admin.inventario.InventarioFragment;
import com.tienda.abarrotes.ui.admin.productos.ProductosFragment;
import com.tienda.abarrotes.ui.admin.trabajadores.TrabajadoresFragment;
import com.tienda.abarrotes.ui.common.SessionManager;
import com.tienda.abarrotes.ui.login.LoginActivity;
import com.tienda.abarrotes.utils.DateTimeUtils;

public class AdminDashboardActivity extends AppCompatActivity {

    private TextView tvUsuarioAdmin;
    private TextView tvFechaAdmin;
    private Button btnCerrarSesionAdmin;
    private Button btnModuloTrabajadores;
    private Button btnModuloProductos;
    private Button btnModuloInventario;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        sessionManager = new SessionManager(this);

        initViews();
        cargarDatosSesion();
        configurarEventos();

        if (savedInstanceState == null) {
            abrirModuloTrabajadores();
        }
    }

    private void initViews() {
        tvUsuarioAdmin = findViewById(R.id.tvUsuarioAdmin);
        tvFechaAdmin = findViewById(R.id.tvFechaAdmin);
        btnCerrarSesionAdmin = findViewById(R.id.btnCerrarSesionAdmin);
        btnModuloTrabajadores = findViewById(R.id.btnModuloTrabajadores);
        btnModuloProductos = findViewById(R.id.btnModuloProductos);
        btnModuloInventario = findViewById(R.id.btnModuloInventario);
    }

    private void cargarDatosSesion() {
        tvUsuarioAdmin.setText("Usuario: " + sessionManager.getUsuario());
        tvFechaAdmin.setText("Fecha: " + DateTimeUtils.getCurrentDate());
    }

    private void configurarEventos() {
        btnCerrarSesionAdmin.setOnClickListener(v -> cerrarSesion());
        btnModuloTrabajadores.setOnClickListener(v -> abrirModuloTrabajadores());
        btnModuloProductos.setOnClickListener(v -> abrirModuloProductos());
        btnModuloInventario.setOnClickListener(v -> abrirModuloInventario());
    }

    private void abrirModuloTrabajadores() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerAdmin, new TrabajadoresFragment())
                .commit();
    }

    private void abrirModuloProductos() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerAdmin, new ProductosFragment())
                .commit();
    }

    private void abrirModuloInventario() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerAdmin, new InventarioFragment())
                .commit();
    }

    private void cerrarSesion() {
        sessionManager.cerrarSesion();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
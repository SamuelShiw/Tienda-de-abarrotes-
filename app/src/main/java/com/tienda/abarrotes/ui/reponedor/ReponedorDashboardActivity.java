package com.tienda.abarrotes.ui.reponedor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.ui.common.SessionManager;
import com.tienda.abarrotes.ui.login.LoginActivity;
import com.tienda.abarrotes.utils.DateTimeUtils;

public class ReponedorDashboardActivity extends AppCompatActivity {

    private TextView tvUsuarioReponedor;
    private TextView tvFechaReponedor;
    private Button btnCerrarSesionReponedor;
    private Button btnModuloListaProductos;
    private Button btnModuloProductosBajos;
    private Button btnModuloHistorialReposicion;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reponedor_dashboard);

        sessionManager = new SessionManager(this);

        initViews();
        cargarDatosSesion();
        configurarEventos();

        if (savedInstanceState == null) {
            abrirModuloListaProductos();
        }
    }

    private void initViews() {
        tvUsuarioReponedor = findViewById(R.id.tvUsuarioReponedor);
        tvFechaReponedor = findViewById(R.id.tvFechaReponedor);
        btnCerrarSesionReponedor = findViewById(R.id.btnCerrarSesionReponedor);
        btnModuloListaProductos = findViewById(R.id.btnModuloListaProductos);
        btnModuloProductosBajos = findViewById(R.id.btnModuloProductosBajos);
        btnModuloHistorialReposicion = findViewById(R.id.btnModuloHistorialReposicion);
    }

    private void cargarDatosSesion() {
        tvUsuarioReponedor.setText("Usuario: " + sessionManager.getUsuario());
        tvFechaReponedor.setText("Fecha: " + DateTimeUtils.getCurrentDate());
    }

    private void configurarEventos() {
        btnCerrarSesionReponedor.setOnClickListener(v -> cerrarSesion());
        btnModuloListaProductos.setOnClickListener(v -> abrirModuloListaProductos());
        btnModuloProductosBajos.setOnClickListener(v -> abrirModuloProductosBajos());
        btnModuloHistorialReposicion.setOnClickListener(v -> abrirHistorialReposicion());
    }

    private void abrirModuloListaProductos() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerReponedor, new ListaProductosFragment())
                .commit();
    }

    private void abrirModuloProductosBajos() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerReponedor, new ProductosBajosFragment())
                .commit();
    }

    private void abrirHistorialReposicion() {
        Intent intent = new Intent(this, HistorialReposicionActivity.class);
        startActivity(intent);
    }

    private void cerrarSesion() {
        sessionManager.cerrarSesion();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
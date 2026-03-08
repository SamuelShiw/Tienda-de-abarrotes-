package com.tienda.abarrotes.ui.cajero;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Caja;
import com.tienda.abarrotes.ui.common.SessionManager;
import com.tienda.abarrotes.ui.login.LoginActivity;
import com.tienda.abarrotes.utils.DateTimeUtils;
import com.tienda.abarrotes.viewmodel.CajaViewModel;

public class CajeroDashboardActivity extends AppCompatActivity {

    private TextView tvUsuarioCajero;
    private TextView tvFechaCajero;
    private TextView tvEstadoCajaCajero;
    private Button btnCerrarSesionCajero;
    private Button btnAbrirCaja;
    private Button btnIrPOS;
    private Button btnCerrarCaja;
    private Button btnResumenCaja;

    private SessionManager sessionManager;
    private CajaViewModel cajaViewModel;

    private final ActivityResultLauncher<Intent> launcherCaja =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> actualizarEstadoCaja()
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cajero_dashboard);

        sessionManager = new SessionManager(this);
        cajaViewModel = new CajaViewModel(this);

        initViews();
        cargarDatosSesion();
        configurarEventos();
        actualizarEstadoCaja();
    }

    private void initViews() {
        tvUsuarioCajero = findViewById(R.id.tvUsuarioCajero);
        tvFechaCajero = findViewById(R.id.tvFechaCajero);
        tvEstadoCajaCajero = findViewById(R.id.tvEstadoCajaCajero);
        btnCerrarSesionCajero = findViewById(R.id.btnCerrarSesionCajero);
        btnAbrirCaja = findViewById(R.id.btnAbrirCaja);
        btnIrPOS = findViewById(R.id.btnIrPOS);
        btnCerrarCaja = findViewById(R.id.btnCerrarCaja);
        btnResumenCaja = findViewById(R.id.btnResumenCaja);
    }

    private void cargarDatosSesion() {
        tvUsuarioCajero.setText("Usuario: " + sessionManager.getUsuario());
        tvFechaCajero.setText("Fecha: " + DateTimeUtils.getCurrentDate());
    }

    private void configurarEventos() {
        btnCerrarSesionCajero.setOnClickListener(v -> cerrarSesion());

        btnAbrirCaja.setOnClickListener(v -> {
            Intent intent = new Intent(this, AbrirCajaActivity.class);
            launcherCaja.launch(intent);
        });

        btnIrPOS.setOnClickListener(v -> {
            Intent intent = new Intent(this, POSVentaActivity.class);
            launcherCaja.launch(intent);
        });

        btnCerrarCaja.setOnClickListener(v -> {
            Intent intent = new Intent(this, CerrarCajaActivity.class);
            launcherCaja.launch(intent);
        });

        btnResumenCaja.setOnClickListener(v -> {
            Intent intent = new Intent(this, ResumenVentasActivity.class);
            startActivity(intent);
        });
    }

    private void actualizarEstadoCaja() {
        Caja cajaAbierta = cajaViewModel.obtenerCajaAbiertaPorCajero(sessionManager.getIdTrabajador());

        if (cajaAbierta == null) {
            tvEstadoCajaCajero.setText("Caja actual: CERRADA");
        } else {
            tvEstadoCajaCajero.setText(
                    "Caja actual: ABIERTA | ID " + cajaAbierta.getIdCaja() +
                            " | Inicial S/ " + cajaAbierta.getMontoInicial()
            );
        }
    }

    private void cerrarSesion() {
        sessionManager.cerrarSesion();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
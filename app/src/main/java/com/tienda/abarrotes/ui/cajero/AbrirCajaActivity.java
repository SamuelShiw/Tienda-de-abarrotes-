package com.tienda.abarrotes.ui.cajero;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.ui.common.SessionManager;
import com.tienda.abarrotes.viewmodel.CajaViewModel;

public class AbrirCajaActivity extends AppCompatActivity {

    private EditText etMontoInicialCaja;
    private Button btnGuardarAperturaCaja;
    private Button btnCancelarAperturaCaja;

    private CajaViewModel cajaViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_caja);

        cajaViewModel = new CajaViewModel(this);
        sessionManager = new SessionManager(this);

        etMontoInicialCaja = findViewById(R.id.etMontoInicialCaja);
        btnGuardarAperturaCaja = findViewById(R.id.btnGuardarAperturaCaja);
        btnCancelarAperturaCaja = findViewById(R.id.btnCancelarAperturaCaja);

        btnGuardarAperturaCaja.setOnClickListener(v -> abrirCaja());
        btnCancelarAperturaCaja.setOnClickListener(v -> finish());
    }

    private void abrirCaja() {
        String monto = etMontoInicialCaja.getText().toString().trim();

        String errorValidacion = cajaViewModel.validarMonto(monto);
        if (errorValidacion != null) {
            mostrarMensaje(errorValidacion);
            return;
        }

        String error = cajaViewModel.abrirCaja(sessionManager.getIdTrabajador(), monto);
        if (error != null) {
            mostrarMensaje(error);
            return;
        }

        mostrarMensaje("Caja abierta correctamente");
        setResult(RESULT_OK);
        finish();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
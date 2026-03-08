package com.tienda.abarrotes.ui.cajero;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Caja;
import com.tienda.abarrotes.ui.common.SessionManager;
import com.tienda.abarrotes.viewmodel.CajaViewModel;

public class CerrarCajaActivity extends AppCompatActivity {

    private TextView tvInfoCajaAbierta;
    private EditText etMontoFinalCaja;
    private Button btnGuardarCierreCaja;
    private Button btnCancelarCierreCaja;

    private CajaViewModel cajaViewModel;
    private SessionManager sessionManager;
    private Caja cajaAbierta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrar_caja);

        cajaViewModel = new CajaViewModel(this);
        sessionManager = new SessionManager(this);

        initViews();
        cargarCajaAbierta();
        configurarEventos();
    }

    private void initViews() {
        tvInfoCajaAbierta = findViewById(R.id.tvInfoCajaAbierta);
        etMontoFinalCaja = findViewById(R.id.etMontoFinalCaja);
        btnGuardarCierreCaja = findViewById(R.id.btnGuardarCierreCaja);
        btnCancelarCierreCaja = findViewById(R.id.btnCancelarCierreCaja);
    }

    private void cargarCajaAbierta() {
        cajaAbierta = cajaViewModel.obtenerCajaAbiertaPorCajero(sessionManager.getIdTrabajador());

        if (cajaAbierta == null) {
            tvInfoCajaAbierta.setText("No tienes una caja abierta");
            btnGuardarCierreCaja.setEnabled(false);
            return;
        }

        tvInfoCajaAbierta.setText(
                "Caja #" + cajaAbierta.getIdCaja() +
                        "\nApertura: " + cajaAbierta.getFechaApertura() + " " + cajaAbierta.getHoraApertura() +
                        "\nMonto inicial: S/ " + cajaAbierta.getMontoInicial() +
                        "\nTotal ventas: S/ " + cajaAbierta.getTotalVentas()
        );
    }

    private void configurarEventos() {
        btnGuardarCierreCaja.setOnClickListener(v -> cerrarCaja());
        btnCancelarCierreCaja.setOnClickListener(v -> finish());
    }

    private void cerrarCaja() {
        if (cajaAbierta == null) {
            mostrarMensaje("No hay caja abierta");
            return;
        }

        String montoFinal = etMontoFinalCaja.getText().toString().trim();

        String errorValidacion = cajaViewModel.validarMonto(montoFinal);
        if (errorValidacion != null) {
            mostrarMensaje(errorValidacion);
            return;
        }

        String error = cajaViewModel.cerrarCaja(sessionManager.getIdTrabajador(), montoFinal);
        if (error != null) {
            mostrarMensaje(error);
            return;
        }

        mostrarMensaje("Caja cerrada correctamente");
        setResult(RESULT_OK);
        finish();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
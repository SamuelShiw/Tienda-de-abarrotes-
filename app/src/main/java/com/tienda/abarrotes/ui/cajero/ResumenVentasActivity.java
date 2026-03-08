package com.tienda.abarrotes.ui.cajero;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Caja;
import com.tienda.abarrotes.ui.common.SessionManager;
import com.tienda.abarrotes.viewmodel.CajaViewModel;

public class ResumenVentasActivity extends AppCompatActivity {

    private TextView tvResumenCaja;
    private CajaViewModel cajaViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_ventas);

        cajaViewModel = new CajaViewModel(this);
        sessionManager = new SessionManager(this);

        tvResumenCaja = findViewById(R.id.tvResumenCaja);

        cargarResumen();
    }

    private void cargarResumen() {
        Caja caja = cajaViewModel.obtenerUltimaCajaPorCajero(sessionManager.getIdTrabajador());

        if (caja == null) {
            tvResumenCaja.setText("No hay información de caja disponible");
            return;
        }

        String texto = "Caja #" + caja.getIdCaja()
                + "\nEstado: " + caja.getEstado()
                + "\nFecha apertura: " + caja.getFechaApertura()
                + "\nHora apertura: " + caja.getHoraApertura()
                + "\nMonto inicial: S/ " + caja.getMontoInicial()
                + "\nFecha cierre: " + (caja.getFechaCierre() == null ? "-" : caja.getFechaCierre())
                + "\nHora cierre: " + (caja.getHoraCierre() == null ? "-" : caja.getHoraCierre())
                + "\nMonto final: S/ " + caja.getMontoFinal()
                + "\nTotal ventas: S/ " + caja.getTotalVentas();

        tvResumenCaja.setText(texto);
    }
}
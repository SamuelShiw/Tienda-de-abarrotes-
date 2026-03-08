package com.tienda.abarrotes.ui.common;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Trabajador;
import com.tienda.abarrotes.viewmodel.TrabajadorViewModel;

public class QRTrabajadorActivity extends AppCompatActivity {

    private ImageView ivQrTrabajador;
    private TextView tvInfoQrTrabajador;

    private TrabajadorViewModel trabajadorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_trabajador);

        trabajadorViewModel = new TrabajadorViewModel(this);

        ivQrTrabajador = findViewById(R.id.ivQrTrabajador);
        tvInfoQrTrabajador = findViewById(R.id.tvInfoQrTrabajador);

        cargarQR();
    }

    private void cargarQR() {
        int idTrabajador = getIntent().getIntExtra("id_trabajador", -1);

        if (idTrabajador == -1) {
            Toast.makeText(this, "No se recibió el trabajador", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Trabajador trabajador = trabajadorViewModel.obtenerTrabajadorPorId(idTrabajador);

        if (trabajador == null) {
            Toast.makeText(this, "Trabajador no encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String contenido = trabajador.getQrDni();
        Bitmap bitmap = QRGenerator.generateSimpleQR(contenido);

        ivQrTrabajador.setImageBitmap(bitmap);
        tvInfoQrTrabajador.setText(
                trabajador.getNombreCompleto().trim() +
                        "\nDNI: " + trabajador.getDni() +
                        "\nRol: " + trabajador.getRol()
        );
    }
}
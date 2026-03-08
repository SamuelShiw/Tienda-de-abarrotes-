package com.tienda.abarrotes.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.tienda.abarrotes.R;

public class ScannerActivity extends AppCompatActivity {

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                    finish();
                } else {
                    Intent data = new Intent();
                    data.putExtra("scanned_code", result.getContents());
                    setResult(RESULT_OK, data);
                    finish();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        iniciarEscaneo();
    }

    private void iniciarEscaneo() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Escanea el código del producto");
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        options.setDesiredBarcodeFormats(ScanOptions.CODE_128, ScanOptions.EAN_13, ScanOptions.EAN_8, ScanOptions.QR_CODE);
        barcodeLauncher.launch(options);
    }
}
package com.tienda.abarrotes.ui.admin.productos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.viewmodel.ProductoViewModel;

public class RegistrarProductoActivity extends AppCompatActivity {

    private EditText etNombreProducto;
    private EditText etCategoriaProducto;
    private EditText etMarcaProducto;
    private EditText etUnidadProducto;
    private EditText etPrecioCompraProducto;
    private EditText etPrecioVentaProducto;
    private EditText etStockMinimoProducto;
    private Button btnGuardarProducto;
    private Button btnCancelarProducto;

    private ProductoViewModel productoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto);

        productoViewModel = new ProductoViewModel(this);

        initViews();
        configurarEventos();
    }

    private void initViews() {
        etNombreProducto = findViewById(R.id.etNombreProducto);
        etCategoriaProducto = findViewById(R.id.etCategoriaProducto);
        etMarcaProducto = findViewById(R.id.etMarcaProducto);
        etUnidadProducto = findViewById(R.id.etUnidadProducto);
        etPrecioCompraProducto = findViewById(R.id.etPrecioCompraProducto);
        etPrecioVentaProducto = findViewById(R.id.etPrecioVentaProducto);
        etStockMinimoProducto = findViewById(R.id.etStockMinimoProducto);
        btnGuardarProducto = findViewById(R.id.btnGuardarProducto);
        btnCancelarProducto = findViewById(R.id.btnCancelarProducto);
    }

    private void configurarEventos() {
        btnGuardarProducto.setOnClickListener(v -> guardarProducto());
        btnCancelarProducto.setOnClickListener(v -> finish());
    }

    private void guardarProducto() {
        String nombre = etNombreProducto.getText().toString().trim();
        String categoria = etCategoriaProducto.getText().toString().trim();
        String marca = etMarcaProducto.getText().toString().trim();
        String unidad = etUnidadProducto.getText().toString().trim();
        String precioCompra = etPrecioCompraProducto.getText().toString().trim();
        String precioVenta = etPrecioVentaProducto.getText().toString().trim();
        String stockMinimo = etStockMinimoProducto.getText().toString().trim();

        String error = productoViewModel.validarDatos(
                nombre, categoria, unidad, precioCompra, precioVenta, stockMinimo
        );

        if (error != null) {
            mostrarMensaje(error);
            return;
        }

        String errorRegistro = productoViewModel.registrarProducto(
                nombre, categoria, marca, unidad, precioCompra, precioVenta, stockMinimo
        );

        if (errorRegistro != null) {
            mostrarMensaje(errorRegistro);
            return;
        }

        mostrarMensaje("Producto registrado correctamente");
        setResult(RESULT_OK);
        finish();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
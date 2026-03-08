package com.tienda.abarrotes.ui.admin.productos;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.utils.AppConstants;
import com.tienda.abarrotes.viewmodel.ProductoViewModel;

public class EditarProductoActivity extends AppCompatActivity {

    private EditText etNombreEditarProducto;
    private EditText etCategoriaEditarProducto;
    private EditText etMarcaEditarProducto;
    private EditText etUnidadEditarProducto;
    private EditText etPrecioCompraEditarProducto;
    private EditText etPrecioVentaEditarProducto;
    private EditText etStockMinimoEditarProducto;
    private Spinner spEstadoEditarProducto;
    private Button btnActualizarProducto;
    private Button btnCancelarEditarProducto;

    private ProductoViewModel productoViewModel;
    private Producto productoActual;
    private int idProducto = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        productoViewModel = new ProductoViewModel(this);

        initViews();
        setupSpinnerEstado();
        recibirDatos();
        configurarEventos();
    }

    private void initViews() {
        etNombreEditarProducto = findViewById(R.id.etNombreEditarProducto);
        etCategoriaEditarProducto = findViewById(R.id.etCategoriaEditarProducto);
        etMarcaEditarProducto = findViewById(R.id.etMarcaEditarProducto);
        etUnidadEditarProducto = findViewById(R.id.etUnidadEditarProducto);
        etPrecioCompraEditarProducto = findViewById(R.id.etPrecioCompraEditarProducto);
        etPrecioVentaEditarProducto = findViewById(R.id.etPrecioVentaEditarProducto);
        etStockMinimoEditarProducto = findViewById(R.id.etStockMinimoEditarProducto);
        spEstadoEditarProducto = findViewById(R.id.spEstadoEditarProducto);
        btnActualizarProducto = findViewById(R.id.btnActualizarProducto);
        btnCancelarEditarProducto = findViewById(R.id.btnCancelarEditarProducto);
    }

    private void setupSpinnerEstado() {
        String[] estados = {
                AppConstants.ESTADO_ACTIVO,
                AppConstants.ESTADO_INACTIVO
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                estados
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstadoEditarProducto.setAdapter(adapter);
    }

    private void recibirDatos() {
        idProducto = getIntent().getIntExtra("id_producto", -1);

        if (idProducto == -1) {
            mostrarMensaje("No se recibió el producto");
            finish();
            return;
        }

        productoActual = productoViewModel.obtenerProductoPorId(idProducto);

        if (productoActual == null) {
            mostrarMensaje("Producto no encontrado");
            finish();
            return;
        }

        etNombreEditarProducto.setText(productoActual.getNombre());
        etCategoriaEditarProducto.setText(productoActual.getCategoria());
        etMarcaEditarProducto.setText(productoActual.getMarca());
        etUnidadEditarProducto.setText(productoActual.getUnidadMedida());
        etPrecioCompraEditarProducto.setText(String.valueOf(productoActual.getPrecioCompra()));
        etPrecioVentaEditarProducto.setText(String.valueOf(productoActual.getPrecioVenta()));
        etStockMinimoEditarProducto.setText(String.valueOf(productoActual.getStockMinimo()));

        setSpinnerSelection(spEstadoEditarProducto, productoActual.getEstado());
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void configurarEventos() {
        btnActualizarProducto.setOnClickListener(v -> actualizarProducto());
        btnCancelarEditarProducto.setOnClickListener(v -> finish());
    }

    private void actualizarProducto() {
        String nombre = etNombreEditarProducto.getText().toString().trim();
        String categoria = etCategoriaEditarProducto.getText().toString().trim();
        String marca = etMarcaEditarProducto.getText().toString().trim();
        String unidad = etUnidadEditarProducto.getText().toString().trim();
        String precioCompra = etPrecioCompraEditarProducto.getText().toString().trim();
        String precioVenta = etPrecioVentaEditarProducto.getText().toString().trim();
        String stockMinimo = etStockMinimoEditarProducto.getText().toString().trim();
        String estado = spEstadoEditarProducto.getSelectedItem().toString();

        String error = productoViewModel.validarDatos(
                nombre, categoria, unidad, precioCompra, precioVenta, stockMinimo
        );

        if (error != null) {
            mostrarMensaje(error);
            return;
        }

        String errorActualizacion = productoViewModel.actualizarProducto(
                idProducto, nombre, categoria, marca, unidad,
                precioCompra, precioVenta, stockMinimo, estado
        );

        if (errorActualizacion != null) {
            mostrarMensaje(errorActualizacion);
            return;
        }

        mostrarMensaje("Producto actualizado");
        setResult(RESULT_OK);
        finish();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
package com.tienda.abarrotes.ui.reponedor;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.ui.common.SessionManager;
import com.tienda.abarrotes.viewmodel.InventarioViewModel;
import com.tienda.abarrotes.viewmodel.ProductoViewModel;

public class ReposicionActivity extends AppCompatActivity {

    private TextView tvNombreProductoReposicion;
    private TextView tvCodigoProductoReposicion;
    private TextView tvStockAlmacenReposicion;
    private TextView tvStockTiendaReposicion;
    private EditText etCantidadReposicion;
    private EditText etObservacionReposicion;
    private Button btnGuardarReposicion;
    private Button btnCancelarReposicion;

    private ProductoViewModel productoViewModel;
    private InventarioViewModel inventarioViewModel;
    private SessionManager sessionManager;

    private Producto productoActual;
    private int idProducto = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reposicion);

        productoViewModel = new ProductoViewModel(this);
        inventarioViewModel = new InventarioViewModel(this);
        sessionManager = new SessionManager(this);

        initViews();
        recibirDatos();
        configurarEventos();
    }

    private void initViews() {
        tvNombreProductoReposicion = findViewById(R.id.tvNombreProductoReposicion);
        tvCodigoProductoReposicion = findViewById(R.id.tvCodigoProductoReposicion);
        tvStockAlmacenReposicion = findViewById(R.id.tvStockAlmacenReposicion);
        tvStockTiendaReposicion = findViewById(R.id.tvStockTiendaReposicion);
        etCantidadReposicion = findViewById(R.id.etCantidadReposicion);
        etObservacionReposicion = findViewById(R.id.etObservacionReposicion);
        btnGuardarReposicion = findViewById(R.id.btnGuardarReposicion);
        btnCancelarReposicion = findViewById(R.id.btnCancelarReposicion);
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

        cargarDatosProducto();
    }

    private void cargarDatosProducto() {
        tvNombreProductoReposicion.setText(productoActual.getNombre());
        tvCodigoProductoReposicion.setText("Código: " + productoActual.getCodigoProducto());
        tvStockAlmacenReposicion.setText("Stock almacén: " + productoActual.getStockAlmacen());
        tvStockTiendaReposicion.setText("Stock tienda: " + productoActual.getStockTienda());
    }

    private void configurarEventos() {
        btnGuardarReposicion.setOnClickListener(v -> guardarReposicion());
        btnCancelarReposicion.setOnClickListener(v -> finish());
    }

    private void guardarReposicion() {
        String cantidad = etCantidadReposicion.getText().toString().trim();
        String observacion = etObservacionReposicion.getText().toString().trim();

        String errorValidacion = inventarioViewModel.validarIngresoMercaderia(cantidad);
        if (errorValidacion != null) {
            mostrarMensaje(errorValidacion);
            return;
        }

        String error = inventarioViewModel.reponerDeAlmacenATienda(
                idProducto,
                cantidad,
                sessionManager.getIdTrabajador(),
                observacion
        );

        if (error != null) {
            mostrarMensaje(error);
            return;
        }

        mostrarMensaje("Reposición realizada correctamente");
        setResult(RESULT_OK);
        finish();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
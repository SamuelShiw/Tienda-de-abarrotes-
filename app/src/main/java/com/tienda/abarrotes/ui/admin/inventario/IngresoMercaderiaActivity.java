package com.tienda.abarrotes.ui.admin.inventario;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.ui.common.SessionManager;
import com.tienda.abarrotes.viewmodel.InventarioViewModel;
import com.tienda.abarrotes.viewmodel.ProductoViewModel;

import java.util.ArrayList;
import java.util.List;

public class IngresoMercaderiaActivity extends AppCompatActivity {

    private Spinner spProductoIngreso;
    private EditText etCantidadIngreso;
    private EditText etObservacionIngreso;
    private Button btnGuardarIngreso;
    private Button btnCancelarIngreso;

    private ProductoViewModel productoViewModel;
    private InventarioViewModel inventarioViewModel;
    private SessionManager sessionManager;

    private final List<Producto> listaProductos = new ArrayList<>();
    private int idProductoRecibido = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_mercaderia);

        productoViewModel = new ProductoViewModel(this);
        inventarioViewModel = new InventarioViewModel(this);
        sessionManager = new SessionManager(this);

        initViews();
        recibirDatos();
        cargarProductosSpinner();
        configurarEventos();
    }

    private void initViews() {
        spProductoIngreso = findViewById(R.id.spProductoIngreso);
        etCantidadIngreso = findViewById(R.id.etCantidadIngreso);
        etObservacionIngreso = findViewById(R.id.etObservacionIngreso);
        btnGuardarIngreso = findViewById(R.id.btnGuardarIngreso);
        btnCancelarIngreso = findViewById(R.id.btnCancelarIngreso);
    }

    private void recibirDatos() {
        idProductoRecibido = getIntent().getIntExtra("id_producto", -1);
    }

    private void cargarProductosSpinner() {
        listaProductos.clear();
        listaProductos.addAll(productoViewModel.listarProductos());

        List<String> nombres = new ArrayList<>();
        for (Producto producto : listaProductos) {
            nombres.add(producto.getNombre() + " (" + producto.getCodigoProducto() + ")");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nombres
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProductoIngreso.setAdapter(adapter);

        if (idProductoRecibido != -1) {
            for (int i = 0; i < listaProductos.size(); i++) {
                if (listaProductos.get(i).getIdProducto() == idProductoRecibido) {
                    spProductoIngreso.setSelection(i);
                    break;
                }
            }
        }
    }

    private void configurarEventos() {
        btnGuardarIngreso.setOnClickListener(v -> guardarIngreso());
        btnCancelarIngreso.setOnClickListener(v -> finish());
    }

    private void guardarIngreso() {
        if (listaProductos.isEmpty()) {
            mostrarMensaje("No hay productos registrados");
            return;
        }

        String cantidad = etCantidadIngreso.getText().toString().trim();
        String observacion = etObservacionIngreso.getText().toString().trim();

        String errorValidacion = inventarioViewModel.validarIngresoMercaderia(cantidad);
        if (errorValidacion != null) {
            mostrarMensaje(errorValidacion);
            return;
        }

        Producto productoSeleccionado = listaProductos.get(spProductoIngreso.getSelectedItemPosition());

        String error = inventarioViewModel.ingresarMercaderiaAlAlmacen(
                productoSeleccionado.getIdProducto(),
                cantidad,
                sessionManager.getIdTrabajador(),
                observacion
        );

        if (error != null) {
            mostrarMensaje(error);
            return;
        }

        mostrarMensaje("Mercadería ingresada correctamente al almacén");
        setResult(RESULT_OK);
        finish();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
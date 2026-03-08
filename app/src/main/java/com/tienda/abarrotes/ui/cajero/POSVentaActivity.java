package com.tienda.abarrotes.ui.cajero;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.ui.adapters.CarritoAdapter;
import com.tienda.abarrotes.ui.common.SessionManager;
import com.tienda.abarrotes.utils.AppConstants;
import com.tienda.abarrotes.viewmodel.CajaViewModel;
import com.tienda.abarrotes.viewmodel.VentaViewModel;

import android.content.Intent;
import com.tienda.abarrotes.ui.common.ScannerActivity;

public class POSVentaActivity extends AppCompatActivity {

    private EditText etCodigoBarrasVenta;
    private EditText etCantidadVenta;
    private EditText etClienteNombreVenta;
    private EditText etClienteDocumentoVenta;
    private Spinner spComprobanteVenta;
    private Button btnBuscarAgregarVenta;
    private Button btnRegistrarVenta;
    private TextView tvProductoEncontradoVenta;
    private TextView tvTotalesVenta;
    private RecyclerView rvCarritoVenta;

    private VentaViewModel ventaViewModel;
    private CajaViewModel cajaViewModel;
    private SessionManager sessionManager;
    private CarritoAdapter carritoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_venta);

        ventaViewModel = new VentaViewModel(this);
        cajaViewModel = new CajaViewModel(this);
        sessionManager = new SessionManager(this);

        initViews();
        setupSpinner();
        setupRecycler();
        configurarEventos();
        validarCajaAbierta();
        refrescarTotales();
    }

    private void initViews() {
        etCodigoBarrasVenta = findViewById(R.id.etCodigoBarrasVenta);
        etCantidadVenta = findViewById(R.id.etCantidadVenta);
        etClienteNombreVenta = findViewById(R.id.etClienteNombreVenta);
        etClienteDocumentoVenta = findViewById(R.id.etClienteDocumentoVenta);
        spComprobanteVenta = findViewById(R.id.spComprobanteVenta);
        btnBuscarAgregarVenta = findViewById(R.id.btnBuscarAgregarVenta);
        btnRegistrarVenta = findViewById(R.id.btnRegistrarVenta);
        tvProductoEncontradoVenta = findViewById(R.id.tvProductoEncontradoVenta);
        tvTotalesVenta = findViewById(R.id.tvTotalesVenta);
        rvCarritoVenta = findViewById(R.id.rvCarritoVenta);
    }

    private void setupSpinner() {
        String[] tipos = {AppConstants.COMP_BOLETA, AppConstants.COMP_FACTURA};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tipos
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spComprobanteVenta.setAdapter(adapter);
    }

    private void setupRecycler() {
        carritoAdapter = new CarritoAdapter(position -> {
            ventaViewModel.quitarProductoDelCarrito(position);
            refrescarCarrito();
            refrescarTotales();
        });

        rvCarritoVenta.setLayoutManager(new LinearLayoutManager(this));
        rvCarritoVenta.setAdapter(carritoAdapter);
    }

    private void configurarEventos() {
        btnBuscarAgregarVenta.setOnClickListener(v -> buscarYAgregarProducto());
        btnRegistrarVenta.setOnClickListener(v -> confirmarVenta());
        tvProductoEncontradoVenta.setOnClickListener(v -> {
            Intent intent = new Intent(this, ScannerActivity.class);
            startActivity(intent);
        });
    }

    private void validarCajaAbierta() {
        if (cajaViewModel.obtenerCajaAbiertaPorCajero(sessionManager.getIdTrabajador()) == null) {
            mostrarMensaje("Debes abrir caja antes de vender");
            btnBuscarAgregarVenta.setEnabled(false);
            btnRegistrarVenta.setEnabled(false);
        }
    }

    private void buscarYAgregarProducto() {
        String codigo = etCodigoBarrasVenta.getText().toString().trim();
        String cantidadTexto = etCantidadVenta.getText().toString().trim();

        String errorCodigo = ventaViewModel.validarCodigoBarras(codigo);
        if (errorCodigo != null) {
            mostrarMensaje(errorCodigo);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadTexto);
            if (cantidad <= 0) throw new Exception();
        } catch (Exception e) {
            mostrarMensaje("Ingresa una cantidad válida");
            return;
        }

        Producto producto = ventaViewModel.buscarProductoPorCodigoBarras(codigo);

        if (producto == null) {
            mostrarMensaje("Producto no encontrado");
            tvProductoEncontradoVenta.setText("Producto no encontrado");
            return;
        }

        tvProductoEncontradoVenta.setText(
                producto.getNombre() +
                        "\nCódigo: " + producto.getCodigoProducto() +
                        "\nPrecio: S/ " + producto.getPrecioVenta() +
                        "\nStock tienda: " + producto.getStockTienda()
        );

        String errorAgregar = ventaViewModel.agregarProductoAlCarrito(producto, cantidad);
        if (errorAgregar != null) {
            mostrarMensaje(errorAgregar);
            return;
        }

        refrescarCarrito();
        refrescarTotales();
        etCodigoBarrasVenta.setText("");
        etCantidadVenta.setText("");
        mostrarMensaje("Producto agregado al carrito");
    }

    private void refrescarCarrito() {
        carritoAdapter.setData(
                ventaViewModel.getCarrito(),
                ventaViewModel.getProductosCarrito()
        );
    }

    private void refrescarTotales() {
        tvTotalesVenta.setText(ventaViewModel.getResumenTotales());
    }

    private void confirmarVenta() {
        if (ventaViewModel.getCarrito().isEmpty()) {
            mostrarMensaje("El carrito está vacío");
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Confirmar venta")
                .setMessage("¿Deseas registrar esta venta?")
                .setPositiveButton("Sí", (dialog, which) -> registrarVenta())
                .setNegativeButton("No", null)
                .show();
    }

    private void registrarVenta() {
        String tipoComprobante = spComprobanteVenta.getSelectedItem().toString();
        String clienteNombre = etClienteNombreVenta.getText().toString().trim();
        String clienteDocumento = etClienteDocumentoVenta.getText().toString().trim();

        String error = ventaViewModel.registrarVenta(
                sessionManager.getIdTrabajador(),
                tipoComprobante,
                clienteNombre,
                clienteDocumento
        );

        if (error != null) {
            mostrarMensaje(error);
            return;
        }

        mostrarMensaje("Venta registrada correctamente");
        ventaViewModel.limpiarCarrito();
        refrescarCarrito();
        refrescarTotales();
        tvProductoEncontradoVenta.setText("Producto no seleccionado");
        etClienteNombreVenta.setText("");
        etClienteDocumentoVenta.setText("");
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
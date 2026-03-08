package com.tienda.abarrotes.ui.admin.productos;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.viewmodel.ProductoViewModel;

import java.util.Locale;

public class DetalleProductoActivity extends AppCompatActivity {

    private TextView tvDetalleNombreProducto;
    private TextView tvDetalleCodigoProducto;
    private TextView tvDetalleCodigoBarrasProducto;
    private TextView tvDetalleCategoriaProducto;
    private TextView tvDetalleMarcaProducto;
    private TextView tvDetalleUnidadProducto;
    private TextView tvDetallePrecioCompraProducto;
    private TextView tvDetallePrecioVentaProducto;
    private TextView tvDetalleStockAlmacenProducto;
    private TextView tvDetalleStockTiendaProducto;
    private TextView tvDetalleStockMinimoProducto;
    private TextView tvDetalleEstadoProducto;
    private TextView tvDetalleFechaProducto;

    private ProductoViewModel productoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        productoViewModel = new ProductoViewModel(this);

        initViews();
        cargarProducto();
    }

    private void initViews() {
        tvDetalleNombreProducto = findViewById(R.id.tvDetalleNombreProducto);
        tvDetalleCodigoProducto = findViewById(R.id.tvDetalleCodigoProducto);
        tvDetalleCodigoBarrasProducto = findViewById(R.id.tvDetalleCodigoBarrasProducto);
        tvDetalleCategoriaProducto = findViewById(R.id.tvDetalleCategoriaProducto);
        tvDetalleMarcaProducto = findViewById(R.id.tvDetalleMarcaProducto);
        tvDetalleUnidadProducto = findViewById(R.id.tvDetalleUnidadProducto);
        tvDetallePrecioCompraProducto = findViewById(R.id.tvDetallePrecioCompraProducto);
        tvDetallePrecioVentaProducto = findViewById(R.id.tvDetallePrecioVentaProducto);
        tvDetalleStockAlmacenProducto = findViewById(R.id.tvDetalleStockAlmacenProducto);
        tvDetalleStockTiendaProducto = findViewById(R.id.tvDetalleStockTiendaProducto);
        tvDetalleStockMinimoProducto = findViewById(R.id.tvDetalleStockMinimoProducto);
        tvDetalleEstadoProducto = findViewById(R.id.tvDetalleEstadoProducto);
        tvDetalleFechaProducto = findViewById(R.id.tvDetalleFechaProducto);
    }

    private void cargarProducto() {
        int idProducto = getIntent().getIntExtra("id_producto", -1);

        if (idProducto == -1) {
            mostrarMensaje("No se recibió el producto");
            finish();
            return;
        }

        Producto producto = productoViewModel.obtenerProductoPorId(idProducto);

        if (producto == null) {
            mostrarMensaje("Producto no encontrado");
            finish();
            return;
        }

        tvDetalleNombreProducto.setText(producto.getNombre());
        tvDetalleCodigoProducto.setText("Código interno: " + producto.getCodigoProducto());
        tvDetalleCodigoBarrasProducto.setText("Código de barras: " + producto.getCodigoBarras());
        tvDetalleCategoriaProducto.setText("Categoría: " + producto.getCategoria());
        tvDetalleMarcaProducto.setText("Marca: " + producto.getMarca());
        tvDetalleUnidadProducto.setText("Unidad: " + producto.getUnidadMedida());
        tvDetallePrecioCompraProducto.setText(String.format(Locale.getDefault(), "Precio compra: S/ %.2f", producto.getPrecioCompra()));
        tvDetallePrecioVentaProducto.setText(String.format(Locale.getDefault(), "Precio venta: S/ %.2f", producto.getPrecioVenta()));
        tvDetalleStockAlmacenProducto.setText("Stock almacén: " + producto.getStockAlmacen());
        tvDetalleStockTiendaProducto.setText("Stock tienda: " + producto.getStockTienda());
        tvDetalleStockMinimoProducto.setText("Stock mínimo: " + producto.getStockMinimo());
        tvDetalleEstadoProducto.setText("Estado: " + producto.getEstado());
        tvDetalleFechaProducto.setText("Fecha registro: " + producto.getFechaRegistro());
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
package com.tienda.abarrotes.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import com.tienda.abarrotes.data.repository.VentaRepository;
import com.tienda.abarrotes.model.DetalleVenta;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VentaViewModel {

    private final VentaRepository repository;
    private final List<DetalleVenta> carrito = new ArrayList<>();
    private final List<Producto> productosCarrito = new ArrayList<>();

    public VentaViewModel(Context context) {
        this.repository = new VentaRepository(context);
    }

    public Producto buscarProductoPorCodigoBarras(String codigoBarras) {
        return repository.buscarProductoPorCodigoBarras(codigoBarras);
    }

    public String validarCodigoBarras(String codigoBarras) {
        if (TextUtils.isEmpty(codigoBarras)) {
            return "Ingresa el código de barras";
        }
        return null;
    }

    public String agregarProductoAlCarrito(Producto producto, int cantidad) {
        if (producto == null) {
            return "Producto no encontrado";
        }

        if (!AppConstants.ESTADO_ACTIVO.equalsIgnoreCase(producto.getEstado())) {
            return "El producto está inactivo";
        }

        if (cantidad <= 0) {
            return "La cantidad debe ser mayor que cero";
        }

        if (producto.getStockTienda() <= 0) {
            return "No hay stock en tienda";
        }

        int index = buscarIndiceProducto(producto.getIdProducto());

        if (index >= 0) {
            DetalleVenta existente = carrito.get(index);
            int nuevaCantidad = existente.getCantidad() + cantidad;

            if (nuevaCantidad > producto.getStockTienda()) {
                return "No hay suficiente stock en tienda";
            }

            existente.setCantidad(nuevaCantidad);
            existente.setImporte(nuevaCantidad * existente.getPrecioUnitario());
        } else {
            if (cantidad > producto.getStockTienda()) {
                return "No hay suficiente stock en tienda";
            }

            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdProducto(producto.getIdProducto());
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(producto.getPrecioVenta());
            detalle.setImporte(cantidad * producto.getPrecioVenta());

            carrito.add(detalle);
            productosCarrito.add(producto);
        }

        return null;
    }

    public void quitarProductoDelCarrito(int position) {
        if (position >= 0 && position < carrito.size()) {
            carrito.remove(position);
            productosCarrito.remove(position);
        }
    }

    public List<DetalleVenta> getCarrito() {
        return new ArrayList<>(carrito);
    }

    public List<Producto> getProductosCarrito() {
        return new ArrayList<>(productosCarrito);
    }

    public double calcularSubtotal() {
        double subtotal = 0.0;
        for (DetalleVenta item : carrito) {
            subtotal += item.getImporte();
        }
        return subtotal;
    }

    public double calcularIgv() {
        return calcularSubtotal() * AppConstants.IGV_RATE;
    }

    public double calcularTotal() {
        return calcularSubtotal() + calcularIgv();
    }

    public String registrarVenta(int idCajero, String tipoComprobante, String clienteNombre, String clienteDocumento) {
        if (TextUtils.isEmpty(tipoComprobante)) {
            return "Selecciona un comprobante";
        }

        return repository.registrarVenta(
                idCajero,
                tipoComprobante,
                clienteNombre,
                clienteDocumento,
                carrito,
                productosCarrito
        );
    }

    public void limpiarCarrito() {
        carrito.clear();
        productosCarrito.clear();
    }

    public String getResumenTotales() {
        return String.format(
                Locale.getDefault(),
                "Subtotal: S/ %.2f\nIGV: S/ %.2f\nTotal: S/ %.2f",
                calcularSubtotal(),
                calcularIgv(),
                calcularTotal()
        );
    }

    private int buscarIndiceProducto(int idProducto) {
        for (int i = 0; i < productosCarrito.size(); i++) {
            if (productosCarrito.get(i).getIdProducto() == idProducto) {
                return i;
            }
        }
        return -1;
    }
}
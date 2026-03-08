package com.tienda.abarrotes.data.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.CajaDao;
import com.tienda.abarrotes.data.dao.MovimientoStockDao;
import com.tienda.abarrotes.data.dao.VentaDao;
import com.tienda.abarrotes.model.Caja;
import com.tienda.abarrotes.model.DetalleVenta;
import com.tienda.abarrotes.model.MovimientoStock;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.model.Venta;
import com.tienda.abarrotes.utils.AppConstants;
import com.tienda.abarrotes.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VentaRepository {

    private final ProductoRepository productoRepository;
    private final CajaDao cajaDao;
    private final VentaDao ventaDao;
    private final MovimientoStockDao movimientoStockDao;

    public VentaRepository(Context context) {
        this.productoRepository = new ProductoRepository(context);
        this.cajaDao = new CajaDao(context);
        this.ventaDao = new VentaDao(context);
        this.movimientoStockDao = new MovimientoStockDao(context);
    }

    public Producto buscarProductoPorCodigoBarras(String codigoBarras) {
        return productoRepository.obtenerProductoPorCodigoBarras(codigoBarras);
    }

    public Caja obtenerCajaAbierta(int idCajero) {
        return cajaDao.obtenerCajaAbiertaPorCajero(idCajero);
    }

    public String registrarVenta(int idCajero,
                                 String tipoComprobante,
                                 String clienteNombre,
                                 String clienteDocumento,
                                 List<DetalleVenta> carrito,
                                 List<Producto> productosCarrito) {

        Caja cajaAbierta = cajaDao.obtenerCajaAbiertaPorCajero(idCajero);
        if (cajaAbierta == null) {
            return "No tienes una caja abierta";
        }

        if (carrito == null || carrito.isEmpty()) {
            return "El carrito está vacío";
        }

        double subtotal = 0.0;
        for (DetalleVenta item : carrito) {
            subtotal += item.getImporte();
        }

        double igv = subtotal * AppConstants.IGV_RATE;
        double total = subtotal + igv;

        Venta venta = new Venta();
        venta.setIdCaja(cajaAbierta.getIdCaja());
        venta.setIdCajero(idCajero);
        venta.setTipoComprobante(tipoComprobante);
        venta.setNumeroComprobante(generarNumeroComprobante(tipoComprobante, cajaAbierta.getIdCaja()));
        venta.setClienteNombre(clienteNombre == null ? "" : clienteNombre.trim());
        venta.setClienteDocumento(clienteDocumento == null ? "" : clienteDocumento.trim());
        venta.setSubtotal(subtotal);
        venta.setIgv(igv);
        venta.setTotal(total);
        venta.setFecha(DateTimeUtils.getCurrentDate());
        venta.setHora(DateTimeUtils.getCurrentTime());

        long idVenta = ventaDao.registrarVentaConDetalle(venta, carrito);
        if (idVenta <= 0) {
            return "No se pudo registrar la venta";
        }

        for (int i = 0; i < carrito.size(); i++) {
            DetalleVenta detalle = carrito.get(i);
            Producto producto = productosCarrito.get(i);

            int nuevoStockTienda = producto.getStockTienda() - detalle.getCantidad();
            if (nuevoStockTienda < 0) {
                return "Stock insuficiente en tienda para " + producto.getNombre();
            }

            boolean stockActualizado = productoRepository.actualizarStocks(
                    producto.getIdProducto(),
                    producto.getStockAlmacen(),
                    nuevoStockTienda
            );

            if (!stockActualizado) {
                return "La venta se registró, pero no se pudo actualizar el stock de " + producto.getNombre();
            }

            MovimientoStock movimiento = new MovimientoStock();
            movimiento.setIdProducto(producto.getIdProducto());
            movimiento.setTipoMovimiento(AppConstants.MOV_VENTA);
            movimiento.setCantidad(detalle.getCantidad());
            movimiento.setOrigen(AppConstants.UBICACION_TIENDA);
            movimiento.setDestino(AppConstants.UBICACION_CLIENTE);
            movimiento.setFecha(DateTimeUtils.getCurrentDate());
            movimiento.setHora(DateTimeUtils.getCurrentTime());
            movimiento.setIdTrabajador(idCajero);
            movimiento.setObservacion("Venta " + tipoComprobante);

            movimientoStockDao.insertar(movimiento);
        }

        cajaDao.actualizarTotalVentas(cajaAbierta.getIdCaja(), cajaAbierta.getTotalVentas() + total);

        return null;
    }

    private String generarNumeroComprobante(String tipoComprobante, int idCaja) {
        String prefijo = AppConstants.COMP_FACTURA.equalsIgnoreCase(tipoComprobante) ? "F" : "B";
        String fecha = DateTimeUtils.getCurrentDate().replace("-", "");
        return String.format(Locale.getDefault(), "%s-%s-%04d", prefijo, fecha, idCaja);
    }
}
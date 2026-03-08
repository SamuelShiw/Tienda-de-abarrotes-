package com.tienda.abarrotes.data.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.MovimientoStockDao;
import com.tienda.abarrotes.model.MovimientoStock;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.utils.AppConstants;
import com.tienda.abarrotes.utils.DateTimeUtils;

import java.util.List;

public class InventarioRepository {

    private final ProductoRepository productoRepository;
    private final MovimientoStockDao movimientoStockDao;

    public InventarioRepository(Context context) {
        this.productoRepository = new ProductoRepository(context);
        this.movimientoStockDao = new MovimientoStockDao(context);
    }

    public List<Producto> listarInventario() {
        return productoRepository.listarProductos();
    }

    public List<Producto> listarProductosConStockBajo() {
        return productoRepository.listarProductosConStockBajo();
    }

    public List<MovimientoStock> listarMovimientos() {
        return movimientoStockDao.listarTodos();
    }

    public String ingresarMercaderiaAlAlmacen(int idProducto, int cantidad, int idTrabajador, String observacion) {
        Producto producto = productoRepository.obtenerProductoPorId(idProducto);

        if (producto == null) {
            return "Producto no encontrado";
        }

        if (cantidad <= 0) {
            return "La cantidad debe ser mayor que cero";
        }

        int nuevoStockAlmacen = producto.getStockAlmacen() + cantidad;
        boolean stockActualizado = productoRepository.actualizarStocks(
                idProducto,
                nuevoStockAlmacen,
                producto.getStockTienda()
        );

        if (!stockActualizado) {
            return "No se pudo actualizar el stock del almacén";
        }

        MovimientoStock movimiento = new MovimientoStock();
        movimiento.setIdProducto(idProducto);
        movimiento.setTipoMovimiento(AppConstants.MOV_INGRESO_ALMACEN);
        movimiento.setCantidad(cantidad);
        movimiento.setOrigen(AppConstants.UBICACION_SISTEMA);
        movimiento.setDestino(AppConstants.UBICACION_ALMACEN);
        movimiento.setFecha(DateTimeUtils.getCurrentDate());
        movimiento.setHora(DateTimeUtils.getCurrentTime());
        movimiento.setIdTrabajador(idTrabajador);
        movimiento.setObservacion(observacion == null ? "" : observacion.trim());

        long resultadoMovimiento = movimientoStockDao.insertar(movimiento);

        return resultadoMovimiento > 0 ? null : "Stock actualizado, pero no se pudo registrar el movimiento";
    }

    public String reponerDeAlmacenATienda(int idProducto, int cantidad, int idTrabajador, String observacion) {
        Producto producto = productoRepository.obtenerProductoPorId(idProducto);

        if (producto == null) {
            return "Producto no encontrado";
        }

        if (cantidad <= 0) {
            return "La cantidad debe ser mayor que cero";
        }

        if (cantidad > producto.getStockAlmacen()) {
            return "No hay suficiente stock en almacén";
        }

        int nuevoStockAlmacen = producto.getStockAlmacen() - cantidad;
        int nuevoStockTienda = producto.getStockTienda() + cantidad;

        boolean actualizado = productoRepository.actualizarStocks(
                idProducto,
                nuevoStockAlmacen,
                nuevoStockTienda
        );

        if (!actualizado) {
            return "No se pudo actualizar el stock";
        }

        MovimientoStock movimiento = new MovimientoStock();
        movimiento.setIdProducto(idProducto);
        movimiento.setTipoMovimiento(AppConstants.MOV_REPOSICION_TIENDA);
        movimiento.setCantidad(cantidad);
        movimiento.setOrigen(AppConstants.UBICACION_ALMACEN);
        movimiento.setDestino(AppConstants.UBICACION_TIENDA);
        movimiento.setFecha(DateTimeUtils.getCurrentDate());
        movimiento.setHora(DateTimeUtils.getCurrentTime());
        movimiento.setIdTrabajador(idTrabajador);
        movimiento.setObservacion(observacion == null ? "" : observacion.trim());

        long resultado = movimientoStockDao.insertar(movimiento);
        return resultado > 0 ? null : "Stock actualizado, pero no se pudo registrar la reposición";
    }
}
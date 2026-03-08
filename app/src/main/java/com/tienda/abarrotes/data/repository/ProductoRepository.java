package com.tienda.abarrotes.data.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.ProductoDao;
import com.tienda.abarrotes.model.Producto;

import java.util.List;

public class ProductoRepository {

    private final ProductoDao productoDao;

    public ProductoRepository(Context context) {
        this.productoDao = new ProductoDao(context);
    }

    public long registrarProducto(Producto producto) {
        return productoDao.insertar(producto);
    }

    public boolean actualizarProducto(Producto producto) {
        return productoDao.actualizar(producto);
    }

    public boolean cambiarEstadoProducto(int idProducto, String estado) {
        return productoDao.cambiarEstado(idProducto, estado);
    }

    public Producto obtenerProductoPorId(int idProducto) {
        return productoDao.obtenerPorId(idProducto);
    }

    public Producto obtenerProductoPorCodigoBarras(String codigoBarras) {
        return productoDao.obtenerPorCodigoBarras(codigoBarras);
    }

    public List<Producto> listarProductos() {
        return productoDao.listarTodos();
    }
    public boolean actualizarStocks(int idProducto, int stockAlmacen, int stockTienda) {
        return productoDao.actualizarStocks(idProducto, stockAlmacen, stockTienda);
    }

    public List<Producto> listarProductosConStockBajo() {
        return productoDao.listarProductosConStockBajo();
    }
}
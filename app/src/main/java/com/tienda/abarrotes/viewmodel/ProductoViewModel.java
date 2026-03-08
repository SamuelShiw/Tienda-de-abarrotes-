package com.tienda.abarrotes.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import com.tienda.abarrotes.data.repository.ProductoRepository;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.utils.AppConstants;
import com.tienda.abarrotes.utils.DateTimeUtils;

import java.util.List;

public class ProductoViewModel {

    private final ProductoRepository repository;

    public ProductoViewModel(Context context) {
        this.repository = new ProductoRepository(context);
    }

    public List<Producto> listarProductos() {
        return repository.listarProductos();
    }

    public Producto obtenerProductoPorId(int idProducto) {
        return repository.obtenerProductoPorId(idProducto);
    }

    public String validarDatos(String nombre, String categoria, String unidadMedida,
                               String precioCompra, String precioVenta, String stockMinimo) {

        if (TextUtils.isEmpty(nombre)) return "Ingresa el nombre del producto";
        if (TextUtils.isEmpty(categoria)) return "Ingresa la categoría";
        if (TextUtils.isEmpty(unidadMedida)) return "Ingresa la unidad de medida";
        if (TextUtils.isEmpty(precioCompra)) return "Ingresa el precio de compra";
        if (TextUtils.isEmpty(precioVenta)) return "Ingresa el precio de venta";
        if (TextUtils.isEmpty(stockMinimo)) return "Ingresa el stock mínimo";

        double compra;
        double venta;
        int stock;

        try {
            compra = Double.parseDouble(precioCompra);
            venta = Double.parseDouble(precioVenta);
            stock = Integer.parseInt(stockMinimo);
        } catch (Exception e) {
            return "Ingresa valores numéricos válidos";
        }

        if (compra < 0) return "El precio de compra no puede ser negativo";
        if (venta <= 0) return "El precio de venta debe ser mayor que cero";
        if (stock < 0) return "El stock mínimo no puede ser negativo";

        return null;
    }

    public String registrarProducto(String nombre, String categoria, String marca,
                                    String unidadMedida, String precioCompra,
                                    String precioVenta, String stockMinimo) {

        Producto producto = new Producto();
        producto.setNombre(nombre.trim());
        producto.setCategoria(categoria.trim());
        producto.setMarca(marca == null ? "" : marca.trim());
        producto.setUnidadMedida(unidadMedida.trim());
        producto.setPrecioCompra(Double.parseDouble(precioCompra.trim()));
        producto.setPrecioVenta(Double.parseDouble(precioVenta.trim()));
        producto.setStockAlmacen(0);
        producto.setStockTienda(0);
        producto.setStockMinimo(Integer.parseInt(stockMinimo.trim()));
        producto.setEstado(AppConstants.ESTADO_ACTIVO);
        producto.setFechaRegistro(DateTimeUtils.getCurrentDate());

        long resultado = repository.registrarProducto(producto);
        return resultado > 0 ? null : "No se pudo registrar el producto";
    }

    public String actualizarProducto(int idProducto, String nombre, String categoria, String marca,
                                     String unidadMedida, String precioCompra,
                                     String precioVenta, String stockMinimo, String estado) {

        Producto producto = repository.obtenerProductoPorId(idProducto);
        if (producto == null) {
            return "No se encontró el producto";
        }

        producto.setNombre(nombre.trim());
        producto.setCategoria(categoria.trim());
        producto.setMarca(marca == null ? "" : marca.trim());
        producto.setUnidadMedida(unidadMedida.trim());
        producto.setPrecioCompra(Double.parseDouble(precioCompra.trim()));
        producto.setPrecioVenta(Double.parseDouble(precioVenta.trim()));
        producto.setStockMinimo(Integer.parseInt(stockMinimo.trim()));
        producto.setEstado(estado.trim());

        boolean actualizado = repository.actualizarProducto(producto);
        return actualizado ? null : "No se pudo actualizar el producto";
    }

    public boolean desactivarProducto(int idProducto) {
        return repository.cambiarEstadoProducto(idProducto, AppConstants.ESTADO_INACTIVO);
    }
}
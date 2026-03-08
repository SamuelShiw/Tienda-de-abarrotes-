package com.tienda.abarrotes.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import com.tienda.abarrotes.data.repository.InventarioRepository;
import com.tienda.abarrotes.model.MovimientoStock;
import com.tienda.abarrotes.model.Producto;

import java.util.List;

public class InventarioViewModel {

    private final InventarioRepository repository;

    public InventarioViewModel(Context context) {
        this.repository = new InventarioRepository(context);
    }

    public List<Producto> listarInventario() {
        return repository.listarInventario();
    }

    public List<Producto> listarProductosConStockBajo() {
        return repository.listarProductosConStockBajo();
    }

    public List<MovimientoStock> listarMovimientos() {
        return repository.listarMovimientos();
    }

    public String validarIngresoMercaderia(String cantidad) {
        if (TextUtils.isEmpty(cantidad)) {
            return "Ingresa la cantidad";
        }

        try {
            int cantidadInt = Integer.parseInt(cantidad.trim());
            if (cantidadInt <= 0) {
                return "La cantidad debe ser mayor que cero";
            }
        } catch (Exception e) {
            return "Ingresa una cantidad válida";
        }

        return null;
    }

    public String ingresarMercaderiaAlAlmacen(int idProducto, String cantidad, int idTrabajador, String observacion) {
        return repository.ingresarMercaderiaAlAlmacen(
                idProducto,
                Integer.parseInt(cantidad.trim()),
                idTrabajador,
                observacion
        );
    }

    public String reponerDeAlmacenATienda(int idProducto, String cantidad, int idTrabajador, String observacion) {
        return repository.reponerDeAlmacenATienda(
                idProducto,
                Integer.parseInt(cantidad.trim()),
                idTrabajador,
                observacion
        );
    }
}
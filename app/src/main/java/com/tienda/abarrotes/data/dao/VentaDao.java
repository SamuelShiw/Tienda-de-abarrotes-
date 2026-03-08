package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.model.DetalleVenta;
import com.tienda.abarrotes.model.Venta;

import java.util.List;

public class VentaDao {

    private final DatabaseHelper dbHelper;

    public VentaDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public long registrarVentaConDetalle(Venta venta, List<DetalleVenta> detalles) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long idVenta = -1;

        try {
            db.beginTransaction();

            ContentValues ventaValues = new ContentValues();
            ventaValues.put(DatabaseContract.VentaTable.COL_ID_CAJA, venta.getIdCaja());
            ventaValues.put(DatabaseContract.VentaTable.COL_ID_CAJERO, venta.getIdCajero());
            ventaValues.put(DatabaseContract.VentaTable.COL_TIPO_COMPROBANTE, venta.getTipoComprobante());
            ventaValues.put(DatabaseContract.VentaTable.COL_NUMERO_COMPROBANTE, venta.getNumeroComprobante());
            ventaValues.put(DatabaseContract.VentaTable.COL_CLIENTE_NOMBRE, venta.getClienteNombre());
            ventaValues.put(DatabaseContract.VentaTable.COL_CLIENTE_DOCUMENTO, venta.getClienteDocumento());
            ventaValues.put(DatabaseContract.VentaTable.COL_SUBTOTAL, venta.getSubtotal());
            ventaValues.put(DatabaseContract.VentaTable.COL_IGV, venta.getIgv());
            ventaValues.put(DatabaseContract.VentaTable.COL_TOTAL, venta.getTotal());
            ventaValues.put(DatabaseContract.VentaTable.COL_FECHA, venta.getFecha());
            ventaValues.put(DatabaseContract.VentaTable.COL_HORA, venta.getHora());

            idVenta = db.insert(DatabaseContract.VentaTable.TABLE_NAME, null, ventaValues);

            if (idVenta <= 0) {
                return -1;
            }

            for (DetalleVenta detalle : detalles) {
                ContentValues detalleValues = new ContentValues();
                detalleValues.put(DatabaseContract.DetalleVentaTable.COL_ID_VENTA, idVenta);
                detalleValues.put(DatabaseContract.DetalleVentaTable.COL_ID_PRODUCTO, detalle.getIdProducto());
                detalleValues.put(DatabaseContract.DetalleVentaTable.COL_CANTIDAD, detalle.getCantidad());
                detalleValues.put(DatabaseContract.DetalleVentaTable.COL_PRECIO_UNITARIO, detalle.getPrecioUnitario());
                detalleValues.put(DatabaseContract.DetalleVentaTable.COL_IMPORTE, detalle.getImporte());

                long idDetalle = db.insert(DatabaseContract.DetalleVentaTable.TABLE_NAME, null, detalleValues);
                if (idDetalle <= 0) {
                    return -1;
                }
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        return idVenta;
    }
}
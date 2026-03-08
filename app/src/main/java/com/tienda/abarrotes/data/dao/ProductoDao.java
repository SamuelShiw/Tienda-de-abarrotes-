package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.utils.AppConstants;
import com.tienda.abarrotes.utils.CodeGenerator;

import java.util.ArrayList;
import java.util.List;

public class ProductoDao {

    private final DatabaseHelper dbHelper;

    public ProductoDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public long insertar(Producto producto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long idInsertado = -1;

        try {
            db.beginTransaction();

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.ProductoTable.COL_CODIGO_PRODUCTO, "");
            values.put(DatabaseContract.ProductoTable.COL_CODIGO_BARRAS, "");
            values.put(DatabaseContract.ProductoTable.COL_NOMBRE, producto.getNombre());
            values.put(DatabaseContract.ProductoTable.COL_CATEGORIA, producto.getCategoria());
            values.put(DatabaseContract.ProductoTable.COL_MARCA, producto.getMarca());
            values.put(DatabaseContract.ProductoTable.COL_UNIDAD_MEDIDA, producto.getUnidadMedida());
            values.put(DatabaseContract.ProductoTable.COL_PRECIO_COMPRA, producto.getPrecioCompra());
            values.put(DatabaseContract.ProductoTable.COL_PRECIO_VENTA, producto.getPrecioVenta());
            values.put(DatabaseContract.ProductoTable.COL_STOCK_ALMACEN, producto.getStockAlmacen());
            values.put(DatabaseContract.ProductoTable.COL_STOCK_TIENDA, producto.getStockTienda());
            values.put(DatabaseContract.ProductoTable.COL_STOCK_MINIMO, producto.getStockMinimo());
            values.put(DatabaseContract.ProductoTable.COL_ESTADO, producto.getEstado());
            values.put(DatabaseContract.ProductoTable.COL_FECHA_REGISTRO, producto.getFechaRegistro());

            idInsertado = db.insert(DatabaseContract.ProductoTable.TABLE_NAME, null, values);

            if (idInsertado > 0) {
                String codigoProducto = CodeGenerator.generateProductCode((int) idInsertado);
                String codigoBarras = CodeGenerator.generateBarcodeFromProductId((int) idInsertado);

                ContentValues updateValues = new ContentValues();
                updateValues.put(DatabaseContract.ProductoTable.COL_CODIGO_PRODUCTO, codigoProducto);
                updateValues.put(DatabaseContract.ProductoTable.COL_CODIGO_BARRAS, codigoBarras);

                db.update(
                        DatabaseContract.ProductoTable.TABLE_NAME,
                        updateValues,
                        DatabaseContract.ProductoTable.COL_ID + " = ?",
                        new String[]{String.valueOf(idInsertado)}
                );
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        return idInsertado;
    }

    public boolean actualizar(Producto producto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas;

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.ProductoTable.COL_NOMBRE, producto.getNombre());
            values.put(DatabaseContract.ProductoTable.COL_CATEGORIA, producto.getCategoria());
            values.put(DatabaseContract.ProductoTable.COL_MARCA, producto.getMarca());
            values.put(DatabaseContract.ProductoTable.COL_UNIDAD_MEDIDA, producto.getUnidadMedida());
            values.put(DatabaseContract.ProductoTable.COL_PRECIO_COMPRA, producto.getPrecioCompra());
            values.put(DatabaseContract.ProductoTable.COL_PRECIO_VENTA, producto.getPrecioVenta());
            values.put(DatabaseContract.ProductoTable.COL_STOCK_MINIMO, producto.getStockMinimo());
            values.put(DatabaseContract.ProductoTable.COL_ESTADO, producto.getEstado());

            filas = db.update(
                    DatabaseContract.ProductoTable.TABLE_NAME,
                    values,
                    DatabaseContract.ProductoTable.COL_ID + " = ?",
                    new String[]{String.valueOf(producto.getIdProducto())}
            );
        } finally {
            db.close();
        }

        return filas > 0;
    }

    public boolean cambiarEstado(int idProducto, String estado) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas;

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.ProductoTable.COL_ESTADO, estado);

            filas = db.update(
                    DatabaseContract.ProductoTable.TABLE_NAME,
                    values,
                    DatabaseContract.ProductoTable.COL_ID + " = ?",
                    new String[]{String.valueOf(idProducto)}
            );
        } finally {
            db.close();
        }

        return filas > 0;
    }

    public Producto obtenerPorId(int idProducto) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Producto producto = null;

        try {
            cursor = db.query(
                    DatabaseContract.ProductoTable.TABLE_NAME,
                    null,
                    DatabaseContract.ProductoTable.COL_ID + " = ?",
                    new String[]{String.valueOf(idProducto)},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                producto = cursorToProducto(cursor);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return producto;
    }

    public Producto obtenerPorCodigoBarras(String codigoBarras) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Producto producto = null;

        try {
            cursor = db.query(
                    DatabaseContract.ProductoTable.TABLE_NAME,
                    null,
                    DatabaseContract.ProductoTable.COL_CODIGO_BARRAS + " = ?",
                    new String[]{codigoBarras},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                producto = cursorToProducto(cursor);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return producto;
    }

    public List<Producto> listarTodos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        List<Producto> lista = new ArrayList<>();

        try {
            cursor = db.query(
                    DatabaseContract.ProductoTable.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    DatabaseContract.ProductoTable.COL_ID + " DESC"
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    lista.add(cursorToProducto(cursor));
                }
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return lista;
    }

    private Producto cursorToProducto(Cursor cursor) {
        Producto producto = new Producto();

        producto.setIdProducto(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_ID)));
        producto.setCodigoProducto(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_CODIGO_PRODUCTO)));
        producto.setCodigoBarras(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_CODIGO_BARRAS)));
        producto.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_NOMBRE)));
        producto.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_CATEGORIA)));
        producto.setMarca(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_MARCA)));
        producto.setUnidadMedida(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_UNIDAD_MEDIDA)));
        producto.setPrecioCompra(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_PRECIO_COMPRA)));
        producto.setPrecioVenta(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_PRECIO_VENTA)));
        producto.setStockAlmacen(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_STOCK_ALMACEN)));
        producto.setStockTienda(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_STOCK_TIENDA)));
        producto.setStockMinimo(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_STOCK_MINIMO)));
        producto.setEstado(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_ESTADO)));
        producto.setFechaRegistro(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COL_FECHA_REGISTRO)));

        return producto;
    }
    public boolean actualizarStocks(int idProducto, int stockAlmacen, int stockTienda) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas;

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.ProductoTable.COL_STOCK_ALMACEN, stockAlmacen);
            values.put(DatabaseContract.ProductoTable.COL_STOCK_TIENDA, stockTienda);

            filas = db.update(
                    DatabaseContract.ProductoTable.TABLE_NAME,
                    values,
                    DatabaseContract.ProductoTable.COL_ID + " = ?",
                    new String[]{String.valueOf(idProducto)}
            );
        } finally {
            db.close();
        }

        return filas > 0;
    }

    public List<Producto> listarProductosConStockBajo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        List<Producto> lista = new ArrayList<>();

        try {
            String query = "SELECT * FROM " + DatabaseContract.ProductoTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.ProductoTable.COL_STOCK_TIENDA +
                    " <= " + DatabaseContract.ProductoTable.COL_STOCK_MINIMO +
                    " ORDER BY " + DatabaseContract.ProductoTable.COL_ID + " DESC";

            cursor = db.rawQuery(query, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    lista.add(cursorToProducto(cursor));
                }
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return lista;
    }
}
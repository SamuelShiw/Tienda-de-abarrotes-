package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.model.MovimientoStock;

import java.util.ArrayList;
import java.util.List;

public class MovimientoStockDao {

    private final DatabaseHelper dbHelper;

    public MovimientoStockDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public long insertar(MovimientoStock movimiento) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long resultado;

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.MovimientoStockTable.COL_ID_PRODUCTO, movimiento.getIdProducto());
            values.put(DatabaseContract.MovimientoStockTable.COL_TIPO_MOVIMIENTO, movimiento.getTipoMovimiento());
            values.put(DatabaseContract.MovimientoStockTable.COL_CANTIDAD, movimiento.getCantidad());
            values.put(DatabaseContract.MovimientoStockTable.COL_ORIGEN, movimiento.getOrigen());
            values.put(DatabaseContract.MovimientoStockTable.COL_DESTINO, movimiento.getDestino());
            values.put(DatabaseContract.MovimientoStockTable.COL_FECHA, movimiento.getFecha());
            values.put(DatabaseContract.MovimientoStockTable.COL_HORA, movimiento.getHora());
            values.put(DatabaseContract.MovimientoStockTable.COL_ID_TRABAJADOR, movimiento.getIdTrabajador());
            values.put(DatabaseContract.MovimientoStockTable.COL_OBSERVACION, movimiento.getObservacion());

            resultado = db.insert(DatabaseContract.MovimientoStockTable.TABLE_NAME, null, values);
        } finally {
            db.close();
        }

        return resultado;
    }

    public List<MovimientoStock> listarTodos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        List<MovimientoStock> lista = new ArrayList<>();

        try {
            cursor = db.query(
                    DatabaseContract.MovimientoStockTable.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    DatabaseContract.MovimientoStockTable.COL_ID + " DESC"
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    lista.add(cursorToMovimiento(cursor));
                }
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return lista;
    }

    public List<MovimientoStock> listarPorProducto(int idProducto) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        List<MovimientoStock> lista = new ArrayList<>();

        try {
            cursor = db.query(
                    DatabaseContract.MovimientoStockTable.TABLE_NAME,
                    null,
                    DatabaseContract.MovimientoStockTable.COL_ID_PRODUCTO + " = ?",
                    new String[]{String.valueOf(idProducto)},
                    null,
                    null,
                    DatabaseContract.MovimientoStockTable.COL_ID + " DESC"
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    lista.add(cursorToMovimiento(cursor));
                }
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return lista;
    }

    private MovimientoStock cursorToMovimiento(Cursor cursor) {
        MovimientoStock movimiento = new MovimientoStock();

        movimiento.setIdMovimiento(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoStockTable.COL_ID)));
        movimiento.setIdProducto(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoStockTable.COL_ID_PRODUCTO)));
        movimiento.setTipoMovimiento(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoStockTable.COL_TIPO_MOVIMIENTO)));
        movimiento.setCantidad(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoStockTable.COL_CANTIDAD)));
        movimiento.setOrigen(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoStockTable.COL_ORIGEN)));
        movimiento.setDestino(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoStockTable.COL_DESTINO)));
        movimiento.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoStockTable.COL_FECHA)));
        movimiento.setHora(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoStockTable.COL_HORA)));
        movimiento.setIdTrabajador(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoStockTable.COL_ID_TRABAJADOR)));
        movimiento.setObservacion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoStockTable.COL_OBSERVACION)));

        return movimiento;
    }
}
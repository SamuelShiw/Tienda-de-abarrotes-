package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.model.Caja;
import com.tienda.abarrotes.utils.AppConstants;

public class CajaDao {

    private final DatabaseHelper dbHelper;

    public CajaDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public long abrirCaja(Caja caja) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long resultado;

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.CajaTable.COL_ID_CAJERO, caja.getIdCajero());
            values.put(DatabaseContract.CajaTable.COL_FECHA_APERTURA, caja.getFechaApertura());
            values.put(DatabaseContract.CajaTable.COL_HORA_APERTURA, caja.getHoraApertura());
            values.put(DatabaseContract.CajaTable.COL_MONTO_INICIAL, caja.getMontoInicial());
            values.put(DatabaseContract.CajaTable.COL_ESTADO, caja.getEstado());
            values.put(DatabaseContract.CajaTable.COL_FECHA_CIERRE, caja.getFechaCierre());
            values.put(DatabaseContract.CajaTable.COL_HORA_CIERRE, caja.getHoraCierre());
            values.put(DatabaseContract.CajaTable.COL_MONTO_FINAL, caja.getMontoFinal());
            values.put(DatabaseContract.CajaTable.COL_TOTAL_VENTAS, caja.getTotalVentas());

            resultado = db.insert(DatabaseContract.CajaTable.TABLE_NAME, null, values);
        } finally {
            db.close();
        }

        return resultado;
    }

    public Caja obtenerCajaAbiertaPorCajero(int idCajero) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Caja caja = null;

        try {
            cursor = db.query(
                    DatabaseContract.CajaTable.TABLE_NAME,
                    null,
                    DatabaseContract.CajaTable.COL_ID_CAJERO + " = ? AND " +
                            DatabaseContract.CajaTable.COL_ESTADO + " = ?",
                    new String[]{String.valueOf(idCajero), AppConstants.CAJA_ABIERTA},
                    null,
                    null,
                    DatabaseContract.CajaTable.COL_ID + " DESC",
                    "1"
            );

            if (cursor != null && cursor.moveToFirst()) {
                caja = cursorToCaja(cursor);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return caja;
    }

    public Caja obtenerUltimaCajaPorCajero(int idCajero) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Caja caja = null;

        try {
            cursor = db.query(
                    DatabaseContract.CajaTable.TABLE_NAME,
                    null,
                    DatabaseContract.CajaTable.COL_ID_CAJERO + " = ?",
                    new String[]{String.valueOf(idCajero)},
                    null,
                    null,
                    DatabaseContract.CajaTable.COL_ID + " DESC",
                    "1"
            );

            if (cursor != null && cursor.moveToFirst()) {
                caja = cursorToCaja(cursor);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return caja;
    }

    public boolean cerrarCaja(int idCaja, String fechaCierre, String horaCierre,
                              double montoFinal, double totalVentas) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas;

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.CajaTable.COL_ESTADO, AppConstants.CAJA_CERRADA);
            values.put(DatabaseContract.CajaTable.COL_FECHA_CIERRE, fechaCierre);
            values.put(DatabaseContract.CajaTable.COL_HORA_CIERRE, horaCierre);
            values.put(DatabaseContract.CajaTable.COL_MONTO_FINAL, montoFinal);
            values.put(DatabaseContract.CajaTable.COL_TOTAL_VENTAS, totalVentas);

            filas = db.update(
                    DatabaseContract.CajaTable.TABLE_NAME,
                    values,
                    DatabaseContract.CajaTable.COL_ID + " = ?",
                    new String[]{String.valueOf(idCaja)}
            );
        } finally {
            db.close();
        }

        return filas > 0;
    }

    public boolean actualizarTotalVentas(int idCaja, double totalVentas) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas;

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.CajaTable.COL_TOTAL_VENTAS, totalVentas);

            filas = db.update(
                    DatabaseContract.CajaTable.TABLE_NAME,
                    values,
                    DatabaseContract.CajaTable.COL_ID + " = ?",
                    new String[]{String.valueOf(idCaja)}
            );
        } finally {
            db.close();
        }

        return filas > 0;
    }

    private Caja cursorToCaja(Cursor cursor) {
        Caja caja = new Caja();

        caja.setIdCaja(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COL_ID)));
        caja.setIdCajero(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COL_ID_CAJERO)));
        caja.setFechaApertura(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COL_FECHA_APERTURA)));
        caja.setHoraApertura(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COL_HORA_APERTURA)));
        caja.setMontoInicial(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COL_MONTO_INICIAL)));
        caja.setEstado(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COL_ESTADO)));
        caja.setFechaCierre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COL_FECHA_CIERRE)));
        caja.setHoraCierre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COL_HORA_CIERRE)));
        caja.setMontoFinal(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COL_MONTO_FINAL)));
        caja.setTotalVentas(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COL_TOTAL_VENTAS)));

        return caja;
    }
}
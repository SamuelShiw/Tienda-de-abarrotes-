package com.tienda.abarrotes.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.tienda.abarrotes.utils.AppConstants;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, AppConstants.DB_NAME, null, AppConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.TrabajadorTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.ProductoTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.MovimientoStockTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.CajaTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.VentaTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.DetalleVentaTable.CREATE_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.DetalleVentaTable.DROP_TABLE);
        db.execSQL(DatabaseContract.VentaTable.DROP_TABLE);
        db.execSQL(DatabaseContract.CajaTable.DROP_TABLE);
        db.execSQL(DatabaseContract.MovimientoStockTable.DROP_TABLE);
        db.execSQL(DatabaseContract.ProductoTable.DROP_TABLE);
        db.execSQL(DatabaseContract.TrabajadorTable.DROP_TABLE);
        onCreate(db);
    }
}
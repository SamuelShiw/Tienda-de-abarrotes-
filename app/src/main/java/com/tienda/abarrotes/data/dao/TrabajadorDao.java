package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.model.Trabajador;
import com.tienda.abarrotes.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class TrabajadorDao {

    private final DatabaseHelper dbHelper;

    public TrabajadorDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public long insertar(Trabajador trabajador) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long resultado;

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.TrabajadorTable.COL_FOTO_URI, trabajador.getFotoUri());
            values.put(DatabaseContract.TrabajadorTable.COL_NOMBRES, trabajador.getNombres());
            values.put(DatabaseContract.TrabajadorTable.COL_APELLIDOS, trabajador.getApellidos());
            values.put(DatabaseContract.TrabajadorTable.COL_DNI, trabajador.getDni());
            values.put(DatabaseContract.TrabajadorTable.COL_TELEFONO, trabajador.getTelefono());
            values.put(DatabaseContract.TrabajadorTable.COL_USUARIO, trabajador.getUsuario());
            values.put(DatabaseContract.TrabajadorTable.COL_CONTRASENA, trabajador.getContrasena());
            values.put(DatabaseContract.TrabajadorTable.COL_ROL, trabajador.getRol());
            values.put(DatabaseContract.TrabajadorTable.COL_ESTADO, trabajador.getEstado());
            values.put(DatabaseContract.TrabajadorTable.COL_QR_DNI, trabajador.getQrDni());
            values.put(DatabaseContract.TrabajadorTable.COL_FECHA_REGISTRO, trabajador.getFechaRegistro());

            resultado = db.insert(DatabaseContract.TrabajadorTable.TABLE_NAME, null, values);
        } finally {
            db.close();
        }

        return resultado;
    }

    public Trabajador obtenerPorUsuario(String usuario) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Trabajador trabajador = null;
        Cursor cursor = null;

        try {
            cursor = db.query(
                    DatabaseContract.TrabajadorTable.TABLE_NAME,
                    null,
                    DatabaseContract.TrabajadorTable.COL_USUARIO + " = ?",
                    new String[]{usuario},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                trabajador = cursorToTrabajador(cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return trabajador;
    }

    public Trabajador obtenerPorId(int idTrabajador) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Trabajador trabajador = null;
        Cursor cursor = null;

        try {
            cursor = db.query(
                    DatabaseContract.TrabajadorTable.TABLE_NAME,
                    null,
                    DatabaseContract.TrabajadorTable.COL_ID + " = ?",
                    new String[]{String.valueOf(idTrabajador)},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                trabajador = cursorToTrabajador(cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return trabajador;
    }

    public List<Trabajador> listarTodos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Trabajador> lista = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    DatabaseContract.TrabajadorTable.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    DatabaseContract.TrabajadorTable.COL_ID + " DESC"
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    lista.add(cursorToTrabajador(cursor));
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return lista;
    }

    public boolean actualizar(Trabajador trabajador) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas;

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.TrabajadorTable.COL_FOTO_URI, trabajador.getFotoUri());
            values.put(DatabaseContract.TrabajadorTable.COL_NOMBRES, trabajador.getNombres());
            values.put(DatabaseContract.TrabajadorTable.COL_APELLIDOS, trabajador.getApellidos());
            values.put(DatabaseContract.TrabajadorTable.COL_DNI, trabajador.getDni());
            values.put(DatabaseContract.TrabajadorTable.COL_TELEFONO, trabajador.getTelefono());
            values.put(DatabaseContract.TrabajadorTable.COL_USUARIO, trabajador.getUsuario());
            values.put(DatabaseContract.TrabajadorTable.COL_CONTRASENA, trabajador.getContrasena());
            values.put(DatabaseContract.TrabajadorTable.COL_ROL, trabajador.getRol());
            values.put(DatabaseContract.TrabajadorTable.COL_ESTADO, trabajador.getEstado());
            values.put(DatabaseContract.TrabajadorTable.COL_QR_DNI, trabajador.getQrDni());

            filas = db.update(
                    DatabaseContract.TrabajadorTable.TABLE_NAME,
                    values,
                    DatabaseContract.TrabajadorTable.COL_ID + " = ?",
                    new String[]{String.valueOf(trabajador.getIdTrabajador())}
            );
        } finally {
            db.close();
        }

        return filas > 0;
    }

    public boolean cambiarEstado(int idTrabajador, String nuevoEstado) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas;

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.TrabajadorTable.COL_ESTADO, nuevoEstado);

            filas = db.update(
                    DatabaseContract.TrabajadorTable.TABLE_NAME,
                    values,
                    DatabaseContract.TrabajadorTable.COL_ID + " = ?",
                    new String[]{String.valueOf(idTrabajador)}
            );
        } finally {
            db.close();
        }

        return filas > 0;
    }

    public boolean existeDni(String dni) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    DatabaseContract.TrabajadorTable.TABLE_NAME,
                    new String[]{DatabaseContract.TrabajadorTable.COL_ID},
                    DatabaseContract.TrabajadorTable.COL_DNI + " = ?",
                    new String[]{dni},
                    null,
                    null,
                    null
            );
            return cursor != null && cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public boolean existeUsuario(String usuario) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    DatabaseContract.TrabajadorTable.TABLE_NAME,
                    new String[]{DatabaseContract.TrabajadorTable.COL_ID},
                    DatabaseContract.TrabajadorTable.COL_USUARIO + " = ?",
                    new String[]{usuario},
                    null,
                    null,
                    null
            );
            return cursor != null && cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public void insertarUsuariosBaseSiNoExisten() {
        if (!existeUsuario("admin")) {
            Trabajador admin = new Trabajador();
            admin.setFotoUri("");
            admin.setNombres("Administrador");
            admin.setApellidos("General");
            admin.setDni("00000001");
            admin.setTelefono("999999991");
            admin.setUsuario("admin");
            admin.setContrasena(com.tienda.abarrotes.utils.SecurityUtils.hashPassword("admin123"));
            admin.setRol(AppConstants.ROL_ADMINISTRADOR);
            admin.setEstado(AppConstants.ESTADO_ACTIVO);
            admin.setQrDni("DNI:00000001|NOMBRE:Administrador General|ROL:ADMINISTRADOR");
            admin.setFechaRegistro(com.tienda.abarrotes.utils.DateTimeUtils.getCurrentDate());
            insertar(admin);
        }

        if (!existeUsuario("cajero")) {
            Trabajador cajero = new Trabajador();
            cajero.setFotoUri("");
            cajero.setNombres("Carlos");
            cajero.setApellidos("Cajero");
            cajero.setDni("00000002");
            cajero.setTelefono("999999992");
            cajero.setUsuario("cajero");
            cajero.setContrasena(com.tienda.abarrotes.utils.SecurityUtils.hashPassword("cajero123"));
            cajero.setRol(AppConstants.ROL_CAJERO);
            cajero.setEstado(AppConstants.ESTADO_ACTIVO);
            cajero.setQrDni("DNI:00000002|NOMBRE:Carlos Cajero|ROL:CAJERO");
            cajero.setFechaRegistro(com.tienda.abarrotes.utils.DateTimeUtils.getCurrentDate());
            insertar(cajero);
        }

        if (!existeUsuario("reponedor")) {
            Trabajador reponedor = new Trabajador();
            reponedor.setFotoUri("");
            reponedor.setNombres("Raul");
            reponedor.setApellidos("Reponedor");
            reponedor.setDni("00000003");
            reponedor.setTelefono("999999993");
            reponedor.setUsuario("reponedor");
            reponedor.setContrasena(com.tienda.abarrotes.utils.SecurityUtils.hashPassword("reponedor123"));
            reponedor.setRol(AppConstants.ROL_REPONEDOR);
            reponedor.setEstado(AppConstants.ESTADO_ACTIVO);
            reponedor.setQrDni("DNI:00000003|NOMBRE:Raul Reponedor|ROL:REPONEDOR");
            reponedor.setFechaRegistro(com.tienda.abarrotes.utils.DateTimeUtils.getCurrentDate());
            insertar(reponedor);
        }
    }

    private Trabajador cursorToTrabajador(Cursor cursor) {
        Trabajador trabajador = new Trabajador();

        trabajador.setIdTrabajador(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COL_ID)));
        trabajador.setFotoUri(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COL_FOTO_URI)));
        trabajador.setNombres(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COL_NOMBRES)));
        trabajador.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COL_APELLIDOS)));
        trabajador.setDni(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COL_DNI)));
        trabajador.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COL_TELEFONO)));
        trabajador.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COL_USUARIO)));
        trabajador.setContrasena(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COL_CONTRASENA)));
        trabajador.setRol(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COL_ROL)));
        trabajador.setEstado(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COL_ESTADO)));
        trabajador.setQrDni(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COL_QR_DNI)));
        trabajador.setFechaRegistro(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COL_FECHA_REGISTRO)));

        return trabajador;
    }
}
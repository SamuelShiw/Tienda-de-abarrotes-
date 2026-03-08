package com.tienda.abarrotes.data.db;

public final class DatabaseContract {

    private DatabaseContract() {
        // Evita instanciación
    }

    public static final class TrabajadorTable {
        public static final String TABLE_NAME = "trabajadores";

        public static final String COL_ID = "id_trabajador";
        public static final String COL_FOTO_URI = "foto_uri";
        public static final String COL_NOMBRES = "nombres";
        public static final String COL_APELLIDOS = "apellidos";
        public static final String COL_DNI = "dni";
        public static final String COL_TELEFONO = "telefono";
        public static final String COL_USUARIO = "usuario";
        public static final String COL_CONTRASENA = "contrasena";
        public static final String COL_ROL = "rol";
        public static final String COL_ESTADO = "estado";
        public static final String COL_QR_DNI = "qr_dni";
        public static final String COL_FECHA_REGISTRO = "fecha_registro";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL_FOTO_URI + " TEXT, "
                        + COL_NOMBRES + " TEXT NOT NULL, "
                        + COL_APELLIDOS + " TEXT NOT NULL, "
                        + COL_DNI + " TEXT NOT NULL UNIQUE, "
                        + COL_TELEFONO + " TEXT NOT NULL, "
                        + COL_USUARIO + " TEXT NOT NULL UNIQUE, "
                        + COL_CONTRASENA + " TEXT NOT NULL, "
                        + COL_ROL + " TEXT NOT NULL, "
                        + COL_ESTADO + " TEXT NOT NULL DEFAULT 'ACTIVO', "
                        + COL_QR_DNI + " TEXT, "
                        + COL_FECHA_REGISTRO + " TEXT NOT NULL"
                        + ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class ProductoTable {
        public static final String TABLE_NAME = "productos";

        public static final String COL_ID = "id_producto";
        public static final String COL_CODIGO_PRODUCTO = "codigo_producto";
        public static final String COL_CODIGO_BARRAS = "codigo_barras";
        public static final String COL_NOMBRE = "nombre";
        public static final String COL_CATEGORIA = "categoria";
        public static final String COL_MARCA = "marca";
        public static final String COL_UNIDAD_MEDIDA = "unidad_medida";
        public static final String COL_PRECIO_COMPRA = "precio_compra";
        public static final String COL_PRECIO_VENTA = "precio_venta";
        public static final String COL_STOCK_ALMACEN = "stock_almacen";
        public static final String COL_STOCK_TIENDA = "stock_tienda";
        public static final String COL_STOCK_MINIMO = "stock_minimo";
        public static final String COL_ESTADO = "estado";
        public static final String COL_FECHA_REGISTRO = "fecha_registro";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL_CODIGO_PRODUCTO + " TEXT NOT NULL UNIQUE, "
                        + COL_CODIGO_BARRAS + " TEXT NOT NULL UNIQUE, "
                        + COL_NOMBRE + " TEXT NOT NULL, "
                        + COL_CATEGORIA + " TEXT NOT NULL, "
                        + COL_MARCA + " TEXT, "
                        + COL_UNIDAD_MEDIDA + " TEXT NOT NULL, "
                        + COL_PRECIO_COMPRA + " REAL NOT NULL, "
                        + COL_PRECIO_VENTA + " REAL NOT NULL, "
                        + COL_STOCK_ALMACEN + " INTEGER NOT NULL DEFAULT 0, "
                        + COL_STOCK_TIENDA + " INTEGER NOT NULL DEFAULT 0, "
                        + COL_STOCK_MINIMO + " INTEGER NOT NULL DEFAULT 0, "
                        + COL_ESTADO + " TEXT NOT NULL DEFAULT 'ACTIVO', "
                        + COL_FECHA_REGISTRO + " TEXT NOT NULL"
                        + ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class MovimientoStockTable {
        public static final String TABLE_NAME = "movimientos_stock";

        public static final String COL_ID = "id_movimiento";
        public static final String COL_ID_PRODUCTO = "id_producto";
        public static final String COL_TIPO_MOVIMIENTO = "tipo_movimiento";
        public static final String COL_CANTIDAD = "cantidad";
        public static final String COL_ORIGEN = "origen";
        public static final String COL_DESTINO = "destino";
        public static final String COL_FECHA = "fecha";
        public static final String COL_HORA = "hora";
        public static final String COL_ID_TRABAJADOR = "id_trabajador";
        public static final String COL_OBSERVACION = "observacion";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL_ID_PRODUCTO + " INTEGER NOT NULL, "
                        + COL_TIPO_MOVIMIENTO + " TEXT NOT NULL, "
                        + COL_CANTIDAD + " INTEGER NOT NULL, "
                        + COL_ORIGEN + " TEXT NOT NULL, "
                        + COL_DESTINO + " TEXT NOT NULL, "
                        + COL_FECHA + " TEXT NOT NULL, "
                        + COL_HORA + " TEXT NOT NULL, "
                        + COL_ID_TRABAJADOR + " INTEGER NOT NULL, "
                        + COL_OBSERVACION + " TEXT, "
                        + "FOREIGN KEY(" + COL_ID_PRODUCTO + ") REFERENCES "
                        + ProductoTable.TABLE_NAME + "(" + ProductoTable.COL_ID + "), "
                        + "FOREIGN KEY(" + COL_ID_TRABAJADOR + ") REFERENCES "
                        + TrabajadorTable.TABLE_NAME + "(" + TrabajadorTable.COL_ID + ")"
                        + ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class CajaTable {
        public static final String TABLE_NAME = "cajas";

        public static final String COL_ID = "id_caja";
        public static final String COL_ID_CAJERO = "id_cajero";
        public static final String COL_FECHA_APERTURA = "fecha_apertura";
        public static final String COL_HORA_APERTURA = "hora_apertura";
        public static final String COL_MONTO_INICIAL = "monto_inicial";
        public static final String COL_ESTADO = "estado";
        public static final String COL_FECHA_CIERRE = "fecha_cierre";
        public static final String COL_HORA_CIERRE = "hora_cierre";
        public static final String COL_MONTO_FINAL = "monto_final";
        public static final String COL_TOTAL_VENTAS = "total_ventas";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL_ID_CAJERO + " INTEGER NOT NULL, "
                        + COL_FECHA_APERTURA + " TEXT NOT NULL, "
                        + COL_HORA_APERTURA + " TEXT NOT NULL, "
                        + COL_MONTO_INICIAL + " REAL NOT NULL, "
                        + COL_ESTADO + " TEXT NOT NULL, "
                        + COL_FECHA_CIERRE + " TEXT, "
                        + COL_HORA_CIERRE + " TEXT, "
                        + COL_MONTO_FINAL + " REAL, "
                        + COL_TOTAL_VENTAS + " REAL DEFAULT 0, "
                        + "FOREIGN KEY(" + COL_ID_CAJERO + ") REFERENCES "
                        + TrabajadorTable.TABLE_NAME + "(" + TrabajadorTable.COL_ID + ")"
                        + ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class VentaTable {
        public static final String TABLE_NAME = "ventas";

        public static final String COL_ID = "id_venta";
        public static final String COL_ID_CAJA = "id_caja";
        public static final String COL_ID_CAJERO = "id_cajero";
        public static final String COL_TIPO_COMPROBANTE = "tipo_comprobante";
        public static final String COL_NUMERO_COMPROBANTE = "numero_comprobante";
        public static final String COL_CLIENTE_NOMBRE = "cliente_nombre";
        public static final String COL_CLIENTE_DOCUMENTO = "cliente_documento";
        public static final String COL_SUBTOTAL = "subtotal";
        public static final String COL_IGV = "igv";
        public static final String COL_TOTAL = "total";
        public static final String COL_FECHA = "fecha";
        public static final String COL_HORA = "hora";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL_ID_CAJA + " INTEGER NOT NULL, "
                        + COL_ID_CAJERO + " INTEGER NOT NULL, "
                        + COL_TIPO_COMPROBANTE + " TEXT NOT NULL, "
                        + COL_NUMERO_COMPROBANTE + " TEXT NOT NULL, "
                        + COL_CLIENTE_NOMBRE + " TEXT, "
                        + COL_CLIENTE_DOCUMENTO + " TEXT, "
                        + COL_SUBTOTAL + " REAL NOT NULL, "
                        + COL_IGV + " REAL NOT NULL, "
                        + COL_TOTAL + " REAL NOT NULL, "
                        + COL_FECHA + " TEXT NOT NULL, "
                        + COL_HORA + " TEXT NOT NULL, "
                        + "FOREIGN KEY(" + COL_ID_CAJA + ") REFERENCES "
                        + CajaTable.TABLE_NAME + "(" + CajaTable.COL_ID + "), "
                        + "FOREIGN KEY(" + COL_ID_CAJERO + ") REFERENCES "
                        + TrabajadorTable.TABLE_NAME + "(" + TrabajadorTable.COL_ID + ")"
                        + ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class DetalleVentaTable {
        public static final String TABLE_NAME = "detalle_venta";

        public static final String COL_ID = "id_detalle";
        public static final String COL_ID_VENTA = "id_venta";
        public static final String COL_ID_PRODUCTO = "id_producto";
        public static final String COL_CANTIDAD = "cantidad";
        public static final String COL_PRECIO_UNITARIO = "precio_unitario";
        public static final String COL_IMPORTE = "importe";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL_ID_VENTA + " INTEGER NOT NULL, "
                        + COL_ID_PRODUCTO + " INTEGER NOT NULL, "
                        + COL_CANTIDAD + " INTEGER NOT NULL, "
                        + COL_PRECIO_UNITARIO + " REAL NOT NULL, "
                        + COL_IMPORTE + " REAL NOT NULL, "
                        + "FOREIGN KEY(" + COL_ID_VENTA + ") REFERENCES "
                        + VentaTable.TABLE_NAME + "(" + VentaTable.COL_ID + "), "
                        + "FOREIGN KEY(" + COL_ID_PRODUCTO + ") REFERENCES "
                        + ProductoTable.TABLE_NAME + "(" + ProductoTable.COL_ID + ")"
                        + ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
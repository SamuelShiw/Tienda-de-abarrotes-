package com.tienda.abarrotes.utils;

public final class AppConstants {

    private AppConstants() {
        // Evita instanciación
    }

    // Base de datos
    public static final String DB_NAME = "abarrotes.db";
    public static final int DB_VERSION = 1;

    // Roles
    public static final String ROL_ADMINISTRADOR = "ADMINISTRADOR";
    public static final String ROL_REPONEDOR = "REPONEDOR";
    public static final String ROL_CAJERO = "CAJERO";

    // Estados generales
    public static final String ESTADO_ACTIVO = "ACTIVO";
    public static final String ESTADO_INACTIVO = "INACTIVO";

    // Estado de caja
    public static final String CAJA_ABIERTA = "ABIERTA";
    public static final String CAJA_CERRADA = "CERRADA";

    // Tipos de movimiento de stock
    public static final String MOV_INGRESO_ALMACEN = "INGRESO_ALMACEN";
    public static final String MOV_REPOSICION_TIENDA = "REPOSICION_A_TIENDA";
    public static final String MOV_AJUSTE_MANUAL = "AJUSTE_MANUAL";
    public static final String MOV_VENTA = "VENTA";

    // Origen y destino de stock
    public static final String UBICACION_ALMACEN = "ALMACEN";
    public static final String UBICACION_TIENDA = "TIENDA";
    public static final String UBICACION_CLIENTE = "CLIENTE";
    public static final String UBICACION_SISTEMA = "SISTEMA";

    // Comprobantes
    public static final String COMP_BOLETA = "BOLETA";
    public static final String COMP_FACTURA = "FACTURA";

    // Sesión
    public static final String PREF_SESSION = "abarrotes_session";
    public static final String KEY_SESSION_ID_TRABAJADOR = "key_session_id_trabajador";
    public static final String KEY_SESSION_USUARIO = "key_session_usuario";
    public static final String KEY_SESSION_ROL = "key_session_rol";
    public static final String KEY_SESSION_LOGGED = "key_session_logged";

    // Formatos
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // IGV base para cálculos
    public static final double IGV_RATE = 0.18;

    // Prefijos de códigos
    public static final String PREFIX_PRODUCTO = "PROD-";
    public static final String PREFIX_BARCODE_BASE = "200000000000";

    // Longitudes
    public static final int CODIGO_PRODUCTO_DIGITS = 4;
    public static final int BARCODE_DIGITS = 12;
}
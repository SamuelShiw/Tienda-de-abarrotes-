package com.tienda.abarrotes.utils;

import com.tienda.abarrotes.model.Trabajador;

import java.util.Locale;

public final class CodeGenerator {

    private CodeGenerator() {
        // Evita instanciación
    }

    public static String generateProductCode(int idProducto) {
        return String.format(
                Locale.getDefault(),
                "%s%0" + AppConstants.CODIGO_PRODUCTO_DIGITS + "d",
                AppConstants.PREFIX_PRODUCTO,
                idProducto
        );
    }

    /**
     * En esta versión el código de barras usa el mismo valor visible del código del producto.
     * Ejemplo: PROD-0001
     */
    public static String generateBarcode(long correlativo) {
        return generateProductCode((int) correlativo);
    }

    public static String generateBarcodeFromProductId(int idProducto) {
        return generateProductCode(idProducto);
    }

    public static String generateWorkerQrContent(Trabajador trabajador) {
        if (trabajador == null) {
            return "";
        }

        String nombreCompleto = safe(trabajador.getNombres()) + " " + safe(trabajador.getApellidos());

        return "DNI:" + safe(trabajador.getDni())
                + "|NOMBRE:" + nombreCompleto.trim()
                + "|ROL:" + safe(trabajador.getRol());
    }

    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }
}
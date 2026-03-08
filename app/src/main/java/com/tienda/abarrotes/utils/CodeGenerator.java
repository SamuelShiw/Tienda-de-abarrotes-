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

    public static String generateBarcode(long correlativo) {
        return String.format(Locale.getDefault(), "%012d", correlativo);
    }

    public static String generateBarcodeFromProductId(int idProducto) {
        long base = 200000000000L;
        return String.format(Locale.getDefault(), "%012d", base + idProducto);
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
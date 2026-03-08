package com.tienda.abarrotes.ui.common;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BarcodeGenerator {

    private BarcodeGenerator() {
    }

    public static Bitmap generateSimpleBarcode(String code) {
        int width = 900;
        int height = 300;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // fondo blanco
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bitmap.setPixel(x, y, Color.WHITE);
            }
        }

        if (code == null || code.trim().isEmpty()) {
            return bitmap;
        }

        int x = 40;
        int top = 20;
        int bottom = 240;

        for (int i = 0; i < code.length(); i++) {
            int digit = Character.isDigit(code.charAt(i)) ? Character.getNumericValue(code.charAt(i)) : 1;
            int bars = 2 + (digit % 4);
            int spaces = 1 + (digit % 3);

            for (int b = 0; b < bars; b++) {
                drawVerticalBar(bitmap, x, top, bottom, 4);
                x += 6;
            }
            x += (spaces * 4);
        }

        return bitmap;
    }

    private static void drawVerticalBar(Bitmap bitmap, int x, int top, int bottom, int width) {
        for (int i = x; i < x + width; i++) {
            for (int y = top; y < bottom; y++) {
                if (i >= 0 && y >= 0 && i < bitmap.getWidth() && y < bitmap.getHeight()) {
                    bitmap.setPixel(i, y, Color.BLACK);
                }
            }
        }
    }
}
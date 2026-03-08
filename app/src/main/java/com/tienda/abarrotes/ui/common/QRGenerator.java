package com.tienda.abarrotes.ui.common;

import android.graphics.Bitmap;
import android.graphics.Color;

public class QRGenerator {

    private QRGenerator() {
    }

    public static Bitmap generateSimpleQR(String content) {
        int size = 700;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

        // Fondo blanco
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                bitmap.setPixel(x, y, Color.WHITE);
            }
        }

        if (content == null || content.trim().isEmpty()) {
            return bitmap;
        }

        // Generador visual simple tipo patrón, no estándar real QR.
        // Sirve como visual académico sin meter dependencia externa.
        int hash = content.hashCode();
        int block = 20;
        int margin = 60;

        for (int y = margin; y < size - margin; y += block) {
            for (int x = margin; x < size - margin; x += block) {
                int value = (x * 31 + y * 17 + hash);
                boolean paint = (value & 1) == 0;

                if (paint) {
                    for (int dy = 0; dy < block; dy++) {
                        for (int dx = 0; dx < block; dx++) {
                            if (x + dx < size && y + dy < size) {
                                bitmap.setPixel(x + dx, y + dy, Color.BLACK);
                            }
                        }
                    }
                }
            }
        }

        // Tres esquinas tipo finder pattern visual
        drawFinder(bitmap, 60, 60, 120);
        drawFinder(bitmap, size - 180, 60, 120);
        drawFinder(bitmap, 60, size - 180, 120);

        return bitmap;
    }

    private static void drawFinder(Bitmap bitmap, int startX, int startY, int boxSize) {
        fillRect(bitmap, startX, startY, boxSize, boxSize, Color.BLACK);
        fillRect(bitmap, startX + 15, startY + 15, boxSize - 30, boxSize - 30, Color.WHITE);
        fillRect(bitmap, startX + 35, startY + 35, boxSize - 70, boxSize - 70, Color.BLACK);
    }

    private static void fillRect(Bitmap bitmap, int x, int y, int w, int h, int color) {
        for (int j = y; j < y + h; j++) {
            for (int i = x; i < x + w; i++) {
                if (i >= 0 && j >= 0 && i < bitmap.getWidth() && j < bitmap.getHeight()) {
                    bitmap.setPixel(i, j, color);
                }
            }
        }
    }
}
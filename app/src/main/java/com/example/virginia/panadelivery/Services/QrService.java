package com.example.virginia.panadelivery.Services;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QrService {

    public static Bitmap generarQr(String dato, int black, int white) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    dato,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    200, 200, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();



        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        int bgColor = black;
        int color = white;

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        bgColor:color;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 200, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}

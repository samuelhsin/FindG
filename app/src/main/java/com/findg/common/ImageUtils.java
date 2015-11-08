package com.findg.common;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by samuelhsin on 2015/10/30.
 */
public abstract class ImageUtils {

    private static final String TAG = ImageUtils.class.getSimpleName();

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static Bitmap getImageFromResource(Resources res, int resourceId) {
        return BitmapFactory.decodeResource(res, resourceId);
    }

    public static Bitmap decodeBytes(byte[] imageBytes) {
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public static byte[] encodeBytes(Bitmap image) {
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, blob);
        return blob.toByteArray();
    }

    public static double getKBSize(byte[] imageBytes) {
        return imageBytes.length / 1024.0;
    }

}

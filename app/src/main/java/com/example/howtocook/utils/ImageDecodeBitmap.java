package com.example.howtocook.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageDecodeBitmap {


    public static Bitmap decodeFromBase64(String image){
        byte[] decodeByteArray = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodeByteArray, 0 , decodeByteArray.length);
    }

    public static String decodeToBase64(Bitmap image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, os);
        String imageEncoded = Base64.encodeToString(os.toByteArray(),Base64.DEFAULT);
        return imageEncoded;
    }

}

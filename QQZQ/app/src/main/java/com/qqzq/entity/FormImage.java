package com.qqzq.entity;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by jie.xiao on 2015/3/2.
 */
public class FormImage {

    private String mName ;

    private String mFileName ;

    private String mValue ;

    private String mMime ;

    private Bitmap mBitmap ;

    public FormImage(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public String getName() {
//        return mName;
        return "file" ;
    }

    public String getFileName() {
        return "logo.png";
    }

    public byte[] getValue() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        mBitmap.compress(Bitmap.CompressFormat.JPEG,80,bos) ;
        return bos.toByteArray();
    }

    public String getMime() {
        return "image/png";
    }
}

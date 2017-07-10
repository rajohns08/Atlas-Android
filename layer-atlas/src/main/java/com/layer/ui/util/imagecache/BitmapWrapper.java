package com.layer.ui.util.imagecache;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.ref.WeakReference;

public class BitmapWrapper {
    private Bitmap mBitmap;
    private String mUrl;
    private WeakReference<View> mViewWeakReference;

    public BitmapWrapper(@NonNull String url, @NonNull View view) {
        mUrl = url;
        mViewWeakReference = new WeakReference<>(view);
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public WeakReference<View> getViewWeakReference() {
        return mViewWeakReference;
    }

    public BitmapWrapper setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        return this;
    }
}

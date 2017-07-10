package com.layer.ui.util.imagecache;

import android.widget.ImageView;

public interface ImageCacheWrapper {

    void load(String targetUrl, String tag, int width, int height, ImageView imageView, Object... args);

    void fetchBitmap(int width, int height, BitmapWrapper bitmapWrapper, Object... args);

    void cancelRequest(ImageView imageView);

    void cancelRequest(Object tag);
}

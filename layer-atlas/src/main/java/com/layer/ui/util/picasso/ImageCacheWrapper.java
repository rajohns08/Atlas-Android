package com.layer.ui.util.picasso;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface ImageCacheWrapper {
    void load(String targetUrl, String tag, int width, int height, ImageView imageView, Object... args);

    void fetchBitmap(String url, String tag, int width, int height, Callback callback, Object... args);

    interface Callback {
        void onSuccess(Bitmap bitmap);

        void onFailure();
    }

    void cancelRequest(ImageView imageView);
    void cancelRequest(String tag);

}

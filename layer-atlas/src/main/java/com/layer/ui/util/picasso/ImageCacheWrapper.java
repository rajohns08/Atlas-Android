package com.layer.ui.util.picasso;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.layer.ui.avatar.AvatarView;

public interface ImageCacheWrapper {

    void cancelRequest(ImageView imageView);

    void load(String targetUrl, String tag, int width, int height, ImageView imageView, Object... args);

    void fetchBitmap(String url, String tag, int width, int height, AvatarView.BitmapWrapper bitmapWrapper, Callback callback, Object... args);

    interface Callback {
        void onSuccess(AvatarView.BitmapWrapper bitmap);
        void onFailure();
    }

}

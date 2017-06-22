package com.layer.ui.util.picasso;

import android.widget.ImageView;

public interface ImageCacheWrapper {

    void cancelRequest(ImageView imageView);

    void load(String targetUrl, String tag, Object placeHolder, Object fade, int size,
            int size1, ImageView imageView, Object... args);

}

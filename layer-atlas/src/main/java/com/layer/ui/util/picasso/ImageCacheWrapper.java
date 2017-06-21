package com.layer.ui.util.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

public interface ImageCacheWrapper {

    void cancelRequest(Target target);

    void load(String targetUrl, String tag, Object placeHolder, Object fade, int size, int size1,
            boolean flag, ImageTarget imageTarget);

    interface ImageTransform {
        Transformation getTransformation(boolean flag);
    }

    interface ImageTarget  extends Target{

    }
}

package com.layer.ui.util;

import static com.layer.ui.util.Log.TAG;

import android.widget.ImageView;

import com.layer.ui.util.picasso.ImageCacheWrapper;
import com.layer.ui.util.picasso.transformations.CircleTransform;
import com.squareup.picasso.Transformation;

public class ImageTransformImpl {
    private final static CircleTransform SINGLE_TRANSFORM = new CircleTransform(TAG + ".single");
    private final static CircleTransform MULTI_TRANSFORM = new CircleTransform(TAG + ".multi");
}

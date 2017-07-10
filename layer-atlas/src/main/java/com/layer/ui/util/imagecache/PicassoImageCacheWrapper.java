package com.layer.ui.util.imagecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.layer.ui.util.imagecache.requesthandlers.MessagePartRequestHandler;
import com.layer.ui.util.imagecache.transformations.CircleTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import static com.layer.ui.util.Log.TAG;

public class PicassoImageCacheWrapper implements ImageCacheWrapper {
    protected final static CircleTransform SINGLE_TRANSFORM = new CircleTransform(TAG + ".single");
    protected final static CircleTransform MULTI_TRANSFORM = new CircleTransform(TAG + ".multi");
    protected final Picasso mPicasso;

    public PicassoImageCacheWrapper(MessagePartRequestHandler messagePartRequestHandler, Context context) {
        mPicasso = new Picasso.Builder(context)
                .loggingEnabled(true)
                .addRequestHandler(messagePartRequestHandler)
                .build();
    }

    @Override
    public void load(String targetUrl, String tag, int size, int size1, ImageView imageView, Object... args) {
        boolean isMultiTransform = false;
        if (args != null && args.length > 0 && (args[0] instanceof Boolean)) {
            isMultiTransform = (boolean) args[0];
        }

        RequestCreator creator = mPicasso.load(targetUrl)
                .tag(tag)
                .noPlaceholder()
                .noFade()
                .centerCrop()
                .resize(size, size);

        creator.transform(isMultiTransform ? MULTI_TRANSFORM : SINGLE_TRANSFORM)
                .into(imageView);
    }

    @Override
    public void fetchBitmap(int width, int height, final BitmapWrapper bitmapWrapper, Object... args) {

        boolean isMultiTransform = false;
        //Boolean is passed in to toggle between Picasso Single Transform and multi transform
        if ((args != null && args.length > 0) && (args[0] instanceof Boolean)) {
            isMultiTransform = (boolean) args[0];
        }

        Target target = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                bitmapWrapper.setBitmap(bitmap);
                View view = bitmapWrapper.getViewWeakReference().get();
                if (view != null) {
                    view.invalidate();
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                bitmapWrapper.setBitmap(null);
                View view = bitmapWrapper.getViewWeakReference().get();
                if (view != null) {
                    view.invalidate();
                }
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}
        };

        RequestCreator creator = mPicasso.load(bitmapWrapper.getUrl())
                .tag(target)
                .noPlaceholder()
                .noFade()
                .centerCrop()
                .resize(width, height);

        creator.transform(isMultiTransform ? MULTI_TRANSFORM : SINGLE_TRANSFORM)
                .into(target);

    }

    @Override
    public void cancelRequest(ImageView imageView) {
        if (imageView != null) {
            mPicasso.cancelRequest(imageView);
        }
    }

    @Override
    public void cancelRequest(Object tag) {
        if (tag != null) {
            mPicasso.cancelTag(tag);
        }
    }
}
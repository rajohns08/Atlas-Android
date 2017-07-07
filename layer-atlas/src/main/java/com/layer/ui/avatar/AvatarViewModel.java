package com.layer.ui.avatar;

import android.graphics.Bitmap;
import android.os.Handler;

import com.layer.sdk.messaging.Identity;
import com.layer.ui.util.imagecache.BitmapWrapper;
import com.layer.ui.util.imagecache.ImageCacheWrapper;

import java.lang.ref.WeakReference;

public class AvatarViewModel implements Avatar.ViewModel  {

    private IdentityNameFormatter mIdentityNameFormatter;
    private WeakReference<Handler> mHandlerWeakReference;
    private WeakReference<Avatar.View> mViewWeakReference;
    private ImageCacheWrapper mImageCacheWrapper;

    public AvatarViewModel(ImageCacheWrapper imageCacheWrapper) {
        mImageCacheWrapper = imageCacheWrapper;
    }

    public void setIdentityNameFormatter(IdentityNameFormatter identityNameFormatter) {
        mIdentityNameFormatter = identityNameFormatter;
    }

    public String getInitialsForAvatarView(Identity added) {
        return mIdentityNameFormatter.getInitials(added);
    }

    @Override
    public IdentityNameFormatter getIdentityNameFormatter() {
        return mIdentityNameFormatter;
    }

    @Override
    public void loadImage(String url, String tag, int width, int height, final BitmapWrapper bitmapWrapper, Object... args) {

        mImageCacheWrapper.fetchBitmap(url, tag, width, height,
                new ImageCacheWrapper.Callback() {
                    @Override
                    public void onSuccess(final Bitmap bitmap) {
                        updateView(bitmapWrapper, bitmap, false);
                    }

                    @Override
                    public void onFailure() {
                        updateView(bitmapWrapper, null, false);
                    }

                    @Override
                    public void onPrepareLoad() {
                        updateView(bitmapWrapper, null, false);
                    }
                }, args);
    }

    private void updateView(final BitmapWrapper bitmapWrapper,final Bitmap bitmap, final boolean isSetClusterSize) {
        final Avatar.View view = mViewWeakReference != null ? mViewWeakReference.get() : null;
        final Handler handler = mViewWeakReference != null ? mHandlerWeakReference.get() : null;

        if (view != null && handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (isSetClusterSize) {
                        view.revalidateView();
                    } else {
                        bitmapWrapper.setBitmap(bitmap);
                    }
                    view.revalidateView();
                }
            });
        }
    }

    @Override
    public void setViewAndHandler(WeakReference<Avatar.View> avatar, WeakReference<Handler> handler) {
        mHandlerWeakReference = handler;
        mViewWeakReference = avatar;
    }

    @Override
    public ImageCacheWrapper getImageCacheWrapper() {
        return mImageCacheWrapper;
    }

    @Override
    public void cancelImage(String url) {
        mImageCacheWrapper.cancelRequest(url);
    }
}

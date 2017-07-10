package com.layer.ui.avatar;

import com.layer.sdk.messaging.Identity;
import com.layer.ui.util.imagecache.BitmapWrapper;
import com.layer.ui.util.imagecache.ImageCacheWrapper;


public class AvatarViewModel implements Avatar.ViewModel  {

    private IdentityNameFormatter mIdentityNameFormatter;
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

        mImageCacheWrapper.fetchBitmap(width, height, bitmapWrapper, args);
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

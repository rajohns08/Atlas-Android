package com.layer.ui.avatar;

import com.layer.sdk.messaging.Identity;
import com.layer.ui.util.imagecache.BitmapWrapper;
import com.layer.ui.util.imagecache.ImageCacheWrapper;

/**
 * Avatar interface exposes the interaction between the AvatarView and AvatarViewModel Avatar.
 * @see Avatar.ViewModel is implemented by
 * @see AvatarViewModel and
 * @see AvatarView
 **/

public interface Avatar {

    interface ViewModel {

         //loadImage Work with Image Caching Library to provide Bitmap to the View
        void loadImage(String url, String tag, int width, int height, BitmapWrapper bitmapWrapper, Object... args);

        void cancelImage(String url);

        //Set custom AvatarInitial on the ViewModel to allow client plug in their custom Initials
        void setIdentityNameFormatter(IdentityNameFormatter identityNameFormatter);

        IdentityNameFormatter getIdentityNameFormatter();

        ImageCacheWrapper getImageCacheWrapper();

        String getInitialsForAvatarView(Identity added);
    }

}

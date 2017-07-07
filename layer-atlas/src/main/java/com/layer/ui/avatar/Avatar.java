package com.layer.ui.avatar;

import android.os.Handler;
import android.view.View;

import com.layer.sdk.messaging.Identity;
import com.layer.ui.util.imagecache.BitmapWrapper;
import com.layer.ui.util.imagecache.ImageCacheWrapper;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Avatar interface exposes the interaction between the AvatarView and AvatarViewModel Avatar.
 * @see Avatar.ViewModel is implemented by
 * @see AvatarViewModel and
 * @see Avatar.View is implemented by
 * @see AvatarView
 **/

public interface Avatar {

    interface ViewModel {

         //loadImage Work with Image Caching Library to provide Bitmap to the View
        void loadImage(String url, String tag, int width, int height, BitmapWrapper bitmapWrapper, Object... args);

        void cancelImage(String url);

        //Set the view on the ViewModel
        void setViewAndHandler(WeakReference<View> avatar, WeakReference<Handler> handler);

        //Set custom AvatarInitial on the ViewModel to allow client plug in their custom Initials
        void setIdentityNameFormatter(IdentityNameFormatter identityNameFormatter);

        IdentityNameFormatter getIdentityNameFormatter();

        ImageCacheWrapper getImageCacheWrapper();

        String getInitialsForAvatarView(Identity added);
    }

}

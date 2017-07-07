package com.layer.ui.avatar;

import android.os.Handler;

import com.layer.sdk.messaging.Identity;
import com.layer.ui.util.imagecache.BitmapWrapper;

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

        //Set the participants in the ViewModel and then call update on the ViewModel
        void setParticipants(Identity[] participants);

        void setParticipants(Set<Identity> participants);

        Set<Identity> getParticipants();

        Map<Identity, String> getIdentityInitials();

        //BitmapWrapper is an interface that wraps the bitmap
        BitmapWrapper getBitmapWrapper(Identity key);

         //loadImage Work with Image Caching Library to provide Bitmap to the View
        void loadImage(String url, String tag, int width, int height, BitmapWrapper bitmapWrapper, Object... args);

        void cancelImage(String url);

        //Set the view on the ViewModel
        void setViewAndHandler(WeakReference<Avatar.View> avatar, WeakReference<Handler> handler);

        //Set custom AvatarInitial on the ViewModel to allow client plug in their custom Initials
        void setAvatarInitials(AvatarInitials avatarInitials);

        void setMaximumAvatar(int maximumAvatar);

        List<BitmapWrapper> getBitmapWrappers();
    }

    interface View {

        boolean setClusterSizes(Map<Identity, String> initials, List<BitmapWrapper> pendingLoads);

        void revalidateView();
    }
}

package com.layer.ui.avatar;

import android.graphics.Canvas;

import com.layer.sdk.messaging.Identity;
import com.layer.sdk.messaging.Presence;
import com.layer.ui.util.picasso.ImageCacheWrapper;

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

        void update();

        void setParticipants(Identity[] participants);

        void setParticipants(Set<Identity> participants);

        Set<Identity> getParticipants();

        int getInitialSize();

        Set<Map.Entry<Identity, String>> getEntrySet();

        UiImageTarget getImageTarget(Identity key);

        void setClusterSizes();

        void loadImage(String targetUrl, String tag, Object placeHolder, Object fade, int size, int size1,
                boolean flag, ImageCacheWrapper.ImageTarget imageTarget);

        void setView(Avatar.View avatar);

        void checkPresence(Presence.PresenceStatus currentStatus, Canvas canvas);
    }

    interface View {

        Avatar.View getAvatar();

        boolean setClusterSizes(Map<Identity, String> initials, List<UiImageTarget> pendingLoads);

        void revalidateView();

        Avatar.View setParticipants(Identity... participants);

        Avatar.View setParticipants(Set<Identity> participants);

        String getInitials(Identity added);

        void drawAvailable(Canvas canvas);

        void drawAway(Canvas canvas);

        void drawOffline(Canvas canvas);

        void drawInvisible(Canvas canvas);

        void drawBusy(Canvas canvas);

        void drawDefault(Canvas canvas);
    }
}

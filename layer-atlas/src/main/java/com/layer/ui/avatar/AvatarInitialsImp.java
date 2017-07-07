package com.layer.ui.avatar;

import com.layer.sdk.messaging.Identity;
import com.layer.ui.util.Util;

public class AvatarInitialsImp implements AvatarInitials {
    @Override
    public String getInitials(Identity identity) {
        return Util.getInitials(identity);
    }
}

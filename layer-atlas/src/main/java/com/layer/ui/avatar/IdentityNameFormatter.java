package com.layer.ui.avatar;

import com.layer.sdk.messaging.Identity;

public interface IdentityNameFormatter {
    String getInitials(Identity identity);
}

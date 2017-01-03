package com.layer.atlas.messagetypes;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by archit on 12/14/16.
 */

public abstract class AttachmentPicker {
    public abstract void bind(ViewGroup root, Callback callback);
    public abstract String[] getRequiredPermissions();

    public static interface Callback {
        public void onSuccess();
        public void onFailure(String error);
    }
}

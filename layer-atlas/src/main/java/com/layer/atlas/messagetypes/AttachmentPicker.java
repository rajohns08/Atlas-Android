package com.layer.atlas.messagetypes;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by archit on 12/14/16.
 */

public interface AttachmentPicker {
    public void bind(ViewGroup root, Callback callback);
    public boolean hasRequiredPermissions();
    public void requestPermissions(Callback callback);

    public static interface Callback {
        public void onSuccess();
        public void onFailure(String error);
    }
}

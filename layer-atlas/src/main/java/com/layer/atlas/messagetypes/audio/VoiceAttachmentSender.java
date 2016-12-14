package com.layer.atlas.messagetypes.audio;

import android.app.Activity;
import android.media.MediaRecorder;

import com.layer.atlas.messagetypes.AttachmentPicker;
import com.layer.atlas.messagetypes.AttachmentSender;
import com.layer.atlas.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by archit on 12/2/16.
 */

public class VoiceAttachmentSender extends AttachmentSender {
    private String mOutputFilePath;

    private WeakReference<Activity> mActivity = new WeakReference<Activity>(null);

    public VoiceAttachmentSender(String title, Integer icon, Activity activity) {
        super(title, icon);
        mActivity = new WeakReference<Activity>(activity);
    }

    public VoiceAttachmentSender(int titleResId, Integer iconResId, Activity activity) {
        this(activity.getString(titleResId), iconResId, activity);
    }

    @Override
    public boolean requestSend() {
        Log.d("Handle message sending");
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public AttachmentPicker getAttachementPicker() {
        return new VoiceAttachementRecorder(mOutputFilePath);
    }
}

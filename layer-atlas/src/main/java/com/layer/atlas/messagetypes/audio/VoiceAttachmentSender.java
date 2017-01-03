package com.layer.atlas.messagetypes.audio;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.layer.atlas.AttachmentSenderWithPicker;
import com.layer.atlas.messagetypes.AttachmentPicker;
import com.layer.atlas.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by archit on 12/2/16.
 */

public class VoiceAttachmentSender extends AttachmentSenderWithPicker<VoiceAttachmentRecorder> {
    private static final int PERMISSION_REQUEST_CODE = 100;

    private String mOutputFilePath;
    private WeakReference<Activity> mActivity = new WeakReference<Activity>(null);
    private VoiceAttachmentRecorder mRecorder;

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
        if (requestCode != PERMISSION_REQUEST_CODE) return;
        if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            if (Log.isLoggable(Log.VERBOSE)) Log.v("VoiceRecorder permission denied");
            return;
        }
        Activity activity = mActivity.get();
        if (activity == null) return;
    }

    @Override
    protected boolean hasPermissions(@NonNull Activity activity, String... permissions) {
        return super.hasPermissions(activity, getAttachmentPicker().getRequiredPermissions());
    }

    @Override
    protected void requestPermissions(@NonNull Activity activity, int permissionsCode, String... permissions) {
        super.requestPermissions(activity, PERMISSION_REQUEST_CODE, getAttachmentPicker().getRequiredPermissions());
    }

    @Override
    public VoiceAttachmentRecorder getAttachmentPicker() {
        if (mRecorder == null) {
            Activity activity = mActivity.get();
            if (activity != null) {
                mRecorder = new VoiceAttachmentRecorder(mOutputFilePath, activity);
            } else {
                throw new NullPointerException("Activity not around for creating picker");
            }
        }

        return mRecorder;
    }
}

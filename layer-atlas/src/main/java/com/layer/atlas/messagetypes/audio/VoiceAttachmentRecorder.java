package com.layer.atlas.messagetypes.audio;

import android.Manifest;
import android.content.Context;
import android.media.MediaRecorder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.layer.atlas.R;
import com.layer.atlas.messagetypes.AttachmentPicker;

import java.io.IOException;

/**
 * Created by archit on 12/14/16.
 */

public class VoiceAttachmentRecorder extends AttachmentPicker {
    private static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;

    private MediaRecorder mMediaRecorder;
    private String mOutputFilePath;
    private Callback mCallback;
    private Context mContext;

    public VoiceAttachmentRecorder(String mOutputFilePath, Context context) {
        this.mOutputFilePath = mOutputFilePath;
        this.mContext = context;
    }

    public void startRecording() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFile(mOutputFilePath);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            if (mCallback != null) {
                mCallback.onFailure(e.getMessage());
                mCallback = null;
            }
        }

        mMediaRecorder.start();
    }

    public void stopRecording() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
        if (mCallback != null) {
            mCallback.onSuccess();
            mCallback = null;
        }
    }

    @Override
    public String[] getRequiredPermissions() {
        return new String[]{PERMISSION_RECORD_AUDIO};
    }

    @Override
    public void bind(ViewGroup root, final Callback callback) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.atlas_voice_message_attachement_recorder, root, false);
        root.addView(view);
        mCallback = callback;

        ImageView micButton = (ImageView) view.findViewById(R.id.record_button);
        micButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startRecording();
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    stopRecording();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}

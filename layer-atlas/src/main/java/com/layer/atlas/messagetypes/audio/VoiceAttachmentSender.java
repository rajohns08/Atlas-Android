package com.layer.atlas.messagetypes.audio;

import android.media.MediaRecorder;

import com.layer.atlas.messagetypes.AttachmentSender;

import java.io.IOException;

/**
 * Created by archit on 12/2/16.
 */

public class VoiceAttachmentSender extends AttachmentSender {
    private MediaRecorder mMediaRecorder;
    private String mOutputFilePath;

    public VoiceAttachmentSender(String title, Integer icon) {
        super(title, icon);
    }

    @Override
    public boolean requestSend() {
        return false;
    }

    public void startRecording() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFile(mOutputFilePath);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mMediaRecorder.prepare();
        }
        catch (IOException e) {
            // TODO --> what?
        }

        mMediaRecorder.start();
    }

    public void stopRecording() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    public VoiceAttachmentSender setOutputFileName(String mOutputFileName) {
        this.mOutputFilePath = mOutputFileName;
        return this;
    }


}

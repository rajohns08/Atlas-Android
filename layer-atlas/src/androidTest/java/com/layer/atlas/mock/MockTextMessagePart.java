package com.layer.atlas.mock;

import android.net.Uri;

import com.layer.sdk.listeners.LayerProgressListener;
import com.layer.sdk.messaging.Message;
import com.layer.sdk.messaging.MessagePart;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by archit on 5/22/17.
 */

public class MockTextMessagePart extends MessagePart {

    private final static String MIME_TYPE = "text/plain";
    private String mContent;

    public MockTextMessagePart(String content) {
        mContent = content;
    }

    @Override
    public Uri getId() {
        return Uri.parse("some uri value");
    }

    @Override
    public Message getMessage() {
        return null;
    }

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }

    @Override
    public byte[] getData() {
        return "MockTextMessagePart".getBytes();
    }

    @Override
    public InputStream getDataStream() {
        return new ByteArrayInputStream(getData());
    }

    @Override
    public long getSize() {
        return mContent.length();
    }

    @Override
    public TransferStatus getTransferStatus() {
        return TransferStatus.COMPLETE;
    }

    @Override
    public void download(LayerProgressListener layerProgressListener) {

    }

    @Override
    public boolean isContentReady() {
        return true;
    }

    @Override
    public void deleteLocalContent() {

    }
}

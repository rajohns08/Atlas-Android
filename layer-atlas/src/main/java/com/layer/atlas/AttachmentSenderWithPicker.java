package com.layer.atlas;

import com.layer.atlas.messagetypes.AttachmentPicker;
import com.layer.atlas.messagetypes.AttachmentSender;

/**
 * Created by archit on 1/3/17.
 */

public abstract class AttachmentSenderWithPicker<T extends AttachmentPicker> extends AttachmentSender {
    public AttachmentSenderWithPicker(String title, Integer icon) {
        super(title, icon);
    }

    public abstract T getAttachmentPicker();
}

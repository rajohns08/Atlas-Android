package com.layer.atlas.test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.layer.atlas.messagetypes.generic.GenericCellFactory;
import com.layer.atlas.mock.MockLayerClient;
import com.layer.atlas.mock.MockMessageImpl;
import com.layer.atlas.mock.MockMessagePart;
import com.layer.sdk.LayerClient;
import com.layer.sdk.messaging.Message;
import com.layer.sdk.messaging.MessagePart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by archit on 5/16/17.
 */

@RunWith(AndroidJUnit4.class)
public class GenericCellFactoryTest {
    private final static String MIME_TYPE = "text/plain";

    private List<MessagePart> mTestMessageParts;
    private MessagePart mTextMessagePart;
    private Message mMessage;
    private LayerClient mLayerClient;

    @Before
    public void setup() {
        mTestMessageParts = new ArrayList<>();
        mTextMessagePart = new MockMessagePart("Generic message".getBytes(), MIME_TYPE);
        mTestMessageParts.add(mTextMessagePart);
        mMessage = new MockMessageImpl(mTestMessageParts);
        mLayerClient = new MockLayerClient();
    }

    @Test
    public void testParseContent() {
        GenericCellFactory genericCellFactory = new GenericCellFactory();
        GenericCellFactory.ParsedContent parsedContent = genericCellFactory.parseContent(mLayerClient, mMessage);
        assertTrue(!parsedContent.getString().isEmpty());
    }

    @Test
    public void testIsType() {
        GenericCellFactory genericCellFactory = new GenericCellFactory();
        assertTrue(genericCellFactory.isType(mMessage));
    }

    @Test
    public void testIsBindable() {
        GenericCellFactory genericCellFactory = new GenericCellFactory();
        assertTrue(genericCellFactory.isBindable(mMessage));
    }

    @Test
    public void testGetPreviewText() {
        GenericCellFactory genericCellFactory = new GenericCellFactory();
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertTrue(!genericCellFactory.getPreviewText(context, mMessage).isEmpty());
    }
}

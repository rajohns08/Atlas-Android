package com.layer.atlas.test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.layer.atlas.messagetypes.generic.GenericCellFactory;
import com.layer.atlas.mock.MockLayerClient;
import com.layer.atlas.mock.MockMessageImpl;
import com.layer.atlas.mock.MockTextMessagePart;
import com.layer.sdk.LayerClient;
import com.layer.sdk.messaging.Message;
import com.layer.sdk.messaging.MessagePart;

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

    private static List<MessagePart> sTestMessageParts;
    private static MessagePart sTextMessagePart;
    private static Message sMessage;
    private static LayerClient sLayerClient;

    static {
        sTestMessageParts = new ArrayList<>();
        sTextMessagePart = new MockTextMessagePart("Mock message");
        sTestMessageParts.add(sTextMessagePart);
        sMessage = new MockMessageImpl(sTestMessageParts);
        sLayerClient = new MockLayerClient();
    }

    @Test
    public void testParseContent() {
        GenericCellFactory genericCellFactory = new GenericCellFactory();
        GenericCellFactory.ParsedContent parsedContent = genericCellFactory.parseContent(sLayerClient, sMessage);
        assertTrue(!parsedContent.getString().isEmpty());
    }

    @Test
    public void testIsType() {
        GenericCellFactory genericCellFactory = new GenericCellFactory();
        assertTrue(genericCellFactory.isType(sMessage));
    }

    @Test
    public void testIsBindable() {
        GenericCellFactory genericCellFactory = new GenericCellFactory();
        assertTrue(genericCellFactory.isBindable(sMessage));
    }

    @Test
    public void testGetPreviewText() {
        GenericCellFactory genericCellFactory = new GenericCellFactory();
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertTrue(!genericCellFactory.getPreviewText(context, sMessage).isEmpty());
    }
}

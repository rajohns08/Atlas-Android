package com.layer.ui.avatar;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import android.graphics.Canvas;
import android.os.Handler;

import com.layer.sdk.messaging.Identity;
import com.layer.ui.util.imagecache.ImageCacheWrapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AvatarViewViewModelTest {
    @Mock
    Identity mMockIdentity, mMockIdentity2;

    @Mock
    Avatar.View mMockView;

    @Mock
    ImageCacheWrapper mMockImageCacheWrapper;

    @Mock
    Canvas mMockCanvas;

    AvatarViewModel mAvatarViewModel;

    @Mock
    Handler mMockHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mAvatarViewModel = new AvatarViewModel(mMockImageCacheWrapper);
        mAvatarViewModel.setViewAndHandler(mMockView, mMockHandler);
    }

    @Test
    public void testIfIdentityIsSet() {
        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);

        mAvatarViewModel.update();
        //verify(mMockView).setClusterSizes(ArgumentMatchers.<Identity, String>anyMap(), ArgumentMatchers.<BitmapWrapper>anyList());
        //verify(mMockView).revalidateView();
    }
}
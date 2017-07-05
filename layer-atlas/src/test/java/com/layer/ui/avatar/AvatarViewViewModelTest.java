package com.layer.ui.avatar;

import static org.mockito.Mockito.verify;

import android.graphics.Canvas;

import com.layer.sdk.messaging.Identity;
import com.layer.ui.util.imagecache.BitmapWrapper;
import com.layer.ui.util.imagecache.ImageCacheWrapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mAvatarViewModel = new AvatarViewModel(mMockImageCacheWrapper);
        mAvatarViewModel.setView(mMockView);
    }

    @Test
    public void testIfIdentityIsSet() {
        mAvatarViewModel.update();
        verify(mMockView).setClusterSizes(ArgumentMatchers.<Identity, String>anyMap(), ArgumentMatchers.<BitmapWrapper>anyList());
        verify(mMockView).revalidateView();
    }
}
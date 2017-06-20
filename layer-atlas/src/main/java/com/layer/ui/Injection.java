package com.layer.ui;

import static com.layer.ui.util.Log.ERROR;

import android.content.Context;

import com.google.android.gms.tasks.RuntimeExecutionException;
import com.layer.sdk.LayerClient;
import com.layer.ui.avatar.Avatar;
import com.layer.ui.avatar.AvatarViewModel;
import com.layer.ui.util.ImageTransformImpl;
import com.layer.ui.util.Log;
import com.layer.ui.util.picasso.ImageCacheWrapper;
import com.layer.ui.util.picasso.ImageCacheWrapperImpl;

import java.lang.ref.WeakReference;

public class Injection {
    private static ImageCacheWrapper sImageCacheWrapper;
    private static WeakReference<LayerClient> sLayerClient;

    public static Avatar.ViewModel provideAvatarViewModel(Context context) {
        return sImageCacheWrapper != null ? new AvatarViewModel(sImageCacheWrapper)
                : new AvatarViewModel(provideImageCachingLibrary(context));
    }

    public static ImageCacheWrapper provideImageCachingLibrary(Context context) {
        if (sImageCacheWrapper == null) {
            if (sLayerClient == null || sLayerClient.get() == null) {
                if (Log.isLoggable(ERROR)) {
                    Log.e("Context or Layer Client is not set");
                }
                throw new RuntimeExecutionException(new Throwable("Context or Layer Client is not set"));
            }
            sImageCacheWrapper = new ImageCacheWrapperImpl(context, sLayerClient.get(), provideImageTransform());
        }
        return sImageCacheWrapper;
    }

    public static void setLayerClient(LayerClient layerClient) {
        sLayerClient = new WeakReference<>(layerClient);
    }

    public static ImageCacheWrapper.ImageTransform provideImageTransform() {
        return new ImageTransformImpl();
    }
}

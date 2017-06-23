package com.layer.ui;


import android.content.Context;

import com.layer.sdk.LayerClient;
import com.layer.ui.avatar.Avatar;
import com.layer.ui.avatar.AvatarViewModel;
import com.layer.ui.util.picasso.ImageCacheWrapper;
import com.layer.ui.util.picasso.PicassoImageCacheWrapper;

import java.lang.ref.WeakReference;

public class Injection {
    private static ImageCacheWrapper sImageCacheWrapper;
    private static WeakReference<LayerClient> sLayerClient;

    /*public static void init(Context context, LayerClient layerClient) {
        sLayerClient = new WeakReference<>(layerClient);
    }

    public static Avatar.ViewModel provideAvatarViewModel(Context context) {
        return sImageCacheWrapper != null ? new AvatarViewModel(sImageCacheWrapper)
                : new AvatarViewModel(new PicassoImageCacheWrapper(sLayerClient.get(), context));
    }*/


   /* public static ImageCacheWrapper provideImageCacheWrapper() {
        new PicassoImageCacheWrapper(sLayerClient,)
    }*/
}

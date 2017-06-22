package com.layer.ui.util.picasso;

import static com.layer.ui.util.Log.TAG;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.layer.sdk.LayerClient;
import com.layer.ui.avatar.AvatarView;
import com.layer.ui.util.picasso.requesthandlers.MessagePartRequestHandler;
import com.layer.ui.util.picasso.transformations.CircleTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

public class PicassoImageCacheWrapper implements ImageCacheWrapper {
    protected final static CircleTransform SINGLE_TRANSFORM = new CircleTransform(TAG + ".single");
    protected final static CircleTransform MULTI_TRANSFORM = new CircleTransform(TAG + ".multi");
    protected final Picasso mPicasso;
    protected MessagePartRequestHandler mMessagePartRequestHandler;

    public PicassoImageCacheWrapper(LayerClient layerClient, Context context) {
        mMessagePartRequestHandler = new MessagePartRequestHandler(layerClient);
        mPicasso = new Picasso.Builder(context)
                .addRequestHandler(mMessagePartRequestHandler)
                .build();
    }

    @Override
    public void load(String targetUrl, String tag, Object placeHolder, Object fade, int size,
            int size1, ImageView imageView, Object... args) {
        boolean isMultiTransform = false;
        if (args != null && args.length > 0) {
            isMultiTransform = (boolean) args[0];
        }

        RequestCreator creator = mPicasso.load(targetUrl)
                .tag(AvatarView.TAG)
                .noPlaceholder()
                .noFade()
                .centerCrop()
                .resize(size, size);

        creator.transform(isMultiTransform ? MULTI_TRANSFORM : SINGLE_TRANSFORM)
                .into(imageView);
    }

    @Override
    public void cancelRequest(ImageView imageView) {
        Picasso.with(imageView.getContext()).cancelRequest(imageView);
    }
}

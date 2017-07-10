package com.layer.ui.avatar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.layer.sdk.messaging.Identity;
import com.layer.sdk.messaging.Presence;
import com.layer.ui.R;
import com.layer.ui.util.AvatarStyle;
import com.layer.ui.util.imagecache.BitmapWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * AtlasAvatar can be used to show information about one user, or as a cluster of multiple users.
 * <p>
 * AtlasAvatar uses Picasso to render the avatar image. So, you need to init
 */
public class AvatarView extends View {

    private Set<Identity> mParticipants = new LinkedHashSet<>();
    private final Map<Identity, String> mInitials = new HashMap<>();
    private final Map<Identity, BitmapWrapper> mBitmapWrappers = new HashMap<>();
    private final List<BitmapWrapper> mPendingLoads = new ArrayList<>();

    private static final Paint PAINT_TRANSPARENT = new Paint();
    private static final Paint PAINT_BITMAP = new Paint();

    private final Paint mPaintInitials = new Paint();
    private final Paint mPaintBorder = new Paint();
    private final Paint mPaintBackground = new Paint();
    private final Paint mPresencePaint = new Paint();
    private final Paint mBackgroundPaint = new Paint();

    private boolean mShouldShowPresence = true;

    // TODO: make these styleable
    private static final float BORDER_SIZE_DP = 1f;
    private static final float MULTI_FRACTION = 26f / 40f;

    private Avatar.ViewModel mViewModel;

    static {
        PAINT_TRANSPARENT.setARGB(0, 255, 255, 255);
        PAINT_TRANSPARENT.setAntiAlias(true);

        PAINT_BITMAP.setARGB(255, 255, 255, 255);
        PAINT_BITMAP.setAntiAlias(true);
    }


    // Sizing set in setClusterSizes() and used in onDraw()
    private float mOuterRadius;
    private float mInnerRadius;
    private float mCenterX;
    private float mCenterY;
    private float mDeltaX;
    private float mDeltaY;
    private float mTextSize;
    private float mPresenceOuterRadius;
    private float mPresenceInnerRadius;
    private float mPresenceCenterX;
    private float mPresenceCenterY;

    private Rect mRect = new Rect();
    private RectF mContentRect = new RectF();
    private int mMaxAvatar = 3;

    public AvatarView(Context context) {
        super(context);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseStyle(getContext(), attrs, defStyleAttr);
    }

    public AvatarView init(@NonNull Avatar.ViewModel avatarViewModel, @NonNull IdentityNameFormatter identityNameFormatter) {
        mViewModel = avatarViewModel;
        mPaintInitials.setAntiAlias(true);
        mPaintInitials.setSubpixelText(true);
        mPaintBorder.setAntiAlias(true);
        mPaintBackground.setAntiAlias(true);

        mPaintBackground.setColor(getResources().getColor(R.color.layer_ui_avatar_background));
        mPaintBorder.setColor(getResources().getColor(R.color.layer_ui_avatar_border));
        mPaintInitials.setColor(getResources().getColor(R.color.layer_ui_avatar_text));
        mViewModel.setIdentityNameFormatter(identityNameFormatter);
        return this;
    }

    public IdentityNameFormatter getIdentityNameFormatter() {
        return mViewModel.getIdentityNameFormatter();
    }

    public AvatarView setStyle(AvatarStyle avatarStyle) {
        mPaintBackground.setColor(avatarStyle.getAvatarBackgroundColor());
        mPaintBorder.setColor(avatarStyle.getAvatarBorderColor());
        mPaintInitials.setColor(avatarStyle.getAvatarTextColor());
        mPaintInitials.setTypeface(avatarStyle.getAvatarTextTypeface());
        return this;
    }

    public AvatarView setParticipants(Identity... participants) {
        mParticipants.clear();
        mParticipants.addAll(Arrays.asList(participants));
        update();
        return this;
    }

    /**
     * Enable or disable showing presence information for this avatar. Presence is shown only for
     * single user Avatars. If avatar is a cluster, presence will not be shown.
     *
     * Default is `true`, to show presence.
     *
     * @param shouldShowPresence set to `true` to show presence, `false` otherwise.
     * @return
     */
    public AvatarView setShouldShowPresence(boolean shouldShowPresence) {
        mShouldShowPresence = shouldShowPresence;
        return this;
    }

    /**
     * Returns if `shouldShowPresence` flag is enabled for this avatar.
     *
     * Default is `true`
     *
     * @return `true` if `shouldShowPresence` is set to `true`, `false` otherwise.
     */
    public boolean getShouldShowPresence() {
        return mShouldShowPresence;
    }

    /**
     * Should be called from UI thread.
     */
    public AvatarView setParticipants(Set<Identity> participants) {
        mParticipants.clear();
        mParticipants.addAll(participants);
        update();
        return this;
    }

    public Set<Identity> getParticipants() {
        return mParticipants;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!changed) return;
        setClusterSizes();
    }

    protected boolean setClusterSizes( ) {
        int avatarCount = mInitials.size();

        if (avatarCount == 0) return false;
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params == null) return false;
        boolean hasBorder = (avatarCount != 1);

        int drawableWidth = params.width - (getPaddingLeft() + getPaddingRight());
        int drawableHeight = params.height - (getPaddingTop() + getPaddingBottom());
        float dimension = Math.min(drawableWidth, drawableHeight);
        float density = getContext().getResources().getDisplayMetrics().density;
        float fraction = (avatarCount > 1) ? MULTI_FRACTION : 1;

        mOuterRadius = fraction * dimension / 2f;
        mInnerRadius = mOuterRadius - (density * BORDER_SIZE_DP);

        mTextSize = mInnerRadius * 4f / 5f;
        mCenterX = getPaddingLeft() + mOuterRadius;
        mCenterY = getPaddingTop() + mOuterRadius;

        float outerMultiSize = fraction * dimension;
        mDeltaX = (drawableWidth - outerMultiSize) / (avatarCount - 1);
        mDeltaY = (drawableHeight - outerMultiSize) / (avatarCount - 1);

        // Presence
        mPresenceOuterRadius = mOuterRadius / 3f;
        mPresenceInnerRadius = mInnerRadius / 3f;
        mPresenceCenterX = mCenterX + mOuterRadius - mPresenceOuterRadius;
        mPresenceCenterY = mCenterY + mOuterRadius - mPresenceOuterRadius;

        synchronized (mPendingLoads) {
            if (!mPendingLoads.isEmpty()) {
                int size = Math.round(hasBorder ? (mInnerRadius * 2f) : (mOuterRadius * 2f));
                for (BitmapWrapper bitmapWrapper : mPendingLoads) {
                    String bitmapUrl = bitmapWrapper.getUrl();
                    // Handle empty paths just like null paths. This ensures empty paths will go
                    // through the normal Picasso flow and the bitmap is set.
                    if (bitmapUrl != null && bitmapUrl.trim().length() == 0) {
                        bitmapUrl = null;
                    }

                    if (bitmapUrl != null) {
                        mViewModel.loadImage(bitmapUrl, bitmapUrl, size, size, bitmapWrapper, (avatarCount > 1));
                    }

                }
                mPendingLoads.clear();
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Clear canvas
        int avatarCount = mInitials.size();
        canvas.drawRect(0f, 0f, canvas.getWidth(), canvas.getHeight(), PAINT_TRANSPARENT);
        if (avatarCount == 0) return;
        boolean hasBorder = (avatarCount != 1);
        float contentRadius = hasBorder ? mInnerRadius : mOuterRadius;

        // Draw avatar cluster
        float cx = mCenterX;
        float cy = mCenterY;
        mContentRect.set(cx - contentRadius, cy - contentRadius, cx + contentRadius, cy + contentRadius);
        for (Map.Entry<Identity, String> entry : mInitials.entrySet()) {
            // Border / background
            if (hasBorder) canvas.drawCircle(cx, cy, mOuterRadius, mPaintBorder);

            // Initials or bitmap
            BitmapWrapper bitmapWrapper = mBitmapWrappers.get(entry.getKey());
            Bitmap bitmap = (bitmapWrapper == null) ? null : bitmapWrapper.getBitmap();

            if (bitmap == null) {
                String initials = entry.getValue();
                mPaintInitials.setTextSize(mTextSize);
                mPaintInitials.getTextBounds(initials, 0, initials.length(), mRect);
                canvas.drawCircle(cx, cy, contentRadius, mPaintBackground);
                canvas.drawText(initials, cx - mRect.centerX(), cy - mRect.centerY() - 1f, mPaintInitials);
            } else {
                canvas.drawBitmap(bitmap, mContentRect.left, mContentRect.top, PAINT_BITMAP);
            }

            // Presence
            if (mShouldShowPresence && avatarCount == 1) { // Show only for single user avatars
                drawPresence(canvas, entry.getKey());
            }

            // Translate for next avatar
            cx += mDeltaX;
            cy += mDeltaY;
            mContentRect.offset(mDeltaX, mDeltaY);
        }
    }

    protected void update() {
        // Limit to mMaxAvatar valid avatars, prioritizing participants with avatars.
        if (mParticipants.size() > mMaxAvatar) {
            Queue<Identity> withAvatars = new LinkedList<>();
            Queue<Identity> withoutAvatars = new LinkedList<>();
            for (Identity participant : mParticipants) {
                if (participant == null) continue;
                if (!TextUtils.isEmpty(participant.getAvatarImageUrl())) {
                    withAvatars.add(participant);
                } else {
                    withoutAvatars.add(participant);
                }
            }

            mParticipants = new LinkedHashSet<>();
            int numWithout = Math.min(mMaxAvatar - withAvatars.size(), withoutAvatars.size());
            for (int i = 0; i < numWithout; i++) {
                mParticipants.add(withoutAvatars.remove());
            }
            int numWith = Math.min(mMaxAvatar, withAvatars.size());
            for (int i = 0; i < numWith; i++) {
                mParticipants.add(withAvatars.remove());
            }
        }

        Diff diff = diff(mInitials.keySet(), mParticipants);
        List<BitmapWrapper> toLoad = new ArrayList<>();

        List<BitmapWrapper> recyclableBitmap = new ArrayList<>();
        for (Identity removed : diff.removed) {
            mInitials.remove(removed);
            BitmapWrapper bitmapWrapper = mBitmapWrappers.remove(removed);
            if (bitmapWrapper != null) {
                mViewModel.cancelImage(bitmapWrapper.getUrl());
                recyclableBitmap.add(bitmapWrapper);
            }
        }

        for (Identity added : diff.added) {
            if (added == null) return;
            mInitials.put(added, mViewModel.getInitialsForAvatarView(added));

            final BitmapWrapper bitmapWrapper;
            if (recyclableBitmap.isEmpty()) {
                bitmapWrapper = new BitmapWrapper(added.getAvatarImageUrl(), this);
            } else {
                bitmapWrapper = recyclableBitmap.remove(0);
            }
            bitmapWrapper.setUrl(added.getAvatarImageUrl());
            mBitmapWrappers.put(added, bitmapWrapper);
            toLoad.add(bitmapWrapper);
        }

        // Cancel existing in case the size or anything else changed.
        // TODO: make caching intelligent wrt sizing
        for (Identity existing : diff.existing) {
            if (existing == null) continue;
            mInitials.put(existing, mViewModel.getInitialsForAvatarView(existing));

            BitmapWrapper existingBitmaps = mBitmapWrappers.get(existing);
            mViewModel.getImageCacheWrapper().cancelRequest(existingBitmaps.getUrl());
            toLoad.add(existingBitmaps);
        }

        for (BitmapWrapper bitmapWrapper : mPendingLoads) {
            if (!toLoad.contains(bitmapWrapper)) {
                mViewModel.getImageCacheWrapper().cancelRequest(bitmapWrapper.getUrl());
            }
        }
        mPendingLoads.clear();
        mPendingLoads.addAll(toLoad);
    }

    private void drawPresence(Canvas canvas, Identity identity) {
        Presence.PresenceStatus currentStatus = identity.getPresenceStatus();
        if (currentStatus == null) {
            return;
        }

        switch (currentStatus) {
            case AVAILABLE:
                drawAvailable(canvas);
                break;
            case AWAY:
                drawAway(canvas);
                break;
            case OFFLINE:
                drawOffline(canvas);
                break;
            case INVISIBLE:
                drawInvisible(canvas);
                break;
            case BUSY:
                drawBusy(canvas);
                break;
            default:
                drawDefault(canvas);
                break;
        }
    }

    public void drawAvailable(Canvas canvas) {
        mPresencePaint.setColor(Color.rgb(0x4F, 0xBF, 0x62));
        drawPresence(canvas, false, true);
    }

    public void drawAway(Canvas canvas) {
        mPresencePaint.setColor(Color.rgb(0xF7, 0xCA, 0x40));
        drawPresence(canvas, false, true);
    }

    public void drawOffline(Canvas canvas) {
        mPresencePaint.setColor(Color.rgb(0x99, 0x99, 0x9c));
        drawPresence(canvas, true, true);
    }

    public void drawInvisible(Canvas canvas) {
        mPresencePaint.setColor(Color.rgb(0x50, 0xC0, 0x62));
        drawPresence(canvas, true, true);
    }

    public void drawBusy(Canvas canvas) {
        mPresencePaint.setColor(Color.rgb(0xE6, 0x44, 0x3F));
        drawPresence(canvas, false, true);
    }

    public void drawDefault(Canvas canvas) {
        drawPresence(canvas, false, false);
    }

    private void drawPresence(Canvas canvas,boolean makeCircleHollow,  boolean drawPresence) {
        if (drawPresence) {
            // Clear background + create border
            mBackgroundPaint.setColor(Color.WHITE);
            mBackgroundPaint.setAntiAlias(true);
            canvas.drawCircle(mPresenceCenterX, mPresenceCenterY, mPresenceOuterRadius, mBackgroundPaint);

            // Draw Presence status
            mPresencePaint.setAntiAlias(true);
            canvas.drawCircle(mPresenceCenterX, mPresenceCenterY, mPresenceInnerRadius, mPresencePaint);

            // Draw hollow if needed
            if (makeCircleHollow) {
                canvas.drawCircle(mPresenceCenterX, mPresenceCenterY, (mPresenceInnerRadius / 2f), mBackgroundPaint);
            }
        }
    }

    private void parseStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AvatarView, R.attr.AvatarView, defStyleAttr);
        mMaxAvatar = ta.getInt(R.styleable.AvatarView_maximumAvatar, 3);
        ta.recycle();
    }

    private static Diff diff(Set<Identity> oldSet, Set<Identity> newSet) {
        Diff diff = new Diff();
        for (Identity old : oldSet) {
            if (newSet.contains(old)) {
                diff.existing.add(old);
            } else {
                diff.removed.add(old);
            }
        }
        for (Identity newItem : newSet) {
            if (!oldSet.contains(newItem)) {
                diff.added.add(newItem);
            }
        }
        return diff;
    }

    private static class Diff {
        public List<Identity> existing = new ArrayList<>();
        public List<Identity> added = new ArrayList<>();
        public List<Identity> removed = new ArrayList<>();
    }
}
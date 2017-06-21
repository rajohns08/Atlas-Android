package com.layer.atlas.view;

import android.app.Activity;
import android.os.Bundle;

import com.layer.atlas.mock.MockLayerClient;
import com.layer.sdk.messaging.Identity;
import com.layer.ui.Injection;
import com.layer.ui.R;
import com.layer.ui.avatar.AvatarInitials;
import com.layer.ui.avatar.AvatarView;

public class AvatarActivityTestView extends Activity {

    private AvatarView mAvatarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_test);
        Injection.setLayerClient(new MockLayerClient());
        mAvatarView = (AvatarView) findViewById(R.id.test_avatar);
        mAvatarView.init(new AvatarInitials() {
            @Override
            public String getInitials(Identity added) {
                return "WA";
            }
        });
    }
}

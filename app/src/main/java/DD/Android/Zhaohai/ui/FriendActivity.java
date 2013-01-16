package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.User;
import DD.Android.Zhaohai.core.UserAvatarLoader;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.inject.Inject;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import static DD.Android.Zhaohai.core.Constants.Extra.FRIEND;

public class FriendActivity extends ZhaohaiActivity {

    @InjectExtra(FRIEND) protected User friend;

    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.tv_name) protected TextView name;

    @Inject protected UserAvatarLoader avatarLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.friend_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(friend.name);

        avatarLoader.bind(avatar, friend);
        name.setText(friend.name);

    }

}

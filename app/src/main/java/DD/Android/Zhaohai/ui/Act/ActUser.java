package DD.Android.Zhaohai.ui.Act;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.User;
import DD.Android.Zhaohai.core.UserAvatarLoader;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.inject.Inject;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import static DD.Android.Zhaohai.core.Constants.Extra.USER;

public class ActUser extends ActZhaohai {

    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.tv_name) protected TextView name;

    @InjectExtra(USER) protected User user;

    @Inject protected UserAvatarLoader avatarLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_user);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avatarLoader.bind(avatar, user);
        name.setText(user.getName());

    }


}

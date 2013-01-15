package DD.Android.Zhaohai.ui;

import static DD.Android.Zhaohai.core.Constants.Extra.ABUSER;

import DD.Android.Zhaohai.core.ABUser;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.AvatarLoader;
import com.google.inject.Inject;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class UserActivity extends BootstrapActivity {

    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.tv_name) protected TextView name;

    @InjectExtra(ABUSER) protected ABUser ABUser;

    @Inject protected AvatarLoader avatarLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_view);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avatarLoader.bind(avatar, ABUser);
        name.setText(String.format("%s %s", ABUser.getFirstName(), ABUser.getLastName()));

    }


}

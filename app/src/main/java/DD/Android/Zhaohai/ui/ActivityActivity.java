package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.Activity;
import android.os.Bundle;
import android.widget.TextView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static DD.Android.Zhaohai.core.Constants.Extra.ACTIVITY;
import static DD.Android.Zhaohai.core.Constants.Other.*;

public class ActivityActivity extends ZhaohaiActivity {

//    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.tv_desc) protected TextView desc;
    @InjectView(R.id.tv_address) protected TextView address;
    @InjectView(R.id.tv_started_at) protected TextView started_at;
    @InjectView(R.id.tv_finished_at) protected TextView finished_at;
    @InjectView(R.id.tv_user) protected TextView user;
    @InjectView(R.id.tv_users) protected TextView users;
    @InjectView(R.id.tv_interested_users) protected TextView interested_users;


    @InjectExtra(ACTIVITY) protected Activity activity;

//    @Inject protected UserAvatarLoader avatarLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view);
        setTitle(activity.getTitle());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        avatarLoader.bind(avatar, activity);
        desc.setText(activity.getDesc());
        address.setText(activity.getAddress());
        SimpleDateFormat sdf = (SimpleDateFormat)POST_DATE_FORMAT.clone();
        sdf.setTimeZone(TimeZone.getDefault());
        started_at.setText(sdf.format(activity.getStarted_at()));
        finished_at.setText(sdf.format(activity.getFinished_at()));

    }


}

package DD.Android.Zhaohai.ui.Act;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import DD.Android.Zhaohai.core.PrettyDateFormat;
import DD.Android.Zhaohai.core.User;
import DD.Android.Zhaohai.core.UserAvatarLoader;
import android.accounts.AccountsException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.inject.Inject;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static DD.Android.Zhaohai.core.Constants.Extra.USER;

public class ActUser extends ActZhaohai {

    @InjectView(R.id.iv_avatar) protected ImageView iv_avatar;
    @InjectView(R.id.tv_name) protected TextView tv_name;

    @InjectView(R.id.tv_points_hold) protected TextView tv_points_hold;
    @InjectView(R.id.tv_points_join) protected TextView tv_points_join;
    @InjectView(R.id.tv_points_interested) protected TextView tv_points_interested;

    @InjectView(R.id.tv_status) protected TextView tv_status;
    @InjectView(R.id.tv_work) protected TextView tv_work;
    @InjectView(R.id.tv_school) protected TextView tv_school;

    @InjectView(R.id.tv_now) protected TextView tv_now;
    @InjectView(R.id.tv_home) protected TextView tv_home;

    @InjectView(R.id.ll_now) protected LinearLayout ll_now;
    @InjectView(R.id.ll_home) protected LinearLayout ll_home;

    @InjectView(R.id.ll_status) protected LinearLayout ll_status;
    @InjectView(R.id.ll_work) protected LinearLayout ll_work;
    @InjectView(R.id.ll_school) protected LinearLayout ll_school;

    @InjectView(R.id.layout_base) protected LinearLayout layout_base;
    @InjectView(R.id.layout_pos) protected LinearLayout layout_pos;

    @InjectExtra(USER) protected User user;

    @Inject
    protected ZhaohaiServiceProvider serviceProvider;

    @Inject protected UserAvatarLoader avatarLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_user);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avatarLoader.bind(iv_avatar, user,"thumb");
        tv_name.setText(user.getName());

        new GetUser().execute();

    }


    private void user_to_view() {
        tv_points_hold.setText(user.getPointsHold());
        tv_points_join.setText(user.getPointsJoin());
        tv_points_interested.setText(user.getPointsInterested());

        tv_status.setText(user.getUserinfo().getStatus());
        tv_work.setText(user.getUserinfo().getWork());
        tv_school.setText(user.getUserinfo().getSchool());

        tv_now.setText(user.getUserinfo().getNow());
        tv_home.setText(user.getUserinfo().getHome());
    }


    private class GetUser extends AsyncTask<Void, String,Void> {

        @Override
        protected void onPreExecute() {
            progressDialogShow(ActUser.this);
            setProgressBarVisibility(true);
        }

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void...params/*参数1*/) {
            try {
                user = serviceProvider.getService().getUser(user.get_id());
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (AccountsException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            
            return null;
        }

        //步骤4：定义后台进程执行完后的处理，本例，采用Toast
        protected void onPostExecute(Void result/*参数3*/) {
            user_to_view();
            progressDialogDismiss();
            setProgressBarVisibility(false);
        }
    }

}

package DD.Android.Zhaohai.ui.Act;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import DD.Android.Zhaohai.authenticator.AccessToken;
import DD.Android.Zhaohai.core.User;
import DD.Android.Zhaohai.core.UserAvatarLoader;
import android.accounts.AccountsException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.wishlist.Toaster;
import com.google.inject.Inject;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import roboguice.util.RoboAsyncTask;
import roboguice.util.Strings;

import java.io.IOException;

import static DD.Android.Zhaohai.core.Constants.Extra.USER;
import static DD.Android.Zhaohai.core.Constants.Http.*;
import static com.github.kevinsawicki.http.HttpRequest.post;

public class ActUser extends ActZhaohai {

    @InjectView(R.id.iv_avatar)
    protected ImageView iv_avatar;
    @InjectView(R.id.tv_name)
    protected TextView tv_name;

    @InjectView(R.id.tv_points_hold)
    protected TextView tv_points_hold;
    @InjectView(R.id.tv_points_join)
    protected TextView tv_points_join;
    @InjectView(R.id.tv_points_interested)
    protected TextView tv_points_interested;

    @InjectView(R.id.tv_status)
    protected TextView tv_status;
    @InjectView(R.id.tv_work)
    protected TextView tv_work;
    @InjectView(R.id.tv_school)
    protected TextView tv_school;

    @InjectView(R.id.tv_now)
    protected TextView tv_now;
    @InjectView(R.id.tv_home)
    protected TextView tv_home;

    @InjectView(R.id.ll_now)
    protected LinearLayout ll_now;
    @InjectView(R.id.ll_home)
    protected LinearLayout ll_home;

    @InjectView(R.id.ll_status)
    protected LinearLayout ll_status;
    @InjectView(R.id.ll_work)
    protected LinearLayout ll_work;
    @InjectView(R.id.ll_school)
    protected LinearLayout ll_school;

    @InjectView(R.id.layout_base)
    protected LinearLayout layout_base;
    @InjectView(R.id.layout_pos)
    protected LinearLayout layout_pos;

    @InjectView(R.id.iv_gender)
    protected ImageView iv_gender;
    @InjectView(R.id.tv_age)
    protected TextView tv_age;
    @InjectView(R.id.ll_sex_background)
    protected LinearLayout ll_sex_background;

    @InjectView(R.id.btn_relationship)
    protected Button btn_relationship;
    @InjectView(R.id.btn_msg)
    protected Button btn_msg;

    @InjectExtra(USER)
    protected User user;

    @Inject
    protected ZhaohaiServiceProvider serviceProvider;

    @Inject
    protected UserAvatarLoader avatarLoader;

    private RoboAsyncTask<String> followTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_user);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avatarLoader.bind(iv_avatar, user, "thumb");
        tv_name.setText(user.getName());

        new GetUser().execute();

    }


    private void user_to_view() {
        if (user.isMale()) {
            ll_sex_background.setBackgroundResource(R.drawable.bg_user_info_male);
            iv_gender.setImageDrawable(getResources().getDrawable(R.drawable.ic_user_male));
        } else {
            ll_sex_background.setBackgroundResource(R.drawable.bg_user_info_famale);
            iv_gender.setImageDrawable(getResources().getDrawable(R.drawable.ic_user_famale));
        }
        tv_age.setText(user.getStrAge());

        tv_points_hold.setText(user.getPointsHold());
        tv_points_join.setText(user.getPointsJoin());
        tv_points_interested.setText(user.getPointsInterested());

        tv_status.setText(user.getUserinfo().getStatus());
        tv_work.setText(user.getUserinfo().getWork());
        tv_school.setText(user.getUserinfo().getSchool());

        tv_now.setText(user.getUserinfo().getNow());
        tv_home.setText(user.getUserinfo().getHome());

        relationship_to_btn(user.getRelationship());
    }

    private void relationship_to_btn(String relationship) {
        if (relationship != null) {
            if (relationship.equals("friend") || relationship.equals("following")) {
                btn_relationship.setEnabled(false);
            }
            btn_relationship.setText(getStringResourceByName(user.getRelationship()));
//            if (relationship.equals("friend") || relationship.equals("follower")) {
//                btn_msg.setEnabled(true);
//            }
        }
        else{
            followFailure();
        }
    }


    private class GetUser extends AsyncTask<Void, String, Void> {

        @Override
        protected void onPreExecute() {
            showProgress();
//            progressDialogShow(ActUser.this);
            setProgressBarVisibility(true);
        }

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void... params/*参数1*/) {
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


    /**
     * Handles onClick event on the Submit button. Sends username/password to
     * the server for authentication.
     * <p/>
     * Specified by android:onClick="handleReg" in the layout xml
     *
     * @param view
     */
    public void handleFollow(View view) {
        if (followTask != null) {
            return;
        }

        showProgress();

        followTask = new RoboAsyncTask<String>(this) {
            public String call() throws Exception {

                return serviceProvider.getService().follow(user.get_id());
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                Throwable cause = e.getCause() != null ? e.getCause() : e;

                followFailure();
            }

            @Override
            public void onSuccess(String relationship) {
                relationship_to_btn(relationship);
            }

            @Override
            protected void onFinally() throws RuntimeException {
                hideProgress();
                followTask = null;
            }

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return followTask.cancel(mayInterruptIfRunning);
//                return super.cancel(mayInterruptIfRunning);    //To change body of overridden methods use File | Settings | File Templates.
            }
        };
        followTask.execute();
    }

    private void followFailure() {
        String message;
        // A 404 is returned as an Exception with this message
        message = getResources().getString(
                R.string.message_follow_failure);

        Toaster.showLong(ActUser.this, message);
    }

    /**
     * Hide progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void hideProgress() {
        dismissDialog(0);
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void showProgress() {
        showDialog(0);
    }


    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }

}

package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import DD.Android.Zhaohai.core.ZNotification;
import android.accounts.AccountsException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.inject.Inject;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.IOException;

import static DD.Android.Zhaohai.core.Constants.Extra.NOTIFICATION;

public class ActivityNotification extends ZhaohaiActivity {

    //    @InjectView(R.id.iv_avatar) protected ImageView avatar;
//    @InjectView(R.id.tv_type)
//    protected TextView tv_type;
    @InjectView(R.id.tv_created_at)
    protected TextView tv_created_at;

    @InjectView(R.id.tr_activity)
    protected TableRow tr_activity;
    @InjectView(R.id.tr_interesting_user)
    protected TableRow tr_interesting_user;
    @InjectView(R.id.tr_activity_request_text)
    protected TableRow tr_activity_request_text;

    @InjectView(R.id.tv_activity)
    protected TextView tv_activity;
    @InjectView(R.id.tv_interesting_user)
    protected TextView tv_interesting_user;
    @InjectView(R.id.tv_activity_request_text)
    protected TextView tv_activity_request_text;


    @InjectView(R.id.tr_reply_admin)
    protected TableRow tr_reply_admin;
    @InjectView(R.id.tr_reply_status)
    protected TableRow tr_reply_status;

    @InjectView(R.id.tv_reply_admin)
    protected TextView tv_reply_admin;
    @InjectView(R.id.tv_reply_status)
    protected TextView tv_reply_status;


    @InjectView(R.id.tr_invite_user)
    protected TableRow tr_invite_user;

    @InjectView(R.id.tv_invite_user)
    protected TextView tv_invite_user;


    @InjectView(R.id.tr_follower)
    protected TableRow tr_follower;

    @InjectView(R.id.tv_follower)
    protected TextView tv_follower;



    @Inject
    protected ZhaohaiServiceProvider serviceProvider;

    @InjectExtra(NOTIFICATION)
    protected ZNotification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notification_view);
//        setTitle(activity.getTitle());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new SetNotification().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.zhaohai, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
//                invite_friend();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class SetNotification extends AsyncTask<Void, String, Void> {

        protected void onPreExecute () {//在 doInBackground(Params...)之前被调用，在ui线程执行
            progressDialogShow(ActivityNotification.this);
        }

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void... params/*参数1*/) {
            try {
                notification = serviceProvider.getService().getNotification(notification.get_id());
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (AccountsException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            return null;

        }

            //步骤4：定义后台进程执行完后的处理，本例，采用Toast

        protected void onPostExecute(Void result/*参数3*/) {
            switch (notification.getType()){
                case ZNotification.ACTIVITY_REQUEST_REPLY:
                    tr_activity.setVisibility(View.VISIBLE);
                    tr_reply_admin.setVisibility(View.VISIBLE);
                    tr_reply_status.setVisibility(View.VISIBLE);
                    tv_activity.setText(notification.getActivity().getTitle());
                    tv_reply_admin.setText(notification.getReply_admin().getName());
                    tv_reply_status.setText(notification.getReply_status());
                    break;
                case ZNotification.ACTIVITY_REQUEST_INVITE:
                    tr_activity.setVisibility(View.VISIBLE);
                    tr_invite_user.setVisibility(View.VISIBLE);
                    tv_activity.setText(notification.getActivity().getTitle());
                    tv_invite_user.setText(notification.getInvite_user().getName());
                    break;
                case ZNotification.ACTIVITY_REQUEST:
                    tr_activity.setVisibility(View.VISIBLE);
                    tr_interesting_user.setVisibility(View.VISIBLE);
                    tr_activity_request_text.setVisibility(View.VISIBLE);
                    tv_activity.setText(notification.getActivity().getTitle());
                    tv_interesting_user.setText(notification.getInteresting_user().getName());
                    tv_activity_request_text.setText(notification.getText());
                    break;
                case ZNotification.FOLLOW:
                    tr_follower.setVisibility(View.VISIBLE);
                    tv_follower.setText(notification.getFollower().getName());
                    break;
                default:
                    break;
            }
            setTitle(notification.getTitle());
//            tv_type.setText(notification.getTitle());
            tv_created_at.setText(notification.getCreated_at().toLocaleString());
            progressDialogCancel();
        }
    }

}

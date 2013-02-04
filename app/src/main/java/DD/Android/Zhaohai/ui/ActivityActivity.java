package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import DD.Android.Zhaohai.core.Activity;
import android.accounts.AccountsException;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.inject.Inject;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static DD.Android.Zhaohai.core.Constants.Extra.ACTIVITY;
import static DD.Android.Zhaohai.core.Constants.Other.*;
import static DD.Android.Zhaohai.core.Constants.Other.ActivityTaskStatus.*;
import static android.content.DialogInterface.*;

public class ActivityActivity extends ZhaohaiActivity {

    //    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.tv_desc)
    protected TextView desc;
    @InjectView(R.id.tv_address)
    protected TextView address;
    @InjectView(R.id.tv_started_at)
    protected TextView started_at;
    @InjectView(R.id.tv_finished_at)
    protected TextView finished_at;
    @InjectView(R.id.tv_user)
    protected TextView user;
    @InjectView(R.id.tv_users)
    protected TextView users;
    @InjectView(R.id.tv_interested_users)
    protected TextView interested_users;
    @Inject
    protected ZhaohaiServiceProvider serviceProvider;

    AlertDialog adialog = null;
    View v_alert_message = null;


    @InjectExtra(ACTIVITY)
    protected Activity activity;
    private OnClickListener join_acitivity;
    private int action = -1;

    private String message;


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
        SimpleDateFormat sdf = (SimpleDateFormat) POST_DATE_FORMAT.clone();
        sdf.setTimeZone(TimeZone.getDefault());
        started_at.setText(sdf.format(activity.getStarted_at()));
        finished_at.setText(sdf.format(activity.getFinished_at()));

        join_acitivity =   new OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText et_message = (EditText)v_alert_message.findViewById(R.id.et_message);
                message = et_message.getText().toString();
                destroy_alert_dialog();
                try {
                    action = JOIN;
                    new ActivityTask().execute();
//                    join(activity.get_id(), et_message.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
//                finally {
//                    destroy_alert_dialog();
//                }
            }

        };

//        setHasOptionsMenu(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (!isUsable())
//            return false;
        switch (item.getItemId()) {
            case R.id.menu_join:
                show_join_dialog();
                return true;
            case R.id.menu_leave:
                leave();
                return true;
            case R.id.menu_invite:
                invite();
                return true;
            case R.id.menu_close:
                close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void close() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void invite() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void leave() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void show_join_dialog() {
        destroy_alert_dialog();

        LayoutInflater inflater = LayoutInflater.from(this);

        v_alert_message = inflater.inflate(R.layout.alter_edittext, null);

        adialog = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.set_join_message))
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(v_alert_message)
                .setNegativeButton(getResources().getString(android.R.string.cancel), null)
                .setPositiveButton(getResources().getString(android.R.string.ok), join_acitivity)
                .show();
    }

    private void destroy_alert_dialog() {
        if(adialog != null){
            adialog.dismiss();
            adialog = null;
        }
    }

    private void join(String id,String message) throws Exception {
        this.setSupportProgressBarIndeterminateVisibility(true);

        serviceProvider.getService().joinActivity(id, message);
    }

    private class ActivityTask extends AsyncTask<Void, String,Void> {

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void...params/*参数1*/) {
            try {
                switch (action){
                    case JOIN:
                        serviceProvider.getService().joinActivity(activity.get_id(),message);
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (AccountsException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return null;
        }

        //步骤4：定义后台进程执行完后的处理，本例，采用Toast
        protected void onPostExecute(Void result/*参数3*/) {
//            printInfo("onPostExecute");
//            if(activity.get_id() != null){
//                startActivity(new Intent(NewActivityBaiduMap.this, ActivityActivity.class).putExtra(ACTIVITY, activity));
//            }
        }
    }


}

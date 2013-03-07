package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import DD.Android.Zhaohai.core.Activity;
import DD.Android.Zhaohai.core.User;
import android.accounts.AccountsException;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static DD.Android.Zhaohai.core.Constants.Extra.ACTIVITY;
import static DD.Android.Zhaohai.core.Constants.Other.ActivityTaskStatus.*;
import static DD.Android.Zhaohai.core.Constants.Other.POST_DATE_FORMAT;
import static android.content.DialogInterface.OnClickListener;

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

    Dialog adialog = null;
    View v_alert_message = null;


    @InjectExtra(ACTIVITY)
    protected Activity activity;
    private OnClickListener join_acitivity;
    private OnClickListener quit_acitivity;
    private OnClickListener close_acitivity;
    private int action = -1;

    private String message;

    MenuItem menu_join = null, menu_leave = null,menu_close = null,menu_invite = null,menu_activity_requests = null;

    User me = null;

//    @Inject protected UserAvatarLoader avatarLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view);
        setTitle(activity.getTitle());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity_to_view();

        join_acitivity =   new OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText et_message = (EditText)v_alert_message.findViewById(R.id.et_message);
                message = et_message.getText().toString();
                action = JOIN;

                runActionTask();
            }

        };

        quit_acitivity = new OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                action = QUIT;
                runActionTask();
            }

        };

        close_acitivity = new OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                action = CLOSE;
                runActionTask();
            }

        };

        progressDialogShow(this);
        new GetActivityTask().execute();
    }

    private void runActionTask() {
        destroy_dialog();
        try {
            progressDialogShow(this);
            new ActivityActionTask().execute();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void activity_to_view() {
        desc.setText(activity.getDesc());
        address.setText(activity.getAddress());
        if(activity.getUser() != null){
            SimpleDateFormat sdf = (SimpleDateFormat) POST_DATE_FORMAT.clone();
            sdf.setTimeZone(TimeZone.getDefault());
            started_at.setText(sdf.format(activity.getStarted_at()));
            finished_at.setText(sdf.format(activity.getFinished_at()));

            user.setText(activity.getUser().getName());

            List<String> user_names = new ArrayList<String>();
            List<String> interested_user_names = new ArrayList<String>();
            for(User u : activity.getUsers()){
                user_names.add(u.getName());
            }
            for(User u : activity.getInterested_users()){
                interested_user_names.add(u.getName());
            }
            users.setText(TextUtils.join(" ",user_names));
            interested_users.setText(TextUtils.join(" ",interested_user_names));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.activity, menu);

        menu_join = menu.findItem(R.id.menu_join);
        menu_leave = menu.findItem(R.id.menu_leave);
        menu_invite = menu.findItem(R.id.menu_invite);
        menu_close = menu.findItem(R.id.menu_close);
        menu_activity_requests = menu.findItem(R.id.menu_activity_requests);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (!isUsable())
//            return false;
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                progressDialogShow(ActivityActivity.this);
                new GetActivityTask().execute();
                return true;
            case R.id.menu_join:
                show_join_dialog();
                return true;
            case R.id.menu_leave:
                show_quit_dialog();
                return true;
            case R.id.menu_invite:
                to_invite_friend();
                return true;
            case R.id.menu_close:
                show_close_dialog();
                return true;
            case R.id.menu_activity_requests:
//                startActivity(new Intent(this, ActivityActivityRequests.class).putExtra(ACTIVITY, activity));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void show_close_dialog() {
        destroy_dialog();

        adialog = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.dialog_sure))
                .setIcon(android.R.drawable.ic_dialog_info)
                .setNegativeButton(getResources().getString(android.R.string.cancel), null)
                .setPositiveButton(getResources().getString(android.R.string.ok), close_acitivity)
                .show();
    }

    private void to_invite_friend() {
        startActivity(new Intent(this, ActivityInviteFriend.class).putExtra(ACTIVITY, activity));
    }

    private void show_quit_dialog() {
        destroy_dialog();

        adialog = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.dialog_sure))
                .setIcon(android.R.drawable.ic_dialog_info)
                .setNegativeButton(getResources().getString(android.R.string.cancel), null)
                .setPositiveButton(getResources().getString(android.R.string.ok), quit_acitivity)
                .show();
    }

    private void show_join_dialog() {
        destroy_dialog();

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

    private void destroy_dialog() {
        if(adialog != null){
            adialog.dismiss();
            adialog = null;
        }
    }

    private void join(String id,String message) throws Exception {
        this.setSupportProgressBarIndeterminateVisibility(true);

        serviceProvider.getService().joinActivity(id, message);
    }

    public boolean isManager() {
        return activity.getUser().get_id().equals(me.get_id());
    }

    public boolean isInActivity() {
        String me_id = me.get_id();
        for(User u : activity.getUsers()){
            if(u.get_id().equals(me_id))
                return true;
        }
        return false;
    }

    public boolean isJoin() {
        String me_id = me.get_id();
        for(User u : activity.getInterested_users()){
            if(u.get_id().equals(me_id))
                return true;
        }
        return false;
    }


    private class GetActivityTask extends AsyncTask<Void, String,Void> {

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void...params/*参数1*/) {
            try {
                activity = serviceProvider.getService().getActivity(activity.get_id());
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (AccountsException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            if(me == null)
            try {
                me = serviceProvider.getService().getMe();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (AccountsException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return null;
        }

        //步骤4：定义后台进程执行完后的处理，本例，采用Toast
        protected void onPostExecute(Void result/*参数3*/) {
            activity_to_view();
            show_menu();
            progressDialogCancel();
        }
    }

    private void show_menu() {
        hidden_menus();
        if (activity.getStatus().equals("opening") || activity.getStatus().equals("running")){
            if(isManager()){
                menu_close.setVisible(true);
                menu_invite.setVisible(true);
//                menu_activity_requests.setVisible(true);
            }
            else{
                if(isInActivity()){
                    menu_leave.setVisible(true);
                }
                else{
                    if(!isJoin())
                        menu_join.setVisible(true);
                }
            }
        }

    }

    private void hidden_menus() {
        menu_close.setVisible(false);
        menu_invite.setVisible(false);
        menu_join.setVisible(false);
        menu_leave.setVisible(false);
        menu_activity_requests.setVisible(false);
    }

    private class ActivityActionTask extends AsyncTask<Void, String,Void> {

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void...params/*参数1*/) {
            try {
                switch (action){
                    case JOIN:
                        serviceProvider.getService().joinActivity(activity.get_id(), message);
                        break;
                    case QUIT:
                        serviceProvider.getService().quitActivity(activity.get_id());
                        break;
                    case CLOSE:
                        serviceProvider.getService().closeActivity(activity.get_id());
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
            new GetActivityTask().execute();
        }
    }



}

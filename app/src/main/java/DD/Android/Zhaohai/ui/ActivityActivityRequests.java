package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import DD.Android.Zhaohai.core.Activity;
import DD.Android.Zhaohai.core.ActivityRequest;
import android.accounts.AccountsException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.inject.Inject;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.IOException;
import java.util.List;

import static DD.Android.Zhaohai.core.Constants.Extra.ACTIVITY;

public class ActivityActivityRequests extends ZhaohaiActivity {

    //    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.lv_requests)
    protected ListView lv_requests;
    @Inject
    protected ZhaohaiServiceProvider serviceProvider;

    @InjectExtra(ACTIVITY)
    protected Activity activity;

    List<ActivityRequest> requests = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_requests);
//        setTitle(activity.getTitle());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new GetRequests().execute();
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

    public ListView getListView() {
        return lv_requests;
    }
    /**
     *
     * Desc:初始化列表数据
     * @param personList
     */
    private void initListData(List<ActivityRequest> personList) {
        DispatchActivityRequestAdapter adapter = new DispatchActivityRequestAdapter(
                this, requests,
                R.layout.dispatch_activity_request_item);
        getListView().setAdapter(adapter);

    }


    private class GetRequests extends AsyncTask<Void, String, Void> {
        protected void onPreExecute () {//在 doInBackground(Params...)之前被调用，在ui线程执行
//            progressDialogShow(ActivityActivityRequests.this);
        }
        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void... params/*参数1*/) {
            try {
                requests = serviceProvider.getService().getActivityRequests(activity.get_id());
                return null;
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (AccountsException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return null;

        }

            //步骤4：定义后台进程执行完后的处理，本例，采用Toast

        protected void onPostExecute(Void result/*参数3*/) {
            if(requests != null)
                initListData(requests);
            progressDialogCancel();
        }
    }

    private class InviteFriendTask extends AsyncTask<Void, String, Void> {
        protected void onPreExecute () {//在 doInBackground(Params...)之前被调用，在ui线程执行
            progressDialogShow(ActivityActivityRequests.this);
        }

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void... params/*参数1*/) {
//            try {
//                List<String> ids = new ArrayList<String>();
//                for(User user : notifications){
//                    if(user.isChecked())
//                        ids.add(user.get_id());
//                }
//                serviceProvider.getService().inviteFriend(activity.get_id(),ids);
//                return null;
//            } catch (IOException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            } catch (AccountsException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
            return null;

        }

        //步骤4：定义后台进程执行完后的处理，本例，采用Toast

        protected void onPostExecute(Void result/*参数3*/) {
//            finish();
        }
    }


}

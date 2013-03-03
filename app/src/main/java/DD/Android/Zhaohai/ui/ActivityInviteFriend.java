package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import DD.Android.Zhaohai.core.Activity;
import DD.Android.Zhaohai.core.User;
import android.accounts.AccountsException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ListView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.inject.Inject;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static DD.Android.Zhaohai.core.Constants.Extra.ACTIVITY;

public class ActivityInviteFriend extends ZhaohaiActivity {

    //    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.lv_friend)
    protected ListView lv_friend;
    @Inject
    protected ZhaohaiServiceProvider serviceProvider;

    @InjectExtra(ACTIVITY)
    protected Activity activity;

    List<User> friend = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_invite_friend);
//        setTitle(activity.getTitle());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportProgressBarIndeterminateVisibility(true);
        new GetFriendTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (!isUsable())
//            return false;
        switch (item.getItemId()) {
            case R.id.menu_invite:
                invite_friend();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void invite_friend() {
        new InviteFriendTask().execute();
    }

    public ListView getListView() {
        return lv_friend;
    }
    /**
     *
     * Desc:初始化列表数据
     * @param personList
     */
    private void initListData(List<User> personList) {
        DispatchSelectUserAdapter adapter = new DispatchSelectUserAdapter(
                this, personList,
                R.layout.dispatch_select_user_item);
        getListView().setAdapter(adapter);

    }


    private class GetFriendTask extends AsyncTask<Void, String, Void> {

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void... params/*参数1*/) {
            try {
                friend = serviceProvider.getService().getFriend();
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
            if(friend != null)
                initListData(friend);
            setSupportProgressBarIndeterminateVisibility(false);
        }
    }

    private class InviteFriendTask extends AsyncTask<Void, String, Void> {

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void... params/*参数1*/) {
            try {
                List<String> ids = new ArrayList<String>();
                for(User user : friend){
                    if(user.isChecked())
                        ids.add(user.get_id());
                }
                serviceProvider.getService().inviteFriend(activity.get_id(),ids);
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
            finish();
        }
    }


}

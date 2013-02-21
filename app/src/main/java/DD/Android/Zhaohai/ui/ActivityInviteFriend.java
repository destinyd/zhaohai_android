package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import DD.Android.Zhaohai.core.Activity;
import android.accounts.AccountsException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.google.inject.Inject;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.IOException;

import static DD.Android.Zhaohai.core.Constants.Extra.ACTIVITY;
import static DD.Android.Zhaohai.core.Constants.Other.ActivityTaskStatus.CLOSE;
import static DD.Android.Zhaohai.core.Constants.Other.ActivityTaskStatus.JOIN;
import static DD.Android.Zhaohai.core.Constants.Other.ActivityTaskStatus.QUIT;

public class ActivityInviteFriend extends ZhaohaiActivity {

    //    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.lv_friend)
    protected ListView lv_friend;
    @Inject
    protected ZhaohaiServiceProvider serviceProvider;

    @InjectExtra(ACTIVITY)
    protected Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_invite_friend);
//        setTitle(activity.getTitle());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private class ActivityTask extends AsyncTask<Void, String, Void> {

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void... params/*参数1*/) {
            try {
                serviceProvider.getService().closeActivity(activity.get_id());
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
//            switch (action){
//                case INVITE:
//                    Log.e("INVITE", "onPostExecute");
//                    startActivity(new Intent(ActivityActivity.this, ActivityInviteFriend.class).putExtra(ACTIVITY, activity));
//                    break;
//            }
        }
    }

}

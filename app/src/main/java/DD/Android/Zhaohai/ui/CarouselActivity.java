

package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.R.id;
import DD.Android.Zhaohai.authenticator.ApiKeyProvider;
import DD.Android.Zhaohai.service.MessageService;
import android.accounts.AccountsException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.view.Window;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.google.inject.Inject;
import com.viewpagerindicator.TitlePageIndicator;
import roboguice.inject.InjectView;

import java.io.IOException;

import static DD.Android.Zhaohai.core.Constants.Extra.APIKEY;

/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class CarouselActivity extends RoboSherlockFragmentActivity {

    @InjectView(id.tpi_header)
    private TitlePageIndicator indicator;
    @InjectView(id.vp_pages)
    private ViewPager pager;

    @Inject private ApiKeyProvider keyProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_view);

//        pager.setAdapter(new BootstrapPagerAdapter(getResources(), getSupportFragmentManager()));
        pager.setAdapter(new ZhaohaiPagerAdapter(getResources(), getSupportFragmentManager()));

        indicator.setViewPager(pager);
        pager.setCurrentItem(2);
        new StartMessageService().execute();

//        // 获取NotificationManager的引用
//        NotificationManager mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//// 创建一个Notification对象
//        int icon = R.drawable.icon;
//        CharSequence tickerText = "Hello";
//        long when = System.currentTimeMillis();
//        Notification notification = new Notification(icon, tickerText, when);
//        notification.flags |= Notification.DEFAULT_ALL;
//
//// 定义Notification的title、message、和pendingIntent
//        Context context = getApplicationContext();
//        CharSequence contentTitle = "你有新的消息";
//        CharSequence contentText = "xxx yyy";
//        Intent notificationIntent = new Intent(this, ActivityNotifications.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
//
//// 通知状态栏显示Notification
//        mNM.notify(HELLO_ID, notification);
    }

    //    private static final int HELLO_ID = 1;
    private class StartMessageService extends AsyncTask<Void, String, Void> {
        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void... params/*参数1*/) {
            boolean isMessagePush = true;//不开启就设置为false;
            if (isMessagePush) {
                try {
                    String apiKey = keyProvider.getAuthKey();
                    Intent serviceIntent = new Intent(CarouselActivity.this, MessageService.class).putExtra(APIKEY,apiKey);
                    startService(serviceIntent);
                    bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);

                } catch (AccountsException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            return null;

        }

        //步骤4：定义后台进程执行完后的处理，本例，采用Toast

        protected void onPostExecute(Void result/*参数3*/) {
        }
    }

    MessageService messageService;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            messageService = ((MessageService.MessageBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            messageService = null;
        }
    };

}

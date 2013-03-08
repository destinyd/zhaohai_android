
package DD.Android.Zhaohai.service;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.Constants;
import DD.Android.Zhaohai.core.ZNotificationStatus;
import DD.Android.Zhaohai.ui.ActivityNotifications;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import roboguice.service.RoboService;

import java.io.IOException;

import static DD.Android.Zhaohai.core.Constants.Extra.APIKEY;
import static DD.Android.Zhaohai.core.Constants.Http.*;
import static com.github.kevinsawicki.http.HttpRequest.get;

public class MessageService extends RoboService {

    //      @Inject
//      protected String authToken;
//    @Inject
//    protected ZhaohaiServiceProvider serviceProvider;
//    @InjectExtra(APIKEY)
    protected String apiKey;

    //获取消息线程
    private MessageThread messageThread = null;

    //点击查看
    private Intent messageIntent = null;
    private PendingIntent messagePendingIntent = null;

    //通知栏消息
    private int messageNotificationID = 1000;
    private Notification messageNotification = null;
    private NotificationManager messageNotificatioManager = null;

    ZNotificationStatus mnotification_status = null;

    @Override
    public void onCreate() {
        super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //初始化
        messageNotification = new Notification();
        messageNotification.icon = R.drawable.icon;
        messageNotification.tickerText = "新消息";
        messageNotification.defaults = Notification.DEFAULT_SOUND;
        messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        messageIntent = new Intent(this, ActivityNotifications.class);
        messagePendingIntent = PendingIntent.getActivity(this, 0, messageIntent, 0);

        //开启线程
        messageThread = new MessageThread();
        messageThread.isRunning = true;
        messageThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        apiKey = intent.getStringExtra(APIKEY);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public class MessageBinder extends Binder {
        public MessageService getService(){
            return MessageService.this;
        }
    }

    @Override
    public void onDestroy() {
        System.exit(0);
        //或者，二选一，推荐使用System.exit(0)，这样进程退出的更干净
//        //messageThread.isRunning = false;
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * 从服务器端获取消息
     */
    class MessageThread extends Thread {
        //运行状态，下一步骤有大用
        public boolean isRunning = true;

        public void run() {
            while (isRunning) {
                if(apiKey != null){
                    //获取服务器消息
                    ZNotificationStatus notification_status = null;
                    try {
                        notification_status = getNotificationsCount();
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
    //                            getUnreadCount();
    //                    String serverMessage = getServerMessage();
                    if (notification_status != null && notification_status.getCount() > 0 && notification_status != mnotification_status) {
                        mnotification_status = notification_status;
                        //更新通知栏
                        messageNotification.setLatestEventInfo(MessageService.this, "您有" + String.valueOf(notification_status.getCount()) + "条未读消息", notification_status.getLast().getTitle() , messagePendingIntent);
                        messageNotificatioManager.notify(messageNotificationID, messageNotification);
                        //每次通知完，通知ID递增一下，避免消息覆盖掉
    //                         messageNotificationID++;
                    }
                }
                try {
                    //休息2分钟
                    Thread.sleep(Constants.Delay.GET_NOTIFICATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public ZNotificationStatus getNotificationsCount() throws IOException {
            try {
                if (apiKey == null)
                    return null;
                String url = URL_NOTIFICATIONS_STATUS + "?" + getTokenParam();
                HttpRequest request = get(url)
                        .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                        .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
                ZNotificationStatus response = JSON.parseObject(request.body(), ZNotificationStatus.class);
                return response;
            } catch (HttpRequest.HttpRequestException e) {
                throw e.getCause();
            }
        }

        private String getTokenParam() {
            return String.format(FORMAT_ACCESS_TOKEN, HEADER_PARSE_ACCESS_TOKEN, apiKey);
        }

    }
}
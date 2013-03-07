
package DD.Android.Zhaohai.service;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ui.ActivityNotifications;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import roboguice.inject.InjectExtra;
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

//    int munread_notifications_count = 0;

    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

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
                    int unread_notifications_count = 0;
                    try {
                        unread_notifications_count = getNotificationsCount();
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
    //                            getUnreadCount();
    //                    String serverMessage = getServerMessage();
                    if (unread_notifications_count > 0) {
                        //更新通知栏
                        messageNotification.setLatestEventInfo(MessageService.this, "新消息", "您有" + String.valueOf(unread_notifications_count) + "条未读消息", messagePendingIntent);
                        messageNotificatioManager.notify(messageNotificationID, messageNotification);
                        //每次通知完，通知ID递增一下，避免消息覆盖掉
    //                         messageNotificationID++;
                    }
                }
                try {
                    //休息2分钟
                    Thread.sleep(120000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

//    /**
//     * 这里以此方法为服务器Demo，仅作示例
//     *
//     * @return 返回服务器要推送的消息，否则如果为空的话，不推送
//     */
//    public String getServerMessage() {
//        return "YES!";
//    }

//    public int getUnreadCount() {
//        return 5;
//    }
        public int getNotificationsCount() throws IOException {
            try {
                if (apiKey == null)
                    return -1;
                String url = URL_NOTIFICATIONS_COUNT + "?" + getTokenParam();
                HttpRequest request = get(url)
                        .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                        .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
                int response = GSON.fromJson(request.bufferedReader(), int.class);
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
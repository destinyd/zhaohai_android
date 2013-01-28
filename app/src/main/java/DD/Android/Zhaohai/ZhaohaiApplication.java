

package DD.Android.Zhaohai;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.widget.Toast;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.github.kevinsawicki.http.HttpRequest;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.FROYO;

/**
 * Zhaohai application
 */
public class ZhaohaiApplication extends Application {

    /**
     * Create main application
     */
    public ZhaohaiApplication() {
        // Disable http.keepAlive on Froyo and below
//        //for baidu start
//        mInstance = this;
//        initEngineManager(this);
//        //for baidu end
        if (SDK_INT <= FROYO)
            HttpRequest.keepAlive(false);
    }

    /**
     * Create main application
     *
     * @param context
     */
    public ZhaohaiApplication(final Context context) {
        this();
        attachBaseContext(context);
    }

    /**
     * Create main application
     *
     * @param instrumentation
     */
    public ZhaohaiApplication(final Instrumentation instrumentation) {
        this();
        attachBaseContext(instrumentation.getTargetContext());
    }

    //for baidu map
    private static ZhaohaiApplication mInstance = null;
    public boolean m_bKeyRight = true;
    public BMapManager mBMapManager = null;

    public static final String strKey = "2C31CDF4C67E64519243B677CE001E2F347C8D9D";
    @Override
    public void onCreate() {
        super.onCreate();

//        try {
//            Class.forName("com.google.android.maps.MapActivity");
//        }
//        catch (Exception e) {
            //无google地图，初始化baidu地图
            mInstance = this;
            initEngineManager(this);
//        }
    }

    @Override
    //建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
    public void onTerminate() {
        // TODO Auto-generated method stub
        if (mBMapManager != null) {
            mBMapManager.destroy();
            mBMapManager = null;
        }
        super.onTerminate();
    }

    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(ZhaohaiApplication.getInstance().getApplicationContext(),
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }
    }

    public static ZhaohaiApplication getInstance() {
        return mInstance;
    }


    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public static class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(ZhaohaiApplication.getInstance().getApplicationContext(), "您的网络出错啦！",
                        Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(ZhaohaiApplication.getInstance().getApplicationContext(), "输入正确的检索条件！",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }

        @Override
        public void onGetPermissionState(int iError) {
            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                //授权Key错误：
//                Toast.makeText(ZhaohaiApplication.getInstance().getApplicationContext(),
//                        "请在 DemoApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG).show();
                ZhaohaiApplication.getInstance().m_bKeyRight = false;
            }
        }
    }


}

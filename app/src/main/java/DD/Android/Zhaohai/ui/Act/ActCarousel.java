

package DD.Android.Zhaohai.ui.Act;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.R.id;
import DD.Android.Zhaohai.service.MessageService;
import DD.Android.Zhaohai.ui.Ada.AdaZhaohaiPager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.view.Window;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TitlePageIndicator;
import roboguice.inject.InjectView;

import static DD.Android.Zhaohai.core.Constants.Extra.APIKEY;

/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class ActCarousel extends RoboSherlockFragmentActivity {

    @InjectView(id.tpi_header)
    private TitlePageIndicator indicator;
    @InjectView(id.vp_pages)
    private ViewPager pager;

//    @Inject private ApiKeyProvider keyProvider;
    SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_carousel);

//        pager.setAdapter(new BootstrapPagerAdapter(getResources(), getSupportFragmentManager()));
        pager.setAdapter(new AdaZhaohaiPager(getResources(), getSupportFragmentManager()));

        indicator.setViewPager(pager);
        pager.setCurrentItem(2);

        init_sliding_menu();


        bind_message_service();
    }

    @Override
    protected void onDestroy() {
        if(is_bind_message_service){
            unbindService(serviceConnection);
        }
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    boolean is_bind_message_service = false;

    private void bind_message_service() {
            try {
//                String apiKey = keyProvider.getAuthKey();
                String apiKey = DD.Android.Zhaohai.authenticator.ZhaohaiAuthenticatorActivity.getAuthToken();
                Intent serviceIntent = new Intent(ActCarousel.this, MessageService.class).putExtra(APIKEY,apiKey);
    //                        startService(serviceIntent);
                bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
                is_bind_message_service = true;
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
    }

    private void init_sliding_menu() {
        menu = new SlidingMenu(this);
        menu.setSlidingEnabled(false);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
//        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.slidingmenumain);

//        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i2) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void onPageSelected(int i) {
////                if(i == 0){
////                    menu.setSlidingEnabled(true);
////                }
////                else{
////                    menu.setSlidingEnabled(false);
////                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//        });
    }
//
//    @Override
//    protected void onDestroy() {
//        try{
//            if(messageService != null)
//                unbindService(serviceConnection);
//        }catch (Exception e){
//
//        }
//        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
//    }

    //    private static final int HELLO_ID = 1;

    MessageService messageService = null;
    public ServiceConnection serviceConnection = new ServiceConnection() {
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

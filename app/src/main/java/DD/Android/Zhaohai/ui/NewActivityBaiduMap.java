package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiApplication;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import DD.Android.Zhaohai.core.Activity;
import android.accounts.AccountsException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.baidu.location.*;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.search.*;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.google.inject.Inject;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.IOException;
import java.util.TimerTask;

import static DD.Android.Zhaohai.core.Constants.Extra.ACTIVITY;
import static DD.Android.Zhaohai.core.Constants.Extra.USER;

public class NewActivityBaiduMap extends ZhaohaiActivity {
    @InjectExtra(ACTIVITY)
    protected Activity activity;
    @InjectView(R.id.actv_q)
    protected AutoCompleteTextView actv_q;
    @InjectView(R.id.btn_search)
    protected Button btn_search;
    @InjectView(R.id.bmapView)
    protected MapView mMapView;
    @Inject
    protected ZhaohaiServiceProvider serviceProvider;

    static public NewActivityBaiduMap factory = null;

//    static MapView mMapView = null;

    private MapController mMapController = null;

    public MKMapViewListener mMapListener = null;
    FrameLayout mMapViewContainer = null;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
//    public NotifyLister mNotifyer = null;

    MyLocationOverlay myLocationOverlay = null;
    int index = 0;
    LocationData locData = null;

//    Handler mHandler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
////            Toast.makeText(NewActivityBaiduMap.this, "msg:" +msg.what, Toast.LENGTH_SHORT).show();
//        }
//
//        ;
//    };

    MKSearch mSearch = null;
    public static String mStrSuggestions[] = {};
//    PoiOverlay poi_overlay;
//    ZhaohaiPoiOverlay poi_overlay;// = new ZhaohaiPoiOverlay(this, mMapView, mSearch);

    TimerTask task = null;// new TimerTask() {
//        public void run() {
//            SuggestionSearchButtonProcess(actv_q);//每次需要执行的代码放到这里面。
//        }
//    };
    java.util.Timer timer = new java.util.Timer(true);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_baidu_map);

        factory = this;

//        mMapView = (MapView)findViewById(R.id.bmapView);

        mMapController = mMapView.getController();

        initMapView();

        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);

        //位置提醒相关代码
//        mNotifyer = new NotifyLister();
//        mNotifyer.SetNotifyLocation(42.03249652949337, 113.3129895882556, 3000, "bd09ll");//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
//        mLocClient.registerNotify(mNotifyer);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
//        option.setScanSpan(60000);//5s for test
        option.setScanSpan(5000);//5s for test
        option.setAddrType("all");
        option.setPriority(LocationClientOption.GpsFirst);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mMapView.getController().setZoom(14);
        mMapView.getController().enableClick(true);

        mMapView.displayZoomControls(true);
        mMapListener = new MKMapViewListener() {

            @Override
            public void onMapMoveFinish() {
            }

            @Override
            public void onClickMapPoi(MapPoi mapPoiInfo) {
//                String title = "";
//                if (mapPoiInfo != null) {
//                    title = mapPoiInfo.strText;
//                    Toast.makeText(NewActivityBaiduMap.this, title, Toast.LENGTH_SHORT).show();
//                }
            }
        };
        mMapView.regMapViewListener(ZhaohaiApplication.getInstance().mBMapManager, mMapListener);
        myLocationOverlay = new MyLocationOverlay(mMapView);
        set_mylocation(new LocationData());
////        if(locData.latitude !=0.0 && locData.longitude !=0.0){
//        myLocationOverlay.setData(locData);
//        mMapView.getOverlays().add(myLocationOverlay);
//        myLocationOverlay.enableCompass();
//        mMapView.refresh();

//        poi_overlay = new PoiOverlay(this, mMapView);
//        poi_overlay = new ZhaohaiPoiOverlay(this, mMapView, mSearch);
//
//        mMapView.getOverlays().add(poi_overlay);
//        AdapterView.OnItemSelectedListener selected_listener = new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                //To change body of implemented methods use File | Settings | File Templates.
//                String full_address = adapterView.getItemAtPosition(i).toString();
////                mSearch.poiSearchInCity("柳州",full_address);
////                mSearch.poiDetailSearch(full_address);
//                mSearch.poiSearchInCity("柳州", "100");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//        };

        AdapterView.OnItemClickListener click_listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MKSuggestionInfo mk_si = (MKSuggestionInfo)adapterView.getItemAtPosition(i);
//                mSearch.poiSearchInCity("柳州",full_address);
//                mSearch.poiDetailSearch(mk_si.key);
//                mSearch.poiSearchInCity("柳州", "餐厅");
                actv_q.setText(mk_si.city + mk_si.key);
//                Log.e("actv_q"," onItemSelected");
//                MKPoiInfo mpi = (MKPoiInfo)adapterView.getAdapter().getItem(i);
//                actv_q.setText(mpi.city + mpi.address);
                actv_q.requestFocus();
                actv_q.setSelection(actv_q.getText().length());
                mSearch.poiSearchInCity(mk_si.city,mk_si.city + mk_si.key);
            }
        };
//        actv_q.setOnItemSelectedListener(selected_listener);

        actv_q.setOnItemClickListener(click_listener);


        mSearch = new MKSearch();
        mSearch.init(ZhaohaiApplication.getInstance().mBMapManager, new MKSearchListener() {

            @Override
            public void onGetPoiResult(MKPoiResult mkPoiResult, int type, int error) {
                //To change body of implemented methods use File | Settings | File Templates.
                if(mkPoiResult.getNumPois() > 0){
                    ZhaohaiPoiOverlay poiOverlay = new ZhaohaiPoiOverlay(NewActivityBaiduMap.this, mMapView, mSearch);
                    poiOverlay.setData(mkPoiResult.getAllPoi());
                    mMapView.getOverlays().clear();
                    mMapView.getOverlays().add(poiOverlay);
                    set_mylocation(locData);
                    mMapView.refresh();
                    poiOverlay.animateTo();
                } else if (mkPoiResult.getCityListNum() > 0) {
                    String strInfo = "在";
                    for (int i = 0; i < mkPoiResult.getCityListNum(); i++) {
                        strInfo += mkPoiResult.getCityListInfo(i).city;
                        strInfo += ",";
                    }
                    strInfo += "找到结果";
//                    Toast.makeText(NewActivityBaiduMap.this, strInfo, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onGetTransitRouteResult(MKTransitRouteResult mkTransitRouteResult, int i) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onGetDrivingRouteResult(MKDrivingRouteResult mkDrivingRouteResult, int i) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onGetWalkingRouteResult(MKWalkingRouteResult mkWalkingRouteResult, int i) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onGetAddrResult(MKAddrInfo mkAddrInfo, int i) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onGetBusDetailResult(MKBusLineResult mkBusLineResult, int i) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onGetSuggestionResult(MKSuggestionResult mkSuggestionResult, int i) {
                if (i != 0 || mkSuggestionResult == null || mkSuggestionResult.getSuggestionNum() == 0) {
                    Toast.makeText(NewActivityBaiduMap.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
                    return;
                }
//                int nSize = mkSuggestionResult.getSuggestionNum();
//                mStrSuggestions = new String[nSize];

//                for (int j = 0; j < nSize; j++) {
//                    mStrSuggestions[j] = mkSuggestionResult.getSuggestion(j).city + mkSuggestionResult.getSuggestion(j).key;
//                }
//                ArrayAdapter<String> suggestionString = new ArrayAdapter<String>(NewActivityBaiduMap.this, android.R.layout.simple_list_item_1,mStrSuggestions);
                //COUNTRIES是一个数组，AutoCompleteTextView会将数组内容和用户输入的匹配，然后再显示出来提示用户
                //R.layout.list_item显示的是提示信息显示的内容的样式
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, COUNTRIES);
                MKSuggestionResultAdapter suggestion_adapter =
                        new MKSuggestionResultAdapter(
                                NewActivityBaiduMap.this,
                                android.R.layout.simple_list_item_1,
                                mkSuggestionResult.getAllSuggestions()
                        );
//                actv_q.setAdapter(suggestionString);
                actv_q.setAdapter(suggestion_adapter);
                actv_q.showDropDown();
//                mSuggestionList.setAdapter(suggestionString);
//                Toast.makeText(NewActivityBaiduMap.this, "suggestion callback", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onGetPoiDetailSearchResult(int i, int error) {
//                if (error != 0) {
//                    Toast.makeText(NewActivityBaiduMap.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(NewActivityBaiduMap.this, "成功，查看详情页面", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        // 设定suggestion响应
        OnClickListener clickListener1 = new OnClickListener() {
            public void onClick(View v) {
                SuggestionSearchButtonProcess(v);
            }
        };
        btn_search.setOnClickListener(clickListener1);

        actv_q.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                timer.cancel();
                if(task != null){
                    task.cancel();
                }
                task = new TimerTask() {
                    public void run() {
                        SuggestionSearchButtonProcess(actv_q);//每次需要执行的代码放到这里面。
                    }
                };
                timer.schedule(task,1500);
//                task = new TimerTask() {
//                    public void run() {
//                        SuggestionSearchButtonProcess(actv_q);//每次需要执行的代码放到这里面。
//                    }
//                };
//                SuggestionSearchButtonProcess(view);
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });


//        testUpdateClick();


//        mMapView.setOnLongClickListener();

//        testUpdateButton = (Button)findViewById(R.id.button1);
//        OnClickListener clickListener = new OnClickListener(){
//            public void onClick(View v) {
//                testUpdateClick();
//            }
//        };
//        testUpdateButton.setOnClickListener(clickListener);


    }

    void SuggestionSearchButtonProcess(View v) {
        mSearch.suggestionSearch(actv_q.getText().toString());
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMapView.onRestoreInstanceState(savedInstanceState);
    }
////
//    public void testUpdateClick() {
//        mLocClient.requestLocation();
//    }

    private void initMapView() {
        mMapView.setLongClickable(true);
        //mMapController.setMapClickEnable(true);
        //mMapView.setSatellite(false);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.zhaohai, menu);
//        return true;
//    }


    /**
     * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;

            LocationData tmp_loc_data = new LocationData();

            tmp_loc_data.latitude = location.getLatitude();
            tmp_loc_data.longitude = location.getLongitude();
            tmp_loc_data.direction = 2.0f;
            tmp_loc_data.accuracy = location.getRadius();
            tmp_loc_data.direction = location.getDerect();
//            locData.latitude = location.getLatitude();
//            locData.longitude = location.getLongitude();
//            locData.direction = 2.0f;
//            locData.accuracy = location.getRadius();
//            locData.direction = location.getDerect();
            Log.d("loctest", String.format("before: lat: %f lon: %f", location.getLatitude(), location.getLongitude()));
            // GeoPoint p = CoordinateConver.fromGcjToBaidu(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
            //  Log.d("loctest",String.format("before: lat: %d lon: %d", p.getLatitudeE6(),p.getLongitudeE6()));
            set_mylocation(tmp_loc_data);
//            if (!locData.equals(tmp_loc_data)) {
////                locData = tmp_loc_data;
//                if (tmp_loc_data.latitude != 0.0 && tmp_loc_data.longitude != 0.0) {
//                    locData = tmp_loc_data;
//                    myLocationOverlay.setData(locData);
//                    mMapView.refresh();
//                    mMapController.animateTo(new GeoPoint((int) (locData.latitude * 1e6), (int) (locData.longitude * 1e6)), mHandler.obtainMessage(1));
//                }
//            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                return;
            }
        }
    }

    public class NotifyLister extends BDNotifyListener {
        public void onNotify(BDLocation mlocation, float distance) {
        }
    }

    protected void set_mylocation(LocationData p_locData){
//        locData = new LocationData();
//        if(locData.latitude !=0.0 && locData.longitude !=0.0){
        if(p_locData != null && p_locData.latitude != 0.0 && p_locData.longitude != 0.0 && p_locData.latitude != 4.9E-324 && p_locData.longitude != 4.9E-324){
            if(locData == null){
                locData = p_locData;
                myLocationOverlay.setData(locData);
                mMapView.getOverlays().add(myLocationOverlay);
                myLocationOverlay.enableCompass();
                mMapView.refresh();
                mMapController.animateTo(new GeoPoint((int) (locData.latitude * 1e6), (int) (locData.longitude * 1e6)));//, mHandler.obtainMessage(1));
            }
            else{
                Log.e("new my location",String.format(" %s, %s",p_locData.latitude,p_locData.longitude));
                locData = p_locData;
                myLocationOverlay.setData(locData);
                mMapView.refresh();
                mMapController.animateTo(new GeoPoint((int) (locData.latitude * 1e6), (int) (locData.longitude * 1e6)));//, mHandler.obtainMessage(1));
            }
        }
    }

    protected void select(MKPoiInfo info) throws Exception{
        activity.setAddress(info.city + info.address + info.name);
        activity.setPt(info.pt);
//        try {
//            serviceProvider.getService().createActivity(activity);
//        } catch (OperationCanceledException e) {
//            this.finish();
//        }
        new CreateActivityTask().execute();
    }

    private class CreateActivityTask extends AsyncTask<Void, String,Void> {
        @Override
        protected void onPreExecute() {
            progressDialogShow(NewActivityBaiduMap.this);
            super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.
        }
        //我们加入一个检测信息的方法，打印当前在哪个线程执行的信息
//        private void printInfo(String info){
//            Log.d("WEI", info + " : Tread is " + Thread.currentThread().getName());
//        }

        //步骤2：实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发，由于这个例子并不需要传递参数，使用Void...，具体书写方式为范式书写
        protected Void/*参数3*/ doInBackground(Void...params/*参数1*/) {
            try {
                Activity p_activity =  serviceProvider.getService().createActivity(activity);
                if(p_activity != null)
                    activity = p_activity;
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
            if(activity.get_id() != null){
                startActivity(new Intent(NewActivityBaiduMap.this, ActivityActivity.class).putExtra(ACTIVITY, activity));
                finish();
            }
//            Toast.makeText(NewActivityBaiduMap.this, "Done!", Toast.LENGTH_SHORT).show();
//            NewActivityBaiduMap.this
        }
    }

}


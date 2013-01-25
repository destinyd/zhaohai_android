package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.ZhaohaiApplication;
import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiApplication;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.*;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class NewActivityBaiduMap extends ZhaohaiActivity {

    final static String TAG = "MainActivty";
    private MapView mMapView = null;

    private MapController mMapController = null;

    FrameLayout mMapViewContainer = null;
    MKMapViewListener mMapListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_baidu_map);

        ZhaohaiApplication app = (ZhaohaiApplication)this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(this);
            app.mBMapManager.init(ZhaohaiApplication.strKey,new ZhaohaiApplication.MyGeneralListener());
        }

        mMapView = (MapView)findViewById(R.id.bmapView);
        mMapController = mMapView.getController();
        initMapView();
        mMapController.enableClick(true);
        mMapController.setZoom(12);
        mMapView.displayZoomControls(true);
        //mMapView.setTraffic(true);
        //mMapView.setSatellite(true);
        mMapView.setDoubleClickZooming(true);
        mMapView.setOnTouchListener(null);

        mMapListener = new MKMapViewListener() {

            @Override
            public void onMapMoveFinish() {
            }

//            @Override
            public void onClickMapPoi(MapPoi mapPoiInfo) {
                String title = "";
                if (mapPoiInfo != null){
                    title = mapPoiInfo.strText;
                    Toast.makeText(NewActivityBaiduMap.this, title, Toast.LENGTH_SHORT).show();

                    mMapController.animateTo(mapPoiInfo.geoPt);
                }
            }
        };
        mMapView.regMapViewListener(ZhaohaiApplication.getInstance().mBMapManager, mMapListener);

        MyLocationOverlay mylocTest = new MyLocationOverlay(mMapView);
        mylocTest.enableCompass();    // 启用指南针
        mMapView.getOverlays().add(mylocTest);
        mMapView.refresh();
    }

    private void initMapView() {
        GeoPoint centerpt = mMapView.getMapCenter();
        int maxLevel = mMapView.getMaxZoomLevel();
        int zoomlevel = mMapView.getZoomLevel();
        boolean isTraffic = mMapView.isTraffic();
        boolean isSatillite = mMapView.isSatellite();
        boolean isDoubleClick = mMapView.isDoubleClickZooming();
        mMapView.setLongClickable(true);
        //mMapController.setMapClickEnable(true);
        // mMapView.setSatellite(false);
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
    protected void onDestroy() {
        mMapView.destroy();
        super.onDestroy();
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.new_activity_baidu_map, menu);
//        return true;
//    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}

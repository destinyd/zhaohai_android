package DD.Android.Zhaohai.ui.Act;
//
//import DD.Android.Zhaohai.R;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.util.Log;
//import android.widget.Toast;
//import com.google.android.maps.*;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.Serializable;
//import java.util.List;
//
//public class NewActivityGoogleMap extends MapActivity implements LocationListener
//{
//    String TAG = "NewActivityGoogleMap";
//    private LocationManager locationManager;
//
//    private MapView mapView;
//    private MapController mapController;
//    private MyLocationOverlay mylayer;
//
//    private boolean enableTool;
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_new_activity_google_map);
//        findControl();
//
//    }
//
//    private void init()
//    {
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
//        {
//
//            new AlertDialog.Builder(NewActivityGoogleMap.this).setTitle("地图工具").setMessage("您尚未开启定位服务，要前往设定页面启动定位服务吗？")
//                    .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
//            {
//
//                public void onClick(DialogInterface dialog, int which)
//                {
//                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                }
//            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
//            {
//                public void onClick(DialogInterface dialog, int which)
//                {
//                    Toast.makeText(NewActivityGoogleMap.this, "未开启定位服务，无法使用本工具!!", Toast.LENGTH_SHORT).show();
//                }
//            }).show();
//
//        }
//        else
//        {
//            enableMyLocation();
//            enableTool = true;
//        }
//
//    }
//
//    private void findControl()
//    {
//        mapView = (MapView) findViewById(R.id.map);
//        mapView.setBuiltInZoomControls(true);
//
//        mapController = mapView.getController();
//        mapController.setZoom(16);
//
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, NewActivityGoogleMap.this);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, NewActivityGoogleMap.this);
//    }
//
//    private void enableMyLocation()
//    {
//        // 定位点
//        List<Overlay> overlays = mapView.getOverlays();
//        mylayer = new MyLocationOverlay(this, mapView);
//        mylayer.enableCompass();
//        mylayer.enableMyLocation();
//        mylayer.runOnFirstFix(new Runnable()
//        {
//
//            public void run()
//            {
////                mapController.animateTo(mylayer.getMyLocation());
//                GeoPoint point = null;
//                try {
////                    GeoPoint p = mylayer.getMyLocation();
//                    point = getLocation(mylayer.getMyLocation());
//                    mapController.animateTo(point);
//                } catch (Exception e) {
////                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
////                    return;
//                }
////                Geo geo = JSON.parseObject(point, Geo.class);
//            }
//        });
//        overlays.add(mylayer);
//    }
//
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        if (enableTool)
//        {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, NewActivityGoogleMap.this);
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, NewActivityGoogleMap.this);
//            mylayer.enableMyLocation();
//            mylayer.enableCompass();
//        }
//        else
//        {
//            init();
//        }
//    }
//
//    @Override
//    protected void onPause()
//    {
//        super.onPause();
//        if (enableTool)
//        {
//            locationManager.removeUpdates(NewActivityGoogleMap.this);
//            mylayer.disableCompass();
//            mylayer.disableMyLocation();
//
//        }
//    }
//
//    @Override
//    protected boolean isRouteDisplayed()
//    {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    public void onLocationChanged(Location location)
//    {
//        GeoPoint point = null;
//        try {
//            point = getLocation(location);
//            mapController.animateTo(point);
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
////            return;
//        }
////        Geo geo = JSON.parseObject(point, Geo.class);
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider)
//    {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider)
//    {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras)
//    {
//        // TODO Auto-generated method stub
//
//    }
//
//    class Geo implements Serializable {
//        private static final long serialVersionUID = -5059507886735990676L;
//        public String name;
//        public ClassStatus Status;
//        public List<ClassPlacemark> Placemark;
//
//        class ClassStatus implements Serializable{
//            private static final long serialVersionUID = 4762621797816860191L;
//            public int code;
//            public String request;
//        }
//        class ClassPlacemark implements Serializable {
//            private static final long serialVersionUID = -2177734746946971408L;
//            public String id,address;
//            public ClassAddressDetails AddressDetails;
//            public ClassExtendedData ExtendedData;
//            public ClassPoint Point;
//            class ClassAddressDetails implements Serializable{
//                private static final long serialVersionUID = 3747428756657248305L;
//                public int Accuracy;
//                public ClassCountry Country;
//                class ClassCountry implements Serializable{
//                    private static final long serialVersionUID = -5050716465368001887L;
//                    public String CountryName,CountryNameCode;
//                    public ClassAdministrativeArea AdministrativeArea;
//                    class ClassAdministrativeArea implements Serializable{
//                        private static final long serialVersionUID = 892544353359138744L;
//                        public String AdministrativeAreaName;
//                        public ClassLocality Locality;
//                        class ClassLocality implements Serializable{
//                            private static final long serialVersionUID = 7901792239728902939L;
//                            public String LocalityName;
//                            public ClassDependentLocality DependentLocality;
//                            class ClassDependentLocality implements Serializable{
//                                private static final long serialVersionUID = 6262240266727777832L;
//                                public String DependentLocalityName;
//                                public ClassThoroughfare Thoroughfare;
//                                class ClassThoroughfare implements Serializable{
//                                    private static final long serialVersionUID = -3291157197456699358L;
//                                    public String ThoroughfareName;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            class ClassExtendedData implements Serializable{
//                private static final long serialVersionUID = -637089097963218356L;
//                public ClassLatLonBox LatLonBox;
//                class ClassLatLonBox implements Serializable{
//                    private static final long serialVersionUID = -3826801000377120224L;
//                    public Double north,south,east,west;
//                }
//            }
//            class ClassPoint implements Serializable{
//                private static final long serialVersionUID = -269528246389248213L;
//                public List<Double> coordinates;
//            }
//        }
//    }
//
//    private GeoPoint getLocation(GeoPoint p) throws Exception {
//        /** 这里采用get方法，直接将参数加到URL上 */
//        return getLocation(p.getLatitudeE6(), p.getLongitudeE6());
//    }
//
//    private GeoPoint getLocation(Location itude) throws Exception {
//        /** 这里采用get方法，直接将参数加到URL上 */
//        Log.e(TAG,"getProvider type:"+itude.getProvider());
//        return getLocation(itude.getLatitude(), itude.getLongitude());
//    }
//
//    private GeoPoint getLocation(int latE6,int lngE6) throws Exception{
//        return getLocation(latE6 / 1E6, lngE6 / 1E6);
//    }
//
//    private
////    String
//            GeoPoint
//    getLocation(double lat,double lng) throws Exception {
////        Log.e(TAG,String.format("lat,lng: %s,%s",lat,lng));
//        String resultString = "";
//
//        /** 这里采用get方法，直接将参数加到URL上 abcdefg */
//        String urlString = String.format("http://maps.google.cn/maps/geo?key=0_i8MsmqaQexpjtgacm_rTSK0eynaSXsOv-qovw&q=%s,%s", lat, lng);
//        Log.i("URL", urlString);
//
//        /** 新建HttpClient */
//        HttpClient client = new DefaultHttpClient();
//        /** 采用GET方法 */
//        HttpGet get = new HttpGet(urlString);
//        try {
//            /** 发起GET请求并获得返回数据 */
//            HttpResponse response = client.execute(get);
//            HttpEntity entity = response.getEntity();
//            BufferedReader buffReader = new BufferedReader(new InputStreamReader(entity.getContent()));
//            StringBuffer strBuff = new StringBuffer();
//            String result = null;
//            while ((result = buffReader.readLine()) != null) {
//                strBuff.append(result);
//            }
//            resultString = strBuff.toString();
////            return resultString;
//
//            /** 解析JSON数据，获得物理地址 */
//            if (resultString != null && resultString.length() > 0) {
//                JSONObject jsonobject = new JSONObject(resultString);
//                JSONArray jsonArray = new JSONArray(jsonobject.get("Placemark").toString());
//                resultString = "";
//                JSONArray arr_point =null;
////                for (int i = 0; i < jsonArray.length(); i++) {
////                    resultString = jsonArray.getJSONObject(i).getString("address");
//                    JSONArray jo = new JSONArray(jsonArray.getJSONObject(0).get("Point").toString());
////                }
//                Log.e(TAG,String.format("lat,lng: %s,%s => %s,%s",lat,lng,arr_point.get(1),arr_point.get(0)));
//                return new GeoPoint((int)((Double)arr_point.get(1)*1E6),(int)((Double)(arr_point.get(0))*1E6));
//            }
//        } catch (Exception e) {
//            throw new Exception("获取物理位置出现错误:" + e.getMessage());
//        } finally {
//            get.abort();
//            client = null;
//        }
//
////        return resultString;
//        return null;
//    }
//}

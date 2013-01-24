package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class NewActivityMap extends MapActivity {
//        extends ZhaohaiActivity {
//    @InjectExtra(Activity) protected DD.Android.Zhaohai.core.Activity activity;
//    @InjectView(R.id.map) protected Fragment map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_activity_map);
//
//        MapView mapView = (MapView)findViewById(R.id.map);
//        mapView.setBuiltInZoomControls(true);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

//        setTitle(newsItem.getTitle());

//        title.setText(newsItem.getTitle());
//        content.setText(newsItem.getContent());

    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

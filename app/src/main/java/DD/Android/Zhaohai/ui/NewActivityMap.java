package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import com.github.kevinsawicki.wishlist.Toaster;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.util.Calendar;

import static DD.Android.Zhaohai.core.Constants.Extra.Activity;

public class NewActivityMap extends ZhaohaiActivity {
//    @InjectExtra(Activity) protected DD.Android.Zhaohai.core.Activity activity;
//    @InjectView(R.id.map) protected Fragment map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_activity_map);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        setTitle(newsItem.getTitle());

//        title.setText(newsItem.getTitle());
//        content.setText(newsItem.getContent());

    }
}

package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import com.github.kevinsawicki.wishlist.Toaster;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.util.Calendar;

import static DD.Android.Zhaohai.core.Constants.Extra.Activity;

public class NewActivity extends ZhaohaiActivity {


//    @InjectExtra(Activity) protected DD.Android.Zhaohai.core.Activity putActivity;

    //    @InjectView(R.id.tv_title) protected TextView title;
    @InjectView(R.id.et_title) protected EditText et_title;
    @InjectView(R.id.et_desc) protected EditText et_desc;
    @InjectView(R.id.et_types) protected EditText et_types;
    @InjectView(R.id.dp_started_at) protected DatePicker dp_started_at;
    @InjectView(R.id.tp_started_at) protected TimePicker tp_started_at;
    @InjectView(R.id.btn_to_map) protected Button btn_to_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tp_started_at.setIs24HourView(true);

        init_now();

        btn_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_title.getText().length() > 0 && et_desc.getText().length() > 0){
                    Activity activity = new Activity();
                    activity.setTitle(et_title.getText().toString());
                    activity.setDesc(et_desc.getText().toString());
                    activity.setTitle(et_title.getText().toString());
                    Calendar c = Calendar.getInstance();
                    c.set(dp_started_at.getYear(),dp_started_at.getMonth(),dp_started_at.getDayOfMonth(),tp_started_at.getCurrentHour(),tp_started_at.getCurrentMinute());
                    activity.setStarted_at(c.getTime());

                    startActivity(new Intent(NewActivity.this, NewActivityMap.class).putExtra(Activity, activity));
                }
                else {
                    Toaster.showLong(NewActivity.this, R.string.require_field_warning);
                }
            }
        });

//        setTitle(newsItem.getTitle());

//        title.setText(newsItem.getTitle());
//        content.setText(newsItem.getContent());

    }


    private void init_now() {
        Calendar calendar = Calendar.getInstance();
        dp_started_at.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),null);
        tp_started_at.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        tp_started_at.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }


}

package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.github.kevinsawicki.wishlist.Toaster;
import roboguice.inject.InjectView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static DD.Android.Zhaohai.core.Constants.Extra.ACTIVITY;

public class NewActivityFragment extends SimpleFragment {

//    @Inject protected ZhaohaiServiceProvider serviceProvider;

    @InjectView(R.id.et_title) protected EditText et_title;
    @InjectView(R.id.et_desc) protected EditText et_desc;
    @InjectView(R.id.et_types) protected EditText et_types;
    @InjectView(R.id.tv_started_at) protected TextView tv_started_at;
    @InjectView(R.id.btn_to_map) protected Button btn_to_map;
    @InjectView(R.id.btn_edit_started_at) protected Button btn_edit_started_at;

    DD.Android.Zhaohai.core.Activity activity = new DD.Android.Zhaohai.core.Activity();
    Calendar calendar_started_at;

    LayoutInflater inflater;
//    SelectTimeDialog dialog = null;
    AlertDialog adialog  = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        setEmptyText(R.string.no_users);

//        tp_started_at.setIs24HourView(true);

//        dp_started_at.setBackgroundColor(Color.BLACK);
//        tp_started_at.setBackgroundColor(Color.BLACK);

        init_now();

        inflater = LayoutInflater.from(getActivity());

        btn_edit_started_at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View textEntryView = inflater.inflate(R.layout.select_time_dialog, null);
//
                DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int which) {
//                        calendar_started_at = adialog.getLayoutInflater()
                        DatePicker dp_time = (DatePicker)textEntryView.findViewById(R.id.dp_time);
                        TimePicker tp_time = (TimePicker)textEntryView.findViewById(R.id.tp_time);
                        Calendar calendar = new GregorianCalendar(dp_time.getYear(),dp_time.getMonth(),dp_time.getDayOfMonth(),tp_time.getCurrentHour(),tp_time.getCurrentHour());
                        calendar_started_at= calendar;
                        activity.setStarted_at(calendar_started_at.getTime());
                        tv_started_at.setText(sdf.format(calendar_started_at.getTime()));
                        adialog.dismiss();
                    }
                };
//
                adialog = new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.set_started_at))
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(textEntryView)
                        .setNegativeButton(getResources().getString(android.R.string.cancel), null)
                        .setPositiveButton(getResources().getString(android.R.string.ok),OkClick)
                        .create();
//                DatePicker dp_time = (DatePicker)textEntryView.findViewById(R.id.dp_time);
                TimePicker tp_time = (TimePicker)textEntryView.findViewById(R.id.tp_time);

                tp_time.setIs24HourView(true);
                adialog.show();

//                dialog =
//                    new SelectTimeDialog.Builder(getActivity())
//                            .setTitle(getResources().getString(R.string.set_started_at))
//                            .setIcon(android.R.drawable.ic_dialog_info)
//                            .setNegativeButton(getResources().getString(android.R.string.cancel), null)
//                            .setPositiveButton(getResources().getString(android.R.string.ok), OkClick)
//                            .create();
//                dialog.show();
//                dialog.set_init_time(calendar_started_at);

//                dialog = SelectTimeDialog.getInstant(getActivity());
//                dialog.setTitle(getResources().getString(R.string.set_started_at));
////                    Dialog dialog = new Dialog(getActivity());
////                    dialog.setContentView(R.layout.select_time_dialog);
////
////                    dialog.setTitle(getResources().getString(R.string.set_started_at));
//                    dialog.show();
            }
        });

        btn_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_title.getText().length() > 0){
                    activity.setTitle(et_title.getText().toString());
                    activity.setDesc(et_desc.getText().toString());
                    activity.setTitle(et_title.getText().toString());
//                    Calendar c = Calendar.getInstance();
//                    c.set(dp_started_at.getYear(),dp_started_at.getMonth(),dp_started_at.getDayOfMonth(),tp_started_at.getCurrentHour(),tp_started_at.getCurrentMinute());
                    activity.setStarted_at(calendar_started_at.getTime());

//                    try {
//                        Class.forName("com.google.android.maps.MapActivity");
//                        startActivity(new Intent(getActivity(), NewActivityGoogleMap.class).putExtra(Activity, activity));
//                    }
//                    catch (Exception e) {
//                        Toast
//                                .makeText(getActivity(),
//                                        getResources().getString(R.string.no_google_map),
//                                        Toast.LENGTH_LONG)
//                                .show();
                        startActivity(new Intent(getActivity(), NewActivityBaiduMap.class).putExtra(ACTIVITY, activity));
//                    }
                }
                else {
                    Toaster.showLong(getActivity(), R.string.require_field_warning);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_activity, null);
    }



    private void init_now() {
        calendar_started_at = Calendar.getInstance();
//        Calendar calendar = Calendar.getInstance();
//        dp_started_at.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),null);
//        tp_started_at.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
//        tp_started_at.setCurrentMinute(calendar.get(Calendar.MINUTE));
        tv_started_at.setText(sdf.format(calendar_started_at.getTime()));
    }
}

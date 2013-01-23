package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.github.kevinsawicki.wishlist.Toaster;
import roboguice.inject.InjectView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static DD.Android.Zhaohai.core.Constants.Extra.Activity;

public class SelectTimeDialog extends AlertDialog {

//    @InjectView(R.id.dp_time) protected DatePicker dp_time;
//    @InjectView(R.id.tp_time) protected TimePicker tp_time;
    protected DatePicker dp_time;
    protected TimePicker tp_time;

    Calendar init_time = null;
//    Date init_time = null;

//    public static SelectTimeDialog getInstant(Context context){
//        return getInstant(context,Calendar.getInstance());
//    }
//
//    public static SelectTimeDialog getInstant(Context context,Calendar time){
//        SelectTimeDialog s = setTime(context, time);
//        return s;
//    }

    public void setTime(Calendar time) {
        set_init_time(time);
        setOnCancelListener(null);
    }

//    public static SelectTimeDialog getInstant(Context context,Calendar time,String title){
//        SelectTimeDialog s = setTime(context, time);
//        s.setTitle(title);
//        return s;
//    }

    protected SelectTimeDialog(Context context) {
        super(context);
    }

    protected SelectTimeDialog(Context context, int theme) {
        super(context, theme);
    }

    protected SelectTimeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected void  set_init_time(Calendar time){
        init_time = time;
        init_now();
    }

//    protected void  init_time(){
//        init_time = Calendar.getInstance();
//        init_now();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_time_dialog);
        dp_time = (DatePicker)findViewById(R.id.dp_time);
        tp_time = (TimePicker)findViewById(R.id.tp_time);
        init_now();
    }

    public static class Builder extends AlertDialog.Builder {

        private SelectTimeDialog dialog;

        public Builder(Context context) {
            super(context);

            dialog = new SelectTimeDialog(context);
        }

        public Builder(Context context, int theme) {
            super(context);
            dialog = new SelectTimeDialog(context, theme);
        }

        @Override
        public SelectTimeDialog create() {
            return dialog;
        }

        @Override
        public Builder setMessage(CharSequence message) {
            dialog.setMessage(message);
            return this;
        }

        @Override
        public Builder setTitle(CharSequence title) {
            dialog.setTitle(title);
            return this;
        }

        @Override
        public Builder setPositiveButton(CharSequence text,
                                         OnClickListener listener) {
            dialog.setButton(BUTTON_POSITIVE, text, listener);
            return this;
        }

        @Override
        public Builder setNegativeButton(CharSequence text,
                                         OnClickListener listener) {
            dialog.setButton(BUTTON_NEGATIVE, text, listener);
            return this;
        }

        @Override
        public Builder setIcon(int iconId) {
            dialog.setIcon(iconId);
            return this;
        }
    }


    protected void init_now() {
        Calendar calendar = new GregorianCalendar();
        if(init_time == null)
            init_time = calendar;
        dp_time.init(
                init_time.get(Calendar.YEAR),
                init_time.get(Calendar.MONTH),
                init_time.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener(){
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                        // Notify the user.
                    }
                }
        );
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        dp_time.setMinDate(calendar.getTimeInMillis());
        calendar.add(Calendar.MONTH,1);
        dp_time.setMaxDate(calendar.getTimeInMillis()); // max date is next month today

        tp_time.setIs24HourView(true);
        tp_time.setCurrentHour(init_time.get(Calendar.HOUR_OF_DAY));
        tp_time.setCurrentMinute(init_time.get(Calendar.MINUTE));

    }

    public Calendar getTime(){
        return new GregorianCalendar(dp_time.getYear(),dp_time.getMonth(),dp_time.getDayOfMonth(),tp_time.getCurrentHour(),tp_time.getCurrentHour());
    }


}

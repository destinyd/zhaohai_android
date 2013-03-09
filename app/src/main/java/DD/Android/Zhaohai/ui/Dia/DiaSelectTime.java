package DD.Android.Zhaohai.ui.Dia;

import DD.Android.Zhaohai.R;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DiaSelectTime extends AlertDialog {

//    @InjectView(R.id.dp_time) protected DatePicker dp_time;
//    @InjectView(R.id.tp_time) protected TimePicker tp_time;
    protected DatePicker dp_time;
    protected TimePicker tp_time;

    Calendar init_time = null;
//    Date init_time = null;

//    public static DiaSelectTime getInstant(Context context){
//        return getInstant(context,Calendar.getInstance());
//    }
//
//    public static DiaSelectTime getInstant(Context context,Calendar time){
//        DiaSelectTime s = setTime(context, time);
//        return s;
//    }

    public void setTime(Calendar time) {
        set_init_time(time);
        setOnCancelListener(null);
    }

//    public static DiaSelectTime getInstant(Context context,Calendar time,String title){
//        DiaSelectTime s = setTime(context, time);
//        s.setTitle(title);
//        return s;
//    }

    protected DiaSelectTime(Context context) {
        super(context);
    }

    protected DiaSelectTime(Context context, int theme) {
        super(context, theme);
    }

    protected DiaSelectTime(Context context, boolean cancelable, OnCancelListener cancelListener) {
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

        setContentView(R.layout.dialog_select_time);
        dp_time = (DatePicker)findViewById(R.id.dp_time);
        tp_time = (TimePicker)findViewById(R.id.tp_time);
        init_now();
    }

    public static class Builder extends AlertDialog.Builder {

        private DiaSelectTime dialog;

        public Builder(Context context) {
            super(context);

            dialog = new DiaSelectTime(context);
        }

        public Builder(Context context, int theme) {
            super(context);
            dialog = new DiaSelectTime(context, theme);
        }

        @Override
        public DiaSelectTime create() {
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
                        // Notify the activity.
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

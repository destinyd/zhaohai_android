package DD.Android.Zhaohai.core;

import java.text.SimpleDateFormat;
import java.util.*;

import static DD.Android.Zhaohai.core.Constants.Other.DEFAULT_TIME_ZONE;
import static DD.Android.Zhaohai.core.Constants.Other.THIS_MONTH_DATE_FORMAT;
import static DD.Android.Zhaohai.core.Constants.Other.TODAY_DATE_FORMAT;
import static DD.Android.Zhaohai.core.Constants.Other.OTHER_DATE_FORMAT;

/**
 * Created with IntelliJ IDEA.
 * User: dd
 * Date: 13-3-9
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public class CuteTime {
    static public String format(Date date){
        if(is_in_today(date))
        {
            TODAY_DATE_FORMAT.setTimeZone(DEFAULT_TIME_ZONE);
            return TODAY_DATE_FORMAT.format(date);
        }
        else if(is_in_this_month(date)){
            Locale locale = Locale.getDefault();
            THIS_MONTH_DATE_FORMAT.setTimeZone(DEFAULT_TIME_ZONE);
            return SimpleDateFormat.getDateInstance(
                    SimpleDateFormat.MONTH_FIELD, locale).format(date);
//            return THIS_MONTH_DATE_FORMAT.format(date);
        }
        else{
            OTHER_DATE_FORMAT.setTimeZone(DEFAULT_TIME_ZONE);
            return OTHER_DATE_FORMAT.format(date);
        }
    }

    static public String format(){
        Date dNow = new Date();
        return format(dNow);
    }

    private static boolean is_in_this_month(Date dNow) {
        return dNow.after(start_of_this_month()) && dNow.before(start_of_next_month());
    }

    private static boolean is_in_today(Date dNow) {
        return dNow.after(start_of_today()) && dNow.before(start_of_tomorrow());
    }

    static public Date start_of_this_month(){
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.DAY_OF_MONTH, 0);
        return c.getTime();
    }

    static public Date start_of_next_month(){
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.DAY_OF_MONTH, 0);
        c.add(Calendar.MONTH, 1);
        return c.getTime();
    }

    static public Date start_of_today(){
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    static public Date start_of_tomorrow(){
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(c.DAY_OF_MONTH, 1);
        return c.getTime();
    }
}

package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.widget.Toast;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKSearch;

public class ZhaohaiPoiOverlay extends PoiOverlay {

    static MKPoiInfo last_info = null;
    AlertDialog adialog = null;

//    static String str_format_alter_message = "";

    MKSearch mSearch;

    public ZhaohaiPoiOverlay(Activity activity, MapView mapView, MKSearch search) {
        super(activity, mapView);
        mSearch = search;
    }

    @Override
    protected boolean onTap(int i) {
        super.onTap(i);
        MKPoiInfo info = getPoi(i);
        if(info.equals(last_info)){
            String str_format_alter_message = NewActivityBaiduMap.factory.getResources().getString(R.string.format_alter_message);
            String str_alter_message = String.format(str_format_alter_message,info.name,info.address);
            if(adialog != null)
                adialog.dismiss();
            adialog = new AlertDialog.Builder(NewActivityBaiduMap.factory)
                    .setTitle(getString(R.string.alter_activity_point))
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage(str_alter_message)
                    .setNegativeButton(getString(android.R.string.cancel), null)
                    .setPositiveButton(getString(android.R.string.ok),OkClick)
                    .show();
//
        }
        else{
            last_info = info;
            Toast.makeText(NewActivityBaiduMap.factory,
                    String.format(getString(R.string.select_point_name),info.name),
                    Toast.LENGTH_SHORT).show();
        }
//        if (info.hasCaterDetails) {
//            mSearch.poiDetailSearch(info.uid);
//        }
        return true;
    }

    //
    DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface d, int which) {
//                        calendar_started_at = adialog.getLayoutInflater()
            try {
                NewActivityBaiduMap.factory.select(last_info);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            adialog.dismiss();
        }
    };

    protected Resources getResources(){
        return NewActivityBaiduMap.factory.getResources();
    }

    protected String getString(int id){
        return getResources().getString(id);
    }
    
}

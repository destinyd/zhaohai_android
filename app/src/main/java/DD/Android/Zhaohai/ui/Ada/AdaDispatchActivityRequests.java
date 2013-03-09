package DD.Android.Zhaohai.ui.Ada;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.ActivityRequest;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdaDispatchActivityRequests extends BaseAdapter {

    private Context mContext;
    private List<ActivityRequest> mRequestList;
    private int mResource;
    private LayoutInflater mInflater;

    public AdaDispatchActivityRequests(Context context, List<ActivityRequest> personList,
                                       int resource) {
        mContext = context;
        mRequestList = personList;
        mResource = resource;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mRequestList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRequestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(mResource, parent, false);
        }
        TextView tvUserName = (TextView) convertView.findViewById(
                R.id.dispatch_item_request_user_name);
        TextView tvText = (TextView) convertView.findViewById(
                R.id.dispatch_item_request_text);
        ActivityRequest activityRequest = mRequestList.get(position);
//        tvUserName.setText(activityRequest.getUser_id());
        tvText.setText(activityRequest.getText());
//        ckbItem.setChecked(activityRequest.isChecked());
//        ckbItem.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                mRequestList.get(position).setChecked(ckbItem.isChecked());//保存checkbox状态至位置对应的列表对象User中
//
//            }
//        });
        activityRequest = null;
        return convertView;
    }

}
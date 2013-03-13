package DD.Android.Zhaohai.ui.Ada;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.PrettyDateFormat;
import DD.Android.Zhaohai.core.ZNotification;
import DD.Android.Zhaohai.ui.Act.ActNotification;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import static DD.Android.Zhaohai.core.Constants.Extra.NOTIFICATION;

public class AdaNotifications extends BaseAdapter {

    private Context mContext;
    private List<ZNotification> mNotificationList;
    private int mResource;
    private LayoutInflater mInflater;

    public AdaNotifications(Context context, List<ZNotification> personList,
                            int resource) {
        mContext = context;
        mNotificationList = personList;
        mResource = resource;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mNotificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNotificationList.get(position);
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
        RelativeLayout rl = (RelativeLayout)convertView.findViewById(R.id.dispatch_item_notificaiton);
        TextView tvTime = (TextView) convertView.findViewById(
                R.id.dispatch_item_notification_time);
        TextView tvText = (TextView) convertView.findViewById(
                R.id.dispatch_item_notification_text);
        ZNotification notification = mNotificationList.get(position);

        if(notification.getRead_at() != null)
            rl.setBackgroundColor(R.color.background_selected);

        tvText.setText(notification.getTitle());
        tvTime.setText(PrettyDateFormat.defaultFormat(notification.getCreated_at()));

        rl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mContext.startActivity(
                        new Intent(mContext, ActNotification.class).
                                putExtra(NOTIFICATION, mNotificationList.get(position))
                                );

            }
        });
        return convertView;
    }

}
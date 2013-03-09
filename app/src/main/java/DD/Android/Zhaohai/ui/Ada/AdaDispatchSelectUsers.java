package DD.Android.Zhaohai.ui.Ada;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.User;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 *
 * <dl>
 * <dt>AdaDispatchSelectUsers.java</dt>
 * <dd>Description: 选择用户界面Adapter类</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>CreateDate: 2011-10-26</dd>
 * </dl>
 *
 */
public class AdaDispatchSelectUsers extends BaseAdapter {

    private Context mContext;
    private List<User> mUserList;
    private int mResource;
    private LayoutInflater mInflater;

    public AdaDispatchSelectUsers(Context context, List<User> personList,
                                  int resource) {
        mContext = context;
        mUserList = personList;
        mResource = resource;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserList.get(position);
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
                R.id.dispatch_item_select_name);
        final CheckBox ckbItem = (CheckBox) convertView.findViewById(
                R.id.dispatch_item_select_user_ckb);
        User person = mUserList.get(position);
        tvUserName.setText(person.getName());
        System.out.println(person.getName());
        ckbItem.setChecked(person.isChecked());
        ckbItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mUserList.get(position).setChecked(ckbItem.isChecked());//保存checkbox状态至位置对应的列表对象User中

            }
        });
        person = null;
        return convertView;
    }

}
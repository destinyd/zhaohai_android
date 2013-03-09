package DD.Android.Zhaohai.ui.Ada;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.Activity;
import DD.Android.Zhaohai.core.CuteTime;
import DD.Android.Zhaohai.ui.Ada.AdaAlternatingColorList;
import android.view.LayoutInflater;

import java.util.List;

public class AdaActivities extends AdaAlternatingColorList<Activity> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public AdaActivities(LayoutInflater inflater, List<Activity> items,
                         boolean selectable) {
        super(R.layout.item_activities, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public AdaActivities(LayoutInflater inflater, List<Activity> items) {
        super(R.layout.item_activities, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_name, R.id.tv_date };
    }

    @Override
    protected void update(int position, Activity item) {
        super.update(position, item);

        setText(R.id.tv_name, item.getTitle());
        setText(R.id.tv_date, CuteTime.format(item.getStarted_at()));
    }
}
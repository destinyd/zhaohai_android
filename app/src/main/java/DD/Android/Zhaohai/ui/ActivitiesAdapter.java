package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.Activity;
import android.view.LayoutInflater;

import java.util.List;

public class ActivitiesAdapter extends AlternatingColorListAdapter<Activity> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public ActivitiesAdapter(LayoutInflater inflater, List<Activity> items,
                             boolean selectable) {
        super(R.layout.activities_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public ActivitiesAdapter(LayoutInflater inflater, List<Activity> items) {
        super(R.layout.activities_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_name, R.id.tv_date };
    }

    @Override
    protected void update(int position, Activity item) {
        super.update(position, item);

        setText(R.id.tv_name, item.getTitle());
        setText(R.id.tv_date, item.getStarted_at().toLocaleString());
    }
}
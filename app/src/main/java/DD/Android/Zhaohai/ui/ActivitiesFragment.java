package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.google.inject.Inject;

import java.util.List;

import static DD.Android.Zhaohai.core.Constants.Extra.ACTIVITY;

public class ActivitiesFragment extends ItemListFragment<DD.Android.Zhaohai.core.Activity> {

    @Inject protected ZhaohaiServiceProvider serviceProvider;

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.checkins_list_item_labels, null));
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);

        super.onDestroyView();
    }

    @Override
    public Loader<List<DD.Android.Zhaohai.core.Activity>> onCreateLoader(int id, Bundle args) {
        final List<DD.Android.Zhaohai.core.Activity> initialItems = items;
        return new ThrowableLoader<List<DD.Android.Zhaohai.core.Activity>>(getActivity(), items) {

            @Override
            public List<DD.Android.Zhaohai.core.Activity> loadData() throws Exception {
                try {
                    return serviceProvider.getService().getActivities();
                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };
    }

    @Override
    protected SingleTypeAdapter<DD.Android.Zhaohai.core.Activity> createAdapter(List<DD.Android.Zhaohai.core.Activity> items) {
        return new ActivitiesAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        DD.Android.Zhaohai.core.Activity activity = ((DD.Android.Zhaohai.core.Activity) l.getItemAtPosition(position));

//        GeoPoint p = new GeoPoint(x, y);
//        GeoPoint p2 = CoordinateConvert.bundleDecode(CoordinateConvert.fromWgs84ToBaidu(p));

//        String uri = String.format("geo:%s,%s?q=%s",
//                activity.getLat(),
//                activity.getLng(),
//                activity.getAddress());

        // Show a chooser that allows the ABUser to decide how to display this data, in this case, map data.
//        startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)), getString(R.string.choose)));
        startActivity(new Intent(getActivity(), ActivityActivity.class).putExtra(ACTIVITY, activity));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_activities;
    }
}

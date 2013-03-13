package DD.Android.Zhaohai.ui.Frag;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import DD.Android.Zhaohai.ui.Act.ActActivity;
import DD.Android.Zhaohai.ui.Ada.AdaActivities;
import DD.Android.Zhaohai.ui.ThrowableLoader;
import android.accounts.AccountsException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.costum.android.widget.LoadMoreListView;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.kevinsawicki.wishlist.Toaster;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.List;

import static DD.Android.Zhaohai.core.Constants.Extra.ACTIVITY;

public class FragActivities extends FragItemList<DD.Android.Zhaohai.core.Activity> {

    @Inject protected ZhaohaiServiceProvider serviceProvider;

//    List<DD.Android.Zhaohai.core.Activity> mItems = items;

    @Override
    protected void configureList(Activity activity, LoadMoreListView listView) {
        super.configureList(activity, listView);

//        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.labels_activities, null));
        getListView().setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new LoadDataTask().execute();
            }
        });
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);

        super.onDestroyView();
    }

    @Override
    public Loader<List<DD.Android.Zhaohai.core.Activity>> onCreateLoader(int id, Bundle args) {
        final List<DD.Android.Zhaohai.core.Activity> initialItems = items;
        mPage = 1;
        mItemsCount = 0;
        return new ThrowableLoader<List<DD.Android.Zhaohai.core.Activity>>(getActivity(), items) {

            @Override
            public List<DD.Android.Zhaohai.core.Activity> loadData() throws Exception {
                try {
                    mItems = serviceProvider.getService().getActivities(mPage);
                    mItemsCount = mItems.size();
                    return mItems;
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
        return new AdaActivities(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(LoadMoreListView l, View v, int position, long id) {
        DD.Android.Zhaohai.core.Activity activity = ((DD.Android.Zhaohai.core.Activity) l.getItemAtPosition(position));

//        GeoPoint p = new GeoPoint(x, y);
//        GeoPoint p2 = CoordinateConvert.bundleDecode(CoordinateConvert.fromWgs84ToBaidu(p));

//        String uri = String.format("geo:%s,%s?q=%s",
//                activity.getLat(),
//                activity.getLng(),
//                activity.getAddress());

        // Show a chooser that allows the ABUser to decide how to display this data, in this case, map data.
//        startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)), getString(R.string.choose)));
        startActivity(new Intent(getActivity(), ActActivity.class).putExtra(ACTIVITY, activity));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_activities;
    }

    private class LoadDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            if (isCancelled()) {
                return null;
            }

//            getListAdapter().getWrappedAdapter().setItems();

            try {
                mPage++;
                List<DD.Android.Zhaohai.core.Activity> activities = serviceProvider.getService().getActivities(mPage);
                if(activities == null || activities.size() == 0){
                    mPage--;
                }
                else{
                    mItems.addAll(activities);
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (AccountsException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);

            if(mItems.size() > mItemsCount){
                onLoadFinished(null,mItems);
                mItemsCount = mItems.size();
            }
            else{
                Toaster.showLong(getActivity(), "没有新数据了.");
            }
//            mListItems.add("Added after load more");

//            getListAdapter().getWrappedAdapter().setItems(mItems.toArray());

            // We need notify the adapter that the data have been changed
//            synchronized(mItems){
//                getListAdapter().getWrappedAdapter().notifyAll();
////                getListAdapter().notify();
//            }

            // Call onLoadMoreComplete when the LoadMore task, has finished
            getListView().onLoadMoreComplete();
        }

        @Override
        protected void onCancelled() {
            // Notify the loading more operation has finished
            getListView().onLoadMoreComplete();
        }
    }
}

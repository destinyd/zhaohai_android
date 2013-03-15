package DD.Android.Zhaohai.ui.Frag;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import DD.Android.Zhaohai.core.User;
import DD.Android.Zhaohai.core.UserAvatarLoader;
import DD.Android.Zhaohai.ui.Act.ActFriend;
import DD.Android.Zhaohai.ui.Ada.AdaUsers;
import DD.Android.Zhaohai.ui.ThrowableLoader;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import com.costum.android.widget.LoadMoreListView;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.google.inject.Inject;

import java.util.List;

import static DD.Android.Zhaohai.core.Constants.Extra.FRIEND;

public class FragFriend extends FragItemList<User> {

    @Inject protected ZhaohaiServiceProvider serviceProvider;
    @Inject private UserAvatarLoader avatars;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_friend);
    }

    @Override
    protected void configureList(Activity activity, LoadMoreListView listView) {
        super.configureList(activity, listView);

//        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.labels_friend, null));
//        getListView().setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                new LoadDataTask().execute();
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);

        super.onDestroyView();
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        final List<User> initialItems = items;
//        mPage = 1;
//        mItemsCount = 0;
        return new ThrowableLoader<List<User>>(getActivity(), items) {
            @Override
            public List<User> loadData() throws Exception {

                try {
                    mItems = serviceProvider.getService().getFriend(
//                            mPage
                    );
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

    public void onListItemClick(LoadMoreListView l, View v, int position, long id) {
        User user = ((User) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), ActFriend.class).putExtra(FRIEND, user));
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_friend;
    }

    @Override
    protected SingleTypeAdapter<User> createAdapter(List<User> items) {
        return new AdaUsers(getActivity().getLayoutInflater(), items, avatars);
    }

//    private class LoadDataTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            if (isCancelled()) {
//                return null;
//            }
//            try {
//                mPage++;
//                List<User> friend = serviceProvider.getService().getFriend(mPage);
//                if(friend == null || friend.size() == 0){
//                    mPage--;
//                }
//                else{
//                    mItems.addAll(friend);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            } catch (AccountsException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            super.onPostExecute(result);
//
//            if(mItems.size() > mItemsCount){
//                onLoadFinished(null,mItems);
//                mItemsCount = mItems.size();
//            }
//            else{
//                Toaster.showLong(getActivity(), "没有新数据了.");
//            }
//            getListView().onLoadMoreComplete();
//        }
//
//        @Override
//        protected void onCancelled() {
//            // Notify the loading more operation has finished
//            getListView().onLoadMoreComplete();
//        }
//    }
}

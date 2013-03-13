package DD.Android.Zhaohai.ui.Frag;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ZhaohaiServiceProvider;
import DD.Android.Zhaohai.core.User;
import DD.Android.Zhaohai.core.UserAvatarLoader;
import DD.Android.Zhaohai.ui.Act.ActUser;
import DD.Android.Zhaohai.ui.Ada.AdaUsers;
import DD.Android.Zhaohai.ui.ThrowableLoader;
import android.accounts.AccountsException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import com.costum.android.widget.LoadMoreListView;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.kevinsawicki.wishlist.Toaster;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static DD.Android.Zhaohai.core.Constants.Extra.USER;

public class FragUsers extends FragItemList<User> {

    @Inject protected ZhaohaiServiceProvider serviceProvider;
    @Inject private UserAvatarLoader avatars;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_users);
    }

    @Override
    protected void configureList(Activity activity, LoadMoreListView listView) {
        super.configureList(activity, listView);

//        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter().addHeader(activity.getLayoutInflater()
                .inflate(R.layout.labels_users, null));
        getListView().setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new LoadDataTask().execute();
            }
        });
    }



    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        final List<User> initialItems = items;
        return new ThrowableLoader<List<User>>(getActivity(), items) {
            @Override
            public List<User> loadData() throws Exception {

                try {
                    mItems = serviceProvider.getService().getUsers(mPage);
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

        startActivity(new Intent(getActivity(), ActUser.class).putExtra(USER, user));
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_users;
    }

    @Override
    protected SingleTypeAdapter<User> createAdapter(List<User> items) {
        return new AdaUsers(getActivity().getLayoutInflater(), items, avatars);
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
                List<User> users = serviceProvider.getService().getUsers(mPage);
                if(users == null || users.size() == 0){
                    mPage--;
                }
                else{
                    mItems.addAll(users);
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

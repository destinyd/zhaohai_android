package DD.Android.Zhaohai.ui;

import static DD.Android.Zhaohai.core.Constants.Extra.ABUSER;

import DD.Android.Zhaohai.core.ABUser;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import DD.Android.Zhaohai.BootstrapServiceProvider;
import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.AvatarLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.google.inject.Inject;

import java.util.Collections;
import java.util.List;

public class UserListFragment  extends ItemListFragment<ABUser> {

    @Inject private BootstrapServiceProvider serviceProvider;
    @Inject private AvatarLoader avatars;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_users);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter().addHeader(activity.getLayoutInflater()
                .inflate(R.layout.user_list_item_labels, null));
    }



    @Override
    public Loader<List<ABUser>> onCreateLoader(int id, Bundle args) {
        final List<ABUser> initialItems = items;
        return new ThrowableLoader<List<ABUser>>(getActivity(), items) {
            @Override
            public List<ABUser> loadData() throws Exception {

                try {
                    List<ABUser> latest = serviceProvider.getService().getUsers();
                    if (latest != null)
                        return latest;
                    else
                        return Collections.emptyList();
                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };

    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        ABUser ABUser = ((ABUser) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), UserActivity.class).putExtra(ABUSER, ABUser));
    }

    @Override
    public void onLoadFinished(Loader<List<ABUser>> loader, List<ABUser> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_users;
    }

    @Override
    protected SingleTypeAdapter<ABUser> createAdapter(List<ABUser> items) {
        return new UserListAdapter(getActivity().getLayoutInflater(), items, avatars);
    }
}

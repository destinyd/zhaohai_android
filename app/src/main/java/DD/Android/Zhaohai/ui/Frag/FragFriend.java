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
import android.widget.ListView;
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

        setEmptyText(R.string.no_friends);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.labels_friend, null));
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);

        super.onDestroyView();
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        final List<User> initialItems = items;
        return new ThrowableLoader<List<User>>(getActivity(), items) {
            @Override
            public List<User> loadData() throws Exception {

                try {
                    List<User> latest = serviceProvider.getService().getFriend();
                    return latest;
//                    if (latest != null)
//                        return latest;
//                    else
//                        return Collections.emptyList();
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
        User user = ((User) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), ActFriend.class).putExtra(FRIEND, user));
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_friends;
    }

    @Override
    protected SingleTypeAdapter<User> createAdapter(List<User> items) {
        return new AdaUsers(getActivity().getLayoutInflater(), items, avatars);
    }
}

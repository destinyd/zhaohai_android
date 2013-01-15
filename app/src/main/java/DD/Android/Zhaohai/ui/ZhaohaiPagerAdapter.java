

package DD.Android.Zhaohai.ui;

import DD.Android.Zhaohai.R;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Pager adapter
 */
public class ZhaohaiPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public ZhaohaiPagerAdapter(Resources resources, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
        case 0:
            FriendFragment friendFragment = new FriendFragment();
            friendFragment.setArguments(bundle);
            return friendFragment;
        case 1:
            UsersFragment usersFragment = new UsersFragment();
            usersFragment.setArguments(bundle);
            return usersFragment;
        case 2:
            ActivitiesFragment activitiesFragment = new ActivitiesFragment();
            activitiesFragment.setArguments(bundle);
            return activitiesFragment;
        default:
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
            return resources.getString(R.string.page_friends);
        case 1:
            return resources.getString(R.string.page_users);
        case 2:
            return resources.getString(R.string.page_activities);
        default:
            return null;
        }
    }
}

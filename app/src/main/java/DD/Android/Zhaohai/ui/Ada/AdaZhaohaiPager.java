

package DD.Android.Zhaohai.ui.Ada;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ui.Frag.FragActivities;
import DD.Android.Zhaohai.ui.Frag.FragFriend;
import DD.Android.Zhaohai.ui.Frag.FragNewActivity;
import DD.Android.Zhaohai.ui.Frag.FragUsers;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Pager adapter
 */
public class AdaZhaohaiPager extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public AdaZhaohaiPager(Resources resources, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
        case 0:
            FragFriend friendFragment = new FragFriend();
            friendFragment.setArguments(bundle);
            return friendFragment;
        case 1:
            FragUsers usersFragment = new FragUsers();
            usersFragment.setArguments(bundle);
            return usersFragment;
        case 2:
            FragActivities activitiesFragment = new FragActivities();
            activitiesFragment.setArguments(bundle);
            return activitiesFragment;
        case 3:
            FragNewActivity newActivity = new FragNewActivity();
            newActivity.setArguments(bundle);
            return newActivity;
        default:
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
            return resources.getString(R.string.page_friend);
        case 1:
            return resources.getString(R.string.page_users);
        case 2:
            return resources.getString(R.string.page_activities);
        case 3:
            return resources.getString(R.string.page_new_activity);
        default:
            return null;
        }
    }
}

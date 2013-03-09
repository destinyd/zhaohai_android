

package DD.Android.Zhaohai.ui.Ada;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.ui.Act.ActivitiesFrag;
import DD.Android.Zhaohai.ui.Frag.FragNewActivity;
import DD.Android.Zhaohai.ui.Frag.FriendFrag;
import DD.Android.Zhaohai.ui.Frag.UsersFrag;
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
            FriendFrag friendFragment = new FriendFrag();
            friendFragment.setArguments(bundle);
            return friendFragment;
        case 1:
            UsersFrag usersFragment = new UsersFrag();
            usersFragment.setArguments(bundle);
            return usersFragment;
        case 2:
            ActivitiesFrag activitiesFragment = new ActivitiesFrag();
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
            return resources.getString(R.string.page_friends);
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



package DD.Android.Zhaohai.test;

import DD.Android.Zhaohai.core.User;
import DD.Android.Zhaohai.ui.Act.ActCarousel;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import static DD.Android.Zhaohai.core.Constants.Extra.USER;


/**
 * Tests for displaying a specific {@link User} item
 */
public class FriendViewTest extends ActivityInstrumentationTestCase2<ActCarousel> {

    /**
     * Create test for {@link DD.Android.Zhaohai.ui.Act.ActCarousel}
     */
    public FriendViewTest() {
        super(ActCarousel.class);
    }

    /**
     * Configure intent used to display a {@link User}
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        // TODO: BUILD News item for testing.
        User user = new User();
        user.setName("Foo");
        user.set_id("Bar");
        intent.putExtra(USER, user);
        setActivityIntent(intent);
    }

    /**
     * Verify activity exists
     */
    public void testActivityExists() {
        assertNotNull(getActivity());
    }
}

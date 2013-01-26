

package DD.Android.Zhaohai.test;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.authenticator.ZhaohaiAuthenticatorActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;


/**
 * Tests of displaying the authenticator activity
 */
public class ZhaohaiAuthenticatorTest extends ActivityInstrumentationTestCase2<ZhaohaiAuthenticatorActivity> {

    /**
     * Create test for {@link DD.Android.Zhaohai.authenticator.ZhaohaiAuthenticatorActivity}
     */
    public ZhaohaiAuthenticatorTest() {
        super(ZhaohaiAuthenticatorActivity.class);
    }

    /**
     * Verify activity exists
     */
    public void testActivityExists() {
        assertNotNull(getActivity());
    }

    /**
     * Verify sign in button is initially disabled
     */
    public void testSignInDisabled() {
        View view = getActivity().findViewById(R.id.b_signin);
        assertNotNull(view);
        assertTrue(view instanceof Button);
        assertFalse(((Button) view).isEnabled());
    }
}



package DD.Android.Zhaohai.test;

import DD.Android.Zhaohai.ui.Act.ActCarousel;
import android.test.ActivityInstrumentationTestCase2;


/**
 * Test displaying of carousel.
 */
public class CarouselTest extends ActivityInstrumentationTestCase2<ActCarousel> {

    /**
     * Create test for {@link DD.Android.Zhaohai.ui.Act.ActCarousel}
     */
    public CarouselTest() {
        super(ActCarousel.class);
    }

    /**
     * Verify activity exists
     */
    public void testActivityExists() {
        assertNotNull(getActivity());
    }
}

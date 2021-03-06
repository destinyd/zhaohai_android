

package DD.Android.Zhaohai.core.core;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import DD.Android.Zhaohai.core.ZhaohaiService;
import DD.Android.Zhaohai.core.User;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests of client API
 */
public class ZhaohaiApiClientUtilTest {

    @Test
    @Ignore("Requires the API to use basic authentication. Parse.com api does not. See BootstrapService for more info.")
    public void shouldCreateClient() throws Exception {
        List<User> users = new ZhaohaiService("demo@androidbootstrap.com", "foobar").getUsers(1);

        assertThat(users.get(0).getName(), notNullValue());
    }
}

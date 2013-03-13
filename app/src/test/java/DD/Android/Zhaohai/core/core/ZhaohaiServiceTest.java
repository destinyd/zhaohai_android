

package DD.Android.Zhaohai.core.core;

import DD.Android.Zhaohai.core.Activity;
import DD.Android.Zhaohai.core.User;
import DD.Android.Zhaohai.core.UserAgentProvider;
import DD.Android.Zhaohai.core.ZhaohaiService;
import com.github.kevinsawicki.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

/**
 * Unit tests of {@link DD.Android.Zhaohai.core.ZhaohaiService}
 */
@RunWith(MockitoJUnitRunner.class)
public class ZhaohaiServiceTest {

    /**
     * Create reader for string
     *
     * @param value
     * @return input stream reader
     * @throws java.io.IOException
     */
    private static BufferedReader createReader(String value) throws IOException {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(
                value.getBytes(HttpRequest.CHARSET_UTF8))));
    }

    @Mock
    private HttpRequest request;

    private ZhaohaiService service;

    /**
     * Set up default mocks
     *
     * @throws java.io.IOException
     */
    @Before
    public void before() throws IOException {
        service = new ZhaohaiService("foo", new UserAgentProvider()) {
            protected HttpRequest execute(HttpRequest request) throws IOException {
                return ZhaohaiServiceTest.this.request;
            }
        };
        doReturn(true).when(request).ok();
    }

    /**
     * Verify getting users with an empty response
     *
     * @throws java.io.IOException
     */
    @Test
    public void getUsersEmptyResponse() throws IOException {
        doReturn(createReader("")).when(request).bufferedReader();
        List<User> users = service.getUsers(1);
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    /**
     * Verify getting news with an empty response
     *
     * @throws java.io.IOException
     */
    @Test
    public void getContentEmptyResponse() throws IOException {
        doReturn(createReader("")).when(request).bufferedReader();
        List<User> content = service.getFriend();
        assertNotNull(content);
        assertTrue(content.isEmpty());
    }

    /**
     * Verify getting checkins with an empty response
     *
     * @throws java.io.IOException
     */
    @Test
    public void getReferrersEmptyResponse() throws IOException {
        doReturn(createReader("")).when(request).bufferedReader();
        List<Activity> referrers = service.getActivities(1);
        assertNotNull(referrers);
        assertTrue(referrers.isEmpty());
    }
}

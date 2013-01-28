
package DD.Android.Zhaohai;

import DD.Android.Zhaohai.authenticator.ApiKeyProvider;
import DD.Android.Zhaohai.core.UserAgentProvider;
import DD.Android.Zhaohai.core.ZhaohaiService;
import android.accounts.AccountsException;
import com.google.inject.Inject;

import java.io.IOException;

/**
 * Provider for a {@link DD.Android.Zhaohai.core.ZhaohaiService} instance
 */
public class ZhaohaiServiceProvider {

    @Inject private ApiKeyProvider keyProvider;
    @Inject private UserAgentProvider userAgentProvider;

    /**
     * Get service for configured key provider
     * <p>
     * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
     *
     * @return bootstrap service
     * @throws java.io.IOException
     * @throws android.accounts.AccountsException
     */
    public ZhaohaiService getService() throws IOException, AccountsException {
        return new ZhaohaiService(keyProvider.getAuthKey(), userAgentProvider);
    }
}

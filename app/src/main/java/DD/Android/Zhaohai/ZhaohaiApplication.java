

package DD.Android.Zhaohai;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import com.github.kevinsawicki.http.HttpRequest;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.FROYO;

/**
 * Zhaohai application
 */
public class ZhaohaiApplication extends Application {

    /**
     * Create main application
     */
    public ZhaohaiApplication() {
        // Disable http.keepAlive on Froyo and below
        if (SDK_INT <= FROYO)
            HttpRequest.keepAlive(false);
    }

    /**
     * Create main application
     *
     * @param context
     */
    public ZhaohaiApplication(final Context context) {
        this();
        attachBaseContext(context);
    }

    /**
     * Create main application
     *
     * @param instrumentation
     */
    public ZhaohaiApplication(final Instrumentation instrumentation) {
        this();
        attachBaseContext(instrumentation.getTargetContext());
    }
}

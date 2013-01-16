package DD.Android.Zhaohai.core;

import DD.Android.Zhaohai.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import com.actionbarsherlock.app.ActionBar;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.inject.Inject;
import roboguice.util.RoboAsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static android.graphics.Bitmap.CompressFormat.PNG;
import static android.graphics.Bitmap.Config.ARGB_8888;
import static android.view.View.VISIBLE;
import static DD.Android.Zhaohai.core.Constants.Http.*;

/**
 * Avatar utilities
 */
public class UserAvatarLoader {

    private static final String TAG = "UserAvatarLoader";

    private static final float CORNER_RADIUS_IN_DIP = 3;

    private static final int CACHE_SIZE = 75;

    private static abstract class FetchAvatarTask extends
            RoboAsyncTask<BitmapDrawable> {

        private static final Executor EXECUTOR = Executors
                .newFixedThreadPool(1);

        private FetchAvatarTask(Context context) {
            super(context, EXECUTOR);
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            Log.d(TAG, "Avatar load failed", e);
        }
    }

    private final float cornerRadius;

    private final Map<Object, BitmapDrawable> loaded = new LinkedHashMap<Object, BitmapDrawable>(
            CACHE_SIZE, 1.0F) {

        private static final long serialVersionUID = -4191624209581976720L;

        @Override
        protected boolean removeEldestEntry(
                Entry<Object, BitmapDrawable> eldest) {
            return size() >= CACHE_SIZE;
        }
    };

    private final Context context;

    private final File avatarDir;

    private final Drawable loadingAvatar;

    private final BitmapFactory.Options options;

    /**
     * Create avatar helper
     *
     * @param context
     */
    @Inject
    public UserAvatarLoader(final Context context) {
        this.context = context;

        loadingAvatar = context.getResources().getDrawable(R.drawable.gravatar_icon);

        avatarDir = new File(context.getCacheDir(), "avatars/" + context.getPackageName());
        if (!avatarDir.isDirectory())
            avatarDir.mkdirs();

        float density = context.getResources().getDisplayMetrics().density;
        cornerRadius = CORNER_RADIUS_IN_DIP * density;

        options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = ARGB_8888;
    }

    /**
     * Get image for User
     *
     * @param User
     * @return image
     */
    protected BitmapDrawable getImage(final User User) {
        File avatarFile = new File(avatarDir, User._id);

        if (!avatarFile.exists() || avatarFile.length() == 0)
            return null;

        Bitmap bitmap = decode(avatarFile);
        if (bitmap != null)
            return new BitmapDrawable(context.getResources(), bitmap);
        else {
            avatarFile.delete();
            return null;
        }
    }

//    /**
//     * Get image for User
//     *
//     * @param User
//     * @return image
//     */
//    protected BitmapDrawable getImage(final CommitUser User) {
//        File avatarFile = new File(avatarDir, User.getEmail());
//
//        if (!avatarFile.exists() || avatarFile.length() == 0)
//            return null;
//
//        Bitmap bitmap = decode(avatarFile);
//        if (bitmap != null)
//            return new BitmapDrawable(context.getResources(), bitmap);
//        else {
//            avatarFile.delete();
//            return null;
//        }
//    }

    /**
     * Decode file to bitmap
     *
     * @param file
     * @return bitmap
     */
    protected Bitmap decode(final File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    /**
     * Fetch avatar from URL
     *
     * @param url
     * @param userId
     * @return bitmap
     */
    protected BitmapDrawable fetchAvatar(final String url, final String userId) {
        File rawAvatar = new File(avatarDir, userId + "-raw");
        HttpRequest request = HttpRequest.get(url);
        if (request.ok())
            request.receive(rawAvatar);

        if (!rawAvatar.exists() || rawAvatar.length() == 0)
            return null;

        Bitmap bitmap = decode(rawAvatar);
        if (bitmap == null) {
            rawAvatar.delete();
            return null;
        }

        bitmap = ImageUtils.roundCorners(bitmap, cornerRadius);
        if (bitmap == null) {
            rawAvatar.delete();
            return null;
        }

        File roundedAvatar = new File(avatarDir, userId.toString());
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(roundedAvatar);
            if (bitmap.compress(PNG, 100, output))
                return new BitmapDrawable(context.getResources(), bitmap);
            else
                return null;
        } catch (IOException e) {
            Log.d(TAG, "Exception writing rounded avatar", e);
            return null;
        } finally {
            if (output != null)
                try {
                    output.close();
                } catch (IOException e) {
                    // Ignored
                }
            rawAvatar.delete();
        }
    }

    /**
     * Sets the logo on the {@link com.actionbarsherlock.app.ActionBar} to the User's avatar.
     *
     * @param actionBar
     * @param User
     * @return this helper
     */
    public UserAvatarLoader bind(final ActionBar actionBar, final User User) {
        return bind(actionBar, new AtomicReference<User>(User));
    }

    /**
     * Sets the logo on the {@link com.actionbarsherlock.app.ActionBar} to the User's avatar.
     *
     * @param actionBar
     * @param userReference
     * @return this helper
     */
    public UserAvatarLoader bind(final ActionBar actionBar,
                             final AtomicReference<User> userReference) {
        if (userReference == null)
            return this;

        final User User = userReference.get();
        if (User == null)
            return this;

        final String avatarUrl = User.getAvatarUrl();
        if (TextUtils.isEmpty(avatarUrl))
            return this;

        final String userId = User._id;

        BitmapDrawable loadedImage = loaded.get(userId);
        if (loadedImage != null) {
            actionBar.setLogo(loadedImage);
            return this;
        }

        new FetchAvatarTask(context) {

            @Override
            public BitmapDrawable call() throws Exception {
                final BitmapDrawable image = getImage(User);
                if (image != null)
                    return image;
                else
                    return fetchAvatar(avatarUrl, userId.toString());
            }

            @Override
            protected void onSuccess(BitmapDrawable image) throws Exception {
                final User current = userReference.get();
                if (current != null && userId.equals(current._id))
                    actionBar.setLogo(image);
            }
        }.execute();

        return this;
    }

    private UserAvatarLoader setImage(final Drawable image, final ImageView view) {
        return setImage(image, view, null);
    }

    private UserAvatarLoader setImage(final Drawable image, final ImageView view,
                                  Object tag) {
        view.setImageDrawable(image);
        view.setTag(R.id.iv_avatar, tag);
        view.setVisibility(VISIBLE);
        return this;
    }

    private String getAvatarUrl(User user) {
        String avatarUrl = URL_BASE + user.getIconUrl();
        return avatarUrl;
    }

//    private String getAvatarUrl(CommitUser User) {
//        return getAvatarUrl(GravatarUtils.getHash(User.getEmail()));
//    }

    /**
     * Bind view to image at URL
     *
     * @param view
     * @param user
     * @return this helper
     */
    public UserAvatarLoader bind(final ImageView view, final User user) {
        if (user == null)
            return setImage(loadingAvatar, view);

        String avatarUrl = getAvatarUrl(user);
        if(avatarUrl.equals("http://192.168.1.4:3002/assets/noface_icon.gif") || TextUtils.isEmpty(avatarUrl))
        {
//            if (TextUtils.isEmpty(avatarUrl))
            return setImage(loadingAvatar, view);
        }

        final String userId = user._id;

        BitmapDrawable loadedImage = loaded.get(userId);
        if (loadedImage != null)
            return setImage(loadedImage, view);

        setImage(loadingAvatar, view, userId);

        final String loadUrl = avatarUrl;
        new FetchAvatarTask(context) {

            @Override
            public BitmapDrawable call() throws Exception {
                if (!userId.equals(view.getTag(R.id.iv_avatar)))
                    return null;

                final BitmapDrawable image = getImage(user);
                if (image != null)
                    return image;
                else
                    return fetchAvatar(loadUrl, userId.toString());
            }

            @Override
            protected void onSuccess(final BitmapDrawable image)
                    throws Exception {
                if (image == null)
                    return;
                loaded.put(userId, image);
                if (userId.equals(view.getTag(R.id.iv_avatar)))
                    setImage(image, view);
            }

        }.execute();

        return this;
    }

//    /**
//     * Bind view to image at URL
//     *
//     * @param view
//     * @param User
//     * @return this helper
//     */
//    public AvatarLoader bind(final ImageView view, final CommitUser User) {
//        if (User == null)
//            return setImage(loadingAvatar, view);
//
//        String avatarUrl = getAvatarUrl(User);
//
//        if (TextUtils.isEmpty(avatarUrl))
//            return setImage(loadingAvatar, view);
//
//        final String userId = User.getEmail();
//
//        BitmapDrawable loadedImage = loaded.get(userId);
//        if (loadedImage != null)
//            return setImage(loadedImage, view);
//
//        setImage(loadingAvatar, view, userId);
//
//        final String loadUrl = avatarUrl;
//        new FetchAvatarTask(context) {
//
//            @Override
//            public BitmapDrawable call() throws Exception {
//                if (!userId.equals(view.getTag(id.iv_avatar)))
//                    return null;
//
//                final BitmapDrawable image = getImage(User);
//                if (image != null)
//                    return image;
//                else
//                    return fetchAvatar(loadUrl, userId);
//            }
//
//            @Override
//            protected void onSuccess(final BitmapDrawable image)
//                    throws Exception {
//                if (image == null)
//                    return;
//                loaded.put(userId, image);
//                if (userId.equals(view.getTag(id.iv_avatar)))
//                    setImage(image, view);
//            }
//
//        }.execute();
//
//        return this;
//    }
}


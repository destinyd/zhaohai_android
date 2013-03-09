package DD.Android.Zhaohai.ui.Ada;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.core.User;
import DD.Android.Zhaohai.core.UserAvatarLoader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdaFriend extends SingleTypeAdapter<User> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd");
    private final UserAvatarLoader avatars;

    /**
     * @param inflater
     * @param items
     */
    public AdaFriend(LayoutInflater inflater, List<User> items, UserAvatarLoader avatars) {
        super(inflater, R.layout.item_friend);


        this.avatars = avatars;
        setItems(items);
    }

    /**
     * @param inflater
     */
    public AdaFriend(LayoutInflater inflater, List<User> items) {
        this(inflater, items, null);

    }

    @Override
    public long getItemId(final int position) {
        final String id = getItem(position)._id;
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.iv_avatar, R.id.tv_name };
    }

    @Override
    protected void update(int position, User user) {

        avatars.bind(imageView(R.id.iv_avatar), user);

        setText(R.id.tv_name, user.getName());

    }
}

package DD.Android.Zhaohai.core;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -7495897652043488896L;

    protected String _id;
    protected String name;
    protected Avatar avatar;

    public String getAvatarUrl() {
        return avatar.getUrl();
    }

    public String getThumbUrl(){
        return avatar.getThumb().getUrl();
    }

    public String getIconUrl(){
        return avatar.getIcon().getUrl();
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

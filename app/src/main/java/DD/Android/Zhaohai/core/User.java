package DD.Android.Zhaohai.core;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {

    private static final long serialVersionUID = 2851521909011520611L;
    public String _id;
    protected String name, email;
    //    public List<String> follower_ids;
//    public List<String> following_ids;
//    public List<String> in_activity_ids;
//    public List<String> interested_activity_ids;
//    public List<String> invited_activity_ids;
//    public List<String> role_ids;
    protected Date created_at, updated_at;
    protected Avatar avatar;
    private boolean checked;    //保存复选框的状态

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getAvatarUrl() {
        return avatar.url;
    }

    public String getIconUrl() {
        return avatar.icon.url;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

package DD.Android.Zhaohai.core;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {


    public String _id,name,email;
    public List<String> follower_ids,following_ids,in_activity_ids,interested_activity_ids,invited_activity_ids,role_ids;
    public Date created_at, updated_at;
    public Avatar avatar;

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

    public List<String> getFollower_ids() {
        return follower_ids;
    }

    public void setFollower_ids(List<String> follower_ids) {
        this.follower_ids = follower_ids;
    }

    public List<String> getFollowing_ids() {
        return following_ids;
    }

    public void setFollowing_ids(List<String> following_ids) {
        this.following_ids = following_ids;
    }

    public List<String> getIn_activity_ids() {
        return in_activity_ids;
    }

    public void setIn_activity_ids(List<String> in_activity_ids) {
        this.in_activity_ids = in_activity_ids;
    }

    public List<String> getInterested_activity_ids() {
        return interested_activity_ids;
    }

    public void setInterested_activity_ids(List<String> interested_activity_ids) {
        this.interested_activity_ids = interested_activity_ids;
    }

    public List<String> getInvited_activity_ids() {
        return invited_activity_ids;
    }

    public void setInvited_activity_ids(List<String> invited_activity_ids) {
        this.invited_activity_ids = invited_activity_ids;
    }

    public List<String> getRole_ids() {
        return role_ids;
    }

    public void setRole_ids(List<String> role_ids) {
        this.role_ids = role_ids;
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
}

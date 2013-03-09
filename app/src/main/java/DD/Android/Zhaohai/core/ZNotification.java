package DD.Android.Zhaohai.core;

import java.io.Serializable;
import java.util.Date;

public class ZNotification implements Serializable {

    private static final long serialVersionUID = 7102052595843891457L;

    public static final int BASE = 0;
    public static final int ACTIVITY_REQUEST = 1;
    public static final int ACTIVITY_REQUEST_REPLY = 2;
    public static final int ACTIVITY_REQUEST_INVITE = 3;
    public static final int FOLLOW = 51;

    public String _id;
    String text;

    //    activity
    Activity activity;
    User interesting_user;
    ActivityRequest activity_request;

    //    activity reply
//    String activity;
    User reply_admin;
    String reply_status;

    //    invited
//    String activity;
    User invite_user;

    //    follower
    User follower;


    Date created_at;
    Date updated_at;
    Date read_at;
    Date deal_at;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ActivityRequest getActivity_request() {
        return activity_request;
    }

    public void setActivity_request(ActivityRequest activity_request) {
        this.activity_request = activity_request;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getInteresting_user() {
        return interesting_user;
    }

    public void setInteresting_user(User interesting_user) {
        this.interesting_user = interesting_user;
    }

    public User getInvite_user() {
        return invite_user;
    }

    public void setInvite_user(User invite_user) {
        this.invite_user = invite_user;
    }

    public Date getRead_at() {
        return read_at;
    }

    public void setRead_at(Date read_at) {
        this.read_at = read_at;
    }

    public User getReply_admin() {
        return reply_admin;
    }

    public void setReply_admin(User reply_admin) {
        this.reply_admin = reply_admin;
    }

    public String getReply_status() {
        if (reply_status == null)
            return null;
        if (reply_status.equals("accept"))
            return "通过";
        else
            return "拒绝";
    }

    public void setReply_status(String reply_status) {
        this.reply_status = reply_status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getDeal_at() {
        return deal_at;
    }

    public void setDeal_at(Date deal_at) {
        this.deal_at = deal_at;
    }

    public int getType() {
        if (getReply_status() != null) {
            return ACTIVITY_REQUEST_REPLY;
        } else if (getInvite_user() != null) {
            return ACTIVITY_REQUEST_INVITE;
        } else if (getActivity() != null) {
            return ACTIVITY_REQUEST;
        } else{// if (getFollower() != null) {
            return FOLLOW;
        }
//        else {
//            return BASE;
//        }
    }

    public String getTitle() {
        switch (getType()) {
            case ACTIVITY_REQUEST_REPLY:
                return "加入请求被响应。";
            case ACTIVITY_REQUEST:
                return "有人想加入。";
            case ACTIVITY_REQUEST_INVITE:
                return "有人邀请您。";
            case FOLLOW:
                return "有人关注了您！";
            default:
                return "未知提示。";
        }
    }
}

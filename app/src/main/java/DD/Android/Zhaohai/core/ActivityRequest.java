package DD.Android.Zhaohai.core;

import com.baidu.platform.comapi.basestruct.GeoPoint;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ActivityRequest implements Serializable {

    private static final long serialVersionUID = 7102052595843891457L;
    public String _id;
    String text;
//    String user_id;
//    String activity_id;

    Date created_at;
    Date deal_at;
    Date updated_at;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
//
//    public String getActivity_id() {
//        return activity_id;
//    }
//
//    public void setActivity_id(String activity_id) {
//        this.activity_id = activity_id;
//    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getDeal_at() {
        return deal_at;
    }

    public void setDeal_at(Date deal_at) {
        this.deal_at = deal_at;
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
//
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
}

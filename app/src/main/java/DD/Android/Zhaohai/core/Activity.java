package DD.Android.Zhaohai.core;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Activity implements Serializable {

    private static final long serialVersionUID = 7102052595843891457L;
    protected String _id;
    protected String title;
    protected String desc;
    protected String user_id;

    protected String address;
    protected List<String> interested_user_ids;
    protected List<String> invited_user_ids;
    protected List<String> user_ids;
    protected List<String> types;
    protected Date closed_at;
    protected Date created_at;
    protected Date finished_at;
    protected Date started_at;
    protected Date updated_at;
    protected float lat,lng;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<String> getInterested_user_ids() {
        return interested_user_ids;
    }

    public void setInterested_user_ids(List<String> interested_user_ids) {
        this.interested_user_ids = interested_user_ids;
    }

    public List<String> getInvited_user_ids() {
        return invited_user_ids;
    }

    public void setInvited_user_ids(List<String> invited_user_ids) {
        this.invited_user_ids = invited_user_ids;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(List<String> user_ids) {
        this.user_ids = user_ids;
    }

    public Date getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(Date closed_at) {
        this.closed_at = closed_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(Date finished_at) {
        this.finished_at = finished_at;
    }

    public Date getStarted_at() {
        return started_at;
    }

    public void setStarted_at(Date started_at) {
        this.started_at = started_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}

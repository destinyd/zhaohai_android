package DD.Android.Zhaohai.core;

import com.baidu.platform.comapi.basestruct.GeoPoint;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Activity implements Serializable {

    private static final long serialVersionUID = 7102052595843891457L;
    public String _id;
    protected String title;
    protected String desc;
//    protected String user_id;

    protected String address;
    protected String status;
    protected User user;
    protected List<User> users;
    protected List<User> interested_users;
    protected List<User> invited_users;
//    protected List<String> interested_user_ids;
//    protected List<String> invited_user_ids;
//    protected List<String> user_ids;
    protected List<String> types;
    protected Date closed_at;
    protected Date created_at;
    protected Date finished_at;
    protected Date started_at;
    protected Date updated_at;
    protected double lat,lng;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(Date finished_at) {
        this.finished_at = finished_at;
    }

    public List<User> getInterested_users() {
        return interested_users;
    }

    public void setInterested_users(List<User> interested_users) {
        this.interested_users = interested_users;
    }

    public List<User> getInvited_users() {
        return invited_users;
    }

    public void setInvited_users(List<User> invited_users) {
        this.invited_users = invited_users;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Date getStarted_at() {
        return started_at;
    }

    public void setStarted_at(Date started_at) {
        this.started_at = started_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setPt(GeoPoint pt){
        this.lat = pt.getLatitudeE6() / 1E6;
        this.lng = pt.getLongitudeE6() / 1E6;
    }

}

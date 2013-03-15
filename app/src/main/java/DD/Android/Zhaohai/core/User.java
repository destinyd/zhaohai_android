package DD.Android.Zhaohai.core;

import android.provider.CalendarContract;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    protected Userinfo userinfo;

    protected Points points;
    protected String relationship;

    private String age = null;

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
        return avatar.getIcon().getUrl();
    }

    public String getThumbUrl() {
        return avatar.getThumb().getUrl();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }

    public Points getPoints() {
        return points;
    }

    public void setPoints(Points points) {
        this.points = points;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPointsHold(){
        return String.valueOf(getPoints().getActivities());
    }

    public String getPointsJoin(){
        return String.valueOf(getPoints().getIn_activities());
    }

    public String getPointsInterested(){
        return String.valueOf(getPoints().getInterested_activities());
    }

    public boolean isMale(){
        String gender = getUserinfo().getGender();
        if(gender != null && gender.equals("male") ){
            return true;
        }
        return false;
    }

    public String getStrAge() {
        if(age == null)
            age = getAge(getUserinfo().getBirth());
        return age;
    }

    private String getAge(Date date){
        Calendar dob = new GregorianCalendar();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);
//        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
//
//    private String getAge(int year, int month, int day){
//        Calendar dob = Calendar.getInstance();
//        Calendar today = Calendar.getInstance();
//
//        dob.set(year, month, day);
//
//        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
//
//        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
//            age--;
//        }
//
//        Integer ageInt = new Integer(age);
//        String ageS = ageInt.toString();
//
//        return ageS;
//    }
}

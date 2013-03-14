package DD.Android.Zhaohai.core;

import java.io.Serializable;
import java.util.Date;

public class Points implements Serializable {

    protected int activities,in_activities,interested_activities;

    public int getActivities() {
        return activities;
    }

    public void setActivities(int activities) {
        this.activities = activities;
    }

    public int getIn_activities() {
        return in_activities;
    }

    public void setIn_activities(int in_activities) {
        this.in_activities = in_activities;
    }

    public int getInterested_activities() {
        return interested_activities;
    }

    public void setInterested_activities(int interested_activities) {
        this.interested_activities = interested_activities;
    }
}

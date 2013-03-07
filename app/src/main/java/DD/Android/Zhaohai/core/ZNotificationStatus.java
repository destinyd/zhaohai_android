package DD.Android.Zhaohai.core;

import java.io.Serializable;
import java.util.Date;

public class ZNotificationStatus implements Serializable {

    private static final long serialVersionUID = -8618085900266701391L;

    ZNotification last;
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ZNotification getLast() {
        return last;
    }

    public void setLast(ZNotification last) {
        this.last = last;
    }
}

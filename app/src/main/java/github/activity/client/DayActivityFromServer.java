package github.activity.client;

import java.util.Date;

/**
 * Created by swap_i on 21/12/14.
 */
public class DayActivityFromServer {

    private Date date;
    private int activityCount;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

}

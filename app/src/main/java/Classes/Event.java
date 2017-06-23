package Classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by UTA2 on 11/05/17.
 */

public class Event  implements Serializable {
    private String name;
    private String dateStart;
    private String dateEnd;
    private String logoUrl;
    private String eventUrl;
    private ArrayList<Activity> activities;
    private int position;

    public Event(String name, String dateStart, String dateEnd, String logoUrl, String eventUrl, ArrayList activities, int position){
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.logoUrl = logoUrl;
        this.eventUrl = eventUrl;
        this.activities = activities;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public ArrayList<Activity> getActivities() { return activities; }

    public int getPosition() {
        return position;
    }
}

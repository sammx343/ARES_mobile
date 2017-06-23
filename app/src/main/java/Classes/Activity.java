package Classes;

import java.io.Serializable;

/**
 * Created by UTA2 on 9/05/17.
 */

public class Activity implements Serializable {
    private int id;
    private String name;
    private String description;
    private int invitedNumber;
    private int invitedMaxNumber;
    private String place;
    private String hourDateStart;
    private String hourDateEnd;
    private int position;

    public Activity(int id, String name, String description, int invitedNumber, int invitedMaxNumber, String place, String hourDateStart, String hourDateEnd, int position) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.invitedNumber = invitedNumber;
        this.invitedMaxNumber = invitedMaxNumber;
        this.place = place;
        this.hourDateStart = hourDateStart;
        this.hourDateEnd = hourDateEnd;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getInvitedNumber() {
        return invitedNumber;
    }

    public int getInvitedMaxNumber() {
        return invitedMaxNumber;
    }

    public String getPlace() {
        return place;
    }

    public String getHourDateStart() {
        return hourDateStart;
    }

    public String getHourDateEnd() {
        return hourDateEnd;
    }

    public int getPosition() {
        return position;
    }
}

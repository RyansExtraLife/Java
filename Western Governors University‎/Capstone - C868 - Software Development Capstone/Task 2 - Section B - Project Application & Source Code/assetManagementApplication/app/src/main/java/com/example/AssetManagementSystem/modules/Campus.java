package com.example.AssetManagementSystem.modules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Campus {

    private final int id;
    private final String campusName;


    private final String campusOpenDate;
    private final String campusAuditDate;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    public Campus(int passedId, String passedTitle, String passedStartDate, String passedEndDate) {
        this.id = passedId;
        this.campusName = passedTitle;
        this.campusOpenDate = passedStartDate;
        this.campusAuditDate = passedEndDate;
    }

    public int getId() {
        return this.id;
    }

    public String getCampusName() {
        return this.campusName;
    }

    public String getCampusOpenDate() {
        return this.campusOpenDate;
    }

    public String getCampusAuditDate() {
        return this.campusAuditDate;
    }

    public String getCampusDates() {
        return campusOpenDate + " - " + campusAuditDate;
    }

    @Override
    public String toString() {
        return campusName + " (" + getCampusDates() + ")";
    }

    public boolean isValidCampus() {

        if (campusName.isEmpty() || campusOpenDate.isEmpty() || campusAuditDate.isEmpty()) {
            return false;
        }
        try {
            Date start = dateFormat.parse(campusOpenDate);
            Date end = dateFormat.parse(campusAuditDate);
            if (!start.before(end)) {
                return false;
            }
        } catch (ParseException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}

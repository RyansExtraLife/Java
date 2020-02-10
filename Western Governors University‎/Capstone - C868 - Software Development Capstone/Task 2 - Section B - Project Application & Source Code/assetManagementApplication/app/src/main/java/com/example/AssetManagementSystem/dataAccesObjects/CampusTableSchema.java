package com.example.AssetManagementSystem.dataAccesObjects;

public interface CampusTableSchema {
    String TABLE_CAMPUS = "campuses";
    String CAMPUS_ID = "id";
    String CAMPUS_TITLE = "campus_title";
    String CAMPUS_OPEN_DATE = "campus_open_date";
    String CAMPUS_AUDIT_DATE = "campus_audit_date";
    String[] CAMPUS_COLUMNS = {CAMPUS_ID, CAMPUS_TITLE, CAMPUS_OPEN_DATE, CAMPUS_AUDIT_DATE};
    String CAMPUS_CREATE = "CREATE TABLE " + TABLE_CAMPUS + " (" + CAMPUS_ID + " INTEGER PRIMARY KEY, " + CAMPUS_TITLE + " TEXT, " + CAMPUS_OPEN_DATE + " TEXT, " + CAMPUS_AUDIT_DATE + " TEXT" + ")";
}

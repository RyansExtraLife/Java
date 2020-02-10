package com.example.AssetManagementSystem.dataAccesObjects;

//Import Application Packages.
import com.example.AssetManagementSystem.database.DatabaseContentProvider;
import com.example.AssetManagementSystem.modules.Campus;


//Import sqlLight Packages
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

//Import Java Utilities.
import java.util.ArrayList;
import java.util.List;

public class CampusTableDAO extends DatabaseContentProvider implements CampusDAOImplementation, CampusTableSchema {

    //Local Variables
    private Cursor cursor;
    private ContentValues initialValues;

    //Super Constructor
    public CampusTableDAO(SQLiteDatabase passedSqlLiteDatabase) {
        super(passedSqlLiteDatabase);
    }

    //Return the initial ContentValues variable.
    private ContentValues getContentValue() {
        return initialValues;
    }

    //Set the Content Values variable with the passed Campus object.
    private void setContentValue(Campus passedCampus) {
        initialValues = new ContentValues();
        initialValues.put(CAMPUS_ID, passedCampus.getId());
        initialValues.put(CAMPUS_TITLE, passedCampus.getCampusName());
        initialValues.put(CAMPUS_OPEN_DATE, passedCampus.getCampusOpenDate());
        initialValues.put(CAMPUS_AUDIT_DATE, passedCampus.getCampusAuditDate());
    }

    //Add a campus to the application database.
    public boolean addCampus(Campus passedCampus) {
        setContentValue(passedCampus);

        try {
            return super.insert(TABLE_CAMPUS, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    //Update a campus within the application database.
    public boolean updateCampus(Campus passedCampus) {
        final String selection = CAMPUS_ID + " = ?";
        final String[] selectionArguments = {String.valueOf(passedCampus.getId())};

        setContentValue(passedCampus);
        try {
            return super.update(TABLE_CAMPUS, getContentValue(), selection, selectionArguments) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    //Remove a campus within the application database.
    public boolean removeCampus(Campus passedCampus) {
        final String selection = CAMPUS_ID + " = ?";
        final String[] selectionArguments = {String.valueOf(passedCampus.getId())};

        return super.delete(TABLE_CAMPUS, selection, selectionArguments) > 0;
    }

    //Return a total count of all database campus objects.
    public int getCampusCount() {
        List<Campus> campusList = getCampuses();
        return campusList.size();
    }

    //Return a Campus by the passed campusId.
    public Campus getCampusById(int passedCampusId) {
        final String selection = CAMPUS_ID + " = ?";
        final String[] selectionArgs = {String.valueOf(passedCampusId)};
        Campus campus = null;

        cursor = super.query(TABLE_CAMPUS, CAMPUS_COLUMNS, selection, selectionArgs, CAMPUS_ID);

        //While the cursor still has objects.
        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                campus = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return campus;
    }

    //Returns all campus objects within the application database.
    public ArrayList<Campus> getCampuses() {
        ArrayList<Campus> campusList = new ArrayList<>();

        cursor = super.query(TABLE_CAMPUS, CAMPUS_COLUMNS, null, null, CAMPUS_ID);

        //While the cursor still has objects.
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Campus campus = cursorToEntity(cursor);
                campusList.add(campus);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return campusList;
    }

    //Converts a passed Cursor object to a new Campus Object.
    protected Campus cursorToEntity(Cursor passedCursor) {

        //Local Variables that store the column index for each Campus variable.
        int campusIdIndex;
        int campusTitleIndex;
        int campusStartIndex;
        int campusEndIndex;

        //Local Variables that store the variables for a Campus object.
        int campusId = -1;
        String campusTitle = "";
        String campusStartDate = "";
        String campusEndDate = "";

        //Check each index and assign its respective value to its variable.
        if (passedCursor != null) {
            if (passedCursor.getColumnIndex(CAMPUS_ID) != -1) {
                campusIdIndex = passedCursor.getColumnIndexOrThrow(CAMPUS_ID);
                campusId = passedCursor.getInt(campusIdIndex);
            }
            if (passedCursor.getColumnIndex(CAMPUS_TITLE) != -1) {
                campusTitleIndex = passedCursor.getColumnIndexOrThrow(CAMPUS_TITLE);
                campusTitle = passedCursor.getString(campusTitleIndex);
            }
            if (passedCursor.getColumnIndex(CAMPUS_OPEN_DATE) != -1) {
                campusStartIndex = passedCursor.getColumnIndexOrThrow(CAMPUS_OPEN_DATE);
                campusStartDate = passedCursor.getString(campusStartIndex);
            }
            if (passedCursor.getColumnIndex(CAMPUS_AUDIT_DATE) != -1) {
                campusEndIndex = passedCursor.getColumnIndexOrThrow(CAMPUS_AUDIT_DATE);
                campusEndDate = passedCursor.getString(campusEndIndex);
            }
        }
        //Returns new object.
        return new Campus(campusId, campusTitle, campusStartDate, campusEndDate);
    }
}
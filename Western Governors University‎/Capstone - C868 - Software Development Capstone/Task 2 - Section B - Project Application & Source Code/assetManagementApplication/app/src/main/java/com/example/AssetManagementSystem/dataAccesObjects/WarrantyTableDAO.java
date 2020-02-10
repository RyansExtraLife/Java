package com.example.AssetManagementSystem.dataAccesObjects;

//Import Application Packages.
import com.example.AssetManagementSystem.database.DatabaseContentProvider;
import com.example.AssetManagementSystem.modules.Warranty;


//Import sqlLight Packages
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

//Import Java Utilities.
import java.util.ArrayList;
import java.util.List;

public class WarrantyTableDAO extends DatabaseContentProvider implements WarrantyDAOImplementation, WarrantyTableSchema {

    //Local Variables
    private Cursor cursor;
    private ContentValues initialContentValues;

    //Super Constructor
    public WarrantyTableDAO(SQLiteDatabase passedSqlLiteDatabase) {
        super(passedSqlLiteDatabase);
    }

    //Return the initial ContentValues variable.
    private ContentValues getContentValue() {
        return initialContentValues;
    }

    //Set the Content Values variable with the passed Warranty object.
    private void setContentValue(Warranty passedWarranty) {
        initialContentValues = new ContentValues();
        initialContentValues.put(WARRANTY_ID, passedWarranty.getId());
        initialContentValues.put(WARRANTY_PRODUCT_ID, passedWarranty.getProductId());
        initialContentValues.put(WARRANTY_NAME, passedWarranty.getWarrantyName());
        initialContentValues.put(WARRANTY_EXPIRATION_DATE, passedWarranty.getWarrantyExpirationDate());
        initialContentValues.put(WARRANTY_TYPE, passedWarranty.getWarrantyType());
    }

    //Add warranty to the application database.
    public boolean addWarranty(Warranty passedWarranty) {
        setContentValue(passedWarranty);
        try {
            return super.insert(TABLE_WARRANTY, getContentValue()) > 0;
        } catch (SQLiteConstraintException exception) {
            return false;
        }
    }

    //Update an warranty in the application database.
    public boolean updateWarranty(Warranty passedWarranty) {
        final String selection = WARRANTY_ID + " = ?";
        final String[] selectionArguments = {String.valueOf(passedWarranty.getId())};

        setContentValue(passedWarranty);

        try {
            return super.update(TABLE_WARRANTY, getContentValue(), selection, selectionArguments) > 0;
        } catch (SQLiteConstraintException exception){
            return false;
        }
    }

    //Remove an warranty in the application database.
    public boolean removeWarranty(Warranty passedWarranty) {
        final String selection = WARRANTY_ID + " = ?";
        final String[] selectionArguments = {String.valueOf(passedWarranty.getId())};

        return super.delete(TABLE_WARRANTY, selection, selectionArguments) > 0;
    }

    //Gets the a a count of all warranty in the application.
    public int getWarrantyCount() {
        List<Warranty> warrantyArrayList = getWarranty();
        return warrantyArrayList.size();
    }

    //Return a List of all warranty in the application database.
    public ArrayList<Warranty> getWarranty() {

        ArrayList<Warranty> warrantyArrayList = new ArrayList<>();

        cursor = super.query(TABLE_WARRANTY, WARRANTY_COLUMNS, null, null, WARRANTY_ID);

        //While the cursor still has objects.
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Warranty temporaryWarranty = cursorToEntity(cursor);
                warrantyArrayList.add(temporaryWarranty);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return warrantyArrayList;
    }

    //Get all warranty by the passed courseId.
    public ArrayList<Warranty> getWarrantyByProduct(int passedProductId) {

        final String selection = WARRANTY_PRODUCT_ID + " = ?";
        final String[] selectionArguments = {String.valueOf(passedProductId)};
        ArrayList<Warranty> warrantyList = new ArrayList<>();

        cursor = super.query(TABLE_WARRANTY, WARRANTY_COLUMNS, selection, selectionArguments, WARRANTY_ID);

        ////While the cursor still has objects.
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Warranty warranty = cursorToEntity(cursor);
                warrantyList.add(warranty);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return warrantyList;
    }

    //Get an warranty by passed warranty Id.
    public Warranty getWarrantyById(int passedWarrantyId) {

        final String selection = WARRANTY_ID + " = ?";
        final String[] selectionArguments = {String.valueOf(passedWarrantyId)};
        Warranty warranty = null;

        cursor = super.query(TABLE_WARRANTY, WARRANTY_COLUMNS, selection, selectionArguments, WARRANTY_ID);

        //While the cursor still has objects.
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                warranty = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return warranty;
    }

    //Converts a passed Cursor object to a new Warranty Object.
    protected Warranty cursorToEntity(Cursor passedCursor) {
        //Local Variables that store the column index for each Warranty variable.
        int warrantyIdIndex;
        int warrantyCourseIdIndex;
        int warrantyNameIndex;
        int warrantyTypeIndex;
        int warrantyDateIndex;

        //Local Variables that store the variables for a Warranty object.
        int warrantyId = -1;
        int warrantyCourseId = -1;
        String warrantyName = "";
        String warrantyType = "";
        String warrantyDate = "";

        //Check each index and assign its respective value to its variable.
        if (passedCursor != null) {
            if (passedCursor.getColumnIndex(WARRANTY_ID) != -1) {
                warrantyIdIndex = passedCursor.getColumnIndexOrThrow(WARRANTY_ID);
                warrantyId = passedCursor.getInt(warrantyIdIndex);
            }
            if (passedCursor.getColumnIndex(WARRANTY_PRODUCT_ID) != -1) {
                warrantyCourseIdIndex = passedCursor.getColumnIndexOrThrow(WARRANTY_PRODUCT_ID);
                warrantyCourseId = passedCursor.getInt(warrantyCourseIdIndex);
            }
            if (passedCursor.getColumnIndex(WARRANTY_NAME) != -1) {
                warrantyNameIndex = passedCursor.getColumnIndexOrThrow(WARRANTY_NAME);
                warrantyName = passedCursor.getString(warrantyNameIndex);
            }
            if (passedCursor.getColumnIndex(WARRANTY_EXPIRATION_DATE) != -1) {
                warrantyDateIndex = passedCursor.getColumnIndexOrThrow(WARRANTY_EXPIRATION_DATE);
                warrantyDate = passedCursor.getString(warrantyDateIndex);
            }
            if (passedCursor.getColumnIndex(WARRANTY_TYPE) != -1) {
                warrantyTypeIndex = passedCursor.getColumnIndexOrThrow(WARRANTY_TYPE);
                warrantyType = passedCursor.getString(warrantyTypeIndex);
            }
        }
        //Returns new object.
        return new Warranty(warrantyId, warrantyCourseId, warrantyName, warrantyType, warrantyDate);
    }
}
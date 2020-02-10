package com.example.AssetManagementSystem.dataAccesObjects;

import com.example.AssetManagementSystem.database.DatabaseContentProvider;
import com.example.AssetManagementSystem.modules.Vendor;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class VendorTableDAO extends DatabaseContentProvider implements VendorDAOImplementation, VendorTableSchema {

    private Cursor cursor;
    private ContentValues initialContentValues;

    public VendorTableDAO(SQLiteDatabase passedSqlLiteDatabase) {
        super(passedSqlLiteDatabase);
    }

    private ContentValues getContentValue() {
        return initialContentValues;
    }

    private void setContentValue(Vendor passedVendor) {
        initialContentValues = new ContentValues();
        initialContentValues.put(VENDOR_ID, passedVendor.getId());
        initialContentValues.put(VENDOR_NAME, passedVendor.getVendorName());
        initialContentValues.put(VENDOR_PHONE, passedVendor.getVendorPhoneNumber());
        initialContentValues.put(VENDOR_EMAIL, passedVendor.getVendorEmail());
        initialContentValues.put(VENDOR_PRODUCT_ID, passedVendor.getProductId());
    }

    public boolean addVendor(Vendor passedVendor) {
        setContentValue(passedVendor);

        try {
            return super.insert(TABLE_VENDORS, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    //Update a vendor in the application database.
    public boolean updateVendor(Vendor passedVendor) {
        final String selection = VENDOR_ID + " = ?";
        final String[] selectionArguments = {String.valueOf(passedVendor.getId())};

        setContentValue(passedVendor);
        try {
            return super.update(TABLE_VENDORS, getContentValue(), selection, selectionArguments) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    //Remove a Vendor from the application database.
    public boolean removeVendor(Vendor passedVendor) {
        final String selection = VENDOR_ID + " = ?";
        final String[] selectionArgs = {String.valueOf(passedVendor.getId())};

        return super.delete(TABLE_VENDORS, selection, selectionArgs) > 0;
    }


    //Returns all of the vendors that are associated with a courseId.
    public ArrayList<Vendor> getVendorsByProduct(int passedVendorId) {
        final String selection = VENDOR_PRODUCT_ID + " = ?";
        final String[] selectionArgs = {String.valueOf(passedVendorId)};
        ArrayList<Vendor> vendorList = new ArrayList<Vendor>();

        cursor = super.query(TABLE_VENDORS, VENDOR_COLUMNS, selection, selectionArgs, VENDOR_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Vendor vendor = cursorToEntity(cursor);
                vendorList.add(vendor);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return vendorList;
    }

    public Vendor getVendorById(int passedVendorId) {
        final String selection = VENDOR_ID + " = ?";
        final String[] selectionArgs = {String.valueOf(passedVendorId)};
        Vendor vendor = null;

        cursor = super.query(TABLE_VENDORS, VENDOR_COLUMNS, selection, selectionArgs, VENDOR_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                vendor = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return vendor;
    }

    public int getVendorCount() {
        List<Vendor> vendorList = getVendors();
        return vendorList.size();
    }

    public ArrayList<Vendor> getVendors() {
        ArrayList<Vendor> vendorList = new ArrayList<>();

        cursor = super.query(TABLE_VENDORS, VENDOR_COLUMNS, null, null, VENDOR_ID);

        //While the cursor still has objects.
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Vendor vendor = cursorToEntity(cursor);
                vendorList.add(vendor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return vendorList;
    }

    protected Vendor cursorToEntity(Cursor passedCursor) {

        //Local Variables that store the column index for each Vendor variable.
        int vendorIdIndex;
        int vendorCourseIdIndex;
        int vendorNameIndex;
        int vendorEmailIndex;
        int vendorPhoneIndex;

        int vendorId = -1;
        int vendorCourseId = -1;
        String vendorName = "";
        String vendorPhoneNumber = "";
        String vendorEmail = "";

        //Check each index and assign its respective value to its variable.
        if (passedCursor != null) {
            if (passedCursor.getColumnIndex(VENDOR_ID) != -1) {
                vendorIdIndex = passedCursor.getColumnIndexOrThrow(VENDOR_ID);
                vendorId = passedCursor.getInt(vendorIdIndex);
            }
            if (passedCursor.getColumnIndex(VENDOR_PRODUCT_ID) != -1) {
                vendorCourseIdIndex = passedCursor.getColumnIndexOrThrow(VENDOR_PRODUCT_ID);
                vendorCourseId = passedCursor.getInt(vendorCourseIdIndex);
            }
            if (passedCursor.getColumnIndex(VENDOR_NAME) != -1) {
                vendorNameIndex = passedCursor.getColumnIndexOrThrow(VENDOR_NAME);
                vendorName = passedCursor.getString(vendorNameIndex);
            }
            if (passedCursor.getColumnIndex(VENDOR_PHONE) != -1) {
                vendorPhoneIndex = passedCursor.getColumnIndexOrThrow(VENDOR_PHONE);
                vendorPhoneNumber = passedCursor.getString(vendorPhoneIndex);
            }
            if (passedCursor.getColumnIndex(VENDOR_EMAIL) != -1) {
                vendorEmailIndex = passedCursor.getColumnIndexOrThrow(VENDOR_EMAIL);
                vendorEmail = passedCursor.getString(vendorEmailIndex);
            }
        }
        //Returns new object.
        return new Vendor(vendorId, vendorCourseId, vendorName, vendorPhoneNumber, vendorEmail);
    }
}
package com.example.AssetManagementSystem.dataAccesObjects;

public interface VendorTableSchema {
    String TABLE_VENDORS = "vendors";
    String VENDOR_ID = "id";
    String VENDOR_NAME = "vendor_name";
    String VENDOR_PHONE = "vendor_phone";
    String VENDOR_EMAIL = "vendor_email";
    String VENDOR_PRODUCT_ID = "vendor_product_id";
    String[] VENDOR_COLUMNS = {VENDOR_ID, VENDOR_NAME, VENDOR_PHONE, VENDOR_EMAIL, VENDOR_PRODUCT_ID};
    String VENDORS_CREATE = "CREATE TABLE " + TABLE_VENDORS + " (" + VENDOR_ID + " INTEGER PRIMARY KEY, " + VENDOR_NAME + " TEXT, " + VENDOR_PHONE + " TEXT, " + VENDOR_EMAIL + " TEXT, " + VENDOR_PRODUCT_ID + " INTEGER" + ")";
}

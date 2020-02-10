package com.example.AssetManagementSystem.dataAccesObjects;

public interface WarrantyTableSchema {
    String TABLE_WARRANTY = "warranty";
    String WARRANTY_ID = "id";
    String WARRANTY_PRODUCT_ID = "warranty_product_id";
    String WARRANTY_NAME = "warranty_name";
    String WARRANTY_EXPIRATION_DATE = "warranty_expiration_date";
    String WARRANTY_TYPE = "warranty_type";
    String[] WARRANTY_COLUMNS = {WARRANTY_ID, WARRANTY_PRODUCT_ID, WARRANTY_NAME, WARRANTY_TYPE, WARRANTY_EXPIRATION_DATE};
    String WARRANTY_CREATE =  "CREATE TABLE " + TABLE_WARRANTY + " (" + WARRANTY_ID + " INTEGER PRIMARY KEY, " + WARRANTY_PRODUCT_ID + " INTEGER, " + WARRANTY_NAME + " TEXT, " + WARRANTY_TYPE + " TEXT, " + WARRANTY_EXPIRATION_DATE + " TEXT " + ")";
}

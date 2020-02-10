package com.example.AssetManagementSystem.dataAccesObjects;

public interface ProductTableSchema {
    String TABLE_PRODUCTS = "products";
    String PRODUCT_ID = "id";
    String PRODUCT_CAMPUS_ID = "product_campus_id";
    String PRODUCT_TITLE = "product_title";
    String PRODUCT_PURCHASE_DATE = "product_purchase_date";
    String PRODUCT_WARRANTY_END_DATE = "product_warranty_end_date";
    String PRODUCT_STATUS = "product_status";
    String[] PRODUCTS_COLUMNS = {PRODUCT_ID, PRODUCT_CAMPUS_ID, PRODUCT_TITLE, PRODUCT_STATUS, PRODUCT_PURCHASE_DATE, PRODUCT_WARRANTY_END_DATE};
    String PRODUCTS_CREATE = "CREATE TABLE " + TABLE_PRODUCTS + " (" + PRODUCT_ID + " INTEGER PRIMARY KEY," + PRODUCT_CAMPUS_ID + " INTEGER," + PRODUCT_TITLE + " TEXT," + PRODUCT_STATUS + " TEXT," + PRODUCT_PURCHASE_DATE + " TEXT," + PRODUCT_WARRANTY_END_DATE + " TEXT " + ")";
}

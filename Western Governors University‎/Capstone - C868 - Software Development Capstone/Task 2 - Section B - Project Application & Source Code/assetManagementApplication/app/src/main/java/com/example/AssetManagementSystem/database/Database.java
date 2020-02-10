package com.example.AssetManagementSystem.database;

import com.example.AssetManagementSystem.dataAccesObjects.WarrantyTableSchema;
import com.example.AssetManagementSystem.dataAccesObjects.ProductTableSchema;
import com.example.AssetManagementSystem.dataAccesObjects.VendorTableDAO;
import com.example.AssetManagementSystem.dataAccesObjects.VendorTableSchema;
import com.example.AssetManagementSystem.dataAccesObjects.CampusTableDAO;
import com.example.AssetManagementSystem.dataAccesObjects.CampusTableSchema;
import com.example.AssetManagementSystem.dataAccesObjects.WarrantyTableDAO;
import com.example.AssetManagementSystem.dataAccesObjects.ProductTableDAO;
import com.example.AssetManagementSystem.dataAccesObjects.NoteTableDAO;
import com.example.AssetManagementSystem.dataAccesObjects.NoteTableSchema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

    private final Context context;
    private static final String DATABASE_NAME = "degreetracker.database";
    private static final int DATABASE_VERSION = 6;
    private DatabaseHelper databaseHelper;

    public static CampusTableDAO CampusDataAccessObject;
    public static ProductTableDAO ProductDataAccessObject;
    public static WarrantyTableDAO WarrantyDataAccessObject;
    public static VendorTableDAO VendorDataAccessObject;
    public static NoteTableDAO NoteDataAccessObject;

    public Database(Context passedContext) {
        this.context = passedContext;
    }

    public Database openDatabase() {
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase sqlLiteDatabase = databaseHelper.getWritableDatabase();

        CampusDataAccessObject = new CampusTableDAO(sqlLiteDatabase);
        ProductDataAccessObject = new ProductTableDAO(sqlLiteDatabase);
        WarrantyDataAccessObject = new WarrantyTableDAO(sqlLiteDatabase);
        VendorDataAccessObject = new VendorTableDAO(sqlLiteDatabase);
        NoteDataAccessObject = new NoteTableDAO(sqlLiteDatabase);

        return this;
    }

    public void closeDatabase() {
        databaseHelper.close();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context passedContext) {
            super(passedContext, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase passedLiteDatabase) {
            passedLiteDatabase.execSQL(CampusTableSchema.CAMPUS_CREATE);
            passedLiteDatabase.execSQL(ProductTableSchema.PRODUCTS_CREATE);
            passedLiteDatabase.execSQL(WarrantyTableSchema.WARRANTY_CREATE);
            passedLiteDatabase.execSQL(VendorTableSchema.VENDORS_CREATE);
            passedLiteDatabase.execSQL(NoteTableSchema.NOTES_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase passedLiteDatabase, int passedOldVersion, int passedNewVersion) {
            passedLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CampusTableSchema.TABLE_CAMPUS);
            passedLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ProductTableSchema.TABLE_PRODUCTS);
            passedLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WarrantyTableSchema.TABLE_WARRANTY);
            passedLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VendorTableSchema.TABLE_VENDORS);
            passedLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NoteTableSchema.TABLE_NOTES);
            onCreate(passedLiteDatabase);
        }
    }
}
package com.example.AssetManagementSystem.dataAccesObjects;

import com.example.AssetManagementSystem.database.DatabaseContentProvider;
import com.example.AssetManagementSystem.modules.Product;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductTableDAO extends DatabaseContentProvider implements ProductDAOImplementation, ProductTableSchema {

    private Cursor cursor;
    private ContentValues initialContentValues;

    public ProductTableDAO(SQLiteDatabase passedSqlLiteDatabase) {
        super(passedSqlLiteDatabase);
    }

    private ContentValues getContentValue() {
        return initialContentValues;
    }

    private void setContentValue(Product passedProduct) {
        initialContentValues = new ContentValues();
        initialContentValues.put(PRODUCT_ID, passedProduct.getId());
        initialContentValues.put(PRODUCT_CAMPUS_ID, passedProduct.getCampusId());
        initialContentValues.put(PRODUCT_STATUS, passedProduct.getProductStatus());
        initialContentValues.put(PRODUCT_TITLE, passedProduct.getProductName());
        initialContentValues.put(PRODUCT_PURCHASE_DATE, passedProduct.getProductPurchaseDate());
        initialContentValues.put(PRODUCT_WARRANTY_END_DATE, passedProduct.getProductWarrantyEndDate());
    }

    public boolean addProduct(Product passedProduct) {
        setContentValue(passedProduct);
        try {
            return super.insert(TABLE_PRODUCTS, getContentValue()) > 0;
        } catch (SQLiteConstraintException exception){
            return false;
        }
    }

    public boolean removeProduct(Product passedProduct) {
        final String[] selectionArgs = {String.valueOf(passedProduct.getId())};
        final String selection = PRODUCT_ID + " = ?";

        return super.delete(TABLE_PRODUCTS, selection, selectionArgs) > 0;
    }

    public boolean updateProduct(Product passedProduct) {
        final String[] selectionArgs = {String.valueOf(passedProduct.getId())};
        final String selection = PRODUCT_ID + " = ?";

        setContentValue(passedProduct);
        try {
            return super.update(TABLE_PRODUCTS, getContentValue(), selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    public Product getProductById(int passedProductId) {
        Product product = null;
        final String[] selectionArgs = {String.valueOf(passedProductId)};
        final String selection = PRODUCT_ID + " = ?";

        cursor = super.query(TABLE_PRODUCTS, PRODUCTS_COLUMNS, selection, selectionArgs, PRODUCT_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                product = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return product;
    }

    public ArrayList<Product> getProductsByCampus(int passedTermId) {
        final String[] selectionArgs = {String.valueOf(passedTermId)};
        final String selection = PRODUCT_CAMPUS_ID + " = ?";

        ArrayList<Product> productList = new ArrayList<>();

        cursor = super.query(TABLE_PRODUCTS, PRODUCTS_COLUMNS, selection, selectionArgs, PRODUCT_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Product product = cursorToEntity(cursor);
                productList.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return productList;
    }

    public int getProductCount() {
        List<Product> productList = getProducts();

        return productList.size();
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> productList = new ArrayList<>();

        cursor = super.query(TABLE_PRODUCTS, PRODUCTS_COLUMNS, null, null, PRODUCT_ID);

        //While the cursor still has objects.
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Product product = cursorToEntity(cursor);
                productList.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return productList;
    }

    //Converts a passed Cursor object to a new Product Object.
    protected Product cursorToEntity(Cursor passedCursor) {

        //Local Variables that store the column index for Product variable.
        int productIdIndex;
        int productTermIdIndex;
        int productTitleIndex;
        int productStatusIndex;
        int productStartIndex;
        int productEndIndex;

        //Local Variables that store the variables for a Product object.
        int productId = -1;
        int productTermId = -1;
        String productTitle = "";
        String productStatus = "";
        String productStart = "";
        String productEnd = "";

        //Check each index and assign its respective value to its variable.
        if (passedCursor != null) {
            if (passedCursor.getColumnIndex(PRODUCT_ID) != -1) {
                productIdIndex = passedCursor.getColumnIndexOrThrow(PRODUCT_ID);
                productId = passedCursor.getInt(productIdIndex);
            }
            if (passedCursor.getColumnIndex(PRODUCT_CAMPUS_ID) != -1) {
                productTermIdIndex = passedCursor.getColumnIndexOrThrow(PRODUCT_CAMPUS_ID);
                productTermId = passedCursor.getInt(productTermIdIndex);
            }
            if (passedCursor.getColumnIndex(PRODUCT_TITLE) != -1) {
                productTitleIndex = passedCursor.getColumnIndexOrThrow(PRODUCT_TITLE);
                productTitle = passedCursor.getString(productTitleIndex);
            }
            if (passedCursor.getColumnIndex(PRODUCT_STATUS) != -1) {
                productStatusIndex = passedCursor.getColumnIndexOrThrow(PRODUCT_STATUS);
                productStatus = passedCursor.getString(productStatusIndex);
            }
            if (passedCursor.getColumnIndex(PRODUCT_PURCHASE_DATE) != -1) {
                productStartIndex = passedCursor.getColumnIndexOrThrow(PRODUCT_PURCHASE_DATE);
                productStart = passedCursor.getString(productStartIndex);
            }
            if (passedCursor.getColumnIndex(PRODUCT_WARRANTY_END_DATE) != -1) {
                productEndIndex = passedCursor.getColumnIndexOrThrow(PRODUCT_WARRANTY_END_DATE);
                productEnd = passedCursor.getString(productEndIndex);
            }
        }
        //Returns new object.
        return new Product(productId, productTermId, productTitle, productStatus, productStart, productEnd);
    }
}

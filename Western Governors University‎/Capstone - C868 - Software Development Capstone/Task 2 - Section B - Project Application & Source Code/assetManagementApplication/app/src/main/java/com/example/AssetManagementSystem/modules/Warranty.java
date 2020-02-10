package com.example.AssetManagementSystem.modules;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Warranty {

    private int id;
    private int productId;
    private String warrantyName;
    private String warrantyType;
    private String warrantyExpirationDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    public Warranty(int id, int passedProductId, String passedWarrantyName, String passedWarrantyType, String passedExpirationDate) {
        this.id = id;
        this.productId = passedProductId;
        this.warrantyName = passedWarrantyName;
        this.warrantyType = passedWarrantyType;
        this.warrantyExpirationDate = passedExpirationDate;
    }

    public int getId() {
        return this.id;
    }

    public int getProductId() {
        return this.productId;
    }

    public String getWarrantyName() {
        return this.warrantyName;
    }

    public String getWarrantyType() {
        return this.warrantyType;
    }

    public String getWarrantyExpirationDate() {
        return this.warrantyExpirationDate;
    }

    public String getWarrantyTypeAndDate() {
        return this.warrantyType + " : " + this.warrantyExpirationDate;
    }

    @Override
    public String toString() {
        return this.warrantyName + " (" + this.warrantyType + ")";
    }

    public boolean isWarrantyValid() {
        if (warrantyName.isEmpty() || warrantyType.isEmpty() || warrantyExpirationDate.isEmpty()) {
            return false;
        }
        try {
            dateFormat.parse(warrantyExpirationDate);
        } catch (ParseException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}

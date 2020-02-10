package com.example.AssetManagementSystem.modules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Product {

    private final int id;
    private final int campusId;
    private final String productName;
    private final String productStatus;
    private final String productPurchaseDate;
    private final String productWarrantyEndDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    public Product(int passedId, int passedCampusId, String passedProductName, String passedProductSatus, String passedPurchaseDate, String passedWarrantyEndDate) {
        this.id = passedId;
        this.campusId = passedCampusId;
        this.productName = passedProductName;
        this.productStatus = passedProductSatus;
        this.productPurchaseDate = passedPurchaseDate;
        this.productWarrantyEndDate = passedWarrantyEndDate;
    }

    public int getId() {
        return this.id;
    }

    public int getCampusId() {
        return this.campusId;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getProductStatus() {
        return this.productStatus;
    }

    public String getProductPurchaseDate() {
        return this.productPurchaseDate;
    }

    public String getProductWarrantyEndDate() {
        return this.productWarrantyEndDate;
    }

    public String getProductDates() {
        return productPurchaseDate + " - " + productWarrantyEndDate;
    }

    @Override
    public String toString() {
        return productName + " (" + getProductDates() + ")";
    }

    public boolean isProductValid() {
        if (productName.isEmpty() || productStatus.isEmpty() || productPurchaseDate.isEmpty() || productWarrantyEndDate.isEmpty()) {
            return false;
        }
        try {
            Date startDate = dateFormat.parse(productPurchaseDate);
            Date endDate = dateFormat.parse(productWarrantyEndDate);
            if (!startDate.before(endDate)) {
                return false;
            }
        } catch (ParseException excpetion) {
            excpetion.printStackTrace();
            return false;
        }
        return true;
    }
}

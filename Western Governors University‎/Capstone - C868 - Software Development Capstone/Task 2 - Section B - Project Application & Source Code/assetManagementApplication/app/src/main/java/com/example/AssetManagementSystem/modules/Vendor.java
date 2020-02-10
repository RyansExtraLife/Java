package com.example.AssetManagementSystem.modules;

public class Vendor {

    private int id;
    private int productId;
    private String vendorName;
    private String vendorPhoneNumber;
    private String vendorEmail;

    public Vendor(int passedId, int passedProductId, String passedVendorName, String passedVendorPhoneNumber, String passedVendorEmail) {
        this.id = passedId;
        this.productId = passedProductId;
        this.vendorName = passedVendorName;
        this.vendorPhoneNumber = passedVendorPhoneNumber;
        this.vendorEmail = passedVendorEmail;
    }

    public int getId() {
        return this.id;
    }

    public int getProductId() {
        return this.productId;
    }

    public String getVendorEmail() {
        return this.vendorEmail;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public String getVendorPhoneNumber() {
        return this.vendorPhoneNumber;
    }

    public String getVendorEmailAndPhoneNumber() {
        return this.vendorPhoneNumber + " : " + this.vendorEmail;
    }

    @Override
    public String toString() {
        return this.vendorName;
    }

    public boolean isVendorValid() {
        return !vendorName.isEmpty() && !vendorEmail.isEmpty() && !vendorPhoneNumber.isEmpty();
    }
}

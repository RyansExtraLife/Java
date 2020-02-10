package com.example.AssetManagementSystem.dataAccesObjects;



import com.example.AssetManagementSystem.modules.Vendor;

import java.util.ArrayList;

interface VendorDAOImplementation {

    boolean addVendor(Vendor passedVendor);

    boolean updateVendor(Vendor passedVendor);

    boolean removeVendor(Vendor passedVendor);

    int getVendorCount();

    ArrayList<Vendor> getVendors();

    ArrayList<Vendor> getVendorsByProduct(int passedVendorId);

    Vendor getVendorById(int passedVendorId);
}

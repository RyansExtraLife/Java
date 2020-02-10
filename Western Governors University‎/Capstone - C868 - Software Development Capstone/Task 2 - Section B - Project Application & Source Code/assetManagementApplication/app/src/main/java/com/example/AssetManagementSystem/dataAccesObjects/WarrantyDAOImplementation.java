package com.example.AssetManagementSystem.dataAccesObjects;

import com.example.AssetManagementSystem.modules.Warranty;

import java.util.ArrayList;

public interface WarrantyDAOImplementation {

    boolean addWarranty(Warranty passedWarranty);

    boolean updateWarranty(Warranty passedWarranty);

    boolean removeWarranty(Warranty passedWarranty);

    int getWarrantyCount();

    ArrayList<Warranty> getWarranty();

    ArrayList<Warranty> getWarrantyByProduct(int passedProductId);

    Warranty getWarrantyById(int passedWarrantyId);
}

package com.example.AssetManagementSystem.dataAccesObjects;


import com.example.AssetManagementSystem.modules.Product;

import java.util.ArrayList;

public interface ProductDAOImplementation {

    boolean addProduct(Product passedProduct);

    boolean updateProduct(Product passedProduct);

    boolean removeProduct(Product passedProduct);

    int getProductCount();

    ArrayList<Product> getProducts();

    ArrayList<Product> getProductsByCampus(int passedTermId);

    Product getProductById(int passedCourseId);
}

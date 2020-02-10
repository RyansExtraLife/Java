
package inventorymanagement;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Inventory {
    
private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ObservableList<Part> allParts = FXCollections.observableArrayList();

    //getAllProducts():ObservableList<Product>
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    //setAllProducts(Product):void
    public void setAllProducts(ObservableList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    //getAllParts():ObservableList<Part>
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    //setAllParts(Part):void
    public void setAllParts(ObservableList<Part> allParts) {
        this.allParts = allParts;
    }

    //addProduct(product):void
    public void addProduct(Product passedProduct){
        this.allProducts.add(passedProduct);
    }

    //removeProduct(int):boolean
    public boolean removeProduct(int productID){
        try{
            int productIndex = productID;
            Product foundProduct = null;
            for (Product currentProduct : this.allProducts){
                if(productIndex == currentProduct.getProductID()){
                    foundProduct = currentProduct;
                    break;
                }
            }
            if (foundProduct != null) {
                allProducts.remove(foundProduct);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //checkForProduct(int):boolean
    public boolean checkForProduct(int productID){
        try{
            int productIndex = productID;
            Product foundProduct = null;
            for (Product currentProduct : this.allProducts){
                if(productIndex == currentProduct.getProductID()){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    //lookupProduct(int):product
    public Product lookupProduct (int productID){
        int productIndex = productID;
        Product foundProduct = null;
        for (Product currentProduct : this.allProducts){
            if(productID == currentProduct.getProductID()){
                foundProduct = currentProduct;
                break;
            }
        }
        if (foundProduct != null){
            return foundProduct;
        }
        return null;
    }

    //updateProduct(int):void
    public void updateProduct(int productID, Product passedProduct){
        for (Product currentProduct : this.allProducts){
            if(productID == currentProduct.getProductID()){
                allProducts.remove(currentProduct);
                allProducts.add(passedProduct);
                break;
            }
        }
    }

    //addPart(part):void
    public void addPart(Part passedPart){
        this.allParts.add(passedPart);
    }

    //deletePart(part):boolean
    public boolean deletePart(Part passedPart){
        try{
            int partIndex = passedPart.getPartID();
            Part foundPart = null;
            for (Part currentPart : this.allParts){
                if(partIndex == currentPart.getPartID()){
                    foundPart = currentPart;
                    break;
                }
            }
            if (foundPart != null) {
                allParts.remove(foundPart);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //lookupPart(int):part
    public Part lookupPart(int passedPartID){
        int partIndex = passedPartID;
        Part foundPart = null;
        for (Part currentPart : this.allParts){
            if(partIndex == currentPart.getPartID()){
                foundPart = currentPart;
                break;
            }
        }
        if(foundPart != null){
            return foundPart;
        }
        return null;
    }

    //updatePart(int, Part):void
    public void updatePart(int partID, Part passedPart){
        int partIndex = partID;
        for (Part currentPart : this.allParts){
            if(partIndex == currentPart.getPartID()){
                allParts.remove(currentPart);
                allParts.add(passedPart);
                break;
            }
        }
    }

    //isPartsEmpty():boolean
    public boolean isPartsEmpty (){
        if(allParts.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}

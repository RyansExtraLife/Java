package inventorymanagement;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;
    private boolean isDeleted = false;

    //getAssociatedParts():ObservableList<Part>
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    //setAssociatedParts(ObservableList<Part>):void
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    //setName(String):void
    public void setName(String name){
        this.name = name;
    }

    //getName():String
    public String getName(){
        return name;
    }

    //setPrice(double):void
    public void setPrice(double price){
        this.price = price;
    }

    //getPrice():double
    public double getPrice(){
        return price;
    }

    //setInStock(int):void
    public void setInStock(int inStock){
        this.inStock = inStock;
    }

    //getInStock():Int
    public int getInStock(){
        return inStock;
    }

    //setMin(int):void
    public void setMin(int min){
        this.min = min;
    }

    //getMin():int
    public int getMin(){
        return min;
    }

    //setMax(int):void
    public void setMax(int max){
        this.max = max;
    }

    //getMax():int
    public int getMax(){
        return max;
    }

    //addAssociatedPart(part):void
    public void addAssociatedPart(Part associatedParts){
        this.associatedParts.add(associatedParts);
    }

    //removeAssoicatedPart(Part):void
    public boolean removeAssociatedPart(Part associatedParts){
        try{
            this.associatedParts.remove(associatedParts);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //lookupAssoicatedPart(int):void
    public Part lookupAssociatedPart(int partID){
        //Check associatedParts arry for partID
        for (Part part : associatedParts){
            if(part.getPartID() == partID){
                return part;
            }
        }
        //If partID not in associatedParts array return null
        return null;
    }

    //setProdcutId(int):void
    public void setProductID(int productID){
        this.productID = productID;
    }

    //getProeductID():int
    public int getProductID(){
        return productID;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean compareProduct(Product passedProduct){
        //Comparision of Doubles
        double thisPrice = this.getPrice();
        double passedPrice = passedProduct.getPrice();
        double difference = thisPrice-passedPrice;
        System.out.print("Hello ");
        if(this.getProductID() != passedProduct.getProductID()){
            return false;
        }
        if(!(this.getName().equals(passedProduct.getName()))){
            return false;
        }
        if (Math.abs(difference-1.0) <= 0.0001) {
            return false;
        }
        if(this.getMin() != passedProduct.getMin()){
            return false;
        }
        if(this.getMax() != passedProduct.getMax()){
            return false;
        }
        System.out.print("true");
        return true;
    }
}

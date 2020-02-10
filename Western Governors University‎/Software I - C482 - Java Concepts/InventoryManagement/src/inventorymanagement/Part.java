package inventorymanagement;

public abstract class Part {
    private int partID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    //Default Constructor
    Part(){}
    
    //Part Constructor
    Part(int partId, String partName, double partPrice, int inStock, int min, int max){       
           this.partID = partId;
           this.name = partName;
           this.price = partPrice;
           this.inStock = inStock;
           this.min = min;
           this.max = max;
        }
    //setName(String):void
    public void setName(String name){
        this.name = name;
    }

    //getName():String
    public String getName(){
        return name;
    }

    //setPrice(Double):void
    public void setPrice(double price){
        this.price = price;
    }

    //getPrice():double
    public Double getPrice(){
        return price;
    }

    //setInStock(int):void
    public void setInStock(int inStock){
        this.inStock = inStock;
    }

    //getInScok():int
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

    //setPartID(int):void
    public void setPartID(int partID){
        this.partID = partID;
    }

    //getPartID():int
    public int getPartID(){
        return partID;
    }

    public boolean comparePart(Part passedPart){
        //Comparision of Doubles
        double thisPrice = this.getPrice();
        double passedPrice = passedPart.getPrice();
        double difference = thisPrice-passedPrice;

        if(this.getPartID() != passedPart.getPartID()){
           return false;
        }
        if(!(this.getName().equals(passedPart.getName()))){
            return false;
        }
        if (Math.abs(difference-1.0) <= 0.000001) {
            return false;
        }
        if(this.getMin() != passedPart.getMin()){
            return false;
        }
        if(this.getMax() != passedPart.getMax()){
            return false;
        }
        return true;
    }

    public String getCompanyName() {return null;}

    //setCompanyName(String):void
    public void setCompanyName(String companyName){}
    
        //getMachineID():int
    public int getMachineID() {return -1;}

    //setMachineID(int):void
    public void setMachineID(int machineID) {}
}

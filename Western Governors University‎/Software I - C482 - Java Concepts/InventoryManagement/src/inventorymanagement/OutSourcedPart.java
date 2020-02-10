package inventorymanagement;

public class OutSourcedPart extends Part{
    
    private String companyName;
    
    //Default Constructor
    OutSourcedPart(){
        super();
        this.companyName = null;
    }
    
    //Constructor
    OutSourcedPart(int partID, String partName, double partPrice, int instock, int min, int max, String companyName){
        super(partID, partName, partPrice, instock, min, max);
        this.companyName = companyName;
    }

    //getCompanyName():String
    @Override
    public String getCompanyName() {return companyName;}

    //setCompanyName(String):void
    @Override
    public void setCompanyName(String companyName){this.companyName = companyName;}
}


package inventorymanagement;

public class InHousePart extends Part {

    private int machineID;
    
    //Default Constructor
    InHousePart(){
        super();
        this.machineID = -1;
    }
    
    //Constructor
    InHousePart(int partID, String partName, double partPrice, int instock, int min, int max, int machineID){
        super(partID, partName, partPrice, instock, min, max);
        this.machineID = machineID;
    }

    //getMachineID():int
    @Override
    public int getMachineID() {return machineID;}

    //setMachineID(int):void
    @Override
    public void setMachineID(int machineID) {this.machineID = machineID;}
}


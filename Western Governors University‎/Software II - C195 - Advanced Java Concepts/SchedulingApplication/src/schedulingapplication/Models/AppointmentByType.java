package schedulingapplication.Models;

public class AppointmentByType {
    private String appointmentType;
    private int appointmentTotal;

    //Default Constructor
    public AppointmentByType() {}

    //Custom Constructor
    public AppointmentByType(String passedAppointmentType, int passedAppointmentTotal) {
        appointmentType = passedAppointmentType;
        appointmentTotal= passedAppointmentTotal;
    }
    
    //getAppointmentType():String
    public String getAppointmentType() {
        return appointmentType;
    }

    //setAppointmentType(String):void
    public void setAppointmentType(String passedType) {
        appointmentType = passedType;
    }

    //getAppointmentTotal():int
    public int getAppointmentTotal() {
        return appointmentTotal;
    }

    //setAppointmentTotal(int):void
    public void setAppointmentTotal(int passedCount) {
        appointmentTotal = passedCount;
    }
}
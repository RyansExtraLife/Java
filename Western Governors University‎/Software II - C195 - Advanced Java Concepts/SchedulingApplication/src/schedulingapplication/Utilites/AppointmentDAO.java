
package schedulingapplication.Utilites;

//
import schedulingapplication.Models.Appointment;
import schedulingapplication.Models.Customer;
import schedulingapplication.Models.User;

import java.time.LocalDate;

import java.util.ArrayList;

import javafx.collections.ObservableList;

public interface AppointmentDAO {
    
    //Returns an ArrayList with the differnt appointment types by month. 
    ArrayList<ObservableList> getAppointmentTypeByMonth(ArrayList<ObservableList> passedDataList, String passedYear);

    //Returns an ObservableLIst with the different Appointments by month. 
    ObservableList<Appointment> getAppointmentsByMonth(LocalDate passedDate);
    
    

    ObservableList<Appointment> getAppointmentsByWeek(LocalDate date);

    ObservableList<Appointment> getAppointmentsByDay(LocalDate date);

    ObservableList<Appointment> getAppointmentsByUser(User consultant);

    ObservableList<Appointment> getAppointmentsByCustomer(Customer customer);
    
    ObservableList<String> getYearList();

    boolean addAppointment(Appointment apt);
   
    boolean updateAppointment(Appointment apt, Appointment deleteapt);

    boolean deleteAppointment(Appointment apt);
}

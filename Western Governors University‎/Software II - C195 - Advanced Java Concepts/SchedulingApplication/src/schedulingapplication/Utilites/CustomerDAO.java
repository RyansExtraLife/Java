//
package schedulingapplication.Utilites;

//
import schedulingapplication.Models.Customer;

//
import javafx.collections.ObservableList;

public interface CustomerDAO {
    //
    boolean addCustomer(Customer customer);
    
    //
    boolean updateCustomer(Customer customer);
    
    //
    boolean deleteCustomer(Customer customer);
    
    //
    ObservableList<Customer> getActiveCustomers();

    //
    ObservableList<Customer> getAllCustomers();
}

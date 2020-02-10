package schedulingapplication.Utilites;

import schedulingapplication.Models.User;

import javafx.collections.ObservableList;

public interface UserDAO {
    //
    User getUser(String userName, String password) throws InvalidUserCredentialsException;
    
    //
    ObservableList<User> getActiveUserSerssions();
}

package schedulingapplication;

//Import application view controllers. 
import schedulingapplication.Views.AppointmentWindowController;
import schedulingapplication.Views.CustomerWindowController;
import schedulingapplication.Views.LoginWindowController;
import schedulingapplication.Views.ModifyAppointmentWindowController;
import schedulingapplication.Views.ModifyCustomerWindowController;
import schedulingapplication.Views.ReportWindowController;

//Import Data Models. 
import schedulingapplication.Models.Address;
import schedulingapplication.Models.Appointment;
import schedulingapplication.Models.City;
import schedulingapplication.Models.Country;
import schedulingapplication.Models.Customer;
import schedulingapplication.Models.User;

//Import I/O libraries.
import java.io.IOException;

//Imort JavaFx libraries. 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class SchedulingApplication extends Application {

    //Local Variables
    private Stage primaryStage;
    private String currentLanguage = "en";
    private boolean initialLogin = false;
    private User currentUser;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Scheduling Application");
        showLoginWindow();
    }

    
    //Launches the applicaiton login window. 
    public void showLoginWindow() {
        try{
            //Create new FXMLLoader for loginWindow
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SchedulingApplication.class.getResource("Views/LoginWindow.fxml"));
            AnchorPane mainAnchorPane = (AnchorPane) fxmlLoader.load();
            
            //Create an instnace of loginWindow
            LoginWindowController loginController = fxmlLoader.getController();
            loginController.setMainWidnow(this);
            
            //Set scene for loginWindow
            Scene loginScene = new Scene(mainAnchorPane);
            
            primaryStage.setScene(loginScene);
            primaryStage.show();  
        }catch (IOException excpetion){
            excpetion.printStackTrace(System.out);
        }
    }
    
    //Launches the application appointment window. 
    public void showAppointmentWindow(){
        try{
            //Create new FXMLLoader for appointmentWindow
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SchedulingApplication.class.getResource("Views/AppointmentWindow.fxml"));
            Parent appointmentsScreenParent = fxmlLoader.load();
            Scene appointmentWindow = new Scene(appointmentsScreenParent);

            //Create Scene for appointmentWindow
            AppointmentWindowController appointmentController = fxmlLoader.getController();
            appointmentController.setMainWindow(this);
            
            if(initialLogin){
                appointmentController.checkForUpcomingApts();
                initialLogin = false;
            }
            
            //Set scene for appointmentWindow
            primaryStage.setScene(appointmentWindow);
            primaryStage.show(); 

        }catch (IOException excpetion){
            excpetion.printStackTrace(System.out);
        }
    }

    //Launches the applications modify appointments window. 
    public void showModifyAppointmentWindow(Boolean passedMode, Appointment passedAppointment){
        try{
            //Create new FXMLLoader for modifyAppointmentWindow
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SchedulingApplication.class.getResource("Views/ModifyAppointmentWindow.fxml"));
            Parent modifyAppointmentsWindowParent = fxmlLoader.load();
            Scene modifyAppointmentWindow = new Scene(modifyAppointmentsWindowParent);

            //Create Scene for appointmentWindow
            ModifyAppointmentWindowController modifyAppointmentController = fxmlLoader.getController();
            modifyAppointmentController.modifyAppointmentMode(passedMode, passedAppointment);
            modifyAppointmentController.setMainWindow(this);

            //Set scene for appointmentWindow
            primaryStage.setScene(modifyAppointmentWindow);
            primaryStage.show(); 
        }catch (IOException excpetion){
            excpetion.printStackTrace(System.out);
        }
    }
        
    //Launches the applications customer window. 
    public void showCustomerWindow() {
        try{
            //Create new FXMLLoader for showCustomerWindow.
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SchedulingApplication.class.getResource("Views/CustomerWindow.fxml"));
            Parent customerWindowParent = fxmlLoader.load();
            Scene customerWindow = new Scene(customerWindowParent);
            
            //Create Scene for customerWindow 
            CustomerWindowController customerController = fxmlLoader.getController();
            customerController.setMainWindow(this);
            
            //Set scene for customerWindow
            primaryStage.setScene(customerWindow);
            primaryStage.show(); 
        }catch (IOException excpetion){
            excpetion.printStackTrace(System.out);
        }
    }
    
    //Launches the applicaitons modify customer window. 
    public void showModifyCustomerWindow(String passedMode, Customer passedCustomer){
        try{
            //Create new FXMLoader for modifyCustomerWindow. 
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SchedulingApplication.class.getResource("Views/ModifyCustomerWindow.fxml"));
            Parent modifyCustomerWindowParent = fxmlLoader.load();
            Scene modifyCustomerWindow = new Scene(modifyCustomerWindowParent);
            
            //Create Scene for modifyCustomerWindow
            ModifyCustomerWindowController modifyCustomerController = fxmlLoader.getController();
            modifyCustomerController.setMainWindow(this);
            modifyCustomerController.modifyCustomerMode(passedMode, passedCustomer);
            
            //Set scene for modifyCustomerWindow
            primaryStage.setScene(modifyCustomerWindow);
            primaryStage.show(); 
        }catch (IOException excpetion){
            excpetion.printStackTrace(System.out);
        }
    }
    
    //Launches the applications report window. 
    public void showReportWindow(){
        try{ 
            //Create new FXMLLoader for reportWindow. 
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SchedulingApplication.class.getResource("Views/ReportWindow.fxml"));
            Parent reportWindowParent = fxmlLoader.load();
            Scene reportCustomerWindow = new Scene(reportWindowParent);
            
            //Create Scene for showReportWindow
            ReportWindowController reportController = fxmlLoader.getController();
            reportController.setMainWidnow(this);
            
            //Set scene for reportWindow
            primaryStage.setScene(reportCustomerWindow);
            primaryStage.show(); 
        }catch (IOException excpetion){
            excpetion.printStackTrace(System.out);
        }
    }
    
    //Varible getters and setters.
    public boolean isInitialLogin() {
        return initialLogin;
    }

    public void setInitialLogin(boolean initialLogin) {
        this.initialLogin = initialLogin;
    }
    
    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
    }
    
    public User getCurrentSessionUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public static void main(String[] args) {
        launch(args);
    }  
}

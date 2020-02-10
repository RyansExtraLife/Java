package schedulingapplication.Views;

//Import application libraries. 
import schedulingapplication.Models.User;
import schedulingapplication.SchedulingApplication;
import schedulingapplication.Utilites.UserDAO;
import schedulingapplication.Utilites.UserDAOImplementation;
import schedulingapplication.Utilites.InvalidUserCredentialsException;
import schedulingapplication.Utilites.ApplicationLogger;

//Import Java misc utilites libraries.
import java.util.Locale;
import java.util.ResourceBundle;

//Import JavaFX libraries.
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginWindowController {
    //FXML Local Variables
    @FXML
    private Label applicationHeaderLabel;
    @FXML
    private Label currentLocal;
    @FXML
    private Label usernameLoginLabel;
    @FXML
    private TextField usernameLoginTextField;
    @FXML
    private Label passwordLoginLabel;
    @FXML
    private TextField passwordLoginTextField;
    @FXML
    private Label selectLocale;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Button loginButton;
    
    //Class local variables.
    private SchedulingApplication setMainWindow;
    private Locale currentLocale;
    private String locationVaribale;
    private ResourceBundle languageResourceBundle;
    
    //
    private UserDAO userDatabaseObject; 

    //Default Constructor.
    public LoginWindowController(){}
    
    //FXML Initizlier.
    @FXML
    public void initialize() {
        //Initialize Default Locale; 
        //currentLocale = Locale.setDefault()
        //Functioned used to change the local. 
        changeLocale("en");
        userDatabaseObject = new UserDAOImplementation();
        ApplicationLogger.logApplicationAction("Login Window has been initialized.");
    }
       
    //Sets the login window as the main window. 
    public void setMainWidnow(SchedulingApplication setMainWindow) {
        this.setMainWindow = setMainWindow;
    }
    
    //Function that handles the action of when the ComboBox is changed. 
    @FXML
    public void changeInComboBox(){
        String selectedLanguage = languageComboBox.getValue();
        if("United States".equals(selectedLanguage)){
            changeLocale("en");
            ApplicationLogger.logApplicationAction("Application language changed to English.");
            setMainWindow.setCurrentLanguage("en");
        }
        if("Spain".equals(selectedLanguage)){
            changeLocale("ca");
            ApplicationLogger.logApplicationAction("Application language changed to Spanish.");
            setMainWindow.setCurrentLanguage("ca");
        }
        if("France".equals(selectedLanguage)){
            changeLocale("fr");
            ApplicationLogger.logApplicationAction("Application language changed to French.");
            setMainWindow.setCurrentLanguage("fr");
        }
    }
    
    //Function that handles the change of locale. 
    private void changeLocale(String passedLocale){
        currentLocale = new Locale(passedLocale);
        languageResourceBundle = ResourceBundle.getBundle("schedulingapplication.Resources/languageProperties", currentLocale);
        applicationHeaderLabel.setText(languageResourceBundle.getString("applicationHeaderLabel"));
        usernameLoginLabel.setText(languageResourceBundle.getString("userName"));
        passwordLoginLabel.setText(languageResourceBundle.getString("password"));
        selectLocale.setText(languageResourceBundle.getString("selectLocale"));
        loginButton.setText(languageResourceBundle.getString("loginButton"));
    }
    
    //Function that handles the action of when the login button is clicked. 
    @FXML
    public void loginButtonActioned() {
        //If testLoginWindows retuns true.
        if(testLoginWindowTextFields()){
            try{
                User sessionUser = userDatabaseObject.getUser(usernameLoginTextField.getText(), passwordLoginTextField.getText());
                setMainWindow.setCurrentUser(sessionUser);
                //Sets inital login to true allowing 15 minut timer notifcation.
                setMainWindow.setInitialLogin(true);
                //Loggs user login attempt.
                ApplicationLogger.logUserLoginAttempt(sessionUser.getUserName(), true);
                setMainWindow.showAppointmentWindow();
            }catch(InvalidUserCredentialsException excpetion){
                throwError(languageResourceBundle.getString("invalidCredentials"));
            }
        }
    } 
    
    //Tests password and text field for proper input. 
    private boolean testLoginWindowTextFields(){  
        String thrownErrorMessage = "";
        //Checks user name for null or empty. 
        if (usernameLoginTextField.getText() != null && usernameLoginTextField.getText().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidUsername");
        }
        //Checks password for null or empty. 
        if (passwordLoginTextField.getText() != null && passwordLoginTextField.getText().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidPassword");
        }
        //Thorw custom error message by region.
        if (thrownErrorMessage.length() != 0){
            ApplicationLogger.logApplicationAction("Application login attempt failed.");
            throwError(thrownErrorMessage);
            return false;
        }else{
            
            return true;
        }
    }

    //Function that provides error handling for login window contoller.
    private void throwError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(languageResourceBundle.getString("errorTitle"));
        alert.setHeaderText(languageResourceBundle.getString("errorHeader"));
        alert.getDialogPane().setContent(new Label(errorMessage));
        alert.showAndWait();
    }
}

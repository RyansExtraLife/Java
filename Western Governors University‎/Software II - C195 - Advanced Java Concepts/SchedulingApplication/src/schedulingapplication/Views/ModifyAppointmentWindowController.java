package schedulingapplication.Views;

//Import application libraries.
import schedulingapplication.SchedulingApplication;
import schedulingapplication.Models.Appointment;
import schedulingapplication.Models.Customer;
import schedulingapplication.Models.User;
import schedulingapplication.Utilites.UserDAOImplementation;
import schedulingapplication.Utilites.ApplicationLogger;
import schedulingapplication.Utilites.AppointmentDAO;
import schedulingapplication.Utilites.AppointmentDAOImplementation;
import schedulingapplication.Utilites.CustomerDAO;
import schedulingapplication.Utilites.CustomerDAOImplementation;
import schedulingapplication.Utilites.UserDAO;


//Import Java misc utilites libraries.
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Import JavaFX libraries.
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;




public class ModifyAppointmentWindowController {
    //FXML Local Variables
    @FXML
    private Label modifyAppointmentLabel;     
    @FXML
    private Label titleLabel;
    @FXML
    private TextField titleTextField;      
    @FXML
    private Label startDateLabel;       
    @FXML
    private DatePicker startDateDatePicker;
    @FXML
    private Label startTimeLabel;
    @FXML
    private TextField startTimeTextField;
    @FXML
    private Label endDateLabel;
    @FXML
    private DatePicker endDateDatePicker;
    @FXML
    private Label endTimeLabel;        
    @FXML
    private TextField endTimeTextField; 
    @FXML
    private Label locationLabel;
    @FXML
    private TextField locationTextField;
    @FXML
    private Label contactLabel;
    @FXML        
    private ChoiceBox<Customer> customerChoiceBox;        
    @FXML
    private Label typeLabel;
    @FXML
    private TextField typeTextField;      
    @FXML
    private Label urlLabel;
    @FXML
    private TextField urlTextField;    
    @FXML
    private Label userLabel;
    @FXML
    private ChoiceBox<User> userChoiceBox;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Label selectLocale;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    
    //Local Variables
    //Change to accomodate business hours (Military Time)
    private int businessStartTime = 9;
    private int businessEndTime = 17;
    
    private boolean editMode;
    private SchedulingApplication setMainWindow;
    private String locationVaribale;
    private Locale currentLocale;
    private ResourceBundle languageResourceBundle;
    private Appointment currentAppointment;
    private Appointment passedAppointment;
    private AppointmentDAO appointmentDAO;
    private CustomerDAO customerDAO;
    private UserDAO userDAO;
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
            
    //
    public ModifyAppointmentWindowController(){}
    
    //FXML Initizlier.
    @FXML
    public void initialize() {
        //Initialize Default Locale;
        changeLocale("en");
        
        appointmentDAO = new AppointmentDAOImplementation();
        customerDAO = new CustomerDAOImplementation();
        customers = customerDAO.getActiveCustomers();
        userDAO = new UserDAOImplementation();
        users = userDAO.getActiveUserSerssions();
      

        //Sets the UserChoiceBox to all unique users from database. 
        userChoiceBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User object) {
                return object.getUserName();
            }

            @Override
            public User fromString(String string) {
                return null; 
            }
        });
        userChoiceBox.setItems(users);
        
        //Sets the CustomerChocieBox to all unqiue customers from database.
        customerChoiceBox.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer object) {
                return object.getCustomerName();
            }

            @Override
            public Customer fromString(String string) {
                return null; 
            }
        });
        customerChoiceBox.setItems(customers);
        
        
        ApplicationLogger.logUserAction("System Log","Appointment Window has been initialized.");
    }
    
    //sets this window as default scene. 
    public void setMainWindow(SchedulingApplication setMainWindow) {
        this.setMainWindow= setMainWindow;
    }
    
    //Handes if the appointment is an add or modify. 
    public void modifyAppointmentMode(Boolean passedMode, Appointment passedAppointment) {
        if(passedMode){    
            this.editMode = true;
                this.passedAppointment = passedAppointment;
                modifyAppointmentLabel.setText("Edit Appointment");
                titleTextField.setText(passedAppointment.getTitle());
                startDateDatePicker.setValue(passedAppointment.getStart().toLocalDate());
                startTimeTextField.setText(passedAppointment.getStart().format(DateTimeFormatter.ofPattern("hh:mm a")));
                endDateDatePicker.setValue(passedAppointment.getStart().toLocalDate());
                endTimeTextField.setText(passedAppointment.getEnd().format(DateTimeFormatter.ofPattern("hh:mm a")));
                locationTextField.setText(passedAppointment.getLocation());
                customerChoiceBox.setValue(passedAppointment.getCustomer());
                typeTextField.setText(passedAppointment.getType());
                urlTextField.setText(passedAppointment.getUrl());
                userChoiceBox.setValue(passedAppointment.getUser());
                descriptionTextArea.setText(passedAppointment.getDescription());
        } else {
            modifyAppointmentLabel.setText("Add Appointment");
            this.editMode = false;
        }
    }
    
    //Function that handles the action of when the ComboBox is changed. 
    @FXML
    public void changeInComboBox(){
        String selectedLanguage = languageComboBox.getValue();
        if("United States".equals(selectedLanguage)){
            changeLocale("en");
            setMainWindow.setCurrentLanguage("en");
        }
        if("Spain".equals(selectedLanguage)){
            changeLocale("ca");
            setMainWindow.setCurrentLanguage("ca");
        }
        if("France".equals(selectedLanguage)){
            changeLocale("fr");
            setMainWindow.setCurrentLanguage("fr");
        }
    }
    
    //Function that handles the change of locale. 
    private void changeLocale(String passedLocale){
        currentLocale = new Locale(passedLocale);
        languageResourceBundle = ResourceBundle.getBundle("schedulingapplication.Resources/languageProperties", currentLocale);
        
        modifyAppointmentLabel.setText(languageResourceBundle.getString("modifyAppointment"));
        titleLabel.setText(languageResourceBundle.getString("titleLabel"));
        startDateLabel.setText(languageResourceBundle.getString("startDate"));
        startTimeLabel.setText(languageResourceBundle.getString("startTime"));
        endDateLabel.setText(languageResourceBundle.getString("endDate"));
        endTimeLabel.setText(languageResourceBundle.getString("endTime"));
        locationLabel.setText(languageResourceBundle.getString("location"));
        contactLabel.setText(languageResourceBundle.getString("contact"));
        typeLabel.setText(languageResourceBundle.getString("type"));
        urlLabel.setText(languageResourceBundle.getString("url"));
        userLabel.setText(languageResourceBundle.getString("user"));
        descriptionLabel.setText(languageResourceBundle.getString("description"));
    }
    
    //Hanldes actions to the save button.
    @FXML
    public void saveButtonActioned(){
        //Test fields for proper input. 
        if (testModifyAppointmentWindowTextFields()) {

            LocalDateTime start = LocalDateTime.of(startDateDatePicker.getValue(), LocalTime.parse(startTimeTextField.getText(), DateTimeFormatter.ofPattern("hh:mm a")));
            LocalDateTime end = LocalDateTime.of(endDateDatePicker.getValue(), LocalTime.parse(endTimeTextField.getText(), DateTimeFormatter.ofPattern("hh:mm a")));
            if (currentAppointment == null) {
                currentAppointment = new Appointment();
                currentAppointment.setCreateDate(LocalDate.now());
                currentAppointment.setCreatedBy(setMainWindow.getCurrentSessionUser().getUserName());
            }
            currentAppointment.setTitle(titleTextField.getText());
            currentAppointment.setContact(((User) userChoiceBox.getValue()).getUserName());
            currentAppointment.setCustomer((Customer) customerChoiceBox.getValue()); //
            currentAppointment.setDescription(descriptionTextArea.getText());
            currentAppointment.setEnd(end);
            currentAppointment.setLastUpdate(LocalDateTime.now());
            currentAppointment.setLastUpdateBy(setMainWindow.getCurrentSessionUser().getUserName());
            currentAppointment.setLocation(locationTextField.getText());
            currentAppointment.setStart(start);
            currentAppointment.setType(typeTextField.getText());
            currentAppointment.setUrl(urlTextField.getText());
            currentAppointment.setUser((User) userChoiceBox.getValue());

            //varible to test if the modifation was sucessfuall.
            boolean modifcationSucessfull = false;
            if(editMode == false){
                    modifcationSucessfull = appointmentDAO.addAppointment(currentAppointment);
                    ApplicationLogger.logUserAction(setMainWindow.getCurrentSessionUser().getUserName(), "Sucessfully updated a appointment.");
            }
            if(editMode == true){
                    modifcationSucessfull = appointmentDAO.updateAppointment(currentAppointment, passedAppointment);
                    ApplicationLogger.logUserAction(setMainWindow.getCurrentSessionUser().getUserName(), "Sucessfully created a new appointment.");
            }
            if (modifcationSucessfull) {
                setMainWindow.showAppointmentWindow();
            } else {
                throwError("SQL Failure: Could not add/modify appointment.");
            }
        }
    }
    
    //Handles action to cancle button
    @FXML
    public void cancelButtonActioned(){
        if (throwConfirmation("Are you sure you want to cancle?")) {
            setMainWindow.showAppointmentWindow();
        }
    }
    
 
    //Tests the 
    private boolean testModifyAppointmentWindowTextFields(){
        String thrownErrorMessage = "";
        
        LocalTime appointmentsStartTime = null;
        LocalTime appointmentEndTime = null;
         
        if (titleTextField.getText() == null || titleTextField.getText().equals("")){
            thrownErrorMessage += languageResourceBundle.getString("invalidTitle");
        }

        if (startDateDatePicker.getValue() == null) {
            thrownErrorMessage += "The start date is empty.\n";
        }
        if (startTimeTextField.getText() == null || startTimeTextField.getText().equals("")) {
            thrownErrorMessage += "The start time is empty.\n";
        } else {
            try {
                appointmentsStartTime = LocalTime.parse(startTimeTextField.getText(), DateTimeFormatter.ofPattern("hh:mm a"));
   
                if (appointmentsStartTime.isBefore(LocalTime.of(businessStartTime, 0)) || appointmentsStartTime.isAfter(LocalTime.of(businessEndTime, 0))) {
                    thrownErrorMessage += "Start time must be between these hours, 9:00AM - 6:00PM.\n";
                }
            } catch (DateTimeParseException exception) {
                thrownErrorMessage += "Time must be in the following format: hh:mm AM/PM \n";
            }
        }
  
        if (endDateDatePicker.getValue() == null) {
            thrownErrorMessage += "The end date is empty. \n";
        }
        
        if (endTimeTextField.getText() == null || endTimeTextField.getText().equals("")) {
            thrownErrorMessage += "The end time is empty \n";
        } else {
            try {
                appointmentEndTime = LocalTime.parse(endTimeTextField.getText(), DateTimeFormatter.ofPattern("hh:mm a"));
   
                if (appointmentEndTime.isBefore(LocalTime.of(businessStartTime, 0)) || appointmentsStartTime.isAfter(LocalTime.of(businessEndTime, 0))) {
                    thrownErrorMessage += "End time must be between these hours, 9:00AM - 6:00PM.\n";
                }
            } catch (DateTimeParseException exception) {
                thrownErrorMessage += "Time must be in the following format: hh:mm AM/PM \n";
            }
        }
 
        if (locationTextField.getText() != null && locationTextField.getText().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidLocation");
        }
        if (customerChoiceBox.getValue() != null && customerChoiceBox.getValue().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidContact");
        }
        if (typeTextField.getText() != null && typeTextField.getText().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidType");
        }
        if (urlTextField.getText() != null && urlTextField.getText().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidurl");
        }
        if (descriptionTextArea.getText() != null && descriptionTextArea.getText().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidDescription");
        }

        if (thrownErrorMessage.length() != 0){
            throwError(thrownErrorMessage);
            return false;
        }else{
            LocalDateTime conflictStartTime = LocalDateTime.of(startDateDatePicker.getValue(), LocalTime.parse(startTimeTextField.getText(), DateTimeFormatter.ofPattern("hh:mm a")));
            LocalDateTime conflictEndTime = LocalDateTime.of(endDateDatePicker.getValue(), LocalTime.parse(endTimeTextField.getText(), DateTimeFormatter.ofPattern("hh:mm a")));
            LocalDate conflictDate = startDateDatePicker.getValue();
            boolean appointmentConfilct = false;
            if (editMode){
                appointmentConfilct = checkAppointmentConflicts(conflictStartTime, conflictEndTime, true);
            }else{
                appointmentConfilct = checkAppointmentConflicts(conflictStartTime, conflictEndTime, false);
            }
            if(appointmentConfilct){
                throwError(languageResourceBundle.getString("timeConflict"));
                return false;
            }else{
                return true;
            }
        }
    }
    
    //Checks for upcoming appointments. Called from schedling application upon inital load. 
    private boolean checkAppointmentConflicts(LocalDateTime passedStartTime, LocalDateTime passedEndTime, boolean passedEditMode){
        ObservableList<Appointment> customerAppointments = appointmentDAO.getAppointmentsByCustomer((Customer) customerChoiceBox.getValue());
        customerAppointments.addAll(appointmentDAO.getAppointmentsByUser((User) userChoiceBox.getValue()));
        
        if (customerAppointments.size() >= 1) {
            for (Appointment currentAppointment : customerAppointments){
                if(passedEditMode){
                    if (currentAppointment.getAppointmentId() != passedAppointment.getAppointmentId()){
                        if (currentAppointment.getStart().getDayOfMonth() == passedStartTime.getDayOfMonth()){ 
                            if(!passedStartTime.toLocalTime().isBefore(currentAppointment.getStart().toLocalTime()) && passedStartTime.toLocalTime().isBefore(currentAppointment.getEnd().toLocalTime())){
                                return true;
                            }
                            if(!passedEndTime.toLocalTime().isBefore(currentAppointment.getStart().toLocalTime()) && passedEndTime.toLocalTime().isBefore(currentAppointment.getEnd().toLocalTime())){
                                return true;
                            }   
                        }            
                    }
                }else{
                    if (currentAppointment.getStart().getDayOfMonth() == passedStartTime.getDayOfMonth()){ 
                        if(!passedStartTime.toLocalTime().isBefore(currentAppointment.getStart().toLocalTime()) && passedStartTime.toLocalTime().isBefore(currentAppointment.getEnd().toLocalTime())){
                            return true;
                        }
                        if(!passedEndTime.toLocalTime().isBefore(currentAppointment.getStart().toLocalTime()) && passedEndTime.toLocalTime().isBefore(currentAppointment.getEnd().toLocalTime())){
                            return true;
                        }   
                    }
                }
            }
        }
        return false; 
    }
   
    
    //
    private boolean throwConfirmation(String passedContentText) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(languageResourceBundle.getString("confirmationTitle"));
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(languageResourceBundle.getString("cancelConfirm"));
        ButtonType yes = new ButtonType(languageResourceBundle.getString("yesButton"), ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType(languageResourceBundle.getString("noButton"), ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.get() == yes;
    }
    
    //
    private void throwError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(languageResourceBundle.getString("errorTitle"));
        alert.setHeaderText(languageResourceBundle.getString("errorHeader"));
        alert.getDialogPane().setContent(new Label(errorMessage));
        alert.showAndWait();
    }
    
}

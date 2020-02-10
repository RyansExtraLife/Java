package schedulingapplication.Views;

//Import application libraries. 
import schedulingapplication.Models.Appointment;
import schedulingapplication.SchedulingApplication;
import schedulingapplication.Utilites.ApplicationLogger;
import schedulingapplication.Utilites.AppointmentDAO;
import schedulingapplication.Utilites.AppointmentDAOImplementation;

//Import Java misc utilites libraries.
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;

//Import JavaFX libraries.
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AppointmentWindowController {
    //FXML Local Variables
    @FXML
    private Label appointmentHeaderLabel;
    @FXML
    private Button modifyAppointmentWindowButton;
    @FXML
    private Button deleteAppointmentWindowButton;
    @FXML
    private Button previousTimeIntervalButton;
    @FXML
    private Label currentTimeIntervalLabel;
    @FXML
    private Button succeedingTimeIntervalButton;
    @FXML
    private ComboBox<String> dateRangeComboBox;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentStartTimeColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentEndTimeColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentCustomerColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentConsultantColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentLocationColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;
    @FXML
    private Label selectLocale;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Button applicaitonWindowButton;
    @FXML
    private Button customerWindowButton;
    @FXML
    private Button reportsWindowButton;
    @FXML
    private Button exitApplicationButton;
    
    //Class local variables.
    private Appointment selectedAppointment;
    private AppointmentDAO appointmentDAO;
    private int sortBy = 0;
    private LocalDate currentDate;
    private Locale currentLocale;
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private ResourceBundle languageResourceBundle;
    private SchedulingApplication setMainWindow;
    private String locationVaribale;

    //Default Constructor.
    public AppointmentWindowController(){}
    
    //FXML Initizlier.
    @FXML
    public void initialize() {
        //Initialize Default Locale.
        changeLocale("en");
        currentDate = LocalDate.now();
        
        //Initialize Table View cells using Lamda expressions. 
        appointmentDAO = new AppointmentDAOImplementation();
        appointmentTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        appointmentStartTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        appointmentEndTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        appointmentCustomerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        appointmentConsultantColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getUserName()));
        appointmentLocationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        appointmentTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        //Update the tableview by month (defualt)
        updateAppointmentTableByMonth();

        ApplicationLogger.logApplicationAction("Appointment Window has been initialized.");
    }
    
    //Sets the currentAppointment window as the main window. 
    public void setMainWindow(SchedulingApplication setMainWindow) {
        this.setMainWindow= setMainWindow;
    }
    
    //Function that handles actions to the modify currentAppointment button. 
    @FXML
    public void actionModifyAppointmentButton() {
        //If a cell is selected assign to varible. 
        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        boolean editmode = false;
        //If a cell is selected go to edit mode. If not add mode. 
        if(selectedAppointment != null){
            editmode = true;
            setMainWindow.showModifyAppointmentWindow(editmode, selectedAppointment);
        }else{
            setMainWindow.showModifyAppointmentWindow(editmode,null);
        }
    }
    
    //Function that handles actions to the delete currentAppointment button. 
    @FXML
    public void actionDeleteAppointmentWindowButton(){
        //Varible that holds if the delete was successfull. 
        boolean modifcationSucessfull;
        //If a cell is selected when button is pushed assign to varible.
        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if(selectedAppointment != null){
            if(throwConfirmationAlert()){
                //Attempt delete, if successfull assign true to variable.
                modifcationSucessfull = appointmentDAO.deleteAppointment(selectedAppointment);
                //Update table to reflect delete depending on month or week view. 
                if(modifcationSucessfull){
                    if(sortBy == 0){
                        updateAppointmentTableByMonth();
                    }
                    if(sortBy == 1){
                        while (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                            currentDate = currentDate.minusDays(1);
                        }
                        updateAppointmentTableByWeek();
                    } 
                    ApplicationLogger.logUserAction(setMainWindow.getCurrentSessionUser().getUserName(), "Sucessfully updated a Customer.");
                } else {
                    throwError("Delete was unsuccessfuall.");
                }
            }
        }else{
            throwError("Please select an appointment to delete.");
        }
    }
    
    //Function that hanldes the previous time interval button.
    @FXML
    public void actionPreviousTimeIntervalButton(){
        if (sortBy == 0) {
            currentDate = currentDate.minusMonths(1);
            updateAppointmentTableByMonth();
        }
        if (sortBy == 1) {
            currentDate = currentDate.minusWeeks(1);
            updateAppointmentTableByWeek();
        }
    }

    //Function that hanldes the succeeding time interval button.
    @FXML
    public void actionSuccedingTimeIntervalButton(){
        if (sortBy == 0) {
            currentDate = currentDate.plusMonths(1);
            updateAppointmentTableByMonth();
        } 
        if (sortBy == 1) {
            currentDate = currentDate.plusWeeks(1);
            updateAppointmentTableByWeek();
        }
    }
    
    //Function that handles the action of when the data range combobox is changed. 
    @FXML
    public void changeInDateRangeComboBox(){ 
        String selectedDateRange = dateRangeComboBox.getValue();
        if("Sort by: Month".equals(selectedDateRange)){
            sortBy = 0;
            updateAppointmentTableByMonth();
        }
        if("Sort by: Week".equals(selectedDateRange)){
            sortBy = 1;
            while (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                currentDate = currentDate.minusDays(1);
            }
            updateAppointmentTableByWeek();
        }   
    }
    
    //Support function for change in date range. 
    private void updateAppointmentTableByMonth() {
        allAppointments = appointmentDAO.getAppointmentsByMonth(currentDate);
        currentTimeIntervalLabel.setText(currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentDate.getYear());
        appointmentTableView.setItems(allAppointments);
    }

    //Support function for change in date range. 
    private void updateAppointmentTableByWeek() {
        allAppointments = appointmentDAO.getAppointmentsByWeek(currentDate);
        currentTimeIntervalLabel.setText(currentDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + " - " + currentDate.plusDays(6).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
        appointmentTableView.setItems(allAppointments);
    }

    //Function that handles the action of when the language combobox is changed. 
    @FXML
    public void changeInLanguageComboBox(){
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
        //Initialize current locaiton. 
        currentLocale = new Locale(passedLocale);
        languageResourceBundle = ResourceBundle.getBundle("schedulingapplication.Resources/languageProperties", currentLocale);
        
        //Change currentAppointment window top bar.
        appointmentHeaderLabel.setText(languageResourceBundle.getString("appointmentHeaderLabel"));
        previousTimeIntervalButton.setText(languageResourceBundle.getString("previousTimeIntervalButton"));
        succeedingTimeIntervalButton.setText(languageResourceBundle.getString("succeedingTimeIntervalButton"));
        
        //Change currentAppointment table view contents.
        appointmentTitleColumn.setText(languageResourceBundle.getString("appointmentTitle"));
        appointmentStartTimeColumn.setText(languageResourceBundle.getString("appointmentStartTime"));
        appointmentEndTimeColumn.setText(languageResourceBundle.getString("appointmentEndTime"));
        appointmentCustomerColumn.setText(languageResourceBundle.getString("appointmentCustomer"));
        appointmentConsultantColumn.setText(languageResourceBundle.getString("appointmentConsultant"));
        appointmentLocationColumn.setText(languageResourceBundle.getString("appointmentLocation"));
        appointmentTypeColumn.setText(languageResourceBundle.getString("appointmentType"));
        
        //Change currentAppointment window navigatin bottm bar.
        selectLocale.setText(languageResourceBundle.getString("selectLocale"));
        applicaitonWindowButton.setText(languageResourceBundle.getString("appointmentsButton"));
        customerWindowButton.setText(languageResourceBundle.getString("customersButton"));
        reportsWindowButton.setText(languageResourceBundle.getString("reportsButton"));
        exitApplicationButton.setText(languageResourceBundle.getString("exitApplication"));
    }
    
    //Function that hanldes actions to the allAppointments button.
    public void actionAppointmentsButton() {
        setMainWindow.showAppointmentWindow();
    }
    
    //Function that handles actions to the customers button. 
    public void actionCustomersButton() {
        setMainWindow.showCustomerWindow();
    }

    //Function that hanldes actions to the reports button.
    public void actionReportsButton() {
        setMainWindow.showReportWindow();
    }

    //Function that hanles actions to the exit button.
    public void actionExitButton() {
        if(throwConfirmationAlert()){
            ApplicationLogger.logUserAction(setMainWindow.getCurrentSessionUser().getUserName(),"The current session has been terminated.");
            setMainWindow.showLoginWindow();
        }
    }
    
    //Funciton that creates alerts when a currentAppointment is within 15 minutes.
    public void checkForUpcomingApts() {
            String appointmentMessage = "";
            List<Appointment> upcomingAppointments = new ArrayList<>();
            //Lamda expressions used to filter thorugh all of the appointments and check each of their start and end times. 
            allAppointments.stream().filter(apt -> apt.getStart().isAfter(LocalDateTime.now()) && apt.getStart().isBefore(LocalDateTime.now().plusMinutes(15))).forEach(upcomingAppointments::add);
            if (upcomingAppointments.size() >= 1) {
                for (Appointment currentAppointment : upcomingAppointments) {
                    appointmentMessage +=  "Appointment" + currentAppointment.getTitle() + " with " + currentAppointment.getCustomer().getCustomerName() + " @ " + currentAppointment.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) + "\n";
                }
                throwAppointmentNotification(appointmentMessage);
            }
    }
    
    //Function that hanldes currentAppointment notifications.
    public void throwAppointmentNotification(String passedMessage) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(languageResourceBundle.getString("appointmentReminder"));
            alert.setHeaderText(languageResourceBundle.getString("appointmentNotification"));
            alert.getDialogPane().setContent(new Label(passedMessage));
            alert.showAndWait();
    }
    
    //Function that throws passed errors. 
    private void throwError(String appointmentErrorBody) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(languageResourceBundle.getString("appointmentErrorTitle"));
        alert.setHeaderText(languageResourceBundle.getString("appointmentErrorHeader"));
        alert.getDialogPane().setContent(new Label(languageResourceBundle.getString(appointmentErrorBody)));
        alert.showAndWait();
    }
        
    //Function that hanldes confirmation alerts.
    public boolean throwConfirmationAlert() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(languageResourceBundle.getString("confirmationTitle"));
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(languageResourceBundle.getString("deleteAppointment"));
        ButtonType yes = new ButtonType(languageResourceBundle.getString("yesButton"), ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType(languageResourceBundle.getString("noButton"), ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.get() == yes;
    }
}

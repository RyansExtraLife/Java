package schedulingapplication.Views;

//Import application libraries. 
import schedulingapplication.Models.Customer;
import schedulingapplication.SchedulingApplication;
import schedulingapplication.Utilites.ApplicationLogger;
import schedulingapplication.Utilites.CustomerDAO;
import schedulingapplication.Utilites.CustomerDAOImplementation;

//Import Java misc utilites libraries.
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

//Import JavaFX libraries.
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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


public class CustomerWindowController {
    @FXML
    private Label customerHeaderLabel;
    @FXML
    private Button modifyCustomerWindowButton;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneNumberColumn;
    @FXML
    private TableColumn<Customer, String> customerAddressColumn;
    @FXML
    private TableColumn<Customer, String> customerCityColumn;
    @FXML
    private TableColumn<Customer, String> customerCountryColumn;
    @FXML
    private TableColumn<Customer, String> customerPostalCodeColumn;
    @FXML
    private TableColumn<Customer, Integer> customerActiveColumn;
    @FXML
    private Button removeCustomerWindowButton;
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
    
    
    private SchedulingApplication setMainWindow;
    private Customer selectedCustomer;
    private CustomerDAO customerDAO;
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private String locationVaribale;
    private Locale currentLocale;
    private ResourceBundle languageResourceBundle;

            
    //FXML Initizlier.
    @FXML
    public void initialize() {
        //Initialize Default Locale; 
        changeLocale("en");
        ApplicationLogger.logUserAction("System Log","Customer Window has been initialized.");
        customerDAO = new CustomerDAOImplementation();
        
        // Initiate data ojbects. 
        allCustomers = customerDAO.getAllCustomers();
        customerTableView.setItems(allCustomers);
        
        //Initialize Table View cells using Lamda expressions. 
        customerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        customerPhoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPhone()));
        customerAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress()+ ":" + cellData.getValue().getAddress().getAddress2()));
        customerCityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity().getCity()));
        customerPostalCodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPostalCode()));
        customerCountryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity().getCountry().getCountry()));
        customerActiveColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getActive()).asObject());
    }
    
    //
    public void setMainWindow(SchedulingApplication setMainWindow) {
        this.setMainWindow= setMainWindow;
    }
    
    //Function that handles actions to the customers button. 
    @FXML
    public void actionModifyCustomerButton() {
        //Gets selected row and assigns it to a variable. 
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null){
            setMainWindow.showModifyCustomerWindow("edit", selectedCustomer);
        }else{
            setMainWindow.showModifyCustomerWindow("add",null);
        }
    }
    
    @FXML
    public void actionRemoveCustomerButton() {
        boolean modifcationSucessfull;
        //Gerts selected row and assigns it to a variable
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        
        if(selectedCustomer != null){
            if(throwConfirmationAlert("deleteCustomer")){
                modifcationSucessfull = customerDAO.deleteCustomer(selectedCustomer);
                if(modifcationSucessfull){
                    allCustomers = customerDAO.getAllCustomers();
                    customerTableView.setItems(allCustomers);
                    ApplicationLogger.logUserAction(setMainWindow.getCurrentSessionUser().getUserName(), "Sucessfully deleted a Customer.");
                } else {
                    throwError("Delete was unsuccessfuall.");
                }
            }
        }else{
            throwError("Please select an appointment to delete.");
        }
    }
    
    //Function that handles the action of when the language combobox is changed. 
    @FXML
    public void changeInLanguageComboBox(){
        String selectedLanguage = languageComboBox.getValue();
        if("United States".equals(selectedLanguage)){
            changeLocale("en");
            ApplicationLogger.logUserAction("System Log","Application language changed to English.");
            setMainWindow.setCurrentLanguage("en");
        }
        if("Spain".equals(selectedLanguage)){
            changeLocale("ca");
            ApplicationLogger.logUserAction("System Log","Application language changed to Spanish.");
            setMainWindow.setCurrentLanguage("ca");
        }
        if("France".equals(selectedLanguage)){
            changeLocale("fr");
            ApplicationLogger.logUserAction("System Log","Application language changed to French.");
            setMainWindow.setCurrentLanguage("fr");
        }
    }
    
    //Function that handles the change of locale. 
    private void changeLocale(String passedLocale){
        currentLocale = new Locale(passedLocale);
        languageResourceBundle = ResourceBundle.getBundle("schedulingapplication.Resources/languageProperties", currentLocale);
        
        //Change customer window top bar.
        customerHeaderLabel.setText(languageResourceBundle.getString("customerHeaderLabel"));
        
        //Change customer table view contents.
        customerNameColumn.setText(languageResourceBundle.getString("customerNameColumn"));
        customerPhoneNumberColumn.setText(languageResourceBundle.getString("customerPhoneNumberColumn"));
        customerAddressColumn.setText(languageResourceBundle.getString("customerAddressColumn"));
        customerCityColumn.setText(languageResourceBundle.getString("customerCityColumn"));
        customerPostalCodeColumn.setText(languageResourceBundle.getString("customerPostalCodeColumn"));
        customerCountryColumn.setText(languageResourceBundle.getString("customerCountryColumn"));
        customerActiveColumn.setText(languageResourceBundle.getString("customerActiveColumn"));
                                        
        //Change navigatin bottm bar.
        selectLocale.setText(languageResourceBundle.getString("selectLocale"));
        applicaitonWindowButton.setText(languageResourceBundle.getString("appointmentsButton"));
        customerWindowButton.setText(languageResourceBundle.getString("customersButton"));
        reportsWindowButton.setText(languageResourceBundle.getString("reportsButton"));
        exitApplicationButton.setText(languageResourceBundle.getString("exitApplication"));
    }
    
    //Function that hanldes actions to the appointments button.
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
        if(throwConfirmationAlert("exitContentText")){
            ApplicationLogger.logUserAction(setMainWindow.getCurrentSessionUser().getUserName(),"The user has ended the session.");
            setMainWindow.showLoginWindow();
        }
    }
    
    //Function that hanldes Confirmation Alerts.
    public boolean throwConfirmationAlert(String passedString) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(languageResourceBundle.getString("confirmationTitle"));
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(languageResourceBundle.getString(passedString));
        ButtonType yes = new ButtonType(languageResourceBundle.getString("yesButton"), ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType(languageResourceBundle.getString("noButton"), ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.get() == yes;
    }
    
    private void throwError(String appointmentErrorBody) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(languageResourceBundle.getString("appointmentErrorTitle"));
        alert.setHeaderText(languageResourceBundle.getString("appointmentErrorHeader"));
        alert.getDialogPane().setContent(new Label(languageResourceBundle.getString(appointmentErrorBody)));
        alert.showAndWait();
    }
}

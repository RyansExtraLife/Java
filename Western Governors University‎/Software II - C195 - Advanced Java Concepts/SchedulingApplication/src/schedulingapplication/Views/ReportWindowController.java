package schedulingapplication.Views;

//
import schedulingapplication.Models.Appointment;
import schedulingapplication.Models.AppointmentByType;
import schedulingapplication.Models.Customer;
import schedulingapplication.Models.User;
import schedulingapplication.SchedulingApplication;
import schedulingapplication.Utilites.ApplicationLogger;
import schedulingapplication.Utilites.AppointmentDAO;
import schedulingapplication.Utilites.AppointmentDAOImplementation;
import schedulingapplication.Utilites.CustomerDAO;
import schedulingapplication.Utilites.CustomerDAOImplementation;
import schedulingapplication.Utilites.UserDAO;
import schedulingapplication.Utilites.UserDAOImplementation;

//
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

//
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;


public class ReportWindowController {
    @FXML
    private Label reportsHeaderLabel;


    @FXML
    private ChoiceBox<String> yearChoiceBox;
     
    //Functions that hanlde each table view and row by month. 
    @FXML
    private TableView<AppointmentByType> januaryAppointmentTypesTableView;
    @FXML
    private TableColumn<AppointmentByType, String> januaryTypes;
    @FXML
    private TableColumn<AppointmentByType, Integer> januaryCount;
    @FXML
    private TableView<AppointmentByType> februaryAppointmentTypesTableVeiw;
    @FXML
    private TableColumn<AppointmentByType, String> februaryTypes;
    @FXML
    private TableColumn<AppointmentByType, Integer> februaryCount;
    @FXML
    private TableView<AppointmentByType> marchAppointmentTypesTableView;
    @FXML
    private TableColumn<AppointmentByType, String> marchTypes;
    @FXML
    private TableColumn<AppointmentByType, Integer> marchCount;
    @FXML
    private TableView<AppointmentByType> aprilAppointmentTypesTableView;
    @FXML
    private TableColumn<AppointmentByType, String> aprilTypes;
    @FXML
    private TableColumn<AppointmentByType, Integer> aprilCount;
    @FXML
    private TableView<AppointmentByType> mayAppointmentTypesTableView;
    @FXML
    private TableColumn<AppointmentByType, String> mayTypes;
    @FXML
    private TableColumn<AppointmentByType, Integer> mayCount;
    @FXML
    private TableView<AppointmentByType> juneAppointmentTypesTableView;
    @FXML
    private TableColumn<AppointmentByType, String> juneTypes;
    @FXML
    private TableColumn<AppointmentByType, Integer> juneCount;
    @FXML
    private TableView<AppointmentByType> julyAppointmentTypesTableView;
    @FXML
    private TableColumn<AppointmentByType, String> julyTypes;
    @FXML
    private TableColumn<AppointmentByType, Integer> julyCount;
    @FXML
    private TableView<AppointmentByType> augustAppointmentTypesTableView;
    @FXML
    private TableColumn<AppointmentByType, String> augustTypes;
    @FXML
    private TableColumn<AppointmentByType, Integer> augustCount;
    @FXML
    private TableView<AppointmentByType> septemberAppointmentTypesTableView;
    @FXML
    private TableColumn<AppointmentByType, String> septemberTypes;
    @FXML
    private TableColumn<AppointmentByType, Integer> septemberCount;
    @FXML
    private TableView<AppointmentByType> octoberAppointmentTypesTableView;
    @FXML
    private TableColumn<AppointmentByType, String> octoberTypes;
    @FXML
    private TableColumn<AppointmentByType, Integer> octoberCount;
    @FXML
    private TableView<AppointmentByType> novemberAppointmentTypesTableView;
    @FXML
    private TableColumn<AppointmentByType, String> novemberTypes;
    @FXML
    private TableColumn<AppointmentByType, Integer> novemberCount;
    @FXML
    private TableView<AppointmentByType> decemberAppointmentTypesTableView;
    @FXML
    private TableColumn<AppointmentByType, String> decemberTypes;
    @FXML
    private TableColumn<AppointmentByType, Integer> decemberCount;
    //FXML by the sort by customer tab. 
    @FXML
    private ChoiceBox customerChoiceBox;
    @FXML
    private TableColumn<Appointment, String> customerColumn;
    @FXML
    private TableView<Appointment> customerTableView;
    @FXML
    private TableColumn<Appointment, String> customerStartColumn;
    @FXML
    private TableColumn<Appointment, String> customerEndColumn;
    @FXML
    private TableColumn<Appointment, String> customerUserColumn;
    @FXML
    private TableColumn<Appointment, String> customerLocationColumn;
    @FXML
    private TableColumn<Appointment, String> customerTypeColumn;
    //FXMl by the sorty by user tab. 
    @FXML
    private ChoiceBox userChoiceBox;
    @FXML
    private TableView<Appointment> userTableView;
    @FXML
    private TableColumn<Appointment, String> userColumn;
    @FXML
    private TableColumn<Appointment, String> userStartColumn;
    @FXML
    private TableColumn<Appointment, String> userEndColumn;
    @FXML
    private TableColumn<Appointment, String> userCustomerColumn;
    @FXML
    private TableColumn<Appointment, String> userLocationColumn;
    @FXML
    private TableColumn<Appointment, String> userTypeColumn;
    //FXML for language bottom bar. 
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
    
    //Variables for the sorty by year. 
    private ArrayList<ObservableList> allMonthsArrayList = new ArrayList<>();
    private ObservableList<String> availableYears = FXCollections.observableArrayList();
    private ObservableList<AppointmentByType> januaryAppointmentData = FXCollections.observableArrayList();
    private ObservableList<AppointmentByType> februaryAppointmentData = FXCollections.observableArrayList();
    private ObservableList<AppointmentByType> marchAppointmentData = FXCollections.observableArrayList();
    private ObservableList<AppointmentByType> aprilAppointmentData = FXCollections.observableArrayList();
    private ObservableList<AppointmentByType> mayAppointmentData = FXCollections.observableArrayList();
    private ObservableList<AppointmentByType> juneAppointmentData = FXCollections.observableArrayList();
    private ObservableList<AppointmentByType> julyAppointmentData = FXCollections.observableArrayList();
    private ObservableList<AppointmentByType> augustAppointmentData = FXCollections.observableArrayList();
    private ObservableList<AppointmentByType> septemberAppointmentData = FXCollections.observableArrayList();
    private ObservableList<AppointmentByType> octoberAppointmentData = FXCollections.observableArrayList();
    private ObservableList<AppointmentByType> novemberAppointmentData = FXCollections.observableArrayList();
    private ObservableList<AppointmentByType> decemberAppointmentData = FXCollections.observableArrayList();
    
    //Variables for user and customer sort windows. 
    private ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
    private ObservableList<User> users = FXCollections.observableArrayList();
    
    //variables to initizlie data objects. 
    private AppointmentDAO appointmentDAO;
    private CustomerDAO customerDAO;
    private UserDAO userDAO;
  
    //Local Variables. 
    private SchedulingApplication setMainWindow;
    private String locationVaribale;
    private Locale currentLocale;
    private ResourceBundle languageResourceBundle;
    
  
    //Default constructur.
    public ReportWindowController (){}
    
    //FXML Initizlier.
    @FXML
    public void initialize() {
        //Initialize Default Locale; 
        changeLocale("en");
        ApplicationLogger.logUserAction("System Log","Report Window has been initialized.");
        
        appointmentDAO = new AppointmentDAOImplementation();
        customerDAO = new CustomerDAOImplementation();
        userDAO= new UserDAOImplementation();
        
        availableYears = appointmentDAO.getYearList();
        yearChoiceBox.setItems(availableYears);
        yearChoiceBox.getSelectionModel().selectFirst();
        allMonthsArrayList.add(januaryAppointmentData);
        allMonthsArrayList.add(februaryAppointmentData);
        allMonthsArrayList.add(marchAppointmentData);
        allMonthsArrayList.add(aprilAppointmentData);
        allMonthsArrayList.add(mayAppointmentData);
        allMonthsArrayList.add(juneAppointmentData);
        allMonthsArrayList.add(julyAppointmentData);
        allMonthsArrayList.add(augustAppointmentData);
        allMonthsArrayList.add(septemberAppointmentData);
        allMonthsArrayList.add(octoberAppointmentData);
        allMonthsArrayList.add(novemberAppointmentData);
        allMonthsArrayList.add(decemberAppointmentData);
        refreshAptData();

        //Initialize Table View cells using Lamda expressions. 
        
        januaryTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        januaryCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentTotal()).asObject());

        februaryTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        februaryCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentTotal()).asObject());
        
        marchTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        marchCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentTotal()).asObject());
        
        aprilTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        aprilCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentTotal()).asObject());
        
        mayTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        mayCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentTotal()).asObject());
        
        juneTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        juneCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentTotal()).asObject());
        
        julyTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        julyCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentTotal()).asObject());
        
        augustTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        augustCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentTotal()).asObject());
        
        septemberTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        septemberCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentTotal()).asObject());
        
        octoberTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        octoberCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentTotal()).asObject());
        
        novemberTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        novemberCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentTotal()).asObject());
        
        decemberTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        decemberCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentTotal()).asObject());
        
  
        //Function that populates the users dropdown.
        users = userDAO.getActiveUserSerssions();
        userChoiceBox.setConverter(new StringConverter<User>() {            
            @Override
            public User fromString(String string) {
                return null; 
            }
            @Override
            public String toString(User object) {
                return object.getUserName();
            }
        });        
        userChoiceBox.setItems(users);
        
        //Function that populates the customers dropdown. 
        customers = customerDAO.getActiveCustomers();
        customerChoiceBox.setConverter(new StringConverter<Customer>() {
            @Override
            public Customer fromString(String string) {
                return null; 
            }
            
            @Override
            public String toString(Customer object) {
                return object.getCustomerName();
            }

        });
        customerChoiceBox.setItems(customers);
        
    }
    
    public void setMainWidnow(SchedulingApplication setMainWindow) {
        this.setMainWindow = setMainWindow;
    }
    
    //Function that handles appointment by type. 
    @FXML
    private void appointmentByTypeButtonAction(){
        januaryAppointmentData.clear();
        februaryAppointmentData.clear();
        marchAppointmentData.clear();
        aprilAppointmentData.clear();
        mayAppointmentData.clear();
        juneAppointmentData.clear();
        julyAppointmentData.clear();
        augustAppointmentData.clear();
        septemberAppointmentData.clear();
        octoberAppointmentData.clear();
        novemberAppointmentData.clear();
        decemberAppointmentData.clear();
        refreshAptData();
    }
    
    //Refreshses any added data. 
    private void refreshAptData() {
        allMonthsArrayList = appointmentDAO.getAppointmentTypeByMonth(allMonthsArrayList, yearChoiceBox.getValue());
        januaryAppointmentTypesTableView.setItems(januaryAppointmentData);
        februaryAppointmentTypesTableVeiw.setItems(februaryAppointmentData);
        marchAppointmentTypesTableView.setItems(marchAppointmentData);
        aprilAppointmentTypesTableView.setItems(aprilAppointmentData);
        mayAppointmentTypesTableView.setItems(mayAppointmentData);
        juneAppointmentTypesTableView.setItems(juneAppointmentData);
        julyAppointmentTypesTableView.setItems(julyAppointmentData);
        augustAppointmentTypesTableView.setItems(augustAppointmentData);
        septemberAppointmentTypesTableView.setItems(septemberAppointmentData);
        octoberAppointmentTypesTableView.setItems(octoberAppointmentData);
        novemberAppointmentTypesTableView.setItems(novemberAppointmentData);
        decemberAppointmentTypesTableView.setItems(decemberAppointmentData);
    }
    
    //function that actions customer choice box. 
    @FXML
    private void appointmentByCustomerhoiceBoxAction(){
        customerAppointments = appointmentDAO.getAppointmentsByCustomer((Customer) customerChoiceBox.getValue());
        customerTableView.setItems(customerAppointments);
        //Initialize Table View cells using Lamda expressions. 
        customerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        customerStartColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        customerEndColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        customerUserColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getUserName()));
        customerLocationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        customerTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
    }
    
    //function that actions user choice box. 
    @FXML
    private void appointmentByUserChoiceBoxAction(){
        userAppointments = appointmentDAO.getAppointmentsByUser((User) userChoiceBox.getValue());
        userTableView.setItems(userAppointments);
        //Initialize Table View cells using Lamda expressions. 
        userColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getUserName()));
        userStartColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        userEndColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        userCustomerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        userLocationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        userTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
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
        
        //Change Navigatin Bottm Bar
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
        if(throwConfirmationAlert()){
            ApplicationLogger.logUserAction(setMainWindow.getCurrentSessionUser().getUserName(),"The current session has been terminated.");
            setMainWindow.showLoginWindow();
        }
    }
    
    //Function that hanldes Confirmation Alerts.
    public boolean throwConfirmationAlert() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(languageResourceBundle.getString("exitTitle"));
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(languageResourceBundle.getString("exitContentText"));
        ButtonType yes = new ButtonType(languageResourceBundle.getString("yesButton"), ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType(languageResourceBundle.getString("noButton"), ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.get() == yes;
    }
}

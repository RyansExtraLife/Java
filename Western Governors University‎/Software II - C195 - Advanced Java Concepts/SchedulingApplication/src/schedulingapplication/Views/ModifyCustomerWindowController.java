package schedulingapplication.Views;

//Import application libraries. 
import schedulingapplication.Models.Address;
import schedulingapplication.Models.City;
import schedulingapplication.Models.Country;
import schedulingapplication.Models.Customer;
import schedulingapplication.Models.User;
import schedulingapplication.SchedulingApplication;
import schedulingapplication.Utilites.ApplicationLogger;
import schedulingapplication.Utilites.CustomerDAO;
import schedulingapplication.Utilites.CustomerDAOImplementation;

//Import Java misc utilites libraries.
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

//Import JavaFX libraries.
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class ModifyCustomerWindowController{
    //FXML Local Variables
    @FXML 
    private Label headerlabe;
    @FXML
    private Label customerNameLabel;
    @FXML
    private TextField customerNameTextField;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private Label addressLineOneLabel;
    @FXML
    private TextField addressLineOneTextField;
    @FXML
    private Label addressLineTwoLabel;
    @FXML
    private TextField addressLineTwoTextField;
    @FXML
    private Label cityLabel;
    @FXML
    private TextField cityTextField;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private Label countryLabel;
    @FXML
    private TextField countryTextField;
    @FXML
    private CheckBox activeUserCheckBox;
    @FXML
    private Label selectLocale;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
     
    //
    private boolean editMode;
    private Customer currentCustomer;
    private CustomerDAO customerDAO;
    private String currentModifyMode;
    private String locationVaribale;
    private Locale currentLocale;
    private ResourceBundle languageResourceBundle;
    private SchedulingApplication setMainWindow;
    
    //Defualt Constructor
    public ModifyCustomerWindowController(){}
    
    //Initalize.
    @FXML
    public void initialize() {
        changeLocale("en");
        customerDAO = new CustomerDAOImplementation();
        ApplicationLogger.logApplicationAction("Login Window has been initialized.");
    }
    
    //Sets the main scene as this one. 
    public void setMainWindow(SchedulingApplication setMainWindow) {
        this.setMainWindow = setMainWindow;
    }

    //If a customer object is passed edit mode, if not add mode. 
    public void modifyCustomerMode(String passedMode, Customer passedCustomer) {
        if("edit".equals(passedMode)){
            this.editMode = true;
            currentCustomer = passedCustomer;
            customerNameTextField.setText(passedCustomer.getCustomerName());
            phoneNumberTextField.setText(passedCustomer.getAddress().getPhone());
            addressLineOneTextField.setText(passedCustomer.getAddress().getAddress());
            addressLineTwoTextField.setText(passedCustomer.getAddress().getAddress2());          
            cityTextField.setText(passedCustomer.getAddress().getCity().getCity());
            postalCodeTextField.setText(passedCustomer.getAddress().getPostalCode());
            countryTextField.setText(passedCustomer.getAddress().getCity().getCountry().getCountry());
            if(passedCustomer.getActive() == 0) {
                    activeUserCheckBox.setSelected(false);
            } else {
                    activeUserCheckBox.setSelected(true);
            }
        }
        if("add".equals(passedMode)){
            this.editMode = false;
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
        if(this.editMode){
            headerlabe.setText(languageResourceBundle.getString("modifyCustomerTitle"));
        }else{
            headerlabe.setText(languageResourceBundle.getString("createCustomerTitle"));
        }
        customerNameLabel.setText(languageResourceBundle.getString("customerNameLabel"));
        phoneNumberLabel.setText(languageResourceBundle.getString("phoneNumberLabel"));
        addressLineOneLabel.setText(languageResourceBundle.getString("addressLineOneLabel"));
        addressLineTwoLabel.setText(languageResourceBundle.getString("addressLineTwoLabel"));          
        cityLabel.setText(languageResourceBundle.getString("postalCodeLabel"));
        postalCodeLabel.setText(languageResourceBundle.getString("countryLabel"));
        countryLabel.setText(languageResourceBundle.getString("cityLabel"));
        activeUserCheckBox.setText(languageResourceBundle.getString("activeUserCheckBox"));
        selectLocale.setText(languageResourceBundle.getString("selectLocale"));
        
    }
    
    //Hnaldes actions to the save button
    @FXML
    public void saveButtonActioned(){
        //Tests fields for proper input
        if(testModifyCustomerWindowTextFields()){
            User currentSessionuser = setMainWindow.getCurrentSessionUser();
            
            if(editMode == false) {
                //Create a blank country object.
                Country tempCountry = new Country();
                tempCountry.setCreateDate(LocalDate.now());
                tempCountry.setCreatedBy(currentSessionuser.getUserName());
                
                //Create a blank city object.
                City tempCity = new City();
                tempCity.setCountry(tempCountry);
                tempCity.setCreateDate(LocalDate.now());
                tempCity.setCreatedBy(currentSessionuser.getUserName());
                
                //Create a blank address object.
                Address tempAddress = new Address();
                tempAddress.setCity(tempCity);
                tempAddress.setCreateDate(LocalDate.now());
                tempAddress.setCreatedBy(currentSessionuser.getUserName());
                
                //Allsign all classes and variables to customer object.
                currentCustomer = new Customer();
                currentCustomer.setAddress(tempAddress);
                currentCustomer.setCreateDate(LocalDate.now());
                currentCustomer.setCreatedBy(currentSessionuser.getUserName());
                currentCustomer.setLastUpdate(LocalDateTime.now());
                currentCustomer.setLastUpdateBy(currentSessionuser.getUserName());
            }
            
            currentCustomer.setCustomerName(customerNameTextField.getText());
            if(activeUserCheckBox.isSelected()) {
                currentCustomer.setActive(1);
            } else {
                currentCustomer.setActive(0);
            }
          
            //assigns address field to current customer
            currentCustomer.getAddress().setPhone(phoneNumberTextField.getText());
            currentCustomer.getAddress().setAddress(addressLineOneTextField.getText());
            currentCustomer.getAddress().setAddress2(addressLineTwoTextField.getText());
            currentCustomer.getAddress().setPostalCode(postalCodeTextField.getText());
            currentCustomer.getAddress().setLastUpdate(LocalDateTime.now());
            currentCustomer.getAddress().setLastUpdateBy(currentSessionuser.getUserName());
            
            //assigns city field to current address
            currentCustomer.getAddress().getCity().setCity(cityTextField.getText());
            currentCustomer.getAddress().getCity().setLastUpdate(LocalDateTime.now());
            currentCustomer.getAddress().getCity().setLastUpdateBy(currentSessionuser.getUserName());
            
            //assisns country field to current city
            currentCustomer.getAddress().getCity().getCountry().setCountry(countryTextField.getText());
            currentCustomer.getAddress().getCity().getCountry().setLastUpdate(LocalDateTime.now());
            currentCustomer.getAddress().getCity().getCountry().setLastUpdateBy(currentSessionuser.getUserName());
           
            //
            boolean modifcationSucessfull = false;
            if(editMode == true){
                    modifcationSucessfull = customerDAO.updateCustomer(currentCustomer);
                    ApplicationLogger.logUserAction(setMainWindow.getCurrentSessionUser().getUserName(), "Sucessfully updated a Customer.");
            }
            if(editMode == false){
                    modifcationSucessfull = customerDAO.addCustomer(currentCustomer);
                    ApplicationLogger.logUserAction(setMainWindow.getCurrentSessionUser().getUserName(), "Sucessfully created a new Customer.");
            }
            
            //
            if(modifcationSucessfull) {
                setMainWindow.showCustomerWindow();
            } else {
                throwTextFieldError("failed customer");
            }
        }
    }
    
    //handles actions to the cancel button
    @FXML
    public void cancelButtonActioned(){
        if (throwConfirmation("Are you sure you want to cancle?")) {
            setMainWindow.showCustomerWindow();
        }
    }
    
    //Test Modify customer window text fields. 
    public boolean testModifyCustomerWindowTextFields(){
        String thrownErrorMessage = "";
        if (customerNameTextField.getText() == null || customerNameTextField.getText().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidName");
        }
        if (phoneNumberTextField.getText() == null || phoneNumberTextField.getText().equals("")) {
            thrownErrorMessage+= languageResourceBundle.getString("invalidPhone");
        }
        if (addressLineOneTextField.getText() == null || addressLineOneTextField.getText().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidAddressOne");
        }
        if (addressLineTwoTextField.getText() == null || addressLineTwoTextField.getText().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidAddressTwo");
        }
        if (cityTextField.getText() == null || cityTextField.getText().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidCity");
        }
        if (postalCodeTextField.getText() == null || postalCodeTextField.getText().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidPostal");
        }
        if (countryTextField.getText() == null || countryTextField.getText().equals("")) {
            thrownErrorMessage += languageResourceBundle.getString("invalidCountry");
        }
        if (thrownErrorMessage.length() != 0){
            throwTextFieldError(thrownErrorMessage);
            return false;
        }else{
            return true;
        }    
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
    private void throwTextFieldError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("errorTitle");
        alert.setHeaderText("errorHeader");
        alert.getDialogPane().setContent(new Label(errorMessage));
        alert.showAndWait();
    }
}

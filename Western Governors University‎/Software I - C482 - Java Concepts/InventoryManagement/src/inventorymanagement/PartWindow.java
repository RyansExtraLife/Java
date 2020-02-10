package inventorymanagement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Optional;
import java.util.Random;

public class PartWindow {
    
    public static Part display(Part passedInPart) {

        Part tempInHousePart = new InHousePart();
        Part tempOutSourcedPart = new OutSourcedPart();
        
        // Part ID Generator
        Random r = new Random();
        int low = 10;
        int high = 1000;
        int randomResult = r.nextInt(high-low) + low;
        
        GridPane partWindowGridPane = new GridPane();
        partWindowGridPane.setPadding(new Insets(10, 10, 10, 10));
        partWindowGridPane.setVgap(10);
        partWindowGridPane.setHgap(10);

        Stage addPartWindow = new Stage();
        addPartWindow.initModality(Modality.APPLICATION_MODAL);
        addPartWindow.setTitle("Add Part");

        //Application Title Label
        Label addPartTitleLabel = new Label("Add Part");
        partWindowGridPane.setConstraints(addPartTitleLabel, 0, 0);
        partWindowGridPane.setColumnSpan(addPartTitleLabel, 3);

        //In-House Radio Button
        RadioButton inHouseRadioButton = new RadioButton("In-House");
        inHouseRadioButton.setSelected(true);
        partWindowGridPane.setConstraints(inHouseRadioButton, 3, 1);

        //Outsourced Radio Button
        RadioButton outsourcedRadioButton = new RadioButton("OutSourcedPart");
        partWindowGridPane.setConstraints(outsourcedRadioButton, 4, 1);
        partWindowGridPane.setColumnSpan(outsourcedRadioButton, 2);

        //Part ID Label
        Label partIdLabel = new Label("ID:");
        partWindowGridPane.setConstraints(partIdLabel, 1, 4);

        //Part ID Textfield
        TextField partIdTextfield = new TextField("Auto-Gen: Disabled");
        partIdTextfield.setEditable(false);
        partWindowGridPane.setConstraints(partIdTextfield, 3, 4);
        partWindowGridPane.setColumnSpan(partIdTextfield, 2);

        //Name Label
        Label nameLabel = new Label("Name:");
        partWindowGridPane.setConstraints(nameLabel, 1, 5);

        //Name Textfield
        TextField nameTextfield = new TextField();
        partWindowGridPane.setConstraints(nameTextfield, 3, 5);
        partWindowGridPane.setColumnSpan(nameTextfield, 2);

        //Inventory Label
        Label inventoryLabel = new Label("Inventory:");
        partWindowGridPane.setConstraints(inventoryLabel, 1, 6);

        //Inventory Textfield
        TextField inventoryTextField = new TextField();
        partWindowGridPane.setConstraints(inventoryTextField, 3, 6);
        partWindowGridPane.setColumnSpan(inventoryTextField, 2);

        //Price Label
        Label priceLabel = new Label("Price:");
        partWindowGridPane.setConstraints(priceLabel, 1, 7);

        //Price Textfield
        TextField priceTextField = new TextField();
        partWindowGridPane.setConstraints(priceTextField, 3, 7);
        partWindowGridPane.setColumnSpan(priceTextField, 2);

        //Max Label
        Label maxLabel = new Label("Max:");
        partWindowGridPane.setConstraints(maxLabel, 1, 8);

        //Max Textfield
        TextField maxTextField = new TextField();
        partWindowGridPane.setConstraints(maxTextField, 3, 8);


        //Min Label
        Label minLabel = new Label("Min:");
        partWindowGridPane.setConstraints(minLabel, 4, 8);

        //Price Textfield
        TextField minTextField = new TextField();
        partWindowGridPane.setConstraints(minTextField, 5, 8);
        partWindowGridPane.setColumnSpan(minTextField, 2);

        //Machine ID Label
        Label machineIdLabel = new Label("Machine ID:");
        partWindowGridPane.setConstraints(machineIdLabel, 1, 9);

        //Company Name Label
        Label comapnyNameLabel = new Label("Company Name:");
        partWindowGridPane.setConstraints(comapnyNameLabel, 1, 9);
        comapnyNameLabel.setVisible(false);

        //Machine ID Textfield
        TextField machineIdTextfield = new TextField();
        partWindowGridPane.setConstraints(machineIdTextfield, 3, 9);
        partWindowGridPane.setColumnSpan(machineIdTextfield, 2);

        //Company Name Textfield
        TextField companyNameTextfield = new TextField();
        partWindowGridPane.setConstraints(companyNameTextfield, 3, 9);
        partWindowGridPane.setColumnSpan(companyNameTextfield, 2);
        companyNameTextfield.setVisible(false);

        //Initialize passedInPart
        if (passedInPart != null){   
            if(passedInPart instanceof InHousePart){
                //Initilzie passedInPart to InHousePart. 
                tempInHousePart.setPartID(passedInPart.getPartID());
                tempInHousePart.setName(passedInPart.getName());
                tempInHousePart.setPrice(passedInPart.getPrice());
                tempInHousePart.setInStock(passedInPart.getInStock());
                tempInHousePart.setMax(passedInPart.getMax());
                tempInHousePart.setMin(passedInPart.getMin());
                tempInHousePart.setMachineID(passedInPart.getMachineID());
                
                //Initialize textFileds for modify part. 
                nameTextfield.setText(tempInHousePart.getName());
                priceTextField.setText(Double.toString(tempInHousePart.getPrice()));
                inventoryTextField.setText(Integer.toString(tempInHousePart.getInStock()));
                maxTextField.setText(Integer.toString(tempInHousePart.getMax()));
                minTextField.setText(Integer.toString(tempInHousePart.getMin()));
                machineIdTextfield.setText(Integer.toString(tempInHousePart.getMachineID()));
            }
            if(passedInPart instanceof OutSourcedPart){
                //Switch default Radial Button. 
                outsourcedRadioButton.setSelected(true);
                comapnyNameLabel.setVisible(true);
                companyNameTextfield.setVisible(true);
                machineIdLabel.setVisible(false);
                machineIdTextfield.setVisible(false);
                
                //Initilzie passedInPart to OurSourcedPart. 
                tempOutSourcedPart.setPartID(passedInPart.getPartID());
                tempOutSourcedPart.setName(passedInPart.getName());
                tempOutSourcedPart.setPrice(passedInPart.getPrice());
                tempOutSourcedPart.setInStock(passedInPart.getInStock());
                tempOutSourcedPart.setMax(passedInPart.getMax());
                tempOutSourcedPart.setMin(passedInPart.getMin());
                tempOutSourcedPart.setCompanyName(passedInPart.getCompanyName());
                
                
                nameTextfield.setText(tempOutSourcedPart.getName());
                priceTextField.setText(Double.toString(tempOutSourcedPart.getPrice()));
                inventoryTextField.setText(Integer.toString(tempOutSourcedPart.getInStock()));
                maxTextField.setText(Integer.toString(tempOutSourcedPart.getMax()));
                minTextField.setText(Integer.toString(tempOutSourcedPart.getMin()));
                companyNameTextfield.setText(tempOutSourcedPart.getCompanyName());
            }
        }

        //Radial Button Group
        final ToggleGroup radialGroup = new ToggleGroup();
        inHouseRadioButton.setToggleGroup(radialGroup);
        outsourcedRadioButton.setToggleGroup(radialGroup);
        radialGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (inHouseRadioButton.isSelected()) {
                    comapnyNameLabel.setVisible(false);
                    companyNameTextfield.setVisible(false);
                    machineIdLabel.setVisible(true);
                    machineIdTextfield.setVisible(true);
                }
                else if (outsourcedRadioButton.isSelected()){
                    comapnyNameLabel.setVisible(true);
                    companyNameTextfield.setVisible(true);
                    machineIdLabel.setVisible(false);
                    machineIdTextfield.setVisible(false);
                }
            }
        });

        Button saveButton = new Button(" Save ");
        partWindowGridPane.setConstraints(saveButton, 5, 13);
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean tryStatementPassed = true;
                try{
                    if (inHouseRadioButton.isSelected()){
                        if(passedInPart != null){
                            tempInHousePart.setPartID(passedInPart.getPartID());
                        }else{
                            tempInHousePart.setPartID(randomResult);
                        }
                        tempInHousePart.setName(nameTextfield.getText());
                        tempInHousePart.setPrice(Double.parseDouble(priceTextField.getText()));
                        tempInHousePart.setInStock(Integer.parseInt(inventoryTextField.getText()));
                        tempInHousePart.setMax(Integer.parseInt(maxTextField.getText()));
                        tempInHousePart.setMin(Integer.parseInt(minTextField.getText()));
                        if(!(machineIdTextfield.getText() == null)){
                            tempInHousePart.setMachineID(Integer.parseInt(machineIdTextfield.getText()));
                        }else{
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Part Creation Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Machine ID cannot be blank.");
                            alert.showAndWait();
                        }
                    }
                    if (outsourcedRadioButton.isSelected()){
                        if(passedInPart != null){
                            tempOutSourcedPart.setPartID(passedInPart.getPartID());
                        }else{
                            tempOutSourcedPart.setPartID(randomResult);
                        }
                        tempOutSourcedPart.setName(nameTextfield.getText());
                        tempOutSourcedPart.setPrice(Double.parseDouble(priceTextField.getText()));
                        tempOutSourcedPart.setInStock(Integer.parseInt(inventoryTextField.getText()));
                        tempOutSourcedPart.setMax(Integer.parseInt(maxTextField.getText()));
                        tempOutSourcedPart.setMin(Integer.parseInt(minTextField.getText()));
                        tempOutSourcedPart.setCompanyName(companyNameTextfield.getText());
                    }
                    
                } catch(Exception e) {
                    tryStatementPassed = false;
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Part Creation Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all required fields.");
                    alert.showAndWait();
                }
                if (tryStatementPassed) {
                    if (inHouseRadioButton.isSelected()){
                        if (tempInHousePart.getMin() > tempInHousePart.getMax()){
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Part Creation Error");
                            alert.setHeaderText(null);
                            alert.setContentText("The minimum inventory is greater then the maximum.");
                            alert.showAndWait();
                        } else if (tempInHousePart.getInStock() > tempInHousePart.getMax()) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Part Creation Error");
                            alert.setHeaderText(null);
                            alert.setContentText("The current inventory is greater then the maximum");
                            alert.showAndWait();
                        } else if (tempInHousePart.getInStock() < tempInHousePart.getMin()) {
                            System.out.print(tempInHousePart.getInStock());
                            System.out.print(tempInHousePart.getMin());
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Part Creation Error");
                            alert.setHeaderText(null);
                            alert.setContentText("The current inventory is less then the maximum.");
                            alert.showAndWait();
                        } else {
                            addPartWindow.close();
                        }
                    }
                    if (outsourcedRadioButton.isSelected()){
                        if (tempOutSourcedPart.getMin() > tempOutSourcedPart.getMax()){
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Part Creation Error");
                            alert.setHeaderText(null);
                            alert.setContentText("The minimum inventory is greater then the maximum.");
                            alert.showAndWait();
                        } else if (tempOutSourcedPart.getInStock() > tempOutSourcedPart.getMax()) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Part Creation Error");
                            alert.setHeaderText(null);
                            alert.setContentText("The current inventory is greater then the maximum");
                            alert.showAndWait();
                        } else if (tempOutSourcedPart.getInStock() < tempOutSourcedPart.getMin()) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Part Creation Error");
                            alert.setHeaderText(null);
                            alert.setContentText("The current inventory is less then the maximum.");
                            alert.showAndWait();
                        } else if (companyNameTextfield.getText().isEmpty()){
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Part Creation Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Company Name cannot be blank.");
                            alert.showAndWait();
                        } else {
                            addPartWindow.close();
                        } 
                    }         
                }
            }
        });

        Button cancelButton = new Button("Cancel");
        partWindowGridPane.setConstraints(cancelButton, 6, 13);
        cancelButton.setOnAction(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Cancel");
            alert.setHeaderText("This will clear any unsaved changes.");
            alert.setContentText("Click Ok to confirm.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if(passedInPart != null) {
                    tempInHousePart.setPartID(-1);
                    addPartWindow.close();
                }else{
                    tempInHousePart.setPartID(0);
                    addPartWindow.close();
                }
            }
        });

        FlowPane root = new FlowPane();
        partWindowGridPane.getChildren().addAll(addPartTitleLabel, inHouseRadioButton, outsourcedRadioButton, partIdLabel, partIdTextfield, nameLabel, nameTextfield, inventoryLabel, inventoryTextField, priceLabel, priceTextField, maxLabel, maxTextField, minLabel, minTextField, machineIdLabel, comapnyNameLabel, machineIdTextfield, companyNameTextfield, cancelButton, saveButton);
        root.getChildren().add(partWindowGridPane);
        addPartWindow.setScene(new Scene(root, 530, 400));
        addPartWindow.showAndWait();
        
       
        if(inHouseRadioButton.isSelected()){
            return tempInHousePart;
        }
        return tempOutSourcedPart;
    }
    
}


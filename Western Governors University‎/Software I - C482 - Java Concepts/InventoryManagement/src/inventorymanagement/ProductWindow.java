package inventorymanagement;

import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.util.Optional;
import java.util.Random;

public class ProductWindow {
    public static Product display(Product passedProduct, ObservableList<Part> passedPartArray) {

        final Product addedProduct = new Product();

        // Part ID Generator
        Random r = new Random();
        int low = 10;
        int high = 1000;
        int randomResult = r.nextInt(high-low) + low;
        
        //Store data for TableViews.
        ObservableList<Part> addPartTableViewList = FXCollections.observableArrayList();
        ObservableList<Part> currentPartsTableViewList = FXCollections.observableArrayList();

        //Create new productWindowGridPane
        GridPane productWindowGridPane = new GridPane();
        productWindowGridPane.setPadding(new Insets(10, 10, 10, 10));
        productWindowGridPane.setVgap(10);
        productWindowGridPane.setHgap(10);

        //Create new addProductWindow Stage
        Stage addProductWindow = new Stage();
        addProductWindow.initModality(Modality.APPLICATION_MODAL);
        addProductWindow.setTitle("Add Product");

        //Application Title Label
        Label addProductTitleLabel = new Label("Add Product");
        productWindowGridPane.setConstraints(addProductTitleLabel, 1, 2);
        productWindowGridPane.setRowSpan(addProductTitleLabel, 2);

        //Part Search Textfield
        TextField partSearchTextfield = new TextField();
        productWindowGridPane.setConstraints(partSearchTextfield, 12, 2);

        //addPartTableView
        TableView<Part> addPartTableView;
        addPartTableView = new TableView<Part>(addPartTableViewList);
        productWindowGridPane.setConstraints(addPartTableView , 9, 9);
        productWindowGridPane.setColumnSpan(addPartTableView , 5);
        productWindowGridPane.setRowSpan(addPartTableView, 6);
        addPartTableView.setPrefHeight(100);
        addPartTableView.setPrefWidth(400);

        //addPartTableView addPartTableViewPartIdColumn
        TableColumn<Part, String> addPartTableViewPartIdColumn = new TableColumn<>("PartId");
        addPartTableViewPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addPartTableView.getColumns().add(addPartTableViewPartIdColumn);
        addPartTableViewPartIdColumn.prefWidthProperty().bind(addPartTableView.widthProperty().divide(4));

        //addPartTableView addPartTableViewProductNameColumn
        TableColumn<Part, String> addPartTableViewProductNameColumn = new TableColumn<>("Product Name");
        addPartTableViewProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartTableView.getColumns().add(addPartTableViewProductNameColumn);
        addPartTableViewProductNameColumn.prefWidthProperty().bind(addPartTableView.widthProperty().divide(4));

        //addPartTableView addPartTableViewInStockColumn
        TableColumn<Part, String> addPartTableViewInStockColumn = new TableColumn<>("Inventory");
        addPartTableViewInStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        addPartTableView.getColumns().add(addPartTableViewInStockColumn);
        addPartTableViewInStockColumn.prefWidthProperty().bind(addPartTableView.widthProperty().divide(4));

        //addPartTableView addPartTableViewPriceColumn
        TableColumn<Part, String> addPartTableViewPriceColumn = new TableColumn<>("Price Per Unit");
        addPartTableViewPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        addPartTableView.getColumns().add(addPartTableViewPriceColumn);
        addPartTableViewPriceColumn.prefWidthProperty().bind(addPartTableView.widthProperty().divide(4));

        //Product ID Label
        Label productIdLabel = new Label("Product ID:");
        productWindowGridPane.setConstraints(productIdLabel, 1, 5);

        //Product ID Textfield
        TextField productIdTextfield = new TextField("Auto-Gen: Disabled");
        productIdTextfield.setEditable(false);
        productWindowGridPane.setConstraints(productIdTextfield, 2, 5);
        productWindowGridPane.setColumnSpan(productIdTextfield, 2);

        //Name Label
        Label nameLabel = new Label("Name");
        productWindowGridPane.setConstraints(nameLabel, 1, 6);

        //Name Textfield
        TextField nameTextfield = new TextField();
        productWindowGridPane.setConstraints(nameTextfield, 2, 6);
        productWindowGridPane.setColumnSpan(nameTextfield, 2);

        //Inventory Label
        Label inventoryLabel = new Label("Inventory");
        productWindowGridPane.setConstraints(inventoryLabel, 1, 7);

        //Inventory Textfield
        TextField inventoryTextField = new TextField();
        productWindowGridPane.setConstraints(inventoryTextField, 2, 7);
        productWindowGridPane.setColumnSpan(inventoryTextField, 2);

        //Price Label
        Label priceLabel = new Label("Price");
        productWindowGridPane.setConstraints(priceLabel, 1, 8);

        //Price Textfield
        TextField priceTextField = new TextField();
        productWindowGridPane.setConstraints(priceTextField, 2, 8);
        productWindowGridPane.setColumnSpan(priceTextField, 2);

        //Max Label
        Label maxLabel = new Label("Max");
        productWindowGridPane.setConstraints(maxLabel, 1, 9);

        //Max Textfield
        TextField maxTextField = new TextField();
        productWindowGridPane.setConstraints(maxTextField, 2, 9);

        //Min Label
        Label minLabel = new Label("Min");
        productWindowGridPane.setConstraints(minLabel, 3, 9);

        //Min Textfield
        TextField minTextField = new TextField();
        productWindowGridPane.setConstraints(minTextField, 4, 9);

        //currentPartsTableView
        TableView<Part> currentPartsTableView;
        currentPartsTableView = new TableView<Part>(currentPartsTableViewList);
        productWindowGridPane.setConstraints(currentPartsTableView , 9, 3);
        productWindowGridPane.setColumnSpan(currentPartsTableView , 5);
        productWindowGridPane.setRowSpan(currentPartsTableView , 5);
        currentPartsTableView.setPrefHeight(125);
        currentPartsTableView.setPrefWidth(400);

        //currentPartsTableView currentPartsTableViewPartIdColumn
        TableColumn<Part, String> currentPartsTableViewPartIdColumn = new TableColumn<>("PartId");
        currentPartsTableViewPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        currentPartsTableView.getColumns().add(currentPartsTableViewPartIdColumn);
        currentPartsTableViewPartIdColumn.prefWidthProperty().bind(currentPartsTableView.widthProperty().divide(4));

        //currentPartsTableView currentPartsTableViewNameColumn
        TableColumn<Part, String> currentPartsTableViewNameColumn = new TableColumn<>("Product Name");
        currentPartsTableViewNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        currentPartsTableView.getColumns().add(currentPartsTableViewNameColumn);
        currentPartsTableViewNameColumn.prefWidthProperty().bind(currentPartsTableView.widthProperty().divide(4));

        //currentPartsTableView currentPartsTableViewInStockColumn
        TableColumn<Part, String> currentPartsTableViewInStockColumn = new TableColumn<>("Inventory");
        currentPartsTableViewInStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        currentPartsTableView.getColumns().add(currentPartsTableViewInStockColumn);
        currentPartsTableViewInStockColumn.prefWidthProperty().bind(currentPartsTableView.widthProperty().divide(4));

        //currentPartsTableView currentPartsTableViewPriceColumn
        TableColumn<Part, String> currentPartsTableViewPriceColumn = new TableColumn<>("Price Per Unit");
        currentPartsTableViewPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        currentPartsTableView.getColumns().add(currentPartsTableViewPriceColumn);
        currentPartsTableViewPriceColumn.prefWidthProperty().bind(currentPartsTableView.widthProperty().divide(4));

        //If a part is passed to the window for modification.
        if(passedProduct != null){
            //Initialize temp variable to populate TextFields.
            Product tempProduct = new Product();
            tempProduct.setProductID(passedProduct.getProductID());
            tempProduct.setName(passedProduct.getName());
            tempProduct.setPrice(passedProduct.getPrice());
            tempProduct.setInStock(passedProduct.getInStock());
            tempProduct.setMin(passedProduct.getMin());
            tempProduct.setMax(passedProduct.getMax());
            tempProduct.setAssociatedParts(passedProduct.getAssociatedParts());

            //Populate TextFields with tempProduct.
            productIdTextfield.setText(Integer.toString(tempProduct.getProductID()));
            nameTextfield.setText(tempProduct.getName());
            priceTextField.setText(Double.toString(tempProduct.getPrice()));
            inventoryTextField.setText(Integer.toString(tempProduct.getInStock()));
            minTextField.setText(Integer.toString(tempProduct.getMin()));
            maxTextField.setText(Integer.toString(tempProduct.getMax()));
        }

        //Product Search Button + Action
        Button partSearchButton = new Button();
        partSearchButton.setText("Search");
        partSearchButton.setOnAction(e -> {
            String filterPartTablesSearch = partSearchTextfield.getText();
            //
            if (filterPartTablesSearch  == null || filterPartTablesSearch.isEmpty()) {
                addPartTableView.setItems(addPartTableViewList);
                currentPartsTableView.setItems(currentPartsTableViewList);
            }else{
                ObservableList<Part> filteredAddParts = FXCollections.observableArrayList();
                ObservableList<Part> filteredCurrentParts = FXCollections.observableArrayList();
                int parsedFilterPartTableSearch = Integer.parseInt(partSearchTextfield.getText());
                if(!(addPartTableViewList.isEmpty())){
                    Part foundPart = null;
                    for (Part currentPart : addPartTableViewList){
                            if (parsedFilterPartTableSearch == currentPart.getPartID()){
                                foundPart = currentPart;
                                break;
                            }
                    }
                    if (foundPart != null) {
                        filteredAddParts.add(foundPart);
                        addPartTableView.setItems(filteredAddParts);
                    }else{
                        addPartTableView.setItems(filteredAddParts);
                    }
                }
                if(!(currentPartsTableViewList.isEmpty())){
                    Part foundPart = null;
                    for (Part currentPart : currentPartsTableViewList){
                            if(parsedFilterPartTableSearch == currentPart.getPartID()){
                                foundPart = currentPart;
                                break;
                            }
                    }
                    if (foundPart != null) {
                        filteredCurrentParts.add(foundPart);
                        currentPartsTableView.setItems(filteredCurrentParts);
                    }else{
                        currentPartsTableView.setItems(filteredCurrentParts);
                    }
                }
            }
        });
        productWindowGridPane.setConstraints(partSearchButton, 11, 2);

        //Add Button + Action
        Button addPartToProductButton = new Button("  Add  ");
        addPartToProductButton.setOnAction(e -> {
            //Add selected Part to addPartTableView for current Product
            Part selectedPartFromCurrentPartsTable = currentPartsTableView.getSelectionModel().getSelectedItem();
            if(selectedPartFromCurrentPartsTable != null){
                addPartTableViewList.add(selectedPartFromCurrentPartsTable);
                selectedPartFromCurrentPartsTable = null;
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Addition");
                alert.setHeaderText(null);
                alert.setContentText("Please select a part to add to the Product.");
                alert.showAndWait();
            }
        });
        productWindowGridPane.setConstraints(addPartToProductButton, 13, 8);

        //Delete Button + Action
        Button deletePartFromProductButton = new Button("Delete");
        deletePartFromProductButton.setOnAction(e-> {
            //Delete selected Part from addPartTableView for current Product
            Part selectedPartFromAddPartsTable = addPartTableView.getSelectionModel().getSelectedItem();
            if(selectedPartFromAddPartsTable != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Part Delete");
                alert.setHeaderText("This will delete Part ID: " + selectedPartFromAddPartsTable.getPartID() +".");
                alert.setContentText("Click Ok to confirm.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    addPartTableViewList.remove(selectedPartFromAddPartsTable);
                    addPartTableView.getSelectionModel().select(null);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Delete");
                alert.setHeaderText(null);
                alert.setContentText("Please select a part for deletion.");
                alert.showAndWait();
            }
        });
        productWindowGridPane.setConstraints(deletePartFromProductButton, 13, 15);

        //saveProductButton + Action
        Button saveProductAndPartsButton = new Button(" Save ");
        saveProductAndPartsButton.setOnAction(e -> {
            boolean tryStatementPassed = true;
            try {
                if (passedProduct != null){
                    addedProduct.setProductID(Integer.parseInt(productIdTextfield.getText()));
                }else{
                    addedProduct.setProductID(randomResult);
                }
                if(nameTextfield.getText() == null) {
                    addedProduct.setName("Default Name");
                }else{
                    addedProduct.setName(nameTextfield.getText());
                }
                addedProduct.setPrice(Double.parseDouble(priceTextField.getText()));
                addedProduct.setInStock(Integer.parseInt(inventoryTextField.getText()));
                addedProduct.setMax(Integer.parseInt(maxTextField.getText()));
                addedProduct.setMin(Integer.parseInt(minTextField.getText()));
                addedProduct.setAssociatedParts(addPartTableViewList);
            }catch(Exception x) {
                tryStatementPassed = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Part Creation Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all required fields.");
                alert.showAndWait();
            }
            if(tryStatementPassed) {
                if (addedProduct.getMin() > addedProduct.getMax()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Product Creation Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The minimum inventory is greater then the maximum.");
                    alert.showAndWait();
                } else if (addedProduct.getInStock() > addedProduct.getMax()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Product Creation Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The current inventory is greater then the maximum");
                    alert.showAndWait();
                } else if (addedProduct.getInStock() < addedProduct.getMin()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Product Creation Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The current inventory is less then the maximum.");
                    alert.showAndWait();
                } else if (addedProduct.getAssociatedParts().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Unable to create Product");
                    alert.setHeaderText(null);
                    alert.setContentText("A new Product must have at least one Part.");
                    alert.showAndWait();

                } else if (!(addedProduct.getAssociatedParts().isEmpty())) {
                    Double totalCostOfParts = 00.00;
                    for (Part currentPart : addedProduct.getAssociatedParts()) {
                        totalCostOfParts = totalCostOfParts + currentPart.getPrice();
                    }
                    if (totalCostOfParts > addedProduct.getPrice()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Unable to create Product");
                        alert.setHeaderText(null);
                        alert.setContentText("A new Product's cost must be more then the sum of its Parts.");
                        alert.showAndWait();
                    } else {
                        addProductWindow.close();
                    }
                }
            }
        });
        productWindowGridPane.setConstraints(saveProductAndPartsButton, 12, 17);
        productWindowGridPane.setHalignment(saveProductAndPartsButton, HPos.RIGHT);

        //cancelProductButton + Action
        Button cancelProductAndPartsButton = new Button("Cancel");
        cancelProductAndPartsButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Cancel");
            alert.setHeaderText("This will clear any unsaved changes.");
            alert.setContentText("Click Ok to confirm.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if(addedProduct != null) {
                    addedProduct.setProductID(-1);
                    addProductWindow.close();
               }else{
                    addedProduct.setProductID(0);
                    addProductWindow.close();
                }
            }
        });
        productWindowGridPane.setConstraints(cancelProductAndPartsButton, 13, 17);
        
        //Populate currentPartsTable
        if (passedPartArray != null) {
            for (Part currentPart : passedPartArray) {
                currentPartsTableViewList.add(currentPart);
                currentPartsTableView.setItems(currentPartsTableViewList);
            }
        }

        //Initialize Product if null
        if (passedProduct != null){

        }

        //Populate addedPartsTable
        if (passedProduct != null){
            for (Part currentAddPart : passedProduct.getAssociatedParts()){
                addPartTableViewList.add(currentAddPart);
                addPartTableView.setItems(addPartTableViewList);
            }
        }

        FlowPane root = new FlowPane();
        productWindowGridPane.getChildren().addAll(addProductTitleLabel, partSearchButton, partSearchTextfield, addPartTableView, productIdLabel, productIdTextfield, nameLabel, nameTextfield, inventoryLabel, inventoryTextField, addPartToProductButton, priceLabel, priceTextField, minLabel, minTextField, maxLabel, maxTextField, currentPartsTableView, deletePartFromProductButton, saveProductAndPartsButton, cancelProductAndPartsButton);
        root.getChildren().add(productWindowGridPane);
        addProductWindow.setScene(new Scene(root, 950, 475));
        addProductWindow.showAndWait();

        return addedProduct;
    }

}

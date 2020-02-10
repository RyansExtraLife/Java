package inventorymanagement;

//Import statements for JavaFX
import java.util.Optional;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryManagement extends Application {
    
    Inventory applicationInventory = new Inventory();

    //Store clicked cells from tableViews.
    Part selectedPartFromTable = null;
    Product selectedProductFromTable = null;
    
    @Override
    public void start(Stage primaryStage) throws Exception{

        GridPane mainGridPane = new GridPane();
        mainGridPane.setPadding(new Insets(20,20,20,20));
        mainGridPane.setVgap(18);
        mainGridPane.setHgap(18);
        mainGridPane.setPrefSize(1200, 500);

        //Application Title Label
        Label applicationTitleLabel = new Label("Inventory Management System");
        mainGridPane.setConstraints(applicationTitleLabel,0,2);
        GridPane.setColumnSpan(applicationTitleLabel, 10);

        //Parts Label
        Label partsTitleLabel = new Label("Parts");
        mainGridPane.setConstraints(partsTitleLabel, 0,5);

        //Parts Search Textfield
        TextField partsSearchTextfield = new TextField();
        mainGridPane.setConstraints(partsSearchTextfield, 9,5);

        //Parts TableView
        TableView<Part> partsTableView = new TableView<Part>(applicationInventory.getAllParts());
        GridPane.setConstraints(partsTableView,0,6);
        GridPane.setColumnSpan(partsTableView, 10);
        partsTableView.setPrefHeight(200);
        partsTableView.setPrefWidth(275);

        //partsTableView partId column
        TableColumn<Part, Integer> tablePartIdColumn = new TableColumn<>("PartId");
        tablePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partsTableView.getColumns().add(tablePartIdColumn);
        tablePartIdColumn.prefWidthProperty().bind(partsTableView.widthProperty().divide(4));

        //partsTableView name column
        TableColumn<Part, String> tablePartName= new TableColumn<>("Part Name");
        tablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsTableView.getColumns().add(tablePartName);
        tablePartName.prefWidthProperty().bind(partsTableView.widthProperty().divide(4));

        //partsTableView inStock column
        TableColumn<Part, Integer> tablePartInventoryLevel = new TableColumn<>("Inventory Level");
        tablePartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partsTableView.getColumns().add(tablePartInventoryLevel);
        tablePartInventoryLevel.prefWidthProperty().bind(partsTableView.widthProperty().divide(4));

        //partsTableView price column
        TableColumn<Part, Double> tablePartPrice = new TableColumn<>("Price Per Unit");
        tablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTableView.getColumns().add(tablePartPrice);
        tablePartPrice.prefWidthProperty().bind(partsTableView.widthProperty().divide(4));

        //Store selected partsTableView row to variable
        partsTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedPartFromTable = partsTableView.getSelectionModel().getSelectedItem();
            }
        });

        //Parts Search Button + Action
        Button partsSearchButton = new Button();
        partsSearchButton.setText("Search");
        partsSearchButton.setOnAction(e -> {
            String filterPartTableSearch = partsSearchTextfield.getText();
            if (filterPartTableSearch  == null || filterPartTableSearch.isEmpty()) {
                partsTableView.setItems(applicationInventory.getAllParts());
            }else {
                ObservableList<Part> filteredAllParts = FXCollections.observableArrayList();
                int parsedFilterPartTableSearch = Integer.parseInt(partsSearchTextfield.getText());
                Part searchedPart = applicationInventory.lookupPart(parsedFilterPartTableSearch );
                filteredAllParts.add(searchedPart);
                partsTableView.setItems(filteredAllParts);
            }
        });
        mainGridPane.setConstraints(partsSearchButton,8,5);

        //Parts Add Button + Action
        Button partsAddButton = new Button();
        partsAddButton.setText(" Add ");
        partsAddButton.setOnAction(e -> {
            Part addedPart = PartWindow.display(null);
  
            if(addedPart.getPartID() != 0) {
                applicationInventory.addPart(addedPart);
                
            }
        });
        mainGridPane.setConstraints(partsAddButton, 7,7);

        //Parts Modify Button + Action
        Button partsModifyButton = new Button();
        partsModifyButton.setText("Modify");
        partsModifyButton.setOnAction(e -> {
            if(selectedPartFromTable != null) {
                Part modifyPart = PartWindow.display(selectedPartFromTable);
                if (!(modifyPart.getPartID() == -1 || modifyPart.getPartID() == 0)){
                    if (!(modifyPart.comparePart(selectedPartFromTable))){
                        applicationInventory.updatePart(selectedPartFromTable.getPartID(), modifyPart);
                        partsTableView.getSelectionModel().select(null);
                        selectedPartFromTable = null;
                    }
                }
            }
        });
        mainGridPane.setConstraints(partsModifyButton, 8,7);

        //Parts Delete Button + Action
        Button partsDeleteButton = new Button();
        partsDeleteButton.setText("Delete");
        partsDeleteButton.setOnAction(e-> {
            if(selectedPartFromTable != null) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirm Part Delete");
                alert.setHeaderText("This will delete Part ID: " + selectedPartFromTable.getPartID() +".");
                alert.setContentText("Click Ok to confirm.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    applicationInventory.deletePart(selectedPartFromTable);
                    partsTableView.getSelectionModel().select(null);
                    selectedPartFromTable = null;
                }
            }
        });
        mainGridPane.setConstraints(partsDeleteButton, 9,7);

        //Products Label
        Label productsTitleLabel = new Label("Products");
        mainGridPane.setConstraints(productsTitleLabel, 10,5);

        //Products Search Textfield
        TextField productsSearchTextfield = new TextField();
        mainGridPane.setConstraints(productsSearchTextfield, 18,5);

        //Products TableView
        TableView<Product> productTableView;
        productTableView = new TableView<Product>(applicationInventory.getAllProducts());
        GridPane.setConstraints(productTableView,10,6);
        GridPane.setColumnSpan(productTableView, 9);
        productTableView.setPrefHeight(200);
        productTableView.setPrefWidth(275);

        TableColumn<Product, String> tableProductIdColumn = new TableColumn<>("ProductID");
        tableProductIdColumn .setCellValueFactory(new PropertyValueFactory<>("productID"));
        productTableView.getColumns().add(tableProductIdColumn );
        tableProductIdColumn .prefWidthProperty().bind(productTableView.widthProperty().divide(4));

        TableColumn<Product, String> tableProductName= new TableColumn<>("Product Name");
        tableProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productTableView.getColumns().add(tableProductName);
        tableProductName.prefWidthProperty().bind(productTableView.widthProperty().divide(4));

        TableColumn<Product, String> tableProductInventoryLevel = new TableColumn<>("Inventory Level");
        tableProductInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productTableView.getColumns().add(tableProductInventoryLevel);
        tableProductInventoryLevel.prefWidthProperty().bind(productTableView.widthProperty().divide(4));

        TableColumn<Product, String> tableProductPrice = new TableColumn<>("Price Per Unit");
        tableProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTableView.getColumns().add(tableProductPrice);
        tableProductPrice.prefWidthProperty().bind(productTableView.widthProperty().divide(4));

        //Store selected productsTableView row to variable
        productTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedProductFromTable = productTableView.getSelectionModel().getSelectedItem();
            }
        });

        //Products Search Button + Action
        Button productsSearchButton = new Button();
        productsSearchButton.setText("Search");
        productsSearchButton.setOnAction(e -> {
            String filterProductTableSearch = productsSearchTextfield.getText();
            if (filterProductTableSearch  == null || filterProductTableSearch.isEmpty()) {
                // No filter --> Add all.
                productTableView.setItems(applicationInventory.getAllProducts());
            }else {
                ObservableList<Product> filteredAllProducts = FXCollections.observableArrayList();
                int parsedFilterProductTableSearch = Integer.parseInt(productsSearchTextfield.getText());
                Product searchedProduct = applicationInventory.lookupProduct(parsedFilterProductTableSearch);
                filteredAllProducts.add(searchedProduct);
                productTableView.setItems(filteredAllProducts);
            }
        });
        mainGridPane.setConstraints(productsSearchButton, 17,5);

        //Products Add Button
        Button productAddButton = new Button();
        productAddButton.setText(" Add ");
        productAddButton.setOnAction(e -> {
                if(!(applicationInventory.isPartsEmpty())){
                    Product addedProduct = ProductWindow.display(null, applicationInventory.getAllParts());
                    if (!(addedProduct.getProductID() == 0 || addedProduct.getProductID() == -1)){
                        applicationInventory.addProduct(addedProduct);
                    }
                }else{
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Unable to create a Product");
                    alert.setHeaderText(null);
                    alert.setContentText("A Product must have a Part, add a Part to continue.");
                    alert.showAndWait();
                }
        });
        mainGridPane.setConstraints(productAddButton, 16,7);

        //Product Modify Button
        Button productModifyButton = new Button();
        productModifyButton.setText("Modify");
        productModifyButton.setOnAction(e -> {
            if(selectedProductFromTable != null) {
                Product modifyProduct = ProductWindow.display(selectedProductFromTable, applicationInventory.getAllParts());
                if (!(modifyProduct.getProductID() == -1 || modifyProduct.getProductID() == 0)){
                    if (!(modifyProduct.compareProduct(selectedProductFromTable))){
                        applicationInventory.updateProduct(selectedProductFromTable.getProductID(), modifyProduct);
                        productTableView.getSelectionModel().select(null);
                        selectedProductFromTable = null;
                    }
                }
            }
        });
        mainGridPane.setConstraints(productModifyButton, 17,7);

        //Product Delete Button + Action
        Button productDeleteButton = new Button();
        productDeleteButton.setText("Delete");
        productDeleteButton.setOnAction(e-> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Product Delete");
            alert.setHeaderText("This will delete Part ID: " + selectedProductFromTable.getProductID() +".");
            alert.setContentText("Are you ok with this?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                applicationInventory.removeProduct(selectedProductFromTable.getProductID());
                productTableView.getSelectionModel().select(null);
                selectedProductFromTable = null;
            }
                
        });
        mainGridPane.setConstraints(productDeleteButton, 18,7);

        //Exit Application Button + Action
        Button applicationExitButton = new Button();
        applicationExitButton.setText("  Exit  ");
        applicationExitButton.setOnAction(e -> Platform.exit());
        mainGridPane.setConstraints(applicationExitButton, 18,8);

        FlowPane root = new FlowPane();
        mainGridPane.getChildren().addAll(applicationTitleLabel, partsTitleLabel, partsSearchButton, partsSearchTextfield, partsTableView, partsAddButton, partsModifyButton, partsDeleteButton, productsTitleLabel, productsSearchButton, productsSearchTextfield, productTableView, productAddButton, productModifyButton, productDeleteButton, applicationExitButton);
        root.getChildren().add(mainGridPane);
        partsTableView.setItems(applicationInventory.getAllParts());
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }

    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

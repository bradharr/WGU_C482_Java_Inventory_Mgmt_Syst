 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 *
 * @author bradharr
 */
public class MainMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    //Button, Field, and Label fxIDs
    @FXML
    private TextField mainPartsSearchTxt;

    @FXML
    private TableView<Part> mainPartsTableView;

    @FXML
    private TableColumn<Part, Integer> partsIdCol;

    @FXML
    private TableColumn<Part, String> partsNameCol;

    @FXML
    private TableColumn<Part, Integer> partsInvCol;

    @FXML
    private TableColumn<Part, Double> partsPriceCol;

    @FXML
    private TextField mainProductsSearchTxt;

    @FXML
    private TableView<Product> mainProductsTableView;

    @FXML
    private TableColumn<Product, Integer> productsIdCol;

    @FXML
    private TableColumn<Product, String> productsNameCol;

    @FXML
    private TableColumn<Product, Integer> productsInvCol;

    @FXML
    private TableColumn<Product, Double> productsPriceCol;
    
    //Button Controls
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        
        Part part = mainPartsTableView.getSelectionModel().getSelectedItem();
        if(part == null)
            return;
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("This will delete the selected Part");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            deletePart(part.getId());
        } 
      
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Product product = mainProductsTableView.getSelectionModel().getSelectedItem();
        if(product == null)
            return;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("This will delete the selected Product");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        deleteProduct(product.getId());
        } 

        
    }

    @FXML
    void onActionExitProgram(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("This will Exit the Inventory Management Program");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        System.exit(0);
        } 

        
    }

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        Part part = mainPartsTableView.getSelectionModel().getSelectedItem();
        if(part == null)
            return;
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        loader.load();
        
        ModifyPartController PartController = loader.getController();
        PartController.retrievePart(part);
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();           
    }

    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        Product product = mainProductsTableView.getSelectionModel().getSelectedItem();
        if(product == null)
            return;
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
        loader.load();
        
        ModifyProductController ProductController = loader.getController();
        ProductController.retrieveProduct(product);
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show(); 
    }

    @FXML
    void onActionSearchParts(ActionEvent event) {
        
        if(mainPartsSearchTxt.getText().isEmpty()) {
            return;
        }
        else if (searchPartID(Integer.parseInt(mainPartsSearchTxt.getText()))) {
        mainPartsTableView.getSelectionModel().select(selectPart(Integer.parseInt(mainPartsSearchTxt.getText())));
    }
    }

    @FXML
    void onActionSearchProducts(ActionEvent event) {
        
        if(mainProductsSearchTxt.getText().isEmpty()) {
            return;
        }
        if(searchProductID(Integer.parseInt(mainProductsSearchTxt.getText()))) {
        mainProductsTableView.getSelectionModel().select(selectProduct(Integer.parseInt(mainProductsSearchTxt.getText())));
        }
    }
    
    //Search Product ID Method
    public boolean searchProductID(int id) {
        for(Product product : Inventory.getAllProducts()) {
            if(product.getId() == id)
            return true;
        }
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error Dialog");
    alert.setContentText("Product Not Found!");
    alert.showAndWait();
    return false;
    }
    
    //Search Part ID Method
    public boolean searchPartID(int id) {
    for(Part part : Inventory.getAllParts()) {
        if(part.getId() == id)
        return true;
    }
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error Dialog");
    alert.setContentText("Part Not Found!");
    alert.showAndWait();
    return false;
    }
    
    //Delete Part Method
    public boolean deletePart(int id) {
    for(Part part : Inventory.getAllParts()) {
        if(part.getId() == id)
            return Inventory.getAllParts().remove(part);
    }
    return false;
}
    //Delete Product Method
    public boolean deleteProduct(int id) {
    for(Product product : Inventory.getAllProducts()) {
        if(product.getId() == id)
            return Inventory.getAllProducts().remove(product);
    }
    return false;
}
    //Select Part Method
    public Part selectPart(int id) {
        
        for(Part part : Inventory.getAllParts()) {
            if(part.getId() == id)
                return part;
        }
        return null;
    }
    
    //Select Product Method
    public Product selectProduct(int id) {

    for(Product product : Inventory.getAllProducts()) {
        if(product.getId() == id)
            return product;
    }
    return null;
    }


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Populating both TableViews
        mainPartsTableView.setItems(Inventory.getAllParts());
        mainProductsTableView.setItems(Inventory.getAllProducts());
        
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInvCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }    
    
}

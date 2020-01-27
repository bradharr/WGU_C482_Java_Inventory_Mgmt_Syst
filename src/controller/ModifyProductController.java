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
 * FXML Controller class
 *
 * @author bradharr
 */
public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;
    
    Product product;
    
    //Button, Field, and Label fxIDs
    @FXML
    private TextField modifyProductIdAutoGen;

    @FXML
    private TextField modifyProductInvTxt;

    @FXML
    private TextField modifyProductPriceTxt;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TextField modifyProductSearchTxt;

    @FXML
    private TableView<Part> modifyProductTableView1;

    @FXML
    private TableColumn<Part, Integer> modifyProductId1Col;

    @FXML
    private TableColumn<Part, String> modifyProductName1Col;

    @FXML
    private TableColumn<Part, Integer> modifyProductInv1Col;

    @FXML
    private TableColumn<Part, Double> modifyProductPrice1Col;

    @FXML
    private TableView<Part> modifyProductTableView2;

    @FXML
    private TableColumn<Part, Integer> modifyProductId2Col;

    @FXML
    private TableColumn<Part, String> modifyProductName2Col;

    @FXML
    private TableColumn<Part, Integer> modifyProductInv2Col;

    @FXML
    private TableColumn<Part, Double> modifyProductPrice2Col;
    
    
    
    //Button Controls
    @FXML
    void onActionAddModifiedProduct(ActionEvent event) {
        Part part = modifyProductTableView1.getSelectionModel().getSelectedItem();
        if(part == null)
            return;
        else {
            product.getAllAssociatedParts().add(part);
        }
            
        
    }

    @FXML
    void onActionDeleteModifiedProduct(ActionEvent event) {
        Part part = modifyProductTableView2.getSelectionModel().getSelectedItem();
        if(part == null)
            return;
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to delete this Part from the list?");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        product.deleteAssociatedPart(part.getId());
        } 
        
    }

    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Changes not Saved");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        } 
    }

    @FXML
    void onActionSaveModifiedProduct(ActionEvent event) throws IOException {

        
        int inventoryValue = Integer.parseInt(modifyProductInvTxt.getText());
        int minValue = Integer.parseInt(modifyProductMinTxt.getText());
        int maxValue = Integer.parseInt(modifyProductMaxTxt.getText());
        if(inventoryValue >= minValue && inventoryValue <= maxValue) {
            
            int idValue = Integer.parseInt(modifyProductIdAutoGen.getText());
            String name = modifyProductNameTxt.getText();
            double price = Double.parseDouble(modifyProductPriceTxt.getText());
            Product product3 = new Product(idValue, name, price, inventoryValue,  minValue, maxValue, product.getAllAssociatedParts());
            if(update(idValue, product3)) {
           
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Ensure all fields are accurate before proceeding.");
            alert.showAndWait();
        }
        
    }

    @FXML
    void onActionSearchModifiedProduct(ActionEvent event) {
     
        if(modifyProductSearchTxt.getText().isEmpty()) {
            return;
        }
        else if (searchPartID(Integer.parseInt(modifyProductSearchTxt.getText()))) {
        modifyProductTableView1.getSelectionModel().select(selectPart(Integer.parseInt(modifyProductSearchTxt.getText())));
    }
    }
    
    //Retrieve Product Method
    public void retrieveProduct(Product product2) {

    modifyProductIdAutoGen.setText(String.valueOf(product2.getId()));
    modifyProductNameTxt.setText(product2.getName());
    modifyProductInvTxt.setText(String.valueOf(product2.getInv()));
    modifyProductPriceTxt.setText(String.valueOf(product2.getPrice()));
    modifyProductMaxTxt.setText(String.valueOf(product2.getMax()));
    modifyProductMinTxt.setText(String.valueOf(product2.getMin()));
    
    product.getAllAssociatedParts().addAll(product2.getAllAssociatedParts());
}
    
    //Select Part Method
    public Part selectPart(int id) {
        
        for(Part part : Inventory.getAllParts()) {
            if(part.getId() == id)
                return part;
        }
        return null;
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
    //Update Method
    public boolean update(int id, Product product) {
        int index = Integer.parseInt(modifyProductIdAutoGen.getText()) - 1;

        for(Product products : Inventory.getAllProducts()){


            if(product.getId() == id) {
                Inventory.getAllProducts().set(index, product);
                return true;
            }

        }
                return false;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        product = new Product();
        
        //Populate both TableViews
        modifyProductTableView1.setItems(Inventory.getAllParts()); 
        modifyProductTableView2.setItems(product.getAllAssociatedParts());
        
        modifyProductId1Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductName1Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInv1Col.setCellValueFactory(new PropertyValueFactory<>("inv"));
        modifyProductPrice1Col.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        modifyProductId2Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductName2Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInv2Col.setCellValueFactory(new PropertyValueFactory<>("inv"));
        modifyProductPrice2Col.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
}

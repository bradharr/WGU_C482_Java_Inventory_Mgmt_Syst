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
public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;
    
    Product product;
    
    //Button, Field, and Label fxIDs
    @FXML
    private TextField addProductAutoGen;

    @FXML
    private TextField addProductNameTxt;
    
    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductSearchTxt;

    @FXML
    private TableView<Part> addProductTableView1;

    @FXML
    private TableColumn<Part, Integer> addProductID1Col;

    @FXML
    private TableColumn<Part, String> addProductName1Col;

    @FXML
    private TableColumn<Part, Integer> addProductInv1Col;

    @FXML
    private TableColumn<Part, Double> addProductPrice1Col;

    @FXML
    private TableView<Part> addProductTableView2;

    @FXML
    private TableColumn<Part, Integer> addProductID2Col;

    @FXML
    private TableColumn<Part, String> addProductName2Col;

    @FXML
    private TableColumn<Part, Integer> addProductInv2Col;

    @FXML
    private TableColumn<Part, Double> addProductPrice2Col;

    //Button Controls
    @FXML
    void onActionAddProduct(ActionEvent event) {
        Part part = addProductTableView1.getSelectionModel().getSelectedItem();
        if(part == null)
            return;
        else {
            product.getAllAssociatedParts().add(part);
        }

    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Part part = addProductTableView2.getSelectionModel().getSelectedItem();
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
    void onActionSaveProduct(ActionEvent event) throws IOException {
        
        
        
        if(Integer.parseInt(addProductInvTxt.getText()) >= Integer.parseInt(addProductMinTxt.getText()) && 
                Integer.parseInt(addProductInvTxt.getText()) <= Integer.parseInt(addProductMaxTxt.getText())) {
      
        int id = Product.incNextID();
        String name = addProductNameTxt.getText();
        int inv = Integer.parseInt(addProductInvTxt.getText());
        Double price = Double.parseDouble(addProductPriceTxt.getText());
        int max = Integer.parseInt(addProductMaxTxt.getText());
        int min = Integer.parseInt(addProductMinTxt.getText());
        
        Inventory.addProduct(new Product(id, name, price, inv, min, max, product.getAllAssociatedParts()));
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }
        else {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error Dialog");
        alert.setContentText("Inventory must fall between the Min and Max Inventory!");
        alert.showAndWait();
        
        }
        
    }

    @FXML
    void onActionSearchProduct(ActionEvent event) {
     
        if(addProductSearchTxt.getText().isEmpty()) {
            return;
        }
        else if (searchPartID(Integer.parseInt(addProductSearchTxt.getText()))) {
        addProductTableView1.getSelectionModel().select(selectPart(Integer.parseInt(addProductSearchTxt.getText())));
    }
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        product = new Product();
        
        //Setting Incrementing ID Field
        addProductAutoGen.setText(Integer.toString(Product.getNextID()));
        
        //Populating both TableViews
        addProductTableView1.setItems(Inventory.getAllParts());
        addProductTableView2.setItems(product.getAllAssociatedParts());
        
        addProductID1Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductName1Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInv1Col.setCellValueFactory(new PropertyValueFactory<>("inv"));
        addProductPrice1Col.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        addProductID2Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductName2Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInv2Col.setCellValueFactory(new PropertyValueFactory<>("inv"));
        addProductPrice2Col.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    

}

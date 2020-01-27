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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;
import model.Part;

/**
 * FXML Controller class
 *
 * @author bradharr
 */
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;
    
    
    //Button, Field, and Label fxIDs
    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private RadioButton OutsourcedRBtn;

    @FXML
    private TextField AddPartIdTxt;

    @FXML
    private TextField AddPartNameTxt;

    @FXML
    private TextField AddInvTxt;

    @FXML
    private TextField AddPriceTxt;

    @FXML
    private TextField AddMaxTxt;

    @FXML
    private TextField AddMinTxt;

    @FXML
    private TextField varFieldTxt;

    @FXML
    private Label compNameLbl;

    //Button Controls
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
    void onActionSavePart(ActionEvent event) throws IOException {

        if(inHouseRBtn.isSelected() && Integer.parseInt(AddInvTxt.getText()) >= Integer.parseInt(AddMinTxt.getText()) && Integer.parseInt(AddInvTxt.getText()) <= Integer.parseInt(AddMaxTxt.getText())){
            saveInHouse();
        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else if(OutsourcedRBtn.isSelected() && Integer.parseInt(AddInvTxt.getText()) >= Integer.parseInt(AddMinTxt.getText()) && Integer.parseInt(AddInvTxt.getText()) <= Integer.parseInt(AddMaxTxt.getText())){
            saveOutsourced();
            
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
    //Set Variable Field Method Based on Radio Button Selection
    public void setVarField(ActionEvent event) {
        
        if(inHouseRBtn.isSelected()) {
            compNameLbl.setText("Machine ID");
            varFieldTxt.setPromptText("Mach ID");
        }
        if(OutsourcedRBtn.isSelected()) {
            compNameLbl.setText("Company Name");
            varFieldTxt.setPromptText("Comp Nm");
        }
    }
    
    //Method for Saving a New In House Part
    public void saveInHouse() {
        
        
            int id = Part.incNextID();
            String name = AddPartNameTxt.getText();
            int inv = Integer.parseInt(AddInvTxt.getText());
            Double price = Double.parseDouble(AddPriceTxt.getText());
            int max = Integer.parseInt(AddMaxTxt.getText());
            int min = Integer.parseInt(AddMinTxt.getText());
            int machineId = Integer.parseInt(varFieldTxt.getText());
            
            Inventory.addPart(new InHouse(id, name, price, inv, min, max, machineId));

    }
    
    //Method for Saving a New Outsourced Part
    public void saveOutsourced() {   
        
            int id = Part.incNextID();
            String name = AddPartNameTxt.getText();
            int inv = Integer.parseInt(AddInvTxt.getText());
            Double price = Double.parseDouble(AddPriceTxt.getText());
            int max = Integer.parseInt(AddMaxTxt.getText());
            int min = Integer.parseInt(AddMinTxt.getText());
            String companyName = varFieldTxt.getText();
            
            Inventory.addPart(new Outsourced(id, name, price, inv, min, max, String.valueOf(companyName)));
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Setting Incrementing ID Field
        AddPartIdTxt.setText(Integer.toString(Part.getNextID()));
    }    

    
}

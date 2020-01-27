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
import model.Part;

/**
 * FXML Controller class
 *
 * @author bradharr
 */
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;
    
    //Button, Field, and Label fxIDs
    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private TextField autoGenID;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private TextField modifyInvTxt;

    @FXML
    private TextField modifyPriceTxt;

    @FXML
    private TextField modifyMaxTxt;

    @FXML
    private TextField modifyMinTxt;

    @FXML
    private TextField modifyVarFieldTxt;
    
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
    void onActionSaveModifiedPart(ActionEvent event) throws IOException {
        
        if(inHouseRBtn.isSelected() && Integer.parseInt(modifyInvTxt.getText()) >= Integer.parseInt(modifyMinTxt.getText()) && Integer.parseInt(modifyInvTxt.getText()) <= Integer.parseInt(modifyMaxTxt.getText())) {
            if(update(Integer.parseInt(autoGenID.getText()), new InHouse(Integer.parseInt(autoGenID.getText()), modifyPartNameTxt.getText(), Double.parseDouble(modifyPriceTxt.getText()), Integer.parseInt(modifyInvTxt.getText()),  Integer.parseInt(modifyMinTxt.getText()), Integer.parseInt(modifyMaxTxt.getText()), Integer.parseInt(modifyVarFieldTxt.getText()))))
           
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else if(outsourcedRBtn.isSelected() && Integer.parseInt(modifyInvTxt.getText()) >= Integer.parseInt(modifyMinTxt.getText()) && Integer.parseInt(modifyInvTxt.getText()) <= Integer.parseInt(modifyMaxTxt.getText())) {
            if(update(Integer.parseInt(autoGenID.getText()), new Outsourced(Integer.parseInt(autoGenID.getText()), modifyPartNameTxt.getText(), Double.parseDouble(modifyPriceTxt.getText()), Integer.parseInt(modifyInvTxt.getText()),  Integer.parseInt(modifyMinTxt.getText()), Integer.parseInt(modifyMaxTxt.getText()), modifyVarFieldTxt.getText())))
        
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
    /**
     * Initializes the controller class.
     */
    
    //Retreive Part Method
    public void retrievePart(Part part) {
        
        autoGenID.setText(String.valueOf(part.getId()));
        modifyPartNameTxt.setText(part.getName());
        modifyInvTxt.setText(String.valueOf(part.getInv()));
        modifyPriceTxt.setText(String.valueOf(part.getPrice()));
        modifyMaxTxt.setText(String.valueOf(part.getMax()));
        modifyMinTxt.setText(String.valueOf(part.getMin()));
        
        if(part instanceof InHouse) {
            InHouse ihPart = (InHouse) part;
            modifyVarFieldTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
            compNameLbl.setText("Machine ID");
            inHouseRBtn.setSelected(true);
        }
        else if(part instanceof Outsourced) {
            Outsourced oPart = (Outsourced) part;
            modifyVarFieldTxt.setText(((Outsourced) part).getCompanyName());
            outsourcedRBtn.setSelected(true);
         
        }
            
    }
    
        //Set Variable Field Method Based on Radio Button Selection
       public void setVarField(ActionEvent event) {
        
        if(inHouseRBtn.isSelected()) {
            compNameLbl.setText("Machine ID");
            modifyVarFieldTxt.setPromptText("Mach ID");
        }
        if(outsourcedRBtn.isSelected()) {
            compNameLbl.setText("Company Name");
            modifyVarFieldTxt.setPromptText("Comp Nm");
        }
    }
       //Update Method
        public boolean update(int id, Part part) {
            int index = Integer.parseInt(autoGenID.getText()) - 1;
            
            for(Part parts : Inventory.getAllParts()){
                
                
                if(part.getId() == id) {
                    Inventory.getAllParts().set(index, part);
                    return true;
                }
             
            }
                    return false;
        }
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

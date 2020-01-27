/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482_java_inventory_fxapplication;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 *
 * @author bradharr
 * 
 * Student: Bradley Harr
 * Student ID: #000931061
 * Course: C482 Software I
 * Project: Inventory Management System
 * 
 */
public class C482_Java_Inventory_FXApplication extends Application {
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        
        //Initial Data
        InHouse part1 = new InHouse(Part.incNextID(), "Hard Drive - IH", 112.00, 6, 10, 0, 19);
        Outsourced part2 = new Outsourced(Part.incNextID(), "LCD Display - OS", 89.00, 7, 0, 10, "Tech Time");
        Outsourced part3 = new Outsourced(Part.incNextID(), "1 GB RAM - OS", 299.00, 2, 0, 10, "TeleTech");
        
        
        associatedParts.add(part1);
        Product product1 = new Product(Product.incNextID(), "Desktop", 400.00, 5, 0, 10, associatedParts);
        associatedParts.add(part2);
        Product product2 = new Product(Product.incNextID(), "Laptop", 800.00, 10, 0, 10, associatedParts);


        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        
        launch(args);
    }
    
}

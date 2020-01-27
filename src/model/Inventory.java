/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author bradharr
 */
public class Inventory {
    
    //Observable Array Lists
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    
    //Add Part Method
    public static void addPart(Part newPart) {
    
      allParts.add(newPart);
          
    }
    
    //Add Product Method
    public static void addProduct(Product newProduct) {
    
      allProducts.add(newProduct);
        
    }
    
    //Getters
    public static ObservableList<Part> getAllParts() {
    
        return allParts;
    
    }
    
    public static ObservableList<Product> getAllProducts() {

    return allProducts;
    
    }
    
    //Lookup Part Method
    public static Part lookupPart(int partID) {
        for(Part p : allParts) {
            if (p.getId() == partID) {
                return p;
            }
        }
        return null;
    }
    
    //Lookup Product Method
    public static Product lookupProduct(int productID) {
        for(Product p : allProducts) {
            if (p.getId() == productID) {
                return p;
            }
        }
        return null;
    }
    
    //Update Part Method
    public static void updatePart(Part selectedPart) {
        allParts.set(selectedPart.getId(), selectedPart);
    }
    
    //Update Product Method
    public static void updatedProduct(Product newProduct) {
        allProducts.set(newProduct.getId(), newProduct);
    }
    
    //Delete Part Method
    public static boolean deletePart(int selectedPart) {
        for(Part p : allParts) {
            if (p.getId() == selectedPart) {
                allParts.remove(p);
                return true;
            }
        }
        return false;
    }
    
    //Delete Product Method
    public static boolean deleteProduct(int selectedProduct) {
        for(Product p : allProducts) {
            if (p.getId() == selectedProduct) {
                allProducts.remove(p);
                return true;
            }
        }
        return false;
    }
}

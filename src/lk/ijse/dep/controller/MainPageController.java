/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.dep.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import lk.ijse.dep.pool.ConnectionPool;

/**
 * FXML Controller class
 *
 * @author sahanr
 */
public class MainPageController implements Initializable {

    @FXML
    private JFXButton btnGet;
    @FXML
    private JFXButton btnRemove;

    private ConnectionPool connectionPool;
    
    
    private Connection connection;
    /**
     * Initializes the controller class.
     */
    
    
    
    public MainPageController() throws ClassNotFoundException, SQLException {
        connectionPool  = new ConnectionPool();
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnGetOnAction(ActionEvent event) {
    
        
        
        new Thread(() -> {
             connection = connectionPool.getConnection();
           
             
              if(connection!=null){
                  Platform.runLater(()->{
                      
                       new Alert(Alert.AlertType.INFORMATION,"Done").show();
                      
                  });
              
            }
            
            
        }).start();
     
    }

    @FXML
    private void btnRemoveOnAction(ActionEvent event) {
        boolean result = connectionPool.removeConnection();
        if(!result){
            new Alert(Alert.AlertType.ERROR,"There are No Connections to Remove !").show();
        }else{
            System.out.println("One Connection Removed ! ");
        }
    }
    
}

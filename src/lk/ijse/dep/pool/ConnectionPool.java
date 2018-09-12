/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.dep.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sahanr
 */
public class ConnectionPool {

    public ArrayList<Connection> connectionPool = new ArrayList<>();

    public ArrayList<Connection> consumerPool = new ArrayList<>();

    private void fillPool() throws ClassNotFoundException, SQLException {
        int inti = 5;
        String classname = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/newStu";
        String uname = "root";
        String pass = "";

        Class.forName(classname);

      

        for (int x = 0; x < inti; x++) {
         Connection connection = DriverManager.getConnection(url, uname, pass);
         connectionPool.add(connection);
        }
    }

    public ConnectionPool() throws ClassNotFoundException, SQLException {
        fillPool();
    }
    
    public synchronized Connection getConnection(){
        
        if(connectionPool.size()!=0){
            Connection connection = connectionPool.get(0);
            
            consumerPool.add(connection);
            connectionPool.remove(connection);
            return connection;
        }else{
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
            }
            return getConnection();
        }
        
    }
    
    public synchronized boolean removeConnection(){
        
        if(consumerPool.size()!=0){
            Connection connection = consumerPool.get(0);
            connectionPool.add(connection);
            consumerPool.remove(connection);
            notify();
            return true;
        }else{
            return false;
        }
        
        
    }
}

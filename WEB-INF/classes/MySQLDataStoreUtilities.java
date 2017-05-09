/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author delianaescobari
 */


public class MySQLDataStoreUtilities {
    
    public ResultSet performQuery(String query){
        ResultSet resultSet;
        
        try{
            // load the driver
            Class.forName("com.mysql.jdbc.Driver");
            
            // get a connection
            String dbURL = "jdbc:mysql://localhost:3306/bestdeal";
            String username = "root";
            String password = "1871Innovation";
            Connection connection = DriverManager.getConnection(
                    dbURL, username, password);
            
            // create a statement
            Statement statement = connection.createStatement();
            
            resultSet = statement.executeQuery(query);
            resultSet.close();
            statement.close();
            connection.close();
            
            return resultSet;
            
        } catch (ClassNotFoundException e) {
//            sqlResult = "<p>Error loading the databse driver: <br>"
//                    + e.getMessage() + "</p>";
            e.printStackTrace();
        } catch (SQLException e) {
//            sqlResult = "<p>Error executing the SQL statement: <br>"
//                    + e.getMessage() + "</p>";
              e.printStackTrace();
        }
        
        return null;
    }
        
    public String addUserQuery(User user){
        String query = "INSERT INTO Customers Values('"
                + user.getName() + "', '"
                + user.getPassword() + "', '"
                + user.getUsertype() + "');";
        
        
        return query;
    }
    
    public User getUserQuery(String username) throws SQLException{
        String query = "SELECT * FROM Customers WHERE username='"
                + username + "'";
        
        ResultSet result = performQuery(query);
        result.next();
        
        String password = result.getString("password");
        String usertype = result.getString("usertype");
                
        User user = new User(username, password, usertype);
        return user;
    }
}

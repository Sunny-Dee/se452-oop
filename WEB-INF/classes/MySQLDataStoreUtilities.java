/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author delianaescobari
 */
public class MySQLDataStoreUtilities {

    private static final String dbURL = "jdbc:mysql://localhost:3306/bestdeal";
    private static final String username = "root";
    private static final String password = "1871Innovation";

    public String addUser(User user) {

        String query = "INSERT INTO Customers Values('"
                + user.getName() + "', '"
                + user.getPassword() + "', '"
                + user.getUsertype() + "');";

        Statement statement = null;
        Connection connection = null;
        String response = "";

        try {

            connection = getConnection();

            // create a statement
            statement = connection.createStatement();

            statement.executeUpdate(query);
            statement.close();
            connection.close();

            response = "Success! ";
        } catch (SQLException e) {
//            sqlResult = "<p>Error executing the SQL statement: <br>"
//                    + e.getMessage() + "</p>";
            e.printStackTrace();
            return "SQL Exception: " + e.getMessage();
        }

        return response;
    }

    public User getUser(String username) {
        String query = "SELECT * FROM Customers WHERE username='"
                + username + "';";

        Statement statement = null;
        Connection connection = null;
        User user = null;

        try {

            connection = getConnection();

            // create a statement
            statement = connection.createStatement();

            ResultSet result = statement.executeQuery(query);

            if (result.next()) {
                String password = result.getString("password");
                String usertype = result.getString("usertype");
                user = new User(username, password, usertype);
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }

        return user;
    }

    public void saveItemToCart(String username, String itemId, String type, String maker) {
        String query = "INSERT INTO Cart(username, itemId, itemType, maker) "
                + "Values('" + username + "', '" + itemId + "', '"
                + type + "', '" + maker + "');";
        
        try {

            Connection connection = getConnection();

            // create a statement
            Statement statement = connection.createStatement();

            statement.executeUpdate(query);
            statement.close();
            connection.close();

        } catch (SQLException e) {

        }
    }

    public ArrayList<OrderItem> getItemsFromCart(String username) {
        String query = "SELECT * FROM Cart WHERE username='"
                + username + "';";
        ArrayList<OrderItem> cartItems = new ArrayList<>();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()){
                String itemId = result.getString("itemId");
                String type = result.getString("itemType");
                Product product = SaxParserDataStore
                        .allProducts.get(type).get(itemId);
                OrderItem orderitem = new OrderItem(product.getName(), 
                        product.getPrice(), product.getImage(), 
                        product.getRetailer());
                cartItems.add(orderitem);
            }
            statement.close();
            connection.close();

        } catch (SQLException e) {
        }
        return cartItems;
    }
    
    public int storeCustomerOrder(String username){
   
                
        try {

            Connection connection = getConnection();
            
            //GET ORDER ID
            String call = "{call OrderId(?, ?)}";
            CallableStatement cs = connection.prepareCall(call);
            cs.setString(1, username);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
//            ResultSet rs = null;
//            if (hadResults) rs = cs.getResultSet();
            int orderId = cs.getInt(2);
            cs.close();
            
            
            // create a statement and a prep stmt for later
            Statement statement = connection.createStatement();
            PreparedStatement storeOrder = 
                connection.prepareStatement("INSERT INTO "
                    + "OrderItems(orderId, username, "
                        + "itemId, itemType) Values(?, ?, ?, ?)");

            //Get all items in cart and store them in orders items table with id
            String cartItemsQuery = "select * from Cart WHERE username='"+username+"';";
            ResultSet rs = statement.executeQuery(cartItemsQuery);
            
            while (rs.next()) {
                String itemId = rs.getString("itemId");
                String itemType = rs.getString("itemType");
                
                storeOrder.setInt(1, orderId);
                storeOrder.setString(2, username);
                storeOrder.setString(3, itemId);
                storeOrder.setString(4, itemType);
                storeOrder.executeUpdate();
            }
            
//            //Clear the cart
            statement.close();
            statement = connection.createStatement();
            String delete = "DELETE FROM Cart WHERE username='" + username + "';";
            statement.executeUpdate(delete);
            
            statement.close();
            connection.close();
            
            return orderId;

        } catch (SQLException e) {
            return -1;
        }

    }
  

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // load the driver
            Class.forName("com.mysql.jdbc.Driver");

            // get a connection
            connection = DriverManager.getConnection(
                    dbURL, username, password);
        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return connection;
    }
}

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

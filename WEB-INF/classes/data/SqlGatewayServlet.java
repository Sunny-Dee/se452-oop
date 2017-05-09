/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author delianaescobari
 */
@WebServlet("/SqlGatewayServlet")
public class SqlGatewayServlet extends HttpServlet{
    
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)throws ServletException, IOException{
        
        response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
        
        String sqlStatement = "SELECT * FROM CustomerOrders";
        String sqlResult = "";
        
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
            
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            sqlResult = SqlUtils.getHtmlTable(resultSet);
            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (ClassNotFoundException e) {
            sqlResult = "<p>Error loading the databse driver: <br>"
                    + e.getMessage() + "</p>";
        } catch (SQLException e) {
            sqlResult = "<p>Error executing the SQL statement: <br>"
                    + e.getMessage() + "</p>";
        }
        
        pw.print(sqlResult);
    }
    
}

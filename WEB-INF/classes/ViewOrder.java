
import data.OrderItem;
import data.MySQLDataStoreUtilities;
import business.Utilities;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;

@WebServlet("/ViewOrder")

public class ViewOrder extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        MySQLDataStoreUtilities db = new MySQLDataStoreUtilities();
        Utilities utility = new Utilities(request, pw);
        //check if the user is logged in
        if (!utility.isLoggedin()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to View your Orders");
            response.sendRedirect("Login");
            return;
        }
        String username = utility.username();
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");
        pw.print("<form name ='ViewOrder' action='ViewOrder' method='get'>");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Order</a>");
        pw.print("</h2><div class='entry'>");

        /*check if the order button is clicked 
		if order button is not clicked that means the view order page is visited freshly
		then user will get textbox to give order number by which he can view order 
		if order button is clicked user will be directed to this same servlet and user has given order number 
		then this page shows all the order details*/
        if (request.getParameter("Order") == null) {
            pw.print("<table align='center'><tr><td>Enter OrderNo &nbsp&nbsp<input name='orderId' type='text'></td>");
            pw.print("<td><input type='submit' name='Order' value='ViewOrder' class='btnbuy'></td></tr></table>");
        }


        /*if order button is clicked that is user provided a order number to view order 
		order details will be fetched and displayed in  a table 
		Also user will get an button to cancel the order */
        if (request.getParameter("Order") != null && request.getParameter("Order").equals("ViewOrder")) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            pw.print("<input type='hidden' name='orderId' value='" + orderId + "'>");
            
            
            
            ArrayList<OrderItem> items = db.getOrderItems(orderId);
            int size = items.size();

            /*get the order size and check if there exist an order with given order number 
			if there is no order present give a message no order stored with this id */

            // display the orders if there exist order with order id
            if (size > 0) {
                pw.print("<table  class='gridtable'>");
                pw.print("<tr><td></td>");
                pw.print("<td>OrderId:</td>");
                pw.print("<td>UserName:</td>");
                pw.print("<td>productOrdered:</td>");
                pw.print("<td>productPrice:</td></tr>");
                for (OrderItem oi : items) {
                    pw.print("<tr>");
                    pw.print("<td><input type='radio' name='itemId' value='" + oi.getId() + "'>"
                            + "<label for='itemId'>" + oi.getName()+"</label></td>");
                    pw.print("<td>" + orderId + ".</td><td>" + username + "</td><td>" + oi.getName() + "</td><td>Price: " + oi.getPrice() + "</td>");
                    pw.print("<td><input type='submit' name='Order' value='CancelOrder' class='btnbuy'></td>");
                    pw.print("</tr>");

                }
                pw.print("</table>");
            } else {
                pw.print("<h4 style='color:red'>You have not placed any order with this order id</h4>");
            }
        }
        //if the user presses cancel order from order details shown then process to cancel the order
        if (request.getParameter("Order") != null && request.getParameter("Order").equals("CancelOrder")) {
//            String orderName = request.getParameter("orderName");
            int orderId = 0;
            orderId = Integer.parseInt(request.getParameter("orderId"));
            String itemId = request.getParameter("itemId");
            
            db.cancelItems(orderId, itemId);
            pw.print("<h4 style='color:red'>Your Order is Cancelled</h4>");

            
            //remove all the orders from hashmap that exist in cancel list
            if (db.getOrderItems(orderId).size() == 0) {
                db.cancelEntireOrder(username, orderId);
            }

        }
        pw.print("</form></div></div></div>");
        utility.printHtml("Footer.html");
    }

}

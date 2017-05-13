
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author delianaescobari
 */
@WebServlet("/ManageOrders")
public class ManageOrders extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        MySQLDataStoreUtilities db = new MySQLDataStoreUtilities();

        //check if the user is logged in
        if (!utility.isLoggedin() || !utility.usertype().equals("manager")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to manage your customers.");
            response.sendRedirect("Login");
            return;
        }

        if (request.getParameter("RequestOrderUpdate") == null) {
            utility.printHtml("Header.html");
            pw.print("<form name ='ManageOrders' action='ManageOrders' method='post'>");
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");

            pw.print("<a style='font-size: 24px;'>BestDeal Users</a>");
            pw.print("</h2><div class='entry'>");

            ArrayList<OrderPayment> orders = db.getAllOrderItems();

            // LIST ALL CUSTS
            if (orders.size() > 0) {
                pw.print("<table  class='gridtable'>");
                pw.print("<tr><td>OrderId</td>");
                pw.print("<td>Username</td>");
                pw.print("<td>Order Date</td>");
                pw.print("<td></td></tr>");

                for (OrderPayment order : orders) {
                    pw.print("<tr>");
                    pw.print("<td><input type='radio' name='orderId' value='" + order.getOrderId() + "'>"
                            + "<label for='username'>" + order.getOrderId() + "</label></td>");
                    pw.print("<td>" + order.getUserName() +"</td>"
                            + "<input type='hidden' name='user' value='"+order.getUserName()+"'></input>");
//                    pw.print("<input type='hidden' name='orderid' value='" + order.getOrderId() + "'></input>");
                    pw.print("<td>" + order.getOrderDate() + "</td>");
                    pw.print("<td><input type='submit' name='RequestOrderUpdate' value='update' class='btnbuy'></td>");
                    pw.print("</tr>");

                }
                pw.print("</table>");
                pw.print("</form></div></div></div>");

            } else {
                pw.print("did not find anything");
            }

        } else {

            String oid = request.getParameter("orderId");
            String username = request.getParameter("user");
            int orderId;
            try {
                orderId = Integer.parseInt(oid);
            } catch (NumberFormatException pe) {
                orderId = -1;
            }

            //if values === delete then delete item first. 
            if (request.getParameter("RequestOrderUpdate").equals("delete")) {
                String itemId = request.getParameter("itemid");
                db.cancelItems(orderId, itemId);

            }

            ArrayList<OrderItem> orderItems = db.getOrderItems(orderId);

            utility.printHtml("Header.html");
            pw.print("<form name ='ManageOrders' action='ManageOrders' method='post'>");
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");

            pw.print("<a style='font-size: 24px;'>Order " + orderId + "</a>");
            pw.print("</h2><div class='entry'>");

            pw.print("<table  class='gridtable'>");
            pw.print("<tr><td>OrderId</td>");
            pw.print("<td>Username</td>");
            pw.print("<td>Order Date</td>");
            pw.print("<td></td></tr>");

            for (OrderItem order : orderItems) {
                pw.print("<tr>");
                pw.print("<td><input type='radio' name='itemname' value='" + order.getName() + "'>"
                        + "<label for='price'>" + order.getName() + "</label></td>");
                pw.print("<td>" + order.getRetailer() + "</td>");
                pw.print("<td>" + order.getPrice() + "</td>");
                pw.print("<input type='hidden' name='itemid' value='" + order.getId() + "'>");
                pw.print("<input type='hidden' name='user' value='" + username + "'>");
                pw.print("<input type='hidden' name='orderId' value='" + orderId + "'>");
                pw.print("<td><input type='submit' name='RequestOrderUpdate' value='delete' class='btnbuy'></td>");
                pw.print("</tr>");

            }
            pw.print("</table>");
            pw.print("</form></div></div></div>");
        }

        utility.printHtml("Footer.html");

    }

}


import data.MySQLDataStoreUtilities;
import business.Utilities;
import data.User;
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
@WebServlet("/ManageCustomers")
public class ManageCustomers extends HttpServlet {

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

        if (request.getParameter("RequestUpdate") == null) {
            utility.printHtml("Header.html");
            pw.print("<form name ='ManageCustomers' action='ManageCustomers' method='post'>");
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");

            pw.print("<a style='font-size: 24px;'>BestDeal Users</a>");
            pw.print("</h2><div class='entry'>");

            ArrayList<User> users = db.getAllUsers();

            // LIST ALL CUSTS
            if (users.size() > 0) {
                pw.print("<table  class='gridtable'>");
                pw.print("<tr><td>Username</td>");
                pw.print("<td>User type</td>");
                pw.print("<td></td></tr>");

                for (User user : users) {
                    pw.print("<tr>");
                    pw.print("<td><input type='radio' name='custusername' value='" + user.getName() + "'>"
                            + "<label for='username'>" + user.getName() + "</label></td>");
                    pw.print("<td>" + user.getUsertype() + "</td>");
                    pw.print("<td><input type='submit' name='RequestUpdate' value='update' class='btnbuy'></td>");
                    pw.print("</tr>");

                }
                pw.print("</table>");
                pw.print("</form></div></div></div>");

            }

        } else {
            utility.printHtml("Header.html");
            String custUsername = request.getParameter("custusername");

            pw.print("<div class='post' style='float: none; width: 100%'>");
            pw.print("<h2 class='title meta'>Update users</h2>"
                    + "<div class='entry'>"
                    + "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");

            pw.print("<form method='post' action='UpdateCustomers'>");
            pw.print("<table id='bestseller' style='width:100%'><tr><td>"
                    + "<h3>Username</h3></td><td><input type='text' name='updateusername' value='" + custUsername + "' class='input' required></input>"
                    + "</td></tr><tr><td>"
                    + "<h3>Password</h3></td><td><input type='password' name='updateuserpw' value='' class='input'></input>"
                    + "</td></tr><tr><td>"
                    + "<h3>User Type</h3></td><td><select name='usertype' class='input'>"
                    + "<option value='customer'>Customer</option>"
                    + "<option value='retailer'>Store Manager</option>"
                    + "<option value='manager'>Salesman</option></select>"
                    + "</td></tr>");
            pw.print("<input type='hidden' name='user' value='" + custUsername + "'></input>");
            pw.print("<input type='hidden' name='RequestUpdate' value='RequestUpdate'></input>");
            pw.print("<tr><td><input type='submit' class='btndel' name='Update' value='Update'></input>");
            pw.print("<input type='submit' class='btndel' name='Update' value='Delete'></input></td></tr></form></table>");
            pw.print("</div></div></div>");

        }
        utility.printHtml("Footer.html");
    }

}

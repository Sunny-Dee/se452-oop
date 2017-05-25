
import data.MySQLDataStoreUtilities;
import business.Utilities;
import data.User;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet("/UpdateCustomers")
public class UpdateCustomers extends HttpServlet {

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

        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        String oldUsername = request.getParameter("user");

        if (request.getParameter("Update").equals("Update")) {
            
           
//            String usertype = request.getParameter("usertype");
            String password = request.getParameter("updateuserpw");

            User user = db.getUser(oldUsername);
            
            if (user == null){
                pw.print("Could not retrieve user.");
            }

            if (request.getParameter("updateusername") != null) {
                String newusername = request.getParameter("updateusername");
                if (newusername != null)
                    user.setName(newusername);
            }
//
//            if (!usertype.equals("") && usertype != null) {
//                user.setName(usertype);
//            }

            if (password != null && !password.equals("")) {
                user.setPassword(password);
            }
            
            if (user.getName() == null){
                pw.print("<div id='content'>Something went wrong</div>");
                return;
            }
            db.deleteUser(oldUsername);
            db.addUser(user);

            //print order update 
            pw.print("<div id='content'><h4 style='color:red'>User has been updated.</h4>"
                    + "<ul>"
                    + "<li> username: " + user.getName() + "</li>"
                    + "<li> password: " + user.getPassword()  + "</li>"
                    + "<li> usertype: " + user.getUsertype() + "</li>"
                    + "</ul></div>");
        } else if (request.getParameter("Update").equals("Delete")) {

            db.deleteUser(oldUsername);
            pw.print("<div id='content'><h4 style='color:red'>User has been deleted</h4></div>");
        }

        utility.printHtml("Footer.html");

    }
}

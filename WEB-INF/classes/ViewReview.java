
import data.Product;
import data.Review;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
@WebServlet("/ViewReview")

public class ViewReview extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);

        //decide if we want user to log in to see reviews. 
//        if (!utility.isLoggedin()) {
//            HttpSession session = request.getSession(true);
//            session.setAttribute("login_msg", "Please Login to view your inventory");
//            response.sendRedirect("Login");
//            return;
//        }
        String productId = request.getParameter("name");
        String productType = request.getParameter("type");
        Product product = Catalogue.allProductsByType
                .get(productType).get(productId);
        MongoDBDataStoreUtilities mongodb = new MongoDBDataStoreUtilities();
        ArrayList<Review> reviews = mongodb.getReviewsByProduct(productId);

        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Reviews</a>");
        pw.print("</h2><div class='entry'>");
        pw.print("<table class='gridtable'>");
        pw.print("<tr>");
        pw.print("<td> Product Name: </td>");
        pw.print("<td>" + product.getName() + "</td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<td> Product Type: </td>");
        pw.print("<td>" + productType + "</td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<td> Product Maker: </td>");
        pw.print("<td>" + product.getRetailer() + "</td>");
        pw.print("</tr>");
        pw.print("</table>");
        pw.print("</h2></div>");

        if (reviews.isEmpty()) {
            pw.print("<div class='entry'><h3>No reviews for this product.</h3></div>");
        } else {
            for (Review r : reviews) {

                pw.print("</h2><div class='entry'>");
                pw.print("<table class='gridtable'>");
                pw.print("<tr>");
                pw.print("<td> Reviewer: </td>");
                pw.print("<td>" + r.getUsername() + "</td>");
                pw.print("</tr>");
                
                pw.print("<tr>");
                pw.print("<td> Rating: </td>");
                pw.print("<td>" + r.getRating() + "</td>");
                pw.print("</tr>");
                
                pw.print("<tr>");
                pw.print("<td> Review date: </td>");
                pw.print("<td>" + r.getReviewDate() + "</td>");
                pw.print("</tr>");
                
                
                pw.print("<tr>");
                pw.print("<td> Review: </td>");
                pw.print("<td>" + r.getReviewText() + "</td>");
                pw.print("</tr>");
                
                pw.print("</table>");
                pw.print("</h2></div>");
            }
        }
        pw.print("</div></div><div></div>");
        utility.printHtml("Footer.html");
    }

}


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

        //check if the user is logged in
        if (!utility.isLoggedin()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to view your inventory");
            response.sendRedirect("Login");
            return;
        }
        
        String productId = request.getParameter("name");
        MongoDBDataStoreUtilities mongodb = new MongoDBDataStoreUtilities();
        
        ArrayList<Review>reviews = mongodb.getReviewsByProduct(productId);
            for(Review r : reviews){
                pw.print("<p>" + r.getReviewText()+ "</p>");
            }
        
    }
    
}

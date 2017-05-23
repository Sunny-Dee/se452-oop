
import data.Product;
import data.Review;
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
@WebServlet("/WriteReview")
public class WriteReview extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        MongoDBDataStoreUtilities db = new MongoDBDataStoreUtilities();
        Utilities utility = new Utilities(request, pw);

        //check if the user is logged in
        if (!utility.isLoggedin()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to view your inventory");
            response.sendRedirect("Login");
            return;
        }

        String prodId = request.getParameter("name");
        String prodType = request.getParameter("type");
        String maker = request.getParameter("maker");

        Product product = SaxParserDataStore.allProducts.get(prodType).get(prodId);

        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Review</a>");
        pw.print("</h2><div class='entry'>");
        pw.print("<table class='gridtable'>");
        pw.print("<tr>");
        pw.print("<td> Product Name: </td>");
        pw.print("<td>" + product.getName() + "</td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<td> Product Type: </td>");
        pw.print("<td>" + prodType + "</td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<td> Product Maker: </td>");
        pw.print("<td>" + product.getRetailer() + "</td>");
        pw.print("</tr>");
        pw.print("</table>");
        pw.print("</h2></div>");

        if (request.getParameter("SubmitReview") == null) {

            pw.print("<div class='entry'>");
            pw.print("<form method='post' action='WriteReview'>");
            pw.print("<table style='width:80%'><tr><td>");
            pw.print("<h3>Your rating: </h3></td><td><select name='rating' class='input'>"
                    + "<option value=0 selected>0</option>"
                    + "<option value=1>1</option>"
                    + "<option value=1>2</option>"
                    + "<option value=2>3</option>"
                    + "<option value=3>4</option>"
                    + "<option value=4>5</option></select></td></tr>");
            pw.print("<input type='hidden' name='name' value='" + prodId + "'>"
                    + "<input type='hidden' name='type' value='" + prodType + "'>"
                    + "<input type='hidden' name='maker' value='" + maker + "'>");

            pw.print("<tr><td><h3>Review date: </h3></td><td>"
                    + " <input type='date' name='reviewdate'></td></tr>");

            pw.print("<tr><td><h3>Your zip-code: </h3></td><td>"
                    + " <input type='text' name='zipcode' value='60600' class='input' required></input></td></tr>");

            pw.print("<tr><td><h3>Your review: </h3></td><td><input type='text' name='review' value='' class='input' required></input></td></tr>");
            pw.print("<tr><td><input type='submit' name='SubmitReview' class='btnbuy' value='Submit' style='float: right;height: "
                    + "20px margin: 20px; margin-right: 10px;'></input>"
                    + "</td></tr>"
                    + "</table>"
                    + "</form></div>");

        } else {

            String productType = request.getParameter("type");
            String productId = request.getParameter("name");
            String rating = request.getParameter("rating");
            String reviewDate = request.getParameter("reviewdate");
            String reviewText = request.getParameter("review");
            String zipcode = request.getParameter("zipcode");

            boolean result = db.submitOtUpdateReview(productId, productType,
                    rating, reviewDate, reviewText, utility.username(), zipcode);

            if (result) {
                pw.print("<div class='entry'><h3>Your review has been submitted</h3>"
                        + "</div>");
            } else {
                pw.print("<div class='entry'><h3>MongoDB is not available"
                        + " right now. Please try again later.</h3></div>");
            }

        }

        //Close content div
        pw.print("</div></div><div></div>");
        utility.printHtml("Footer.html");
    }

}

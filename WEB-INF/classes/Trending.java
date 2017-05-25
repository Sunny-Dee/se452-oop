
import data.PopProduct;
import data.Product;
import data.Review;
import data.ReviewProduct;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Trending")

public class Trending extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        MongoDBDataStoreUtilities db = new MongoDBDataStoreUtilities();
        MySQLDataStoreUtilities sqlDb = new MySQLDataStoreUtilities();
        DecimalFormat df = new DecimalFormat("#.0");

//        TreeMap<Double, Product> topProducts = db.getTopFiveProducts();
//        int numProds = topProducts.size();
        ArrayList<ReviewProduct> reviews = db.getTopFiveProducts();
        ArrayList<PopProduct> popularProducts = sqlDb.getBestSellers();
        ArrayList<String> popularZipcodes = sqlDb.getPopularZipcodes();

        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Highest Rated Products</a></h2>");

        int index = 0;
        while (index < reviews.size() && index < 5) {
            ReviewProduct review = reviews.get(index);
            Product product = SaxParserDataStore.allProducts.get(review.getProductType())
                    .get(review.getId());
            pw.print("<div class='entry'>");
            pw.print("<table class='gridtable'>");

            pw.print("<tr>");
            pw.print("<td colspan='2'><img class='thumb' src='images/" + review.getProductType()
                    + "/" + product.getImage() + "' alt='' height=150 width=150/></td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Product Name: </td>");
            pw.print("<td>" + product.getName() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Product Maker: </td>");
            pw.print("<td>" + product.getRetailer() + "</td>");
            pw.print("</tr>");
            pw.print("<tr>");
            pw.print("<td> Average Rating: </td>");
            pw.print("<td>" + df.format(review.getAverageRating()) + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Total Reviews: </td>");
            pw.print("<td>" + review.getTotalReviews() + "</td>");
            pw.print("</tr>");
            pw.print("</table>");
            pw.print("</div>");
            index++;
        }
        pw.print("</div>");

        pw.print("<div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Best Sellers</a></h2>");
        for (PopProduct p : popularProducts) {
            Product product = SaxParserDataStore.allProducts.get(p.getProductType())
                    .get(p.getId());

            pw.print("<div class='entry'>");
            pw.print("<table class='gridtable'>");

            pw.print("<tr>");
            pw.print("<td colspan='2'><img class='thumb' src='images/" + p.getProductType()
                    + "/" + product.getImage() + "' alt='' height=150 width=150/></td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Product Name: </td>");
            pw.print("<td>" + product.getName() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Product Maker: </td>");
            pw.print("<td>" + product.getRetailer() + "</td>");
            pw.print("</tr>");
            pw.print("<tr>");
            pw.print("<td> Units Sold: </td>");
            pw.print("<td>" + p.getNumTimesBought() + "</td>");
            pw.print("</tr>");

            pw.print("</table>");
            pw.print("</div>");
        }
        pw.print("</div>");

        //Important zipcodes
        pw.print("<div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Top 5 zipcodes</a></h2>");

        pw.print("<div class='entry'>");
        pw.print("<table class='gridtable'>");
        int rank = 1;
        for (String zip : popularZipcodes) {
            pw.print("<tr>");
            pw.print("<td> Zipcode: " + rank++ + "</td>");
            pw.print("<td>" + zip + "</td>");
            pw.print("</tr>");
        }
        pw.print("</table>");
        pw.print("</div>");
        pw.print("</div>");

        pw.print("</div>");
        utility.printHtml("Footer.html");
    }
}

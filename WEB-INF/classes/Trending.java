
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
        DecimalFormat df = new DecimalFormat("#.0");

//        TreeMap<Double, Product> topProducts = db.getTopFiveProducts();
//        int numProds = topProducts.size();
        ArrayList<ReviewProduct> reviews = db.getTopFiveProducts();

        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Trending Products</a></h2>");

        int index = 0;
        while (index < reviews.size() && index < 5) {
            ReviewProduct review = reviews.get(index);
            Product product = SaxParserDataStore.allProducts.get(review.getProductType())
                    .get(review.getId());
            pw.print("<div class='entry'>");
            pw.print("<table class='gridtable'>");

            pw.print("<tr>");
            pw.print("<td><img class='thumb' src='images/" + review.getProductType()
                    + "/" + product.getImage() + "' alt='' height=120 width=120/></td>");
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

//        for (Map.Entry<String, ArrayList<Review>> entry : reviews.entrySet()){
//            for (Review r : entry.getValue()){
//                pw.print("<tr><td>Name: </td><td>"+r.getProductName()+"</td><tr>");
//                pw.print("<tr><td>Rating: </td><td>"+r.getRating()+"</td></tr>");
//            }
//        }
        pw.print("</div></div>");
        utility.printHtml("Footer.html");
    }
}

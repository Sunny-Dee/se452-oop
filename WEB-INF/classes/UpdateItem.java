
import data.Product;
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

/**
 *
 * @author delianaescobari
 */
@WebServlet("/UpdateItem")
public class UpdateItem extends HttpServlet {
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);

        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");
        
        //get attributes;
        String itemId = request.getParameter("name");
        String productType = request.getParameter("prodtype");
        
        //get product
        Product prod = SaxParserDataStore
                .allProducts.get(productType).get(itemId);
        
        if (request.getParameter("Updt") != null){
                        
            //update product
            String name = request.getParameter("updateName");
            String price = request.getParameter("updatePrice");
            String discount = request.getParameter("updateDiscount");
            String condition = request.getParameter("updateCondition");
            
            if (name != null && !name.equals("")) prod.setName(name);
            if (condition != null && !condition.equals("")) prod.setCondition(condition);
            
            try {
                if(price != null && !price.equals("")){
                    double priceD = Double.parseDouble(price);
                    if(priceD > 0) prod.setPrice(priceD);
                }
                
                if (discount != null && !discount.equals("")){
                    double discD = Double.parseDouble(discount);
                    if(discD > 0) prod.setDiscount(discD);
                }
                
            } catch (NumberFormatException ne){
                pw.print("<h4 style='color:red'>Price and discount values must be valid numbers: discount: "
                         +discount +  " price: " +price + "</h4>");
            }
            
        }
        
        //Make a form
       pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
       pw.print("<a style='font-size: 24px;'>Update product</a></h2>");
       pw.print("<div class='entry'><table id='bestseller'>");
       pw.print("<tr><td><div id='update_item'>");
       pw.print("<h3>"+prod.getName()+"</h3>");
       pw.print("<strong>"+ "$" + prod.getPrice() + "</strong><ul>");
       pw.print("<li id='item'><img src='images/"+ productType +"/"+prod.getImage()
               +"' alt='' /></li>");
       pw.print("<li><form method='post' action='UpdateItem'>");
       pw.print("<label for='updateName'>Update name:</label><input type='text' name='updateName'><br>");
       pw.print("<label for='updatePrice'>Update price:</label><input type='number' step='0.01' min='0' name='updatePrice'><br>");
       pw.print("<label for='updateDiscount'>Update discount:</label><input type='number' step='0.01' min='0' name='updateDiscount'><br>");
       pw.print("<label for='updateCondition'>Update condition:</label><input type='text' name='updateCondition'>");
       pw.print("<input type='hidden' name='name' value='" + itemId + "'>");
       pw.print("<input type='hidden' name='prodtype' value='" + productType + "'>");
       pw.print("<input type='submit' class='btndel' name='Updt' value='Update Item'></form>");
       pw.print("<br></li></ul></div></td></tr></table></div></div></div>");
      
       utility.printHtml("Footer.html");
    }
}

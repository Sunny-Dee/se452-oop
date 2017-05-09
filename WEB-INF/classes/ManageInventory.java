
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
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
//Store Manager can Add/Delete/Update products
@WebServlet("/ManageInventory")
public class ManageInventory extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        Utilities utility = new Utilities(request, pw);
        String productType = request.getParameter("prodtype");

        //check if the user is logged in
        if (!utility.isLoggedin() || !utility.usertype().equals("retailer")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to View your Orders");
            response.sendRedirect("Login");
            return;
        }

        String username = utility.username();
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        if (productType == null) {
            pw.print("<form name ='ManageInventory' action='ManageInventory' method='get'>");
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
            pw.print("<a style='font-size: 24px;'>Select a product type to manage</a>"
                    + "<table style='width:100%'><tr><td>"
                    + "<h3>Product Type</h3></td><td><select name='prodtype' class='input'>"
                    + "<option value='tv'>TV</option>"
                    + "<option value='tablet'>Tablets</option>"
                    + "<option value='smartphone'>Smartphones</option>"
                    + "<option value='laptop'>Laptops</option></select>"
                    + "</td></tr></table>"
                    + "<input type='submit' class='btnbuy' value='Show' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>"
                    + "</form>" + "</div></div>");
        } else {
            HashMap<String, Product> products = SaxParserDataStore.allProducts.get(productType);
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
            pw.print("<a style='font-size: 24px;'>"+ productType +"</a>");
            pw.print("<div class='entry'><table id='bestseller'>");
            int i = 1;
            int size = products.size();
            for (Map.Entry<String, Product> entry : products.entrySet()) {
                Product product = entry.getValue();
                if (i % 3 == 1) {
                    pw.print("<tr>");
                }
                pw.print("<td><div id='shop_item'>");
                pw.print("<h3>" + product.getName() + "</h3>");
                pw.print("<strong>" + "$" + product.getPrice() + "</strong><ul>");
                pw.print("<li id='item'><img src='images/" + productType + "/" + product.getImage() + "' alt='' /></li>");
                pw.print("<li><form method='post' action='DeleteItem'>"
                        + "<input type='hidden' name='name' value='" + entry.getKey() + "'>"
                        + "<input type='hidden' name='type' value='" + productType + "'>"
                        + "<input type='hidden' name='maker' value='" + product.getRetailer() + "'>"
                        + "<input type='hidden' name='access' value='retailer'>"
                        + "<input type='submit' class='btndel' value='Delete'></form></li>");
                pw.print("</form></li></ul></div></td>");
                if (i % 3 == 0 || i == size) {
                    pw.print("</tr>");
                }
                i++;
            }
            pw.print("</table></div></div></div>");		

            //TODO add form to add a product.
            
            //Todo add for to edit a product.
            
           
        }
        
         utility.printHtml("Footer.html");

    }

    public String displayAllProducts() {
        return "";
    }

    public void deleteProduct() {

    }
}

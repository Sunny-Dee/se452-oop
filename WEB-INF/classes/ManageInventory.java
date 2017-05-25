
import data.Catalogue;
import business.Utilities;
import data.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
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
            HttpServletResponse response) throws ServletException, IOException{
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        Utilities utility = new Utilities(request, pw);
        String productType = request.getParameter("prodtype");

        //check if the user is logged in
        if (!utility.isLoggedin() || !utility.usertype().equals("retailer")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to view your inventory");
            response.sendRedirect("Login");
            return;
        }

        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");
        

        if (productType == null) {
            pw.print("<form name ='ManageInventory' action='ManageInventory' method='get'>");
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
            pw.print("<a style='font-size: 24px;'>Select a product type to manage</a></h2>"
                    + "<table style='width:100%'><tr><td>"
                    + "<h3>Product Type</h3></td><td><select name='prodtype' class='input'>"
                            + "<option value='tv'>TV</option>"
                            + "<option value='tablet'>Tablets</option>"
                            + "<option value='smartphone'>Smartphones</option>"
                            + "<option value='laptop'>Laptops</option></select>"
                    + "</td></tr>"
                    + "<tr><input type='submit' class='btnbuy' value='Show'></input></tr>"
                    + "</table></form></div></div>");
        } else {
            
            //get parameter to see if they deleted something, if yes, delete from map
            //session set attribute deleting, adding updating. 
            if (request.getParameter("Delete") != null ){
                String prodId = request.getParameter("name");
                Catalogue.allProductsByType.get(productType).remove(prodId);
            } else if (request.getParameter("Update") != null){
                //Redirect to update item with params. 
//                HttpSession session = request.getSession(true);
//                session.setAttribute("productId", request.getParameter("name"));
//                session.setAttribute("productype", productType);

                RequestDispatcher rd = request.getRequestDispatcher("UpdateItem");
                rd.forward(request, response);
            }
            
            HashMap<String, Product> products = Catalogue.allProductsByType.get(productType);
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
            pw.print("<a style='font-size: 24px;'>"+ productType +" inventory</a></h2>");
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
                pw.print("<li><form method='post' action='ManageInventory'>"
                        + "<input type='hidden' name='name' value='" + entry.getKey() + "'>"
                        + "<input type='hidden' name='prodtype' value='" + productType + "'>"
                        + "<input type='hidden' name='maker' value='" + product.getRetailer() + "'>"
                        + "<input type='hidden' name='access' value='retailer'>"
                        + "<input type='submit' class='btndel' name='Delete' value='Delete'>"
                        + "<input type='submit' class='btndel' name='Update' value='Update'></form></li>");
                pw.print("</ul></div></td>");
                if (i % 3 == 0 || i == size) {
                    pw.print("</tr>");
                }
                i++;
            }
            pw.print("</table></div></div>"); 
            pw.print("</div>");		            
        
        }
        
         utility.printHtml("Footer.html");

    }

}

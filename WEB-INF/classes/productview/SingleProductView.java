/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productview;

import data.Catalogue;
import data.Product;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author delianaescobari
 */

@WebServlet("/SingleProductView")
public class SingleProductView extends HttpServlet {
    protected void doGet(HttpServletRequest request, 
                HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
        String productId = request.getParameter("productId");
        String pId2 = (String) request.getSession(true).getAttribute("productId");
        //TO test just test both of these attributes to the screen
        
        pw.print("<p>product id from request param" + productId +"</p>");
        pw.print("<p>id from session id" + pId2 +"</p>");
        
//        Product product = Catalogue.allProducts.get(productId);
                
        //Display product:
    }
}

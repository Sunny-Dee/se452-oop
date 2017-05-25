
import data.Catalogue;
import data.MySQLDataStoreUtilities;
import data.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author delianaescobari
 */
@WebServlet("/Test")
public class Test extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        MySQLDataStoreUtilities db = new MySQLDataStoreUtilities();
        
        String[] productTypes = {"smartphone", "tablet", "tv", "laptop"};

//        HashMap<String, HashMap<String, Product>> products = db.getAllProductsByType(Arrays.asList(productTypes));
        HashMap<String, Product> tvs = Catalogue.allProducts;
        
        for (Map.Entry entry : tvs.entrySet()) {
            Product p = (Product) entry.getValue();
            pw.print("<p><ul>");
            pw.print("<li>" + p.getName() + "</li>");
            pw.print("<li>" + p.getProductType() + "</li>");
            pw.print("</ul></p>");
        }

    }

}

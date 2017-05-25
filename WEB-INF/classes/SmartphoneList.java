
import data.Catalogue;
import productview.ProductList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author delianaescobari
 */

@WebServlet("/SmartphoneList")

public class SmartphoneList extends ProductList {
    
    public SmartphoneList(){
        super("smartphone", "Smartphones", Catalogue.allProductsByType.get("smartphone"));
    }
    
}


import data.Product;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/Startup")

/*  
startup servlet Intializes HashMap in SaxParserDataStore
 */
public class Startup extends HttpServlet {

    public void init() throws ServletException {
        SaxParserDataStore.addHashmap();

        // LOAD MYSQL HASHMAP HERE 
        MySQLDataStoreUtilities db = new MySQLDataStoreUtilities();
        String[] productTypes = {"smartphone", "tablet", "tv", "laptop"};
        //Arrays.asList
        new Catalogue().allProductsByType = db.getAllProductsByType(Arrays.asList(productTypes));
        Catalogue.allProducts = db.getAllProducts();
    }
}

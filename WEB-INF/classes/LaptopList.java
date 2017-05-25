
import javax.servlet.annotation.WebServlet;


/**
 *
 * @author delianaescobari
 */

@WebServlet("/LaptopList")
public class LaptopList extends ProductList{
    
    public LaptopList(){
        super("laptop", "Laptops", Catalogue.allProductsByType.get("laptop"));

    }
}

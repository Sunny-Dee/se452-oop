
import javax.servlet.annotation.WebServlet;



/**
 *
 * @author delianaescobari
 */

@WebServlet("/TvList")


public class TvList extends ProductList{
    public TvList(){
        super("tv", "TV's", Catalogue.allProductsByType.get("tv"));
    }
    
}


import java.util.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Console")


/* 
	Console class contains class variables name,price,image,retailer,condition,discount and Accessories Hashmap.

	Console class constructor with Arguments name,price,image,retailer,condition,discount and Accessories .
	  
	Accessory class contains getters and setters for name,price,image,retailer,condition,discount and Accessories .

 */
public class Console extends Product {

    HashMap<String, String> accessories;

    public Console(String name, double price, String image, String retailer, String condition, double discount) {
        super(name, price, image, retailer, condition, discount);
        this.accessories = new HashMap<>();
    }

    public Console() {

    }

    HashMap<String, String> getAccessories() {
        return accessories;
    }

    public void setAccessories(HashMap<String, String> accessories) {
        this.accessories = accessories;
    }

   

}




import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author delianaescobari
 */

@WebServlet("/Product")

public class Product extends HttpServlet {

//    private final String productType;
    private String id;
    private String name; 
    private double price; 
    private String image; 
    private String retailer; 
    private String condition;
    private double discount;

    public Product(String name, double price, String image, String retailer, String condition, double discount) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.condition = condition;
        this.discount = discount;
        this.retailer = retailer;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}

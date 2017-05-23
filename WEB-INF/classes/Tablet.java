import data.Product;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Tablet")

/* 
	Tablet class contains class variables name,price,image,retailer,condition,discount.

	Tablet class has a constructor with Arguments name,price,image,retailer,condition,discount.
	  
	Tablet class contains getters and setters for name,price,image,retailer,condition,discount.
*/

public class Tablet extends Product{

	public Tablet(String name, double price, String image, String retailer, String condition,double discount){
		super(name, price, image, retailer, condition, discount);
	}
	
	public Tablet(){
		
	}
	
}

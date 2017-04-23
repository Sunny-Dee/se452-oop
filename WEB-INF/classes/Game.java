import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Game")

/* 
	Game class contains class variables name,price,image,retailer,condition,discount.

	Game class has a constructor with Arguments name,price,image,retailer,condition,discount.
	  
	Game class contains getters and setters for name,price,image,retailer,condition,discount.

*/

public class Game extends Product{
	
	public Game(String id,String name, double price, String image, String retailer,String condition,double discount){
		super(name, price, image, retailer, condition, discount);
	}
	
	public Game(){
		super();
	}

	
}

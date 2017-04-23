
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ConsoleList")

public class ConsoleList extends ProductList {

    /* Console Page Displays all the Consoles and their Information in Game Speed */
    
    
    public ConsoleList(){
        super("console", "Consoles", SaxParserDataStore.allProducts.get("console"));
    }
 
}


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GamesList")

public class GamesList extends ProductList {
    
    public GamesList(){
        super("game", "Games", SaxParserDataStore.allProducts.get("game")); //SaxParserDataStore.games);
    }
    
    private static final long serialVersionUID = 1L;

}

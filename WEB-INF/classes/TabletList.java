


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TabletList")

public class TabletList extends ProductList {

	/* Trending Page Displays all the Tablets and their Information in Game Speed */
    public TabletList(){
        super("tablet", "Tablets", Catalogue.allProductsByType.get("tablet"));
    }
}

package business;

import data.Catalogue;
import data.Product;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/autocomplete")

public class AutoCompleteServlet extends HttpServlet {

    private ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        String searchId = request.getParameter("searchId");
        PrintWriter pw = response.getWriter();
        StringBuffer sb = new StringBuffer();

        if (searchId != null) {
            searchId = searchId.toLowerCase();
        } else {
            context.getRequestDispatcher("/error.jsp");
        }

        boolean namesAdded = false;
        if (action.equals("complete")) {
            if (!searchId.equals("")) {
                AjaxUtility a = new AjaxUtility();
                sb = a.readdata(searchId);

                if (sb != null || !sb.equals("")) {
                    namesAdded = true;
                }
                if (namesAdded) {
                    response.setContentType("text/xml");
                    pw.write("<products>" + sb.toString() + "</products>");
                }
            } else {
                pw.write("<products><product><productName>No matches</productName></product></products>");
            }

        } else if (action.equals("lookup")) {
//            HttpSession session = request.getSession(true);
//            session.setAttribute("productId", searchId);
//            response.sendRedirect("SingleProductView");
            searchId = request.getParameter("searchId");
            displayProduct(request, pw, searchId);
        }
    }

    public void displayProduct(HttpServletRequest request, PrintWriter pw, String productId) {
        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        Product product = Catalogue.allProducts.get(productId);
                pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>" + product.getRetailer() + " " + product.getProductType() + "</a>");
        pw.print("</h2><div class='entry'><table id='bestseller'>");
        pw.print("<td><div id='shop_item'>");
        pw.print("<h3>" + product.getName() + "</h3>");
        pw.print("<strong>" + "$" + product.getPrice() + "</strong><ul>");
        pw.print("<li id='item'><img src='images/" + product.getProductType() + "/" + product.getImage() + "' alt='' height='200' width='200'/></li>");
        pw.print("<li><form method='post' action='Cart'>"
                + "<input type='hidden' name='name' value='" + product.getId() + "'>"
                + "<input type='hidden' name='type' value='" + product.getProductType() + "'>"
                + "<input type='hidden' name='maker' value='" + product.getRetailer() + "'>"
                + "<input type='hidden' name='access' value=''>"
                + "<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
        pw.print("<li><form method='post' action='WriteReview'>" + "<input type='hidden' name='name' value='" + product.getId() + "'>"
                + "<input type='hidden' name='type' value='" + product.getProductType() + "'>"
                + "<input type='hidden' name='maker' value='" + product.getRetailer() + "'>"
                + "<input type='hidden' name='access' value=''>"
                + "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
        pw.print("<li><form method='post' action='ViewReview'>" + "<input type='hidden' name='name' value='" + product.getId() + "'>"
                + "<input type='hidden' name='type' value='" + product.getProductType() + "'>"
                + "<input type='hidden' name='maker' value='" + product.getRetailer() + "'>"
                + "<input type='hidden' name='access' value=''>"
                + "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
        pw.print("</ul></div></td>");
        pw.print("</tr>");
        pw.print("</table></div></div></div>");
        utility.printHtml("Footer.html");

    }

}

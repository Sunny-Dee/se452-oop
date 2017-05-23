
import data.OrderPayment;
import data.Product;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Utilities")

/*
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.

 */
public class Utilities extends HttpServlet {

    HttpServletRequest req;
    PrintWriter pw;
    String url;
    HttpSession session;
    MySQLDataStoreUtilities db;

    public Utilities(HttpServletRequest req, PrintWriter pw) {
        this.req = req;
        this.pw = pw;
        this.url = this.getFullURL();
        this.session = req.getSession(true);
        db = new MySQLDataStoreUtilities();
    }

    /*  Printhtml Function gets the html file name as function Argument,
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/
    public void printHtml(String file) {
        String result = HtmlToString(file);
        //to print the right navigation in header of username cart and logout etc
        if (file == "Header.html") {
            result = result + "<div id='menu' style='float: right;'><ul>";
            if (session.getAttribute("username") != null) {
                String username = session.getAttribute("username").toString();
                String usertype = session.getAttribute("usertype").toString();
                username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
                result = result + "<li><a href='ViewOrder'>ViewOrder</a></li>"
                        + "<li><a>Hello, " + username + "</a></li>"
                        + "<li><a href='Account'>Account</a></li>"
                        + "<li><a href='Logout'>Logout</a></li>";

                if (usertype.equals("retailer")) {  //Store Manager
                    //Store Manager can Add/Delete/Update products
                    result = result + "<li><a href='ManageInventory'>Manage Inventory</a></li>";
                } else if (usertype.equals("manager")) {
                    // Manager can  can create Customer accounts and can Add/Delete/Update customers’ orders
                    result = result
                            + "<li><a href='ManageCustomers'>Manage Users</a></li>"
                            + "<li><a href='ManageOrders'>Manage Orders</a></li>";
                }

            } else {
                result = result + "<li><a href='ViewOrder'>View Order</a></li>" + "<li><a href='Login'>Login</a></li>";
            }
            result = result + "<li><a href='Cart'>Cart (" + CartCount() + ")</a></li></ul></div></div><div id='page'>";
            pw.print(result);
        } else {
            pw.print(result);
        }
    }


    /*  getFullURL Function - Reconstructs the URL user request  */
    public String getFullURL() {
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getContextPath();
        StringBuffer url = new StringBuffer();
        url.append(scheme).append("://").append(serverName);

        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        url.append("/");
        return url.toString();
    }

    /*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
    public String HtmlToString(String file) {
        String result = null;
        try {
            String webPage = url + file;
            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            result = sb.toString();
        } catch (Exception e) {
        }
        return result;
    }

    /*  logout Function removes the username , usertype attributes from the session variable*/
    public void logout() {
        session.removeAttribute("username");
        session.removeAttribute("usertype");
    }

    public String formatDollars(double total) {
        return "$" + String.format("%1$,.2f", total);
    }

    /*  logout Function checks whether the user is loggedIn or Not*/
    public boolean isLoggedin() {
        if (session.getAttribute("username") == null) {
            return false;
        }
        return true;
    }

    /*  username Function returns the username from the session variable.*/
    public String username() {
        if (session.getAttribute("username") != null) {
            return session.getAttribute("username").toString();
        }
        return null;
    }
    

    /*  usertype Function returns the usertype from the session variable.*/
    public String usertype() {
        if (session.getAttribute("usertype") != null) {
            return session.getAttribute("usertype").toString();
        }
        return null;
    }

    /*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
    public User getUser() {
        User user = db.getUser(username());
        return user;
    }

    public ArrayList<OrderItem> getCartItems() {
        return db.getItemsFromCart(username());
    }

    
    public int storeAndGetOrderId(){
        return db.storeCustomerOrder(username());
    }

    /*  CartCount Function gets  the size of User Orders*/
    public int CartCount() {
        if (isLoggedin()) {
            return getCartItems().size();
        }
        return 0;
    }

    /* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/
    public void storeProduct(String itemId, String type, String maker) {

        db.saveItemToCart(username(), itemId, type, maker);

    }

    // store the payment details for orders
    public void storePayment(int orderId, String userAddress, String creditCardNo, String zipcode) {
        
        OrderPayment orderpayment = new OrderPayment(orderId, username(), 
                userAddress, creditCardNo, zipcode);
        db.storePayment(orderpayment);
        
    }
    
    public void deleteItem(String itemId, String itemtype){
        if (usertype().equals("retailer")){
            HashMap<String, Product> map = SaxParserDataStore.allProducts.get(itemtype);
            map.remove(itemId);
        }
    }
}

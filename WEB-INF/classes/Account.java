
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

@WebServlet("/Account")

public class Account extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayAccount(request, response);
	}

	/* Display Account Details of the Customer (Name and Usertype) */

	protected void displayAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		try
         {  
           response.setContentType("text/html");
			if(!utility.isLoggedin())
			{
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Please Login to add items to cart");
				response.sendRedirect("Login");
				return;
			}
			HttpSession session=request.getSession(); 	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Account</a>");
			pw.print("</h2><div class='entry'>");
			User user=utility.getUser();
			pw.print("<table class='gridtable'>");
			pw.print("<tr>");
			pw.print("<td> User Name: </td>");
			pw.print("<td>" +user.getName()+ "</td>");
			pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<td> User Type: </td>");
			pw.print("<td>" +user.getUsertype()+ "</td>");
			pw.print("</tr>");
			pw.print("</table>");		
			pw.print("</h2></div></div></div>");		
			utility.printHtml("Footer.html");	        	
		}
		catch(Exception e)
		{
		}		
	}
}

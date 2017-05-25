package business;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        
        if (searchId != null){
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
                } else {
                   pw.write("<products><product>Just a test</product></products>");

                }
            } else {
                pw.write("<products><product>Just a test</product></products>");
            }
            
        } else if (action.equals("lookup")){
            pw.write("<products>looking for this</products >");
        }
    }

}

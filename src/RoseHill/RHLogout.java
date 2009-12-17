package RoseHill;
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import RoseHill.Deed;

/**
 * Logs out the user from the system and destroys the session instance.
 * @author Walid Sibo
 * @version %I%,%G%
 */
public class RHLogout extends HttpServlet{

    PrintWriter os;
    String url = Deed.url;
    /**
     * Logs the user out.
     * @param req the request input stream
     * @param res the ouput stream
     * @throws    ServletException
     * @throws    IOException
     */
    public void doGet(HttpServletRequest req, 
		    HttpServletResponse res) 
	throws ServletException, IOException{

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	Enumeration values = req.getParameterNames();
	String name= "";
	String value = "";
	String username = "";
	String unicID = "";
	boolean testFlagOnPC = true;
	// WS
	// String url = "http://showers/servlets/timesheet";
	//
	url += "RHUserLogin";
    
	while (values.hasMoreElements()) {
	    name = ((String)values.nextElement()).trim();
	    value = doubleApostrify((req.getParameter(name)).trim());
	    if (name.equals("username"))
		username = value;
	    if (name.equals("unicID"))
		unicID = value;
	}	
	HttpSession session = null;
	session = req.getSession();
	session.invalidate();
	/*
	try {
	    File df;
	    df = new File(unicID);
	    df.delete();

	} catch (Exception ex){
	    out.println(ex.toString());
	}
	*/
	out.println("<HTML><HEAD><TITLE>RoseHill Log out</TITLE>");
	out.println("<body><center>You have successfully logged out."+
		    "<p><a href="+ url + "> "+
		    "Login again</a></center></body></html>");
	out.close();

    }
    /**
     * Adds an extra apostrophy to each occurrence of an apostrophy in the
     * input string.
     * @param s the input string
     * @return String the modified string
     */
  final String doubleApostrify(String s){

      StringBuffer apostrophe_safe = new StringBuffer(s);
      int len = s.length();
      int c = 0;
      while (c < len) {
	  if (apostrophe_safe.charAt(c) == '\''){
	      apostrophe_safe.insert(c, '\'');
	      c += 2;
	      len = apostrophe_safe.length();
	  }
	  else{
	      c++;
	  }
      }
      return apostrophe_safe.toString();
  }

}























































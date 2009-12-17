package RoseHill;
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import RoseHill.Deed;
/**
 * The main selection menu of this application.
 * @author Walid Sibo
 * @version %I%,%G%
 */

public class RHUserMenu extends HttpServlet{

    /**
     * Interface to RoseHill data base.
     * @author Walid Sibo
     * Date: Sept. 2001
     */
    String WEBserver = "";
    String url = Deed.url;
    Connection con;
    Statement stmt;
    ResultSet rs;
    PrintWriter os;
    String unicID = "";
    String username = "";
    /**
     * Presents the menu selection for the user.
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
	String name, value;
	while (values.hasMoreElements()){
	    name = ((String)values.nextElement()).trim();
	    value = (req.getParameter(name)).trim();
	    if(name.equals("username")){
		username = value;
	    }
	    if(name.equals("unicID")){
		unicID = value;
	    }
	}
	//
	// check for the user
	//
	if(!Deed.checkUserLogin(out, username, req, "User Menu", url)) return;
	out.println("<HTML><HEAD><TITLE>Rose Hill User Menu </TITLE>");
	out.println("</HEAD><BODY>");
	out.println("<FORM NAME=menuForm >");
	out.println("<center><h1> Rose Hill User Menu </h1>");
	out.println("<center><h3> Select one of the following options,</h3>");
	out.println("<HR><H2><FONT color=#000099> Interments</FONT></H2>");
	out.println("<Center><table border=0><tr><td>");
	out.println("<ul><li>");
	out.println("To add a new interment record:</ul></td><td>");
	out.println("<INPUT type=button value=\"Add Interment\" onClick="+
		    "\"document.location='"+url+
		    "Rose?username="+
		    username+"'\";></input>");
	out.println("</td></tr><tr><td>");
	out.println("<ul><li>");
	out.println("To query, browse, or edit interment record(s):</ul></td><td>");
	out.println("<INPUT type=button value=\"Query Interment\" "+
		    "onClick=\"document.location='"+url+
		    "RoseBrowse?username="+ username +"'\";></input>");
	out.println("</td></tr>");
	
	out.println("</table></center>");
	
	out.println("<HR><H2><FONT color=#000099>Deeds</FONT></H2>");;
	out.println("<Center><table border=0><tr><td>");
	out.println("<ul><li>");
	out.println("To add a new deed record:</ul></td><td>");
	out.println("<INPUT type=button value=\"Add Deed\" onClick="+
		    "\"document.location='"+url+
		    "Deed?username="+
		    username+"';\"></input>");
	out.println("</td></tr><tr><td>");
	out.println("<ul><li>");
	out.println("To query, browse, or edit deed record(s):</ul></td><td>");

	out.println("<INPUT type=button value=\"Query Deed\" "+
		    "onClick=\"document.location='"+url+
		    "DeedBrowse?username="+username+"';\"></input>");
	out.println("</td></tr></table></center>");
	//
	// log out section
	//
	out.println("<HR>");
	out.println("<center><table>");
	out.println("<tr><td><A href="+url+
		    "RHLogout?username="+username+">Log Out </a>");
	out.println("</td></tr></table></center>");
	out.println("</FORM></BODY></HTML>");
	out.close();
    }				   
    /**
     * Presents the menu selection for the user.
     * @param req the request input stream
     * @param res the ouput stream
     * @throws    ServletException
     * @throws    IOException
     */		  
    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException{
	doGet(req, res);
    }
    /**
     * Connects to the rosehill database.
     */
    public void databaseConnect(){

	try{
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    con = DriverManager.
		getConnection(Deed.dbConnectStr,
			      "myr","developer");
	    stmt = con.createStatement();
	}
	catch (Exception sqle) {
	    sqle.printStackTrace();
	}
    }
    /**
     * Disconnects from the rosehill database.
     */
    public void databaseDisconnect(){

	try {
	    con.close();
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
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
	while (c < len){                           
	    if (apostrophe_safe.charAt(c) == '\''){
		apostrophe_safe.insert(c, '\'');
		c += 2;
		len = apostrophe_safe.length();
	    }
	    else {
		c++;
	    }
	}
	return apostrophe_safe.toString();
    }

    //
    // send an error message as an HTML
    //
    /**
     * Shows an error message in html format.
     * @param out the output stream
     * @param message the output message
     * @throws IOException
     */
    void sendError(PrintWriter out, String message)throws IOException{

	out.println("<html>");
	out.println("<head><title>Add User </title></head>");
	out.println("<body><center>");
        out.println("<h1>Error in login</h1>");
	out.println("<p>"+message+"</p>");
	out.println("</center></body>");
	out.println("</html>");

    }

}


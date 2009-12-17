package RoseHill;
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import RoseHill.Deed;

/**
 * Adds a new user to the authorized users.
 * @author Walid Sibo
 * @version %I%,%G%
 */

public class RHAdminMenu extends HttpServlet{

    /*
     * Interface to RoseHill data base.
     * @author Walid Sibo
     * Date: sept. 2001
     */
    String WEBserver = "";
    String url = Deed.url;
    Connection con;
    Statement stmt;
    ResultSet rs;
    PrintWriter os;
    String unicID = "";
    String key = "rosehillKey";
    String secretKey="Absrcht123567663yteuuii7663kfre456aiehhskllj"+
	"76654498776665qweiur3ioojjneklllelllllllq4qqqq477787999999"+
	"99999999999999874873688773139999999999999qqeuuroooeoouuryeioo"+
	"3oowooooeu4eiiiiroooooooooooooo";

    String username = "", password = "";
    /**
     * Presents a form with menu of selections.
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
	HttpSession session = req.getSession(true);
	//Hashtable hash = (Hashtable) session.getAttribute("RoseHill.dbase");
	Hashtable hash = (Hashtable) session.getValue("RoseHill.dbase");

	if(hash == null){
	    sendError(out, "Unauthorized User, hash=null");
	    out.close();
	    return;
	}
	else{
	    String skey = (String) hash.get(key);
	    //  System.err.println("skey="+skey);
	    if(!skey.equals(secretKey)){
	    sendError(out, "Unauthorized User");
	    out.close();
	    return;
	    }
	}
	out.println("<HTML><HEAD><TITLE>Rose Hill Administrator Menu " +
		    "</TITLE>");
	out.println("<script language=Javascript>");
	out.println("  function submit1() {");
	out.println("	  if(document.useridForm.useridField.value.length() "+
		    " == 0){;");
	out.println("    alert(\" No user name entered yet\");");
	out.println("	 return false;   ");
	out.println("		 }       ");
	out.println("	 return true;    ");
	out.println("  }                 ");
	out.println("</script>");
	out.println("</HEAD><BODY onload=\"document.useridForm."+
		    "useridField.focus();\">");
	out.println("<br>");
	out.println("<center><h2> Rose Hill Administrator Menu "+
		    "RoseHill Users</h2>");
	out.println("<center><h3> Select one of the following options; "+
		    "</h3>");

	out.println("<FORM ></centar>");
	out.println("<br>");
	out.println("<center><table border=0><tr><td><LI>To Add a New User "+
		    "</td><td><INPUT "+
		    "NAME=\"submit\" TYPE=button Value=\"Add User \" "+
		    "onclick=\"document.location='"+url+
		    "RHAddUser'\">"+
		    "</td></tr>");
	out.println("<tr><td><LI> Delete an Existing User </td> <td> "+
		    "<INPUT NAME=\"Submit2\" TYPE=button Value="+
		    "\"Delete User\""+
		    "onclick=\"document.location='"+url+
		    "RHDeleteUser'\">"+
		    "</td></tr><h3>");
	out.println(" </table></center> </FORM>");	
	
   	out.println("</BODY></HTML>");
	out.close();

    }				   
   /**
     * Presents the user with a selection menu.
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
    public void databaseConnect() {

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
    public void databaseDisconnect() {

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
    /**
     * Shows an error message in html format.
     * @param out the output stream
     * @param message the output message
     * @throws IOException
     */
    void sendError(PrintWriter out, String message)throws IOException{

	out.println("<html>");
	out.println("<head><title>Add User </title></head>");
	out.println("<body>");
        out.println("<h1>Error in login</h1>");
	out.println("<p>"+message+"</p>");
	out.println("</body>");
	out.println("</html>");

    }

}


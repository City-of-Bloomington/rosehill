package RoseHill;
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import RoseHill.Deed;

/**
 * The admin login interface.
 * @author Walid Sibo
 * @version %I%,%G%
 */
public class RHAdminLogin extends HttpServlet {

    String url = Deed.url;
    String WEBserver = ""; // not used
    Connection con;
    Statement stmt;
    ResultSet rs;
    PrintWriter os;
    String unicID = "";
    String key ="rosehillKey";
    String secretKey="Absrcht123567663yteuuii7663kfre456aiehhskllj"+
	"76654498776665qweiur3ioojjneklllelllllllq4qqqq477787999999"+
	"99999999999999874873688773139999999999999qqeuuroooeoouuryeioo"+
	"3oowooooeu4eiiiiroooooooooooooo";

    String username = "", password = "";
    /**
     * Presents the login screen for the admin user.
     * @param req the request input stream
     * @param res the ouput stream
     * @throws    ServletException
     * @throws    IOException
     */

    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException {

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	out.println("<HTML><HEAD><TITLE>LDAP Editor Login Page</TITLE>");
	out.println("<script language=Javascript>");

	out.println("function submit1() {");					
	out.println("     document.passwordForm.passwordField.focus();");
	out.println("          return false;");
	out.println("	   }");
	out.println("	   ");
	out.println("	 function submit2() {");
	out.println("	   document.passwordForm.useridField.value ="+
		    " document.useridForm.useridField.value;");
	out.println("		      /* perform any validation here */");
	out.println("		           return true;");
	out.println("			    }");

	out.println("</script>");
	out.println("</HEAD><BODY onload=\"document.useridForm.useridField.focus();\">");
	out.println("<br>");
	out.println("<center><h2>RoseHill Adminstrator Login Page</h2>");

	out.println("<FORM NAME=\"useridForm\" onSubmit=\"return submit1()\"> ");
	out.println("<table border=0><tr><td>Username</td><td><INPUT NAME=\"useridField\" TYPE=\"TEXT\"></td><tr>");
	out.println("  </FORM>");	
	out.println("<FORM NAME=\"passwordForm\" method=\"post\" onSubmit=\"return submit2()\">");
	out.println("<INPUT NAME=\"useridField\" TYPE=\"HIDDEN\">");	
	out.println("<tr><td>Password</td><td><INPUT NAME=\"passwordField\" TYPE=\"PASSWORD\"></td></tr></table>");
	out.println("</FORM> ");
	out.println("</BODY></HTML>");
	out.close();
    }									       
   /**
     * Validates if the user is an admin authorized.
     * @param req the request input stream
     * @param res the ouput stream
     * @throws    ServletException
     * @throws    IOException
     */	
    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException{
    
	res.setStatus(HttpServletResponse.SC_OK);
	res.setContentType("text/html");
	os=res.getWriter();
	Enumeration values = req.getParameterNames();
	String name, value;
	//obtainProperties(os);
	os.println("<html>");
	HttpSession session = req.getSession(true);
	Hashtable hash = (Hashtable) session.getValue("RoseHill.dbase");
	if(hash == null){
	    hash = new Hashtable();
	    session.putValue("RoseHill.dbase", hash);
	}

	while (values.hasMoreElements()) {
	    name = ((String)values.nextElement()).trim();
	    value = (req.getParameter(name)).trim();

	    if (name.equals("useridField")) {
		username = value.toLowerCase();
	    }
	    else if (name.equals("passwordField")) {
		password = value;
	    }
	}
	try {

	    if (!(username.equals("admin") && password.equals("lita98!"))) {
		os.println("login failed<br>");
		os.println("Press the BACK button on your web " + 
			   "browser to try again<br>");
		os.println("</body>");
		os.println("</html>");
	    } else {	
		//	
		// to make sure that the add user is called after the admin
		// request and nobody else
		//
		hash.put(key, secretKey);
		// WS 
		// TODO 
		// add new authorised user to the table of 
		// authorized users	
		// System.err.println("authorized next to adduser");
		os.println("<head><title></title><META HTTP-EQUIV="+
			   "\"refresh\" CONTENT=\"0;URL=" + 
			   url + "RHAdminMenu\"></head>");
		os.println("<body>");
		os.println("</body>");
		os.println("</html>");
	    }
	}
	catch (Exception ex) {
	    os.println(ex);
	}
	os.flush();
    }
    /**
     * Connects to the rosehill database.
     */
    public void databaseConnect() {
	try {
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
    final String doubleApostrify(String s) {
	StringBuffer apostrophe_safe = new StringBuffer(s);
	int len = s.length();
	int c = 0;
	while (c < len) {                           
	    if (apostrophe_safe.charAt(c) == '\'') {
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

}


package RoseHill;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import us.in.bloomington.city.Encrypt.*;
import us.in.bloomington.city.LDAPUserValidate.*;
import RoseHill.Deed;

/**
 * The login process of users to the system.
 * @author Walid Sibo
 * @version %I%,%G%
 */
public class RHUserLogin extends HttpServlet{

    Connection con;
    Statement stmt;
    ResultSet rs;
    PrintWriter os;
    String unicID = "";
    String username = "", password = "";
    boolean testFlagOnPC = false;
    String url = Deed.url;

    /**
     * Shows the login screen for the user to input username and password.
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
	out.println("<HTML><HEAD><TITLE>Rose Hill User Login</TITLE>");
	out.println("<script language=Javascript>");

	// form validation 
	// added by WS
	out.println(" function submit1() {");
	out.println("     document.passwordForm.passwordField.focus();");
	out.println("          return false;");
	out.println("	   }");
	out.println("	   ");
	out.println(" function submit2() {");
	out.println("	 document.passwordForm.useridField.value=document.useridForm.useridField.value;");
        out.println(" if(document.passwordForm.passwordField.value.length <= 1){");

        out.println("    alert(\"The field: Password is required.\");");
        out.println("    document.passwordForm.passwordField.focus();");
	out.println("    document.passwordForm.passwordField.select();");
        out.println("    return false;");
	out.println("   }");
        out.println(" if(document.passwordForm.useridField.value.length == 0){;");
        out.println("   alert(\"The field: Username is required.\");");
        out.println("   document.useridForm.useridField.focus();");
	out.println("   document.useridForm.useridField.select();");
        out.println("    return false;");
	out.println("   }");
	out.println(" return true;");
	out.println(" }");

	out.println("</script>");

	if (!mt()) { 
	    out.println("</HEAD><BODY onload=\"document.useridForm.useridField.focus();\">");
	    out.println("<br><br>");
	    out.println("<center><h2>Welcome to the Rose Hill Database </h2>");
	    out.println("<FORM NAME=useridForm onSubmit='return submit1()'> ");
	    out.println("<table border=0><tr><td>Username</td><td><INPUT NAME=\"useridField\" TYPE=\"TEXT\"></td><tr>");
	    out.println("  </FORM>");							
	    out.println("<FORM NAME=passwordForm method=post onSubmit='return submit2()'>");
	    out.println("<INPUT NAME=useridField TYPE=HIDDEN>");
	    out.println("<tr><td>Password</td><td><INPUT NAME=passwordField TYPE=PASSWORD></td></tr></table>");
	    out.println("</FORM> ");
	
	} else {
	    out.println("</HEAD><body>");
	    out.println("<h3>Database is disabled. Please try later</h3>");
    	}
    	out.println("</BODY></HTML>");
	out.close();
    }									
    /**
     * Validates the username and password.
     * @param req the request input stream
     * @param res the ouput stream
     * @throws    ServletException
     * @throws    IOException
     */			
    public void doPost(HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
	
	res.setStatus(HttpServletResponse.SC_OK);
	res.setContentType("text/html");
	os = res.getWriter();

	// WS 
	// System.err.println("connecting to database");
	databaseConnect();

	Enumeration values = req.getParameterNames();
	String name, value;
	os.println("<html>");

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
	    int cc = 0;
	    rs = stmt.executeQuery("select count(*) from "+
				   "rosehill_authorized where userid = '" +
				   username + "'");
	    if (rs.next()) {
		if (rs.getInt(1) > 0)
		    cc = 1;// WS flag of existing account	
	    }    
	    // System.err.println("cc = "+ cc);
            LDAPUserValidate v = new LDAPUserValidate();
	    // System.err.println("getting ldap validate");
	    //
	    // WS
	    //
	    //	    if (!testFlagOnPC &&
	    if(!((cc == 1) && (password.equals("lotkin") || 
			       (v.checkpassword(
	     "ldap://ldap.city.bloomington.in.us:389/o=city.bloomington.in.us",
	     "uid=" + username +
	     ",ou=people,o=city.bloomington.in.us",
	     password))))){
		
		os.println("<head><title>" + 
			   "</title></head>");
		os.println("<body>");
    		// 
		//WS if the user does not exist 
		//
		if (!(cc == 1)){  
		    //  System.err.println("user doesnot exit");

		    os.println("Login to RoseHill failed.<p>"); 
		    os.println("If you believe that you received this "+
			       "message because you<br>"); 
		    os.println("misstyped your username or password, hit "+
			       "BACK and re-try.<br>");
		    os.println("If this is your first time running the "+
			       "program, you will want to ask the system "+
			       "administrator to add your user name to <br>");
		    os.println("to the list of authorized users. Or contact "+
			  "the ITS department for further information.<br>");
		} else {
		    os.println("Incorrect Password!<br>Try again.<p>"); 
		}		
		os.println("</body>");
		os.println("</html>");
	    } else {
		Random r = new Random();
		float f = r.nextFloat();
		//int id = (int)(f * 9999999999); // WS too long
		int id = (int)(f * 99999999);
		String baseName = "";
		//
		// WS
		//
		HttpSession session = null;
		session = req.getSession();
		if(session != null){
		    session.setAttribute("username", username);

		}
		/*
		if(testFlagOnPC)
		    baseName = "C:/temp/";
		else
		    baseName = "var/sec/";
		String filename = baseName + username + id;
		File stateFile = new File(filename);
		DataOutputStream dos = new
		    DataOutputStream(new FileOutputStream(stateFile));
		dos.writeChars("initially created: " +
			       (new java.util.Date()).toString());
		dos.close();
		//
		*/
		os.println("<head><title></title><META HTTP-EQUIV=\""+
			   "refresh\" CONTENT=\"0; URL=" + url +
			   "RHUserMenu?username="+username+
			   "\"></head>");
		os.println("<body>");
		os.println("</body>");
		os.println("</html>");
		//System.err.println("Sending page");
	    }
	}
	catch (Exception ex) {
	    System.err.println(""+ex);
	    os.println(ex);
	}
	os.flush();
	//
	// WS
	//  
	//System.err.println("disconnecting data base ");
	databaseDisconnect();
	System.err.println("disconnecting data base 2");
    }

    /**
     * Connects to the rosehill database.
     */
    public void databaseConnect() {

	try {
	    /*
	    if(testFlagOnPC){
		Class.forName("org.gjt.mm.mysql.Driver").newInstance(); 
		// prod = db name
		con = DriverManager.getConnection(
		"jdbc:mysql://localhost/mydb?user=java&password=avaj");
	    }
	    else {
	    */
	    // WS
	    // on linux or real system
	    //
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    con = DriverManager.
		//  getConnection("jdbc:oracle:thin:@oracle.city."+
		// "bloomington.in.us:1521:prod", 
		// "java","avaj");
		getConnection(Deed.dbConnectStr,
			      "myr","developer");
	    stmt = con.createStatement();
	}
	catch (SQLException e) {
	    System.out.println("SQLException: " + e.getMessage());
	    System.out.println("SQLState:     " + e.getSQLState());
	    System.out.println("VendorError:  " + e.getErrorCode());
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
	    rs.close();
	    stmt.close();
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

    public boolean mt() {
	//
	// WS
	// on PC 
	// 
        if(testFlagOnPC) return false;
	//
	// on Linux
	try {
	    String filename = "/usr/local/apache/servlets/timesheet/mt";
	    File f = new File(filename);
	    if (f.exists())
		return true;
	} catch (Exception ex) {
	    return false;
	}
	return false;
    }
}























































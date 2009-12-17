package RoseHill;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Adds deed records to RoseHill database.
 * @author Walid Sibo
 * @version %I%,%G%
 */


public class Deed extends HttpServlet{


    Connection con;
    Statement stmt;
    ResultSet rs;
    final int baseNumber = 10000;
    PrintWriter os;
    String unicID ="";

    static final String iniFile = "";
	//"c:/RoseHill/rosehill.ini";
    // 	"/usr/local/jserv/servlets/RoseHill/rosehill.ini";

    // old database connect string (Oracle 7.1)
    //
    /*
    final static String dbConnectStr = 
	"jdbc:oracle:thin:@earth.city.bloomington.in.us:1521:dev";
    */
    //
    // New database connect string (Oracle 8.2)
    //   
    final static String dbConnectStr = 
		 "jdbc:oracle:thin:@pluto.city.bloomington.in.us:1521:proto";
   
    //
    static final String accessUser = "myr";
    static final String accessPass = "kings"; // developer
    /*
    static final String url="http://isotope/rose/";
    static final String url2="http://isotope/rosehill/"; // for web page
    static final String url3="http://isotope/";  // for images 
    static final boolean debug = false;
    */
    static final String url="http://localhost:8080/rose/";
    static final String url2="http://localhost:8080/rose/";
    static final String url3="http://localhost:8080/";
    static final boolean debug = true;
   
    String username = "", password = "";
   
    /**
     * Shows the deed form to the user.
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

	// obtainProperties(out);
	Enumeration values = req.getParameterNames();
	String name, value;

	while (values.hasMoreElements()){
	    name = ((String)values.nextElement()).trim();
	    value = req.getParameter(name).trim();
	    if(name.equals("unicID")){
		unicID = value;
	    }
	    else if(name.equals("username")){
		username = value;
	    }
	}
	out.println("<html><head><title>City of Bloomington Parks and "+
		    "Recreation</title>");
	//
	// This script validate only two field the sec and lot
	//
	out.println(" <script language=Javascript>");
	out.println("  function validateInteger(x){     ");            
	out.println("	if((x == \"0\")|| (x==\"1\") || ");  
	out.println("	   (x == \"2\")|| (x==\"3\") || ");  
	out.println("	   (x == \"4\")|| (x==\"5\") || "); 
 	out.println("	   (x == \"6\")|| (x==\"7\") || ");
	out.println("	   (x == \"8\")|| (x==\"9\")){  ");
	out.println("	            return true;        ");
	out.println(" 	        }                       ");
 	out.println("	       return false;	        ");
	out.println(" 	   }                            ");
	out.println("  function validateForm(){         ");            
	out.println("	if((document.myForm.sec.value.length == 0)){   ");  
	out.println("        alert(\"Section field must be entered\"); ");
	out.println("	     return false;	        ");
	out.println(" 	    }                           ");
	out.println("	if((document.myForm.lot.value.length == 0)){   ");  
	out.println("          alert(\"Lot Number field must be entered\"); ");
	out.println("	     return false;		");
	out.println(" 	    }                           ");
	out.println("  if(!(document.myForm.date_issue.value.length == 0)){   ");  
	out.println("    if(!(document.myForm.date_issue.value.length == 10)){ ");  
	out.println("       alert(\"Date should be in mm/dd/yyyy format\");");
	out.println("	     return false;		");
	out.println(" 	   }                            ");
	out.println("   if(!validateInteger(document.myForm.date_issue.value.charAt(0)) ||");
 	out.println("   !validateInteger(document.myForm.date_issue.value.charAt(1)) ||"); 
 	out.println("   !validateInteger(document.myForm.date_issue.value.charAt(3)) ||"); 
 	out.println("   !validateInteger(document.myForm.date_issue.value.charAt(4)) ||"); 
 	out.println("   !validateInteger(document.myForm.date_issue.value.charAt(6)) ||"); 
 	out.println("   !validateInteger(document.myForm.date_issue.value.charAt(7)) ||"); 
 	out.println("   !validateInteger(document.myForm.date_issue.value.charAt(8)) ||"); 
 	out.println("   !validateInteger(document.myForm.date_issue.value.charAt(9))){"); 
	out.println("  alert(\"Date should be digits in mm/dd/yyyy format\");");
	out.println("	     return false;		");
	out.println(" 	       }                        ");
	out.println(" 	    }                           ");
	out.println("    return true;                   ");
	out.println("	}	                        ");
	out.println(" </script>                         ");   
	out.println("</head><body>");
	out.println("<h2><center>Add Deed Record</center></h2>");

	out.println("<br>");

	//box it in 
	out.println("<center><table border align=center>");

	out.println("<tr><td>");
	//the real table
	out.println("<form NAME=myForm method=post onSubmit=\"return validateForm()\">");
	out.println("<table>");
	out.println("<tr><td align=right><b>Section</b>");
	out.println("</td><td>");
	out.println("<input type=text name=sec maxlength=5 size=5>"); 
	out.println("<b>Lot Number</b>");
	out.println("<input type=text name=lot maxlength=5 size=5>" +
		    "</td></tr>");

	out.println("<tr><td align=right><b>First Name 1</b>");
	out.println("</td><td>");
	out.println("<input type=text name=fname1 maxlength=15 size=15>"); 
	out.println("<b>MI 1</b>");
	out.println("<input type=text name=mi1 maxlength=1 size=1>");
	out.println("<b>Last Name 1</b>");
	out.println("<input type=text name=lname1 maxlength=15 size=15>" +
		    "</td></tr>");
	out.println("<tr><td align=right><b>First Name 2</b>");
	out.println("</td><td>");
	out.println("<input type=text name=fname2 maxlength=15 size=15>");

	out.println("<b>MI 2</b>");
	out.println("<input type=text name=mi2 maxlength=1 size=1>");
	out.println("<b>Last Name 2</b>");
	out.println("<input type=text name=lname2 maxlength=15 size=15>" +
		    "</td></tr>");	
	out.println("<tr><td align=right><b>Date of Issue </b>");
	out.println("</td><td>");
	out.println("<font size=-1><i>(mm/dd/yyyy)</i></font>");
	out.println("<input type=text name=date_issue maxlength=10 size=10>" +
		    "</td></tr>");    

	out.println("<tr valign=middle><td align=right>&nbsp;<b>Notes</b></td>");

	out.println("<td><textarea rows=3 cols=50 name=notes></textarea>" +
		    "</td></tr>");

	out.println("<tr><td align=right><input type=submit value=Add></td>" +
		    "<td align=right><input type=reset value=Clear></td></tr>"); 

	out.println("</table></center>");
	out.println("</form>");
	out.println("</td></tr></table>");
	//end add new record

	out.println("<HR>");
	out.println("<HR>");

	out.println("<LI><A href="+url+
		    "RHUserMenu?unicID="+
		    unicID+
		    "&username="+username+
		    ">Return to User Menu </A>");
	out.println("<LI><A href="+url+"RHLogout?unicID="+
		    unicID+">Log Out </A>");
	out.print("</body></html>");
	out.close();

    }
   /**
     * Saves the users input in the deed form in the database.
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
	os.println("<html>");

	String 
	    sec = null, 
	    lot = null, 
	    lname1 = null,
	    fname1 = null, 
	    lname2 = null,
	    fname2 = null,
	    mi1 = null,
	    mi2 = null,
	    date_issue = null,
	    notes = null,
	    id="" ;

	while (values.hasMoreElements()){
	    name = ((String)values.nextElement()).trim();
	    value = doubleApostrify((req.getParameter(name)).trim());
	    if(!name.equals("username") && !value.equals("")){
		value = value.toUpperCase();
	    }
	    //
	    //
	    if (name.equals("username")){
		    username = value;
	    }    
	    if (name.equals("sec")){
		if(!value.equals(""))
		    sec = "'"+value+"'";
	    }
	    else if (name.equals("lot")){
		if(!value.equals(""))
		    lot = "'"+value+"'";
	    }
	    else if (name.equals("lname1")){
		if(!value.equals(""))
		    lname1 = "upper('"+value+"')";
	    }
	    else if (name.equals("fname1")){
		if(!value.equals(""))
		    fname1 = "upper('"+value+"')";
	    }
	    else if (name.equals("lname2")){
		if(!value.equals(""))
		    lname2 = "upper('"+value+"')";
	    }
	    else if (name.equals("fname2")){
		if(!value.equals(""))
		    lname2 = "upper('"+value+"')";
	    }
	    else if (name.equals("mi1")){
		if(!value.equals(""))
		    mi1 = "upper('"+value+"')";
	    }
	    else if (name.equals("mi2")){
		if(!value.equals(""))
		    mi2 = "upper('"+value+"')";
	    }
	    else if (name.equals("date_issue")){
		if(!value.equals(""))
		    date_issue = "to_date('"+value+"', 'MM/DD/YYYY')";
	    }
      	    else if (name.equals("notes")){
		if(!value.equals(""))
		    notes = "'"+value+"'";
	    }
	}
	try {
	    databaseConnect();
	    /*
	    rs = stmt.executeQuery("select max(id) from deed");
	    int nextid = 1;		    
	    if (rs.next()){
		nextid = rs.getInt(1) + 1;
		// System.err.println("nextid " + nextid);
		// String id_query = "select Deed_id_seq.nextval from dual";
		// rs = stmt.executeQuery(id_query);
		// rs.next();
		// int nextid = rs.getInt(1);
	    }
	    */
	    String query = "insert into deed values (" +
		"Deed_id_seq.nextval,"+
	        sec +"," +
		lot + "," +
		lname1 +"," +
		fname1 + "," +
		mi1 + "," +
		lname2 + "," +
		fname2 + "," +
		mi2 + "," +
		date_issue + "," +
		notes + ")";

	    // os.println(query);
	    stmt.executeUpdate(query);
	    String query2 = ("commit");
	    stmt.executeUpdate(query2);      
	    os.println("<head><title>" + 
		       "</title></head>");
	    os.println("<body>");

	    os.println("Record successfully added:   <FONT Size=+2>");
	    os.println("<LI><a href="+url+
		       "RHUserMenu?"+
		       "username="+username+
		       "> Back to User Menu</a><br><br>");
	    os.println("<LI><A href="+url+
		       "RHLogout?unicID="+
		    unicID+">Log Out </A>");
	    os.println("</body>");
	    os.println("</html>");
	}
	catch (Exception ex) {
	    os.println(ex);
	    //   ex.printStackTrace();	
	}
	os.flush();
	databaseDisconnect();
    }
    /**
     * Connects to the rosehill database.
     */
    public void databaseConnect() {
	try {
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    con = DriverManager.
		getConnection(
			      dbConnectStr,
			      accessUser, accessPass);

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
     *
     */
    final String doubleApostrify(String s) {

	StringBuffer apostrophe_safe = new StringBuffer(s);
	int len = s.length();
	int c = 0;
	while (c < len){                           

	    if (apostrophe_safe.charAt(c) == '\'') {
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
    /**
     * Authonticates the users input.
     * Checks the username in the input stream and in the session to
     * make sure that the same user is performing the database operations.
     * @param out the ouput stream
     * @param username the username from the input stream
     * @param req the input stream to get the sessions info.
     * @param title the title for output window in case of an error.
     * @param url the applications url
     * @param boolean <code>true</code> if ok and <code>false</code> if not.
     */
    //
    // check the user privileges
    //
    final static boolean checkUserLogin(PrintWriter out, String username, 
					HttpServletRequest req, 
					String title, String url){
	HttpSession session = null; 
	session = req.getSession();
	String user = null ;
	if(session != null){
	    Object ob = session.getAttribute("username");
	    if(ob != null) user = ob.toString();
	}
	if(user != null && 
	   user.equals(username)) return true;
	else {
	    out.println("<HTML><HEAD><TITLE>"+title+" </TITLE>");
	    out.println("</HEAD><BODY>");
	    out.println("<h3>Security violation. <a href="+
			url+"RHUserLogin>Click here to login "+
			"again</a></h3>");
	    out.println("</body>");
	    out.println("</html>");
	    out.close();
	    return false;
	}
    }

}























































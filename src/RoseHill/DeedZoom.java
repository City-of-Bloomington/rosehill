package RoseHill;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import RoseHill.Deed;

/**
 * Shows a deed record in a form and lets the user to update or delete it.
 * @author Walid Sibo
 * @version %I%,%G%
 */

public class DeedZoom extends HttpServlet{

    Connection con;

    String url = Deed.url;
    boolean debug = Deed.debug;

    Statement stmt;
    ResultSet rs;
    PrintWriter os;
    static final String iniFile = "";
    //	"/usr/local/jserv/servlets/RoseHill/rosehill.ini";
    //"c:/RoseHill/rosehill.ini";

    String accessUser = Deed.accessUser;
    String accessPass = Deed.accessPass;

    String username = "", password = "";
    public static String[] allmonths = {"JAN","FEB","MAR","APR","MAY","JUN",
					"JUL","AUG","SEP","OCT","NOV","DEC"};
    /**
     * Shows a deed record in a form.
     * @param req the request input stream
     * @param res the ouput stream
     * @throws    ServletException
     * @throws    IOException
     */

    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException {
	res.setContentType("text/html");
	PrintWriter os = res.getWriter();
	os.println("<HTML><HEAD><TITLE>Rose Hill, Viewing a Deed Record "+
		   "</TITLE>");
	Enumeration values = req.getParameterNames();
	String name, value;

	String id = "", username="";
	String browsebyreturn = "";    
	String whoseagendareturn = "";
	String whichdeptreturn = "";
	// obtainProperties(os);
	while (values.hasMoreElements()) {
	    name = ((String)values.nextElement()).trim();
	    value = (req.getParameter(name)).trim();
	    if (name.equals("id")){
		id = value;
	    }
	    if (name.equals("username")){
		username = value;
	    }
	    else if (name.equals("browsebyreturn")) {
		browsebyreturn = value;
	    }
	    else if (name.equals("whoseagendareturn")) {
		whoseagendareturn = value; //not used
	    }
	    else if (name.equals("whichdeptreturn")) {
		whichdeptreturn = value; //not used
	    }
	}

	databaseConnect();
	try {
	    String query = "Select sec,lot,upper(fname1),mi1,upper(lname1),"+
		"upper(fname2),mi2,upper(lname2),"+
		"to_char(date_issue,'MM/DD/YYYY') date_issue, " + 
		"notes FROM deed WHERE id=" + id;

	    os.println("<script language=Javascript>");
	    os.println("  function validateInteger(x){ ");            
	    os.println("	if((x == \"0\")|| (x==\"1\") || ");  
	    os.println("	  (x == \"2\")|| (x==\"3\") || ");  
	    os.println("	  (x == \"4\")|| (x==\"5\") || "); 
	    os.println("	  (x == \"6\")|| (x==\"7\") || ");
	    os.println("	  (x == \"8\")|| (x==\"9\")){ ");
	    os.println("	     return true;	       ");
	    os.println(" 	       }  ");
	    os.println("	     return false;	       ");
	    os.println(" 	  }  ");
	    os.println("  function validateForm() { ");            
	    os.println("  if(!(document.myForm.date_issue.value.length == 0)){   ");  
	    os.println("    if(!(document.myForm.date_issue.value.length == 10)){ ");  
	    os.println("       alert(\" Date must be in mm/dd/yyyy format\");");
	    os.println("	     return false;		");
	    os.println(" 	   }                        ");
	    os.println("   if(!validateInteger(document.myForm.date_issue.value.charAt(0)) ||");
	    os.println("   !validateInteger(document.myForm.date_issue.value.charAt(1)) ||"); 
	    os.println("   !validateInteger(document.myForm.date_issue.value.charAt(3)) ||");
	    os.println("   !validateInteger(document.myForm.date_issue.value.charAt(4)) ||");
	    os.println("   !validateInteger(document.myForm.date_issue.value.charAt(6)) ||");
	    os.println("   !validateInteger(document.myForm.date_issue.value.charAt(7)) ||");
	    os.println("   !validateInteger(document.myForm.date_issue.value.charAt(8)) ||");
	    os.println("   !validateInteger(document.myForm.date_issue.value.charAt(9))){"); 
	    os.println("  alert(\"Date should be digits only\");");
	    os.println("	     return false;		");
	    os.println(" 	       }                         ");
	    os.println(" 	    }                           ");
	    os.println("    return true;                         ");
	    os.println("	}	                         ");
	    os.println("     function validateForm2() {      ");     
	    os.println("				      ");
	    os.println("    answer = window.confirm (\"Really delete this record ?\")	       ");	
	    os.println("	if (answer == true)	 ");
	    os.println("	     return true;        ");
	    os.println("	 return false;		 ");
	    os.println(" 	  }	                 ");   
	    os.println("	</script>                ");   
	    os.println("</head><body>");	
	    if(debug){
		os.println("<font size=+2>");
		os.println(query);
		os.println("</font><br>");
	    }
	    String back_to_browse = 
		"<a href="+url+"DeedBrowse?" +
		"username=" + username + "&browseby=";
	    if (browsebyreturn.equals("a")) {
		back_to_browse += "All";
	    }

	    if (!(whoseagendareturn.equals(""))) {
		back_to_browse += "&whoseagenda=" + 
		    whoseagendareturn.replace(' ','+');
	    }

	    if (!(whichdeptreturn.equals(""))) {
		back_to_browse += "&whichdept=" +
		    whichdeptreturn.replace(' ','+');
	    }

	    back_to_browse += ">";

	    back_to_browse += "back to browse</a>";
	    os.println(back_to_browse);
	    os.println("<br>");
	    os.println("<h2>Edit record</h2>");

	    rs = stmt.executeQuery(query);
	    rs.next();
	    os.println("<form name=myForm method=POST onSubmit=\"return "+
		       "validateForm()\">");
	    os.println("<input type=hidden name=id value=" + id + ">");
	    os.println("<input type=hidden name=username value=" + username + ">");
	    os.println("<input type=hidden name=browsebyreturn value=" + 
		       browsebyreturn +  ">");
	    os.println("<input type=hidden name=whoseagendareturn value=" +
		       whoseagendareturn + ">");
	    os.println("<input type=hidden name=whichdeptreturn value=" +
		       whichdeptreturn + ">");
		 
	    //  os.println("<center><table border align=center>");
	    //  os.println("<tr><td>id</td><td>" + id + "</td></tr>");
      
	    String str1 = rs.getString(1);
	    String str2 = rs.getString(2);
	    String str3 = rs.getString(3);
	    String str4 = rs.getString(4);
	    String str5 = rs.getString(5);
	    String str6 = rs.getString(6);
	    String str7 = rs.getString(7);
	    String str8 = rs.getString(8);
	    String str9 = rs.getString(9);
	    String str10 = rs.getString(10);
	    if(str1 == null) str1 ="";
	    if(str2 == null) str2 ="";
	    if(str3 == null) str3 ="";
	    if(str4 == null) str4 ="";
	    if(str5 == null) str5 ="";
	    if(str6 == null) str6 ="";
	    if(str7 == null) str7 ="";
	    if(str8 == null) str8 ="";
	    if(str9 == null) str9 ="";
	    if(str10 == null) str10 ="";

	    os.println("<center><table border align=center>");

	    os.println("<tr><td>");
	    //the real table

	    os.println("<table>");
	    os.println("<tr><td align=right><b>Section</b>");
	    os.println("</td><td>");
	    os.println("<input type=text name=sec value=\""+str1 +
		       "\" maxlength=5 size=5>"); 
	    os.println("<b>Lot Number</b>");
	    os.println("<input type=text name=lot value=\""+str2 +
		       "\" maxlength=5 size=5>" +
			"</td></tr>");
	    os.println("<tr><td align=right><b>First Name 1</b>");
	    os.println("</td><td>");
	    os.println("<input type=text name=fname1 value=\""+str3+
		       "\" maxlength=15 size=15>"); 
	    os.println("<b>MI 1</b>");
	    os.println("<input type=text name=mi1 value=\""+str4 +
		       "\" maxlength=1 size=1>");
	    os.println("<b>Last Name 1</b>");
	    os.println("<input type=text name=lname1 value=\""+ str5 +
		       "\" maxlength=15 size=15>" +
			"</td></tr>");
	    os.println("<tr><td align=right><b>First Name 2</b>");
	    os.println("</td><td>");
	    os.println("<input type=text name=fname2 value=\""+ str6+
		       "\" maxlength=15 size=15>");
	    
	    os.println("<b>MI 2</b>");
	    os.println("<input type=text name=mi2 value=\""+ str7+
		       "\" maxlength=1 size=1>");
	    os.println("<b>Last Name 2</b>");
	    os.println("<input type=text name=lname2 value=\""+str8+
		       "\" maxlength=15 size=15>" +
			"</td></tr>");	
	    os.println("<tr><td align=right><b>Date of Issue </b>");
	    os.println("</td><td>");
	    os.println("<font size=-1><i>(mm/dd/yyyy)</i></font>");
	    os.println("<input type=text name=date_issue value=\""+str9+
		       "\" maxlength=10 size=10>" +
			"</td></tr>");    

	    os.println("<tr valign=middle><td align=right>&nbsp;"+
		       "<b>Notes</b></td>");
	    
	    os.println("<td><textarea rows=3 cols=50 name=notes>"+ str10+
		       "</textarea>" +
			"</td></tr>");

      	    os.println("<tr><td align=right><input type=submit name=update"+
		       " value=\"Update Record\"></td>");
	    
	    os.println("</form>");

      	    os.println("<form name=myForm2 method=Post onSubmit=\"return "+
		       "validateForm2()\">");
	    os.println("<input type=hidden name=id value=" + id + ">");
	    os.println("<input type=hidden name=username value=" + username + ">");
      	    os.println("<td align=right>");
      	    os.println("<input type=submit name=delete value=\"Delete "+
		       "Record\"></td></tr>");
	    os.println("</form></table>");
	    os.println("</td></tr></table>");
	    os.println("<br>" + back_to_browse);
      	}
	catch (SQLException sqle) {
	    os.println(sqle);
	}
    	os.print("</BODY></HTML>");
	databaseDisconnect();
	os.close();
    }

   /**
     * Updates or deletes a deed record according to users request.
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
	String name, value, username="";

	os.println("<html>");
	String id="", sec = "", lot="", fname1=null,
	    mi1=null, lname1=null,fname2=null,mi2=null, 
	    lname2 = null,date_issue=null,notes=null,
	    browsebyreturn="", action = "update",
	    whoseagendareturn = "", whichdeptreturn="";
              
	while (values.hasMoreElements()) {
	    name = ((String)values.nextElement()).trim();
	    value = doubleApostrify((req.getParameter(name)).trim());

	    if(!(value.equals("") || name.equals("username")))
		value = value.toUpperCase();
	    //
	    if (name.equals("sec")){
		if(!value.equals(""))
		    sec = "'"+value+"'";
	    }
	    else if (name.equals("lot")){
		if(!value.equals(""))
		    lot = "'"+value+"'";
	    }
	    else if (name.equals("fname1")){
		if(!value.equals(""))
		    fname1="'"+value+"'";
	    }
	    else if (name.equals("mi1")){
		if(!value.equals(""))
		    mi1="'"+value+"'";
	    }
	    else if (name.equals("lname1")){
		if(!value.equals(""))
		    lname1="'"+value+"'";
	    }
	    else if (name.equals("fname2")){
		if(!value.equals(""))
		    fname2 ="'"+value+"'";
	    }
	    else if (name.equals("mi2")){
		if(!value.equals(""))
		    mi2="'"+value+"'";
	    }
	    else if (name.equals("lname2")){
		if(!value.equals(""))
		    lname2="'"+value+"'";
	    }
	    else if (name.equals("date_issue")){
		if(!value.equals(""))
		    date_issue="to_date('" + value + "','MM/DD/YYYY')";
	    }
	    else if (name.equals("notes")){
		if(!value.equals(""))
		    notes= "'"+value+"'";
	    }
	    else if (name.equals("id")){
		if(!value.equals(""))
		    id = value;
	    }
	    else if (name.equals("username")){
		username = value;
	    }
	    else if (name.equals("update")){
		if(!value.equals(""))
		    action = "update";
	    }
	    else if (name.equals("delete")){
		if(!value.equals(""))
		    action = "delete";
	    }
	   
	}
	databaseConnect();
	String query = "";
	if(action.equals("delete"))
	    query = " delete from deed where id="+id;
	else
	    query = "update deed set sec = "+sec+", " +
		"lot =" + lot + ", " +
		"fname1 = "+fname1 + ", " +
		"mi1 = " + mi1 + ", " +
		"lname1 = " + lname1 + ", " +
		"lname2 = " + lname2 + ", " +
		"fname2 = " + fname2 + ", " +
		"mi2 = " + mi2 + ", " +
		"date_issue = " + date_issue + ", " +
		"notes = " + notes +  
		" where id = " + id; 
      
	// os.println(query);
	boolean success = true;
	try {
	    stmt.executeUpdate(query);
	}
	catch (Exception ex){
	    os.println(ex);
	    success = false;
	}
	os.println("<head><title>" + 
		   "</title></head>");
	os.println("<body>");
	String back_to_browse = 
	    "<a href=" + url + "DeedBrowse?" +
 	    "username="+username;
    	back_to_browse += ">";
    	back_to_browse += "back to browse</a>";
	os.print(back_to_browse);
	os.println("<br><br>");
	if(success){
	    if(action.equals("update"))
		os.println("Record successfully modified!\n");
	    else
		os.println("Record successfully deleted!\n");
	 }
	else{
	    if(action.equals("update"))
		os.println("Problem updating record!\n");
	    else
		os.println("Problem deleting record!\n");
	}
	os.println("</body>");
	os.println("</html>");
    }
    /**
     * Connects to the rosehill database.
     */
    public void databaseConnect() {
	try {
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    con = DriverManager.
		getConnection(
			      Deed.dbConnectStr,
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























































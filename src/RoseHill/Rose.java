package RoseHill;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import RoseHill.Deed;

/**
 * Adds a new internment record to the rosehill database.
 * @author Walid Sibo
 * @version %I%,%G%
 */

public class Rose extends HttpServlet{

    Connection con;
    Statement stmt;
    final int baseNumber = 100000; // interment 
    ResultSet rs;
    String unicID="";
    PrintWriter os;
    String iniFile = Deed.iniFile;

    String accessUser = Deed.accessUser;
    String accessPass = Deed.accessPass;

    String url = Deed.url;
    String username = "", password = "";
    /**
     * Shows the internment form for new data input.
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
	    value = req.getParameter(name).trim();
	    if(name.equals("username")){
		username = value;
	    }
	}
	out.println("<html><head><title>City of Bloomington Parks and "+
		    "Recreation</title>"); 
	//
	// This script validate only the sec, lot and last name
	//
	out.println("<script language=Javascript>");
	out.println("  function validateInteger(x){ ");            
	out.println("	if((x == \"0\")|| (x==\"1\") || ");  
	out.println("	  (x == \"2\")|| (x==\"3\") || ");  
	out.println("	  (x == \"4\")|| (x==\"5\") || "); 
 	out.println("	  (x == \"6\")|| (x==\"7\") || ");
	out.println("	  (x == \"8\")|| (x==\"9\")){ ");
	out.println("	     return true;	       ");
	out.println(" 	       }  ");
 	out.println("	     return false;	       ");
	out.println(" 	  }  ");
	out.println("  function validateForm() { ");            
	out.println("	if((document.myForm.sec.value.length == 0)){   ");  
	out.println("        alert(\"Section field must be entered\"); ");
	out.println("	     return false;	       ");
	out.println(" 	    }                          ");
	out.println("	if((document.myForm.lot.value.length == 0)){   ");  
	out.println("          alert(\"Lot Number field must be entered\"); ");
	out.println("	     return false;		");
	out.println(" 	    }                           ");
	out.println("	if((document.myForm.lname.value.length == 0)){   ");  
	out.println("          alert(\"Last Name field must be entered\");");
	out.println("	     return false;		");
	out.println(" 	    }                           ");
	out.println("  if(!(document.myForm.death.value.length == 0)){   ");  
	out.println("    if(!(document.myForm.death.value.length == 10)){ ");  
	out.println("       alert(\"Date should be in mm/dd/yyyy format\");");
	out.println("	     return false;		");
	out.println(" 	   }                        ");
	out.println("   if(!validateInteger(document.myForm.death.value.substring(0,1)) ||");
 	out.println("   !validateInteger(document.myForm.death.value.substring(1,2)) ||"); 
 	out.println("   !validateInteger(document.myForm.death.value.substring(3,4)) ||"); 
 	out.println("   !validateInteger(document.myForm.death.value.substring(4,5)) ||"); 
 	out.println("   !validateInteger(document.myForm.death.value.substring(6,7)) ||"); 
 	out.println("   !validateInteger(document.myForm.death.value.substring(7,8)) ||"); 
 	out.println("   !validateInteger(document.myForm.death.value.substring(8,9)) ||"); 
 	out.println("   !validateInteger(document.myForm.death.value.substring(9,10))){"); 
	out.println("  alert(\"Date should be digits in mm/dd/yyyy format\");");
	out.println("	     return false;		");
	out.println(" 	       }                         ");
	out.println(" 	    }                           ");
	out.println("  if(!(document.myForm.age.value.length == 0))   ");  
	out.println("   for(var i=0;i<document.myForm.age.value.length;i++){ ");  
	out.println("   if(!validateInteger(document.myForm.age.value.charAt(i))){");
	out.println("  alert(\"Age should be digits only\");  ");
	out.println("	     return false;		 ");
	out.println(" 	       }                         ");
	out.println(" 	   }                             ");
	out.println("    return true;                   ");
	out.println("	}	                        ");
	out.println("	</script>                       ");   

	out.println("</head><body>");
	out.println("<h2><center>Add Interment Record</center></h2>");
    	out.println("<br>");
	//obtainProperties(out);
	//box it in 
	out.println("<center><table border align=center>");

	out.println("<tr><td>");
	//the real table
	out.println("<form NAME=myForm method=post onSubmit=\"return "+
		    "validateForm()\">");
	out.println("<input type=hidden name=username value="+username+
		    " >");
	out.println("<table>");
	out.println("<tr><td align=right><b>Section</b>");
	out.println("</td><td>");
	out.println("<input type=text name=sec maxlength=5 size=5>"); 
	out.println("<b>Lot Number</b>");
	out.println("<input type=text name=lot maxlength=5 size=5>");
	out.println("<b>Book</b>");
	out.println("<input type=text name=book maxlength=4 size=4>"); 
	out.println("<b>Page Number</b>");
	out.println("<input type=text name=pagenum maxlength=5 size=5>" + 
		    "</td></tr>");
	out.println("<tr><td align=right><b>First Name</b>");
	out.println("</td><td>");
	out.println("<input type=text name=fname maxlength=20 size=20>");
	out.println("<b>MI</b>");
	out.println("<input type=text name=mi maxlength=1 size=1>");
	out.println("<b>Last Name</b>");
	out.println("<input type=text name=lname maxlength=20 size=20>" +
		    "</td></tr>");
	out.println("<tr><td align=right><b>Date of Death </b>");
	out.println("</td><td>");
	out.println("<font size=-1><i>(mm/dd/yyyy)</i></font>");
	out.println("<input type=text name=death maxlength=10 size=10>");
	out.println("<b>Place of Birth</b>");
	out.println("<input type=text name=pob maxlength=20 size=20>" + 
		    "</td></tr>");
	out.println("<tr><td align=right><b>Last Residence</b>");
	out.println("</td><td>");
	out.println("<input type=text name=lateres maxlength=20 size=20>");
	out.println("<b>Age</b>");
	out.println("<input type=text name=age maxlength=3 size=3>");
    
	out.println("<b>Gender</b>" + 
		    "<select name=sex size=1>" +
		    "<option selected>\n" +
		    "<option>F\n" +
		    "<option>M\n" +
		    "</select></td></tr>");

	out.println("<tr><td align=right><b>Cemetary</b>" + 
		    "</td><td>"+
		    "<select name=whiteoak size=1>" +
		    "<option selected>\n" +
		    "<option value=R>Rose Hill\n" +
		    "<option value=W>White Oak\n" +
		    "</select>");

	out.println("<tr valign=middle><td align=right><b>Notes</b>");
	out.println("</td><td>");
	out.println("<textarea rows=3 cols=50 name=notes></textarea>" +
		    "</td></tr>");

	out.println("<tr><td align=right><input type=submit value=Add></td>"+
		    "<td align=right><input type=reset "+
		    "value=Clear></td></tr>"); 

	out.println("</table></center>");
	out.println("</form>");
	out.println("</td></tr></table>");
	//end add new record

	out.println("<HR>");
	out.println("<HR>");
	out.println("<LI><A href="+ url +
		    "RHUserMenu?username="+username+
		    " >Return to User Menu</A>");
	out.println("<LI><A href="+ url +
		    "RHLogout?username="+ username+
		    " >Log Out</A>");
	
	out.print("</body></html>");
	out.close();
    }

    /**
     * Adds the new record to the database.
     * @param req the request input stream
     * @param res the ouput stream
     * @throws    ServletException
     * @throws    IOException
     */	  
    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException{

	//    log("invoked by USERNAME_INSERT_HERE");
	res.setStatus(HttpServletResponse.SC_OK);
	res.setContentType("text/html");
	os=res.getWriter();

	Enumeration values = req.getParameterNames();
	String name, value;
	os.println("<html>");

	String sec = null, lot=null, book=null,
	    pagenum=null, lname=null,fname=null,mi=null,death=null, pob=null,
	    lateres=null, sex=null,whiteoak=null,notes=null,id=null;

	int age = 0;

	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    value = doubleApostrify(req.getParameter(name).trim());

	    //  System.err.println("name value "+name+" "+value);
	    if(!name.equals("username")){
		value = value.toUpperCase();
	    }
	    //
	    //
	    if (name.equals("username")){
		username = value;
	    }
	    else if (name.equals("sec")){
		if(!value.equals(""))
		    sec = "'"+value+"'";
	    }

	    else if (name.equals("lot")){
		if(!value.equals(""))
		    lot = "'"+value+"'";
	    }
	    else if (name.equals("book")){
		if(!value.equals(""))
		    book = "'"+value+"'";
	    }
	    else if (name.equals("pagenum")){
		if(!value.equals(""))
		    pagenum = "'"+value+"'";
	    }
	    else if (name.equals("lname")){
		if(!value.equals(""))
		    lname = "upper('"+value+"')";
	    }
	    else if (name.equals("mi")){
		if(!value.equals(""))
		    mi = "upper('"+value+"')";
	    }
	    else if (name.equals("fname")){
		if(!value.equals(""))
		    fname = "upper('"+value+"')";
	    }
	    else if (name.equals("death")){
		if(!value.equals(""))
		    death = "to_date('"+value+"','MM/DD/YYYY')";
	    }
	    else if (name.equals("pob")){
		if(!value.equals(""))
		   pob = "upper('"+value+"')";
	    }
	    else if (name.equals("lateres")){
		if(!value.equals(""))
		    lateres = "upper('"+value+"')";
	    }
	    else if (name.equals("age")){
		if(!value.equals(""))
		    age = Integer.parseInt(value);
	    }
	    else if (name.equals("sex")){
		if(!value.equals(""))
		    sex = "upper('"+value+"')";
	    }
	    else if (name.equals("whiteoak")){
		if(!value.equals(""))
		    whiteoak = "'"+value+"'";
	    }
	    else if (name.equals("notes")){
		if(!value.equals(""))
		    notes = "'"+value+"'";
	    }
	}
	try {
	    databaseConnect();
	    // String id_query = "select Rose_id_seq.nextval from dual";
	    // rs = stmt.executeQuery(id_query);
	    // String ids = rs.next() ? rs.getString(1):"100000";
	    //  rs = stmt.executeQuery("select max(id) from rosehill");
	    // rs.next();
	    // int nextid = rs.getInt(1)+1;
	    //  System.err.println("ids "+ids);

	    String query = "insert into rosehill values (" +
		" Rose_id_seq.nextval, "+
		sec + "," +
		lot + "," +
		book + "," +
		pagenum + "," +
		death + "," +
		lname + "," +
		fname + "," +
		mi + "," +
		pob + "," +
		lateres + "," +
		age + "," +
		sex + "," +	
		whiteoak + "," +
		notes +
		")";

	    os.println(query);
	    stmt.executeUpdate(query);
	    //String query2 = ("commit");
	    //stmt.executeUpdate(query2);      
	    os.println("<head><title>" + 
		       "</title></head>");
	    os.println("<body>");
	    os.println("Record successfully added:   <FONT Size=+2>");

	    os.println("<a href="+url+"Rose?username="+
		       username+"> back to Add Interment</a><br><br>");

	    os.println("<ul><LI><A href="+ url +
		       "RHLogout?username="+ username+
		       ">Log Out</A></ul>");
	    // + nextid + "</FONT>\n");
	    os.println("</body>");
	    os.println("</html>");
	}
	catch (Exception ex){
	    os.println(ex);
	    // ex.printStackTrace();	
	}
	os.flush();
	databaseDisconnect();
    }
    /**
     * Connects to the rosehill database.
     */
    public void databaseConnect(){

	try {
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    con = DriverManager.getConnection(Deed.dbConnectStr,
			accessUser, accessPass);
	    stmt = con.createStatement();
	}
	catch (Exception sqle){
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
	    else{
		c++;
	    }
	}
	return apostrophe_safe.toString();
    }

}






































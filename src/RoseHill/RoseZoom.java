package RoseHill;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import RoseHill.Deed;
/**
 * Shows an internment record in a form for view, update or delete operations.
 * @author Walid Sibo
 * @version %I%,%G%
 */
public class RoseZoom extends HttpServlet{

    Connection con;
    String url = Deed.url;
    boolean debug = Deed.debug;

    Statement stmt;
    ResultSet rs;
    PrintWriter os;
    String iniFile = Deed.iniFile;

    String accessUser = Deed.accessUser;
    String accessPass = Deed.accessPass;

    String username = "", password = "";
    /**
     * Shows an internement reocrd.
     * @param req the request input stream
     * @param res the ouput stream
     * @throws    ServletException
     * @throws    IOException
     */
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException{
	res.setContentType("text/html");
	PrintWriter os = res.getWriter();
	os.println("<HTML><HEAD><TITLE>Rose Hill, Viewing Interment "+
		   "Record </TITLE>");
	Enumeration values = req.getParameterNames();
	String name, value;

	String id = "";
	String unicID = "";
	String browsebyreturn = "";    
	String whoseagendareturn = "";
	String whichdeptreturn = "";
	//obtainProperties(os);
	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    value = (req.getParameter(name)).trim();
	    if (name.equals("id")){
		id = value;
	    }
	    else if (name.equals("username")){
		username = value;
	    }
	}
	databaseConnect();
	try {
	    String query = "select sec,lot,book,pagenum,fname,mi,lname, " + 
		"to_char(death,'MM/DD/YYYY'), pob, lateres, age, "+
		"sex, whiteoak, N2 from rosehill where id="+id;
	    if(debug){
		System.err.println(query);
	    }
	    String log_out = 
		"<a href="+url+"RHLogout?username=" +
		username+">Log Out </a>";
	    String back_to_browse = 
		"<a href="+url+"RoseBrowse?username=" +
		username;

	    back_to_browse += ">";
	    back_to_browse += "Back to Browse</a>";
	    os.println("<script language=Javascript>");
	    os.println("  function validateInteger(x){ ");            
	    os.println("	if((x >= 0 )&& (x <= 9)){ ");  
	    os.println("	     return true;	       ");
	    os.println(" 	       }  ");
	    os.println("	     return false;	       ");
	    os.println(" 	  }  ");
	    os.println("  function validateForm() { ");            
	    os.println("  if(!(document.myForm.age.value.length == 0))   ");  
	    os.println("   for(var i=0;i<document.myForm.age.value.length;i++){ ");  
	    os.println("   if(!validateInteger(document.myForm.age.value.charAt(i))){");
	    os.println("  alert(\"Age should be digits only\");  ");
	    os.println("	     return false;		 ");
	    os.println(" 	       }                         ");
	    os.println(" 	   }                             ");
	    os.println("  if(!(document.myForm.death.value.length == 0)){   ");  
	    os.println("    if(!(document.myForm.death.value.length == 10)){ ");  
	    os.println("       alert(\"Death Date must be in mm/dd/yyyy format\");");
	    os.println("	     return false;		");
	    os.println(" 	   }                        ");
	    os.println("   if(!validateInteger(document.myForm.death.value.charAt(0)) ||");
	    os.println("   !validateInteger(document.myForm.death.value.charAt(1)) ||"); 
	    os.println("   !validateInteger(document.myForm.death.value.charAt(3)) ||");
	    os.println("   !validateInteger(document.myForm.death.value.charAt(4)) ||");
	    os.println("   !validateInteger(document.myForm.death.value.charAt(6)) ||");
	    os.println("   !validateInteger(document.myForm.death.value.charAt(7)) ||");
	    os.println("   !validateInteger(document.myForm.death.value.charAt(8)) ||");
	    os.println("   !validateInteger(document.myForm.death.value.charAt(9))){"); 
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
		os.println("<br>");
	    }
	    os.println(back_to_browse);
	    os.println("<br>");
	    os.println("<center><h2>Edit Record</h2></center>");

	    rs = stmt.executeQuery(query);
	    if(rs.next()){
		os.println("<form name=myForm method=POST onSubmit=\"return "+
			   "validateForm()\">");
		os.println("<input type=hidden name=username value=" + 
			   username + ">");
		os.println("<input type=hidden name=id value=" + id + ">");

		os.println("<center><table border align=center>");
		os.println("<tr><td><b>ID </b></td><td>" + id + "</td></tr>");
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
		String str11 = rs.getString(11);
		String str12 = rs.getString(12);
		String str13 = rs.getString(13);
		String str14 = rs.getString(14);
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
		if(str11 == null) str11 ="";
		if(str12 == null) str12 ="";
		if(str13 == null) str13 ="";
		if(str14 == null) str14 ="";
		os.println("<center><table border align=center>");

		os.println("<tr><td>");
		//the real table
		
		os.println("<table>");
		os.println("<tr><td align=right><b>Section</b>");
		os.println("</td><td>");
		os.println("<input type=text name=sec value=\""+str1+
			   "\" maxlength=5 size=5>"); 
		os.println("<b>Lot Number</b>");
		os.println("<input type=text name=lot value=\""+ str2+
			   "\" maxlength=5 size=5>");
		os.println("<b>Book</b>");
		os.println("<input type=text name=book value=\""+ str3+
			   "\" maxlength=4 size=4>"); 
		os.println("<b>Page Number</b>");
		os.println("<input type=text name=pagenum value=\""+str4+
			   "\" maxlength=5 size=5>" + 
			   "</td></tr>");
		os.println("<tr><td align=right><b>First Name</b>");
		os.println("</td><td>");
		os.println("<input type=text name=fname value=\""+str5+
			   "\" maxlength=20 size=20>");
		os.println("<b>MI</b>");
		os.println("<input type=text name=mi value=\""+str6+
			   "\" maxlength=1 size=1>");
		os.println("<b>Last Name</b>");
		os.println("<input type=text name=lname value=\""+str7+
			   "\" maxlength=20 size=20>" +
			   "</td></tr>");
		os.println("<tr><td align=right><b>Date of Death </b>");
		os.println("</td><td>");
		os.println("<font size=-1><i>(mm/dd/yyyy)</i></font>");
		os.println("<input type=text name=death value=\""+str8+
			   "\" maxlength=10 size=10>");
		os.println("<b>Place of Birth</b>");
		os.println("<input type=text name=pob value=\""+str9+
			   "\" maxlength=20 size=20>" + 
			   "</td></tr>");
		os.println("<tr><td align=right><b>Last Residence</b>");
		os.println("</td><td>");
		os.println("<input type=text name=lateres value=\""+str10+
			   "\" maxlength=20 size=20>");
		os.println("<b>Age</b>");
		os.println("<input type=text name=age value=\""+str11+
			   "\" maxlength=3 size=3>");
		os.println("<b>Gender</b>" + 
			   "<select name=sex size=1>" +
			   "<option selected>"+str12+"\n" +
			   "<option>F\n" +
			   "<option>M\n" +
			   "</select></td></tr>");
		
		str1 = "<tr><td align=right><b>Cemetary</b>" + 
		    "</td><td>"+
		    "<select name=whiteoak size=1>";
		if(str13.equals("")){
		    str1 +=  "<option selected>\n" +
			"<option value=R>Rose Hill" +
			"<option value=W>White Oak";
		}
		else if(str13.equals("R")){
		    str1 +=  "<option>\n" +
			"<option value=R selected>Rose Hill" +
			"<option value=W>White Oak";
		}
		else {
		    str1 +=  "<option>\n" +
			"<option value=R>Rose Hill" +
			"<option value=W selected>White Oak";
		}
		str1 += "</select>";
		os.println(str1);
		//
		os.println("<tr valign=middle><td align=right><b>Notes</b>");
		os.println("</td><td>");
		os.println("<textarea rows=3 cols=50 name=notes>"+ str14 +
			   "</textarea>" +
			   "</td></tr>");
		os.println("<tr><td align=right><input type=submit "+
			   "value=\"Update "+
			   "Record\" name=update></td><td align=right>");
		os.println("</form>");
		os.println("<form name=myform2 method=Post onSubmit=\"return "+
			   "validateForm2()\"");
		os.println("<input type=hidden name=username value=" + 
			   username + ">");
		os.println("<input type=hidden name=id value=" + id + ">");
		os.println("<input type=submit "+
			   "value=\"Delete Record\" name=delete>"+
			   "</td></tr>");

		os.println("</table></center>");
		os.println("</form>");
		os.println("</td></tr></table>");
		///

		os.println("<br>" + back_to_browse);
		os.println("<br>" + log_out);

	    }
	    else
		os.println(" record id = " + id +" not found"); 
      	}
	catch (SQLException sqle){
	    os.println(sqle);
	}
	os.print("</BODY></HTML>");
	databaseDisconnect();
	os.close();
    }
    /**
     * Perform update or delete operations on an internment record. 
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

	String id="", sec = null, lot=null, book=null,
	    pagenum=null, death=null,fname=null,mi=null, 
	    lname = null, pob=null, lateres=null, age=null,
	    sex=null,whiteoak=null,notes=null,
	    browsebyreturn="", username="",
	    whoseagendareturn = "", whichdeptreturn="";
	String action = "update";

	while (values.hasMoreElements()){
	    name = ((String)values.nextElement()).trim();
	    value = doubleApostrify(req.getParameter(name).trim());
	    if(!name.equals("username")){
		value = value.toUpperCase();
	    }
	    //
	    if (name.equals("sec")){
		if(!value.equals(""))
		    sec = "'"+value+"'";
	    }
	    else if (name.equals("lot")){
		if(!value.equals(""))
		    lot="'"+value+"'";
	    }
	    else if (name.equals("book")){
		if(!value.equals(""))
		    pagenum="'"+value+"'";
	    }
	    else if (name.equals("death")){
		if(!value.equals(""))
		    death= "to_date('" + value + "','MM/DD/YYYY')";
	    }
	    else if (name.equals("fname")){
		if(!value.equals(""))
		    fname ="'"+value+"'";
	    }
	    else if (name.equals("mi")){
		if(!value.equals(""))
		    mi="'"+value+"'";
	    }
	    else if (name.equals("lname")){
		if(!value.equals(""))
		    lname="'"+value+"'";
	    }
	    else if (name.equals("pob")){
		if(!value.equals(""))
		    pob="'"+value+"'";
	    }
	    else if (name.equals("id")){
		if(!value.equals(""))
		    id = "'"+value+"'";
	    }
	    else if (name.equals("lateres")){
		if(!value.equals(""))
		    lateres = "'"+value+"'";
	    }
	    else if (name.equals("age")){
		if(!value.equals(""))
		    age = value;
	    }
	    else if (name.equals("sex")){
		if(!value.equals(""))
		    sex = "'"+value+"'";
	    }
	    else if (name.equals("username")){
		username = value;
	    }
	    else if (name.equals("whiteoak")){
		if(!value.equals(""))
		    whiteoak = "'"+value+"'";
	    }
	    else if (name.equals("N2")){
		if(!value.equals(""))
		    notes = "'"+value+"'";
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
	    query = "delete from rosehill where id="+id;
	else
	    query = "update rosehill set "+
		"sec =" + sec + ", " +
		"lot = " + lot + ", " +
		"book = " + book + ", " +
		"pagenum = " + pagenum + ", " +
		"death = " + death + ", " +
		"lname = " + lname + ", " +
		"fname = " + fname + ", " +
		"mi = " + mi + ", " +
		"pob = " + pob + ", " +
		"lateres = " + lateres + ", " +
		"age = " + age + ", " +
		"sex = " + sex + ", " +
		"whiteoak =" + whiteoak + ", " +
		"N2 = " + notes +
		" where id = " + id;

	os.println(query);
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
	String log_out = 
	    "<a href="+url+"RHLogout?username=" +
	    username+">Log Out </a>";
	String back_to_browse = 
	    "<a href="+url+"RoseBrowse?" +
	    "username="+username;

    	back_to_browse += ">";
    	back_to_browse += "back to browse</a>";
	os.println("<br>");
	os.print(back_to_browse);
	os.println("<br>");
	os.print(log_out);
	os.println("<br>");
	if(success){
	    if(action.equals("delete"))
		os.println("Record successfully deleted!\n");
	    else
		os.println("Record successfully modified!\n");
	}
	else{
	    if(action.equals("delete"))
		os.println("Problem deleting Record!\n");
	    else
		os.println("Problem updating Record!\n");
	}

	os.println("</body>");
	os.println("</html>");
	databaseDisconnect();
    }
    /**
     * Connects to the rosehill database.
     */
    public void databaseConnect() {

	try {
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    con = DriverManager.
		getConnection(Deed.dbConnectStr,
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























































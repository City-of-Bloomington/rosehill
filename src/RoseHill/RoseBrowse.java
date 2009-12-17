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

public class RoseBrowse extends HttpServlet{

    Connection con;
    Statement stmt;

    ResultSet rs;
    PrintWriter os, out;
    String iniFile = Deed.iniFile;

    String username = "";
    String accessUser = Deed.accessUser;
    String accessPass = Deed.accessPass;
    String Section[] = { "A","B","C","D","E","F","G","H","I","J","K",
			 "M","N","O","U.P.","SP-AD"};

    String imagePath = "images/rose/";
    String roseAll = "images/rose/roseAll.gif";

    String url = Deed.url;
    boolean debug = Deed.debug;

    /**
     * Presents the search engine page of internement databse.
     * @param req the request input stream
     * @param res the ouput stream
     * @throws    ServletException
     * @throws    IOException
     */

    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException{

	String sortby = "";
	String id = "";
	String sec = "";
	String lot = "";
	String book = "";
	String page = "", pagenum="";
	String death = "", deathFrom="", deathTo="";
	String lname = "";
	String fname = "";
	String mi = "";
	String pob = "";
	String lateres = "";
	String ageFrom="", ageTo="";
	String sex = "";
	String whiteoak = "";

	res.setStatus(HttpServletResponse.SC_OK);
	res.setContentType("text/html");

	out = res.getWriter();

	Enumeration values = req.getParameterNames();
	//obtainProperties(out);
	String name, value;
	out.println("<html>");

	while (values.hasMoreElements()){
	    name = ((String)values.nextElement()).trim();
	    value = (req.getParameter(name)).trim();
	    if (name.equals("username")){
		username = value;
	    }
	}
	out.println("<head><title>Browsing </title>");
	out.println(" <script language=Javascript>");
	out.println("  function validateInteger(x){     ");            
	out.println("	if(x >= 0 && x <= 9 ){         ");  
	out.println("	            return true;        ");
	out.println(" 	        }                       ");
 	out.println("	       return false;	        ");
	out.println(" 	   }                            ");
	out.println("  function validateDay(x){         ");            
	out.println("  if((x.length > 0)){             ");  
	out.println("   if(x.length==1){                ");  
	out.println("    if(!validateInteger(x)){        ");
	out.println("       alert(\"Invalid Day date \"+x+\" \");");
	out.println("	     return false;	       	  ");
	out.println(" 	    }                             ");
	out.println(" 	  }                               "); 
	out.println("   else {                            ");// two chars
	out.println("      if(!((x.charAt(0) == \"0\") ||    ");
	out.println("        (x.charAt(0) == \"2\") ||    ");
	out.println("        (x.charAt(0) == \"2\") ||    ");
	out.println("        (x.charAt(0) == \"3\"))) {   ");
	out.println("         alert(\"Invalid Day date \"+x+\" \");");
	out.println("	     return false;		  ");
	out.println(" 	    }                             ");
	out.println("      if(!validateInteger(x.charAt(1))){   ");
	out.println("         alert(\"Invalid Day date \"+x+\" \");");
	out.println("	     return false;		  ");
	out.println(" 	    }                             ");
	out.println("      if(x.charAt(0) == 0 &&    "); // for 0x format
	out.println("         !(x.charAt(1) > 0 &&  "); 
	out.println("         x.charAt(1) <= 9 )){     ");
	out.println("         alert(\"Invalid Day date \"+x+\" \");");
	out.println("	     return false;		  ");
	out.println(" 	    }                             ");
	out.println("      if(x.charAt(0) == 3 &&    ");  // case 30, 31
	out.println("         x.charAt(1) > 1 ){         ");
	out.println("         alert(\"Invalid Day date \"+x+\" \");");
 	out.println("	      return false;		  ");
	out.println(" 	     }                             ");
	out.println("      }                               ");
	out.println("    }                               ");
	out.println("   return true;                     ");
	out.println("   }                                ");
	out.println("  function validateMonth(x){         ");            
	out.println("  if((x.length > 0)){                ");  
	// out.println("       alert(\" Month date \"+x+\" \");");
	out.println("   if(x.length==1){                   ");  
	out.println("    if(!validateInteger(x)){          ");
	out.println("       alert(\"Invalid Month date:\"+x+\" \");");
	out.println("	     return false;	       	  ");
	out.println(" 	    } }                            ");
	out.println("   else {                            ");// two chars
	out.println("      if( x.charAt(0) == 0 &&  "); // for 0x format
	out.println("          !x.charAt(1) >= 1) {    ");
	out.println("          alert(\"Invalid Month date:\"+x+\" \");");
	out.println("	     return false;	       	  ");
	out.println("	    }                        ");
	out.println("      if(!validateInteger(x.charAt(0)) ||  ");
	out.println("        !validateInteger(x.charAt(1))){    ");
	out.println("         alert(\"Invalid Month date \"+x+\" \");");
	out.println("	     return false;		  ");
	out.println(" 	    }                             ");
	out.println("      if( x.charAt(0) > 1 &&    ");
	out.println("          x.charAt(1) >= 0) {    ");
	out.println("       alert(\"Invalid Month date:\"+x+\" \");");
	out.println("	     return false;	       	  ");
	out.println("      }}}                            ");
	out.println("    return true;                   ");
	out.println("	}	                        ");
	out.println("  function validateYear(x){        "); 
   	out.println("  if((x.length > 0)){   ");  
	out.println("   if(!x.length==4){");  
	out.println("       alert(\"Invalid Year \"+x+\" \");");
	out.println("	     return false;	       	  ");
	out.println("    }                              ");
	out.println("    if(!validateInteger(x.charAt(0)) || ");
	out.println("       !validateInteger(x.charAt(1)) || ");
	out.println("       !validateInteger(x.charAt(2)) || ");
	out.println("       !validateInteger(x.charAt(3))){ ");
	out.println("         alert(\"Invalid Year \"+x+\" \");");
	out.println("	     return false;	       	  ");
	out.println(" 	     }                             ");
	out.println(" 	   }                               "); 
	out.println("	   return true;	         	  ");
	out.println(" 	 }                             ");
	out.println("  function validateForm(){         ");            
	out.println(" if(!validateDay(document.myForm.deathDayFrom.value)||");
	out.println("  !validateDay(document.myForm.deathDayTo.value)||");
	out.println("  !validateMonth(document.myForm.deathMonthFrom.value)||");
	out.println("  !validateMonth(document.myForm.deathMonthTo.value)||");
	out.println("  !validateYear(document.myForm.deathYearFrom.value)||");
	out.println("  !validateYear(document.myForm.deathYearTo.value)||");
	out.println("  !validateDay(document.myForm.deathDayAt.value)||");
	out.println("  !validateMonth(document.myForm.deathMonthAt.value)||");
	out.println("  !validateYear(document.myForm.deathYearAt.value)){");
	out.println("	   return false;	      	  ");
	out.println("	  }                              ");
	out.println(" if((!document.myForm.deathDayFrom.value.legth==0 ||");
	out.println("  !document.myForm.deathMonthFrom.value.length==0) && ");
	out.println("  document.myForm.deathYearFrom.value.length==0){  ");
	out.println("         alert(\"From Year need to be set \");");
	out.println("	   return false;	      	  ");
	out.println("	  }                              ");
	out.println(" if((!document.myForm.deathDayTo.value.legth==0 ||");
	out.println("  !document.myForm.deathMonthTo.value.length==0) && ");
	out.println("  document.myForm.deathYearTo.value.length==0){  ");
	out.println("         alert(\"To Year need to be set \");");
	out.println("	   return false;	      	  ");
	out.println("	  }                              ");
	out.println("    return true;                   ");
	out.println("	}	                        ");
	out.println(" </script>                         ");   
	out.println("</head><body>");

	String log_out = 
	    "<a href="+url+"RHLogout?username=" +
	    username+">Log Out </a>";
	//
	// Browsing the records
	//
	out.println("<center><h2>Interment Browsing</h2></center>");
	//
	out.println("<form name=myForm method=POST onsubmit=\"return validateForm();\">");
	out.println("<input type=hidden name=username value=" + username + 
		    "></input>");
	out.println("<center><table align=center border=1 "+
		    "cellpadding=2 cellspacing=1>");
	out.println("<tr bgcolor=#CDC9A3><td align=right>");
	out.println("<b>Sort by </b></td><td align=left>");
	out.println("<select name=sortby>");
	out.println("<option selected>Last, First Name");
	out.println("<option>Section, Lot, Name");
	out.println("<option>Book");
	out.println("<option>Death Date");
	out.println("<option>ID");
	out.println("<option>First, Last Name");
	out.println("<option>Age");
	out.println("<option>Sex");
	out.println("<option>Place of Birth");
	out.println("<option>Last Residance");
	out.println("<option>Whiteoak");
	out.println("</select></td></tr>");
	out.println("<input type=hidden name=minRecords value=0>");

	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Show Only "+
		    "</b></td><td><left>");
	out.println("<input type=text name=maxRecords value=100 size=6>");
	out.println("Records per Page</left></td></tr>");

	// section lot book page
	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Swction: "+
		    "</b></td><td><left>");
	out.println("<input type=text name=sec size=5>");
	out.println("<b>Lot: </b>");
	out.println("<input type=text name=lot size=5>");
	out.println("<b>Book: </b>");
	out.println("<input type=text name=book size=5>");
	out.println("<b>Page: </b>");
	out.println("<input type=text name=page size=5></left></td></tr>");

	// Name
	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Last Name: </b>"+
		    "</td><td><left>");
	out.println("<input type=text name=lname size=20>");
	out.println("<b>First Name: </b>");
	out.println("<input type=text name=fname size=15>");
	out.println(" <b>MI: </b>");
	out.println("<input type=text name=mi size=5></left></td></tr>");

	// Death date
	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Death Date: "+
		    "</b></td><td>");
	// one row table 
	out.println("<table border=0><tr><td valign=center>");
	// table for At
	out.println("<b>At:</b></td><td><table border=0>"); 
	out.println("<tr><td align=left>mm </td>"+
		    "<td align=left>dd  </td>"+
		    "<td align=left>yyyy </td></tr>");
	out.println("<tr><td alighn=left><input type=text name=deathMonthAt "+
		    "size=2 maxlength=2>/</td>");
	out.println("<td alighn=left><input type=text name=deathDayAt "+
		    "size=2 maxlength=2>/</td>");
	out.println("<td alighn=left><input type=text name=deathYearAt "+
		    "size=4 maxtlength=4></td></tr></table></td><td>");

	// table for from
	out.println("or, <b>From:</b></td><td><table border=0>"); 
	out.println("<tr><td align=left>mm </td>"+
		    "<td align=left>dd </td>"+
		    "<td align=left>yyyy </td></tr>");
	out.println("<tr><td alighn=left><input type=text name=deathMonthFrom "+
		    "size=2 maxlength=2>/</td>");
	out.println("<td alighn=left><input type=text name=deathDayFrom "+
		    "size=2 maxlength=2>/</td>");
	out.println("<td alighn=left><input type=text name=deathYearFrom "+
		    "size=4 maxtlength=4></td></tr></table></td><td>");

	// table for to
	out.println("<td valign=center><b>To: </b></td><td><table border=0>"); 
	out.println("<tr><td align=left>mm "+
		    "</td><td align=left>dd </td><td align=left>yyyy</tr>");
	out.println("<tr><td alighn=left><input type=text name=deathMonthTo "+
		    "size=2 maxlength=2>/</td>");
	out.println("<td alighn=left><input type=text name=deathDayTo "+
		    "size=2 maxlength=2>/</td>");
	out.println("<td alighn=left><input type=text name=deathYearTo "+
		    "size=4 maxtlength=4></td></tr></table></td></tr></table>");
	//
	out.println("</td></tr>");

	// Gender
	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Gender: </b>"+
		    "<br></td><td><left>");
	out.println("<select name=sex size=1>");
	out.println("<option selected>All");
	out.println("<option value=M>M");
	out.println("<option value=F>F");
	out.println("</select>");

	out.println(" <b>Cemetary: </b>");
	out.println("<select name=whiteoak size=1>");
	out.println("<option selected value=All>All");
	out.println("<option value=R>Rose Hill");
	out.println("<option value=W>White Oak");
	out.println("</select>");
	out.println("</left></td></tr>");

	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Place of Birth: "+
		    "</b></td><td><left>");
	out.println("<input type=text name=pob size=15>");
	out.println("<b>Last Residance </b>");
	out.println("<input type=text name=lateres size=15></td></tr>");

	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Age, </b></td><td><left>");
	out.println("<b>From: </b>");
	out.println("<input type=text name=ageFrom size=5><b> To: </b> ");
	out.println("<input type=text name=ageTo size=5> years </left>"+
		    "</td></tr>");
	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Output Type:"+
		    "</b></td><td><left><b>");

	out.println("<input type=radio name=wt value=table checked>Table");
	out.println("<input type=radio name=wt value=report>Report</left>"
		    + "</td></b></tr>");
	out.println("</table></center>");
	//out.println("<tr><td colspan=2 align=right>");

	out.println("<center><table border=0>");
	out.println("<tr><td align=right>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	out.println("<td align=right>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	out.println("</tr><tr><td align=right>&nbsp;&nbsp;&nbsp;&nbsp;"+
		    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
		    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
		    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
		    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
		    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	out.println("<td align=right><b><input type=submit value=\"Browse\" "+
		    "></b></tr></table>");
	out.println("<br>");
	out.println(log_out);
	out.println("</center>");
	out.println("</form>");

	out.println("</body>\n</html>");
	out.close();

    }
    /**
     * Shows the records that matches the users query.
     * @param req the request input stream
     * @param res the ouput stream
     * @throws    ServletException
     * @throws    IOException
     */

    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException{

	String sortby = "";
	String id = "";
	String sec = "";
	String lot = "";
	String book = "";
	String page = "", pagenum="";
	String death = "";
	String lname = "";
	String fname = "";
	String mi = "";
	String pob = "";
	String lateres = "";
	String ageFrom="", ageTo="";
	String sex = "";
	String whiteoak = "";
	String deathDayFrom = "",
	    deathMonthFrom = "",
	    deathYearFrom = "",
	    deathDayTo = "",
	    deathMonthTo = "",
	    deathYearTo = "",
	    deathDayAt = "",
	    deathMonthAt = "",
	    deathYearAt = "";

	String[] repItem = {"Name", "Section","Lot","Age", 
			    "Sex","Date of Death","Place of Birth",
			    "Place of Residence","Cemetary","Book",
			    "Page","Notes"}; 
	String[] tableTitle={"Id","Section","Lot","Book","Page","Name ",
			     "Death","Place of Residence",
			     "Place of Birth","Age","Gender","Cemetary",
			     "&nbsp;&nbsp;Notes &nbsp;&nbsp;"};

	res.setStatus(HttpServletResponse.SC_OK);
	res.setContentType("text/html");
	os = res.getWriter();
	int maxRecords = 100, minRecords = 0, row, incr=100;
	Enumeration values = req.getParameterNames();
	// obtainProperties(os);
	String name, value, wt="table";
	boolean rangeFlag = true, showNext=true;

	os.println("<html>");

	while (values.hasMoreElements()){
	    name = ((String)values.nextElement()).trim();
	    value = (req.getParameter(name)).trim();
	    if(!name.equals("username") && !value.equals(""))
		value = value.toUpperCase();
	    //
	    if (name.equals("username")){
		username = value;
	    }
	    else if (name.equals("wt")){
		wt = value;
	    }
	    else if (name.equals("maxRecords")){
		try{
		    maxRecords = Integer.parseInt(value);
		}catch(Exception ex){};
	    }
	    else if (name.equals("minRecords")){
		try{
		    minRecords = Integer.parseInt(value);
		}catch(Exception ex){};
	    }
	    else if (name.equals("id")){
		id = value;
	    }
	    else if (name.equals("sec")){
		sec = value;
	    }
	    else if (name.equals("lot")){
		lot = value;
	    }
	    else if (name.equals("book")){
		book = value;
	    }
	    else if (name.equals("page")){
		pagenum = value;
	    }
	    else if (name.equals("deathDayFrom")){
		deathDayFrom = value;
	    }
	    else if (name.equals("deathMonthFrom")){
		deathMonthFrom = value;
	    }
	    else if (name.equals("deathYearFrom")){
		deathYearFrom = value;
	    }
	    else if (name.equals("deathDayTo")){
		deathDayTo = value;
	    }
	    else if (name.equals("deathMonthTo")){
		deathMonthTo = value;
	    }
	    else if (name.equals("deathYearTo")){
		deathYearTo = value;
	    }
	    else if (name.equals("deathDayAt")){
		deathDayAt = value;
	    }
	    else if (name.equals("deathMonthAt")){
		deathMonthAt = value;
	    }
	    else if (name.equals("deathYearAt")){
		deathYearAt = value;
	    }
	    else if (name.equals("lname")){
		lname = value;
	    }
	    else if (name.equals("fname")){
		fname = value;
	    }
	    else if (name.equals("mi")){
		mi = value;
	    }
	    else if (name.equals("pob")){
		pob = value;
	    }
	    else if (name.equals("lateres")){
		lateres = value;
	    }
	    else if (name.equals("ageFrom")){
		ageFrom = value;
	    }
	    else if (name.equals("ageTo")){
		ageTo = value;
	    }
	    else if (name.equals("sex")){
		sex = value;
	    }
	    else if (name.equals("whiteoak")){
		whiteoak = value;
	    }
	    else if (name.equals("sortby")){
		sortby = value;
	    }
	    else {
		System.err.println("Unknown choice "+name+" "+value);
	    }
	}
	try{
	    databaseConnect();
	    if(minRecords > maxRecords){ //swap
		int temp = minRecords;
		minRecords = maxRecords;
		maxRecords = temp;
	    }
	    os.println("<head><title>Browsing Records" + 
		       "</title></head>");
	    os.println("<body>");
	    os.println("<form name=anyform>");
	    os.println("<input type=hidden name=username value=" + username + 
			"></input>");
	    os.println("</form>");
	    String back_to_main = 
		"<a href="+url+"RHUserMenu?username="+
		username+">" +
		"Back to User Menu</a>";
	    String back_to_browse = 
		"<a href="+url+"RoseBrowse?username="+
		username+">" +
		"Back to Browse </a>";
	    String log_out = "<a href="+url+
		"RHLogout?username="+
		username+">Log Out </a>";
	    os.println("<center><h2>Parks and Recreation </h2>");
	    os.println("<font color=blue><h2>Rose Hill Cemetary </h2>"+
		       "</font></center>");
	    /*
	    os.println(back_to_main+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
		       "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
		       "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+log_out);
	    */
	    os.println("<br>");
	    //
	    // check where clause, it is common for table and report
	    //
	    Vector wherecases = new Vector();
	    boolean andFlag = false;
	    if(!id.equals("")){
		wherecases.addElement(" id='"+id+"'");
		andFlag = true;
	    }	    
	    if(!sec.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(sec.indexOf("%") > -1)
		    str += "sec like '"+sec+"'";
		else
		    str += "sec='"+sec+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	   
	    if(!lot.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(lot.indexOf("%") > -1)
		    str += "lot like '"+lot+"'";
		else
		    str += "lot='"+lot+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	  
	    if(!book.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(book.indexOf("%") > -1)
		    str += "book like '"+book+"'";
		else
		    str += "book='"+book+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	  
	    if(!pagenum.equals("")){
		String str="";
		if(andFlag) str = " and ";
		str += "pagenum='"+pagenum+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	  
	    if(!lname.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(lname.indexOf("%") > -1)
		    str += "lname like '"+lname+"'";
		else
		    str += "lname='"+lname+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	  
	    if(!fname.equals("")){

		String str="";
		if(andFlag) str = " and ";
		if(fname.indexOf("%") > -1)
		    str += "fname like '"+fname+"'";
		else
		    str += "fname='"+fname+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	  
	    if(!mi.equals("")){
		String str="";
		if(andFlag) str = " and ";
		str += "mi='"+mi+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }
	    if(!pob.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(pob.indexOf("%") > -1)
		    str += "pob like '"+pob+"'";
		else
		    str += "pob='"+pob+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	
	    if(!lateres.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(lateres.indexOf("%") > -1)
		    str += "lateres like '"+lateres+"'";
		else
		    str += "lateres='"+lateres+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	
	    if(!sex.equals("ALL")){
		String str="";
		if(andFlag) str = " and ";
		str += "sex='"+sex+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	
	    if(!whiteoak.equals("ALL")){
		String str="";
		if(andFlag) str = " and ";
		str += "whiteoak='"+whiteoak+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	
	    if(!ageFrom.equals("")){
		String str="";
		if(andFlag) str = " and ";
		str += "to_number('"+ageFrom+"') <= to_number(age)";
		wherecases.addElement(str);
		andFlag = true;
	    }
	    if(!ageTo.equals("")){
		String str="";
		if(andFlag) str = " and ";
		str += "to_number('"+ageTo+"') >= to_number(age)";
		wherecases.addElement(str);
		andFlag = true;
	    }
	    if(!deathDayAt.equals("") || !deathMonthAt.equals("") || 
	       !deathYearAt.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(!deathDayAt.equals("") && !deathMonthAt.equals("") && 
		   !deathYearAt.equals("")){
		    str += "to_date('"+ deathMonthAt+"/"+deathDayAt+"/"+ 
			deathYearAt+ "','MM/DD/YYYY') = death";
		}
		else if(!deathDayAt.equals("") && !deathMonthAt.equals("")){
		    str += "to_char(to_date('"+ deathMonthAt+"/"+deathDayAt+ 
			"','MM/DD'),'MM/DD') = to_char(death,'MM/DD')";
		} 
		else if(!deathDayAt.equals("") && !deathYearAt.equals("")){
		    str += "to_char(to_date('"+ deathDayAt+"/"+deathYearAt+ 
			"','DD/YYYY'),'dd/yyyy') = to_char(death,'dd/yyyy')";
		} 
		else if(!deathMonthAt.equals("") && !deathYearAt.equals("")){
		    str += "to_char(to_date('"+ deathMonthAt+"/"+deathYearAt+ 
			"','MM/YYYY'),'mm/yyyy') = to_char(death,'mm/yyyy')";
		} 
		else if(!deathDayAt.equals("")){
		    str += "to_char(to_date('"+ deathDayAt+
			"','dd'),'dd') = to_char(death,'dd')";
		} 
		else if(!deathMonthAt.equals("")){
		    str += "to_char(to_date('"+ deathMonthAt+
			"','mm'),'mm') = to_char(death,'mm')";
		} 
		else if(!deathYearAt.equals("")){
		    str += "to_char(to_date('"+ deathYearAt+
			"','yyyy'),'yyyy') = to_char(death,'yyyy')";
		} 
		if(!str.equals("")){
		    wherecases.addElement(str);
		    andFlag = true;
		}
	    }
	    if(!deathDayFrom.equals("") || !deathMonthFrom.equals("") || 
	       !deathYearFrom.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(!deathDayFrom.equals("") && !deathMonthFrom.equals("") && 
		   !deathYearFrom.equals("")){
		    str += "to_date('"+ deathMonthFrom+"/"+deathDayFrom+"/"+ 
			deathYearFrom+ "','MM/DD/YYYY') <= death";
		}
		else if(!deathMonthFrom.equals("") && !deathYearFrom.equals("")){
		    str += "to_date('"+ deathMonthFrom+"/"+deathYearFrom+ 
			"','MM/YYYY') <= death";
		} 
		else if(!deathYearFrom.equals("")){
		    str += "to_date('"+ deathYearFrom +
			"','yyyy') <= death";
		} 
		if(!str.equals("")){
		    wherecases.addElement(str);
		    andFlag = true;
		}
	    }
	    if(!deathDayTo.equals("") || !deathMonthTo.equals("") || 
	       !deathYearTo.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(!deathDayTo.equals("") && !deathMonthTo.equals("") && 
		   !deathYearTo.equals("")){
		    str += "to_date('"+ deathMonthTo+"/"+deathDayTo+"/"+ 
			deathYearTo+ "','MM/DD/YYYY') >= death";
		}
		else if(!deathMonthTo.equals("") && !deathYearTo.equals("")){
		    str += "to_date('"+ deathMonthTo+"/"+deathYearTo+ 
			"','MM/YYYY') >= death";
		} 
		else if(!deathYearTo.equals("")){
		    str += "to_date('"+ deathYearTo+
			"','yyyy') >= death";
		} 
		if(!str.equals("")){
		    wherecases.addElement(str);
		    andFlag = true;
		}
	    }

	    String query2 = "select count(*) from rosehill "; 
	    String added ="lname,fname";
	    sortby = sortby.toLowerCase();// id, lot, age, book, sex, whiteoak
	    if(sortby.startsWith("section")) 
		sortby = "sec,lot,"+added;
	    else if(sortby.equals("book")) 
		sortby = "book,pagenum,"+added;
	    else if(sortby.startsWith("last")) 
		sortby =added+",sec,lot,death";
	    else if(sortby.startsWith("first")) 
		sortby ="fname,lname,sec,lot";
	    else if(sortby.startsWith("death")) 
		sortby ="death,"+added+",sec,lot";
	    else if(sortby.equals("place of birth")) 
		sortby ="pob,"+added+",sec,lot";
	    else if(sortby.equals("last residance")) 
		sortby ="lateres,"+added+",sec,lot";
	    else sortby = sortby+","+added;
	    //
	    // Table presentation first
	    //
	    if(wt.equals("TABLE")){
		String query = "select id,sec,lot,book,pagenum,"+
		    "initcap(lname) ||', '|| initcap(fname) ||' '|| mi,"+
		    " to_char(death,'MM/DD/YYYY'),"+
		    "initcap(lateres) lateres, initcap(pob) pob, age, "+
		    "sex, whiteoak, N2 from rosehill ";
	    
		//   String query = "select * from rosehill ";
		if(wherecases.size() > 0){
		    query += " where ";
		    query2 += " where ";
		    for (int c = 0; c < wherecases.size(); c++){
			query  += wherecases.elementAt(c).toString();
			query2 += wherecases.elementAt(c).toString();
		    }
		}

		query += " order by "+sortby;
		if(debug){
		    os.println("<font size=3>" + query + "</font>");
		    // System.err.println(query);
		}
		rs = stmt.executeQuery(query2);
		int total = 0;
		if(rs.next()){
		    total = rs.getInt(1);
		}
		os.println("Matching total records :"+ total + 
			   "<br>");
		if(total < maxRecords && minRecords == 0){ 
		    os.println("Showing all the: "+ total + 
			       " records <br>");
		    showNext = false;
		}
		else if(total <= maxRecords && total > minRecords){
		    os.println("Showing the records from:"+ minRecords +
			       " to " + total+ "<br>");
		    showNext = false;
		}
		else if(total > maxRecords && total > minRecords){
		    os.println("Showing the records from:"+ minRecords +
			       " to " + maxRecords+ "<br>");
		    incr = maxRecords - minRecords;
		    
		}
		else if(total < minRecords){
		    os.println("Error in setting the \"From\" field in "+
			       "\"Show Records\", go back and "+
			       "reset this field. <br>");
		    rangeFlag = false;
		    showNext = false;
		}
		os.println("<br><font color=red><i>Note: To get the map of "+
			   "Rose Hill and the related Section click on the  "+
			   "link under the Section column below."+
			   "</i></font><br>");	
		if(rangeFlag){
		    rs = stmt.executeQuery(query);
		    os.println("<table border=0 cellspanning=1 "+
			       "cellpadding=1>");
		    
		    int colcnt = rs.getMetaData().getColumnCount();
		    // System.err.println(colcnt);

		    row = 0;
		    int  rowsSent = 0;
		    while (rs.next()){
			
			if(row >= minRecords && row <= maxRecords){
			    if(row%10 == 0){
				os.println("<tr BGCOLOR=#666666>");
				for (int c = 0; c < tableTitle.length; c++) {
				    os.println("<th><FONT Size=-1>");
				    os.println(tableTitle[c]);
				    os.println("</FONT></th>");
				}
				os.println("</tr>");
			    }
			
			    if(row%2 == 0)
				os.println("<tr bgcolor=white>");
			    else
				os.println("<tr bgcolor=#C0C3D0>");
			    for (int c = 0; c < colcnt; c++){
				os.println("<td>");
				String that = rs.getString(c+1);
				//  System.err.print(that+" ");
				if (c == 0){
				os.println("<a href="+url + 
					   "RoseZoom?id=" 
					   + that + "&username="+username+">"
					   + that + "</a>");
				}
				else if(c == 1){
				    String lott = rs.getString(c+2);
				    os.println("<a href="+url + 
					       "showImage?sec=" 
					       + that + 
					       "&lot="+lott+
					       "&username="+username+">"
					       + that + "</a>");
				}
				else{
				    if (that != null) 
					os.println(that);
				    else
					os.println("&nbsp;&nbsp;");
				    os.println("</td>");
				}
			    }
			    os.println("</tr>");
			    rowsSent++;
			}
			row++;
			if(row > maxRecords) break;
			// to accelerate the browsing especially in 
			// large output
			if(rowsSent%20 == 0) os.flush();
		    }
		    os.println("</table>");
		}
	    }
	    else{ //report
		//
		// Report presentation
		//
		String query = "select initcap(lname)||', '||"+
		    "initcap(fname) ||' '||mi,"+    // 1
		    "sec, lot, age, sex,"+          // 5 
		    "to_char(death,'Mon DD, YYYY'),"+ // 6
		    "initcap(pob) pob,"+            // 7
		    "initcap(lateres) lateres,"+    // 8
		    "whiteoak,book,pagenum,N2 from rosehill ";
	    
		if(wherecases.size() > 0){
		    query += " where ";
		    query2 += " where ";
		    for (int c = 0; c < wherecases.size(); c++){
			query  += wherecases.elementAt(c).toString();
			query2 += wherecases.elementAt(c).toString();
		    }
		}
		query += " order by "+sortby;
		if(debug){
		    os.println("<font size=3>" + query + "</font>");
		    // System.err.println(query);
		}
		rs = stmt.executeQuery(query2);
		int total = 0;
		if(rs.next()){
		    total = rs.getInt(1);
		}

		os.println("Matching total records :"+ total + 
			   "<br>");
		if(total < maxRecords && minRecords == 0){ 
		    os.println("Showing all the: "+ total + 
			       " records <br>");
		    showNext = false;
		}
		else if(total <= maxRecords && total > minRecords){
		    os.println("Showing the records from:"+ minRecords +
			       " to " + total+ "<br>");
		    showNext = false;
		}
		else if(total > maxRecords && total > minRecords){
		    os.println("Showing the records from:"+ minRecords +
			       " to " + maxRecords+ "<br>");
		    incr = maxRecords - minRecords;
		    
		}
		else if(total < minRecords){
		    os.println("Error in setting the \"From\" field in "+
			       "\"Show Records\", go back and "+
			       "reset this field. <br>");
		    rangeFlag = false;
		    showNext = false;
		}
		
		//System.err.println("total: "+total);
		//System.err.println("query: "+query);
		
		if(rangeFlag){
		    rs = stmt.executeQuery(query);
		    int colcnt = rs.getMetaData().getColumnCount();
		    //System.err.println("column count: "+colcnt);
		    os.println("<center><Font size=+2>Rose Hill Cemetary"+
			       "</Font><br>");
		    os.println("<Font size=+1>Interment Report</Font><br>"+
			       "<br><br>");
		    os.println("<hr></center>");

		    //  System.out.println("After rs.next");
		    row = 0;
		    int rowsSent = 0;
		    while (rs.next()){
			if(row >= minRecords && row <= maxRecords){
			    os.println("<table border=0 cellspacing=1 "+
				       "cellpadding=1>");
			    // section
			    int c = 0;
			    String that = "";
			    for(int i=0; i<colcnt;i++){
				that = rs.getString(i+1);
				if(that != null){
				    if(i == 8){
					if(that.equals("R")) 
					    that ="Rose Hill";
					else 
					    that ="White Oak";
				    }
				    //System.err.println(" "+i+" "+repItem[i]);
				    writeItem(that, repItem[i]);
				}
			    }
			    os.println("</table>");
			    os.println("<br><hr>");
			    rowsSent++;
			    if(rowsSent % 20 == 0) os.flush();
			}
			row++;
			if(row > maxRecords) break;
		    }
		}
	    }
	    //
	    // end both table or report
	    //
	    if(showNext){
		os.println("<form method=post>");
		os.println("<input type=hidden name=username value=" + 
			   username + "></input>");
		os.println("<input type=hidden name=sortby value=\"" + 
			   sortby + "\"></input>");
		os.println("<input type=hidden name=minRecords value=" + 
			   (minRecords+incr) + "></input>");
		os.println("<input type=hidden name=maxRecords value=" + 
			   (maxRecords+incr) + "></input>");
		if(!id.equals("")){
		    os.println("<input type=hidden name=id value=" + id +
			       "></input>");
		}
		if(!sec.equals("")){
		    os.println("<input type=hidden name=sec value=" + 
			       sec + "></input>");
		}
		if(!lot.equals("")){
		    os.println("<input type=hidden name=lot value=" + 
			       lot + "></input>");
		}
		if(!book.equals("")){
		    os.println("<input type=hidden name=book value=" + 
			       book+"></input>");
		}
		if(!pagenum.equals("")){
		    os.println("<input type=hidden name=page value=" + 
			       pagenum+"></input>");
		}
		if(!lname.equals("")){
		    os.println("<input type=hidden name=lname value=" + 
			       lname+"></input>");
		}
		if(!fname.equals("")){
		    os.println("<input type=hidden name=fname value=" + 
			       fname+"></input>");
		}
		if(!mi.equals("")){
		    os.println("<input type=hidden name=mi value=" + 
			       mi+"></input>");
		}
		if(!deathDayFrom.equals("")){
		    os.println("<input type=hidden name=deathDayFrom value=" + 
			       deathDayFrom+"></input>");
		}
		if(!deathMonthFrom.equals("")){
	          os.println("<input type=hidden name=deathMonthFrom value=" + 
			       deathMonthFrom+"></input>");
		}
		if(!deathYearFrom.equals("")){
	          os.println("<input type=hidden name=deathYearFrom value=" + 
			       deathYearFrom+"></input>");
		}
		if(!deathDayTo.equals("")){
		    os.println("<input type=hidden name=deathDayTo value=" + 
			       deathDayTo+"></input>");
		}
		if(!deathMonthTo.equals("")){
	          os.println("<input type=hidden name=deathMonthTo value=" + 
			       deathMonthTo+"></input>");
		}
		if(!deathYearTo.equals("")){
	          os.println("<input type=hidden name=deathYearTo value=" + 
			       deathYearTo+"></input>");
		}
		if(!deathDayAt.equals("")){
		    os.println("<input type=hidden name=deathDayAt value=" + 
			       deathDayAt+"></input>");
		}
		if(!deathMonthAt.equals("")){
	          os.println("<input type=hidden name=deathMonthAt value=" + 
			       deathMonthAt+"></input>");
		}
		if(!deathYearAt.equals("")){
	          os.println("<input type=hidden name=deathYearAt value=" + 
			       deathYearAt+"></input>");
		}
		if(!sex.equals("")){
		    os.println("<input type=hidden name=sex value=" + 
			       sex+"></input>");
		}
		if(!whiteoak.equals("")){
		    os.println("<input type=hidden name=whiteoak value=" + 
			       whiteoak+"></input>");
		}
		if(!pob.equals("")){
		    os.println("<input type=hidden name=pob value=" + 
			       pob+"></input>");
		}
		if(!lateres.equals("")){
		    os.println("<input type=hidden name=lateres value=" + 
			       lateres+"></input>");
		}
		if(!ageFrom.equals("")){
		    os.println("<input type=hidden name=ageFrom value=" + 
			       ageFrom+"></input>");
		}
		if(!ageTo.equals("")){
		    os.println("<input type=hidden name=ageTo value=" + 
			       ageTo+"></input>");
		}
		os.println("<input type=hidden name=wt value=" + 
			   wt+"></input>");
		// os.println("<br>"); 
		// os.println("<hr>"); 
		os.println("<center><table ><tr><td>"); 
		os.println("<input type=submit value=\"Next Page\"" + 
			   "></input>");
		os.println("</td></tr></table></center>"); 
		os.println("</form>");
	    }
	    os.println(back_to_browse +"<br>"+back_to_main +"<br>"+ 
		       log_out);
	    os.println("</body>");
	    os.println("</html>");
	}
	catch (Exception ex){
	    System.err.println(ex);
	    os.println(ex);
	}
	os.flush();
	os.close();
	databaseDisconnect();

    }
    /**
     * Formats and writes a tupple of title-item to a table.
     * @param that the item.
     * @param title the title of the item.
     */
    void writeItem(String that, String itemTitle){
	os.println("<tr><td align=right><b>");
	os.println(itemTitle + ": </b></td><td align=left>");
	os.println(that+"</td></tr>");
    }
    /**
     * Connects to the rosehill database.
     */
    public void databaseConnect(){
	//obtainProperties();
	try {
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    con = DriverManager.
		getConnection(Deed.dbConnectStr,
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























































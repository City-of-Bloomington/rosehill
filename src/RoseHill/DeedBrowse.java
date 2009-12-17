package RoseHill;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import RoseHill.Deed;

/**
 * The search engine for the  deed records in the databse.
 * @author Walid Sibo
 * @version %I%,%G%
 */
public class DeedBrowse extends HttpServlet{

    Connection con;
    Statement stmt;

    ResultSet rs;
    PrintWriter os, out;
    String iniFile = Deed.iniFile;
    String username = "";
    String sortby = "";
    String accessUser = Deed.accessUser;
    String accessPass = Deed.accessPass;

    String url = Deed.url;
    boolean debug = Deed.debug;
    /**
     * Shows the deed search form to the user.
     * @param req the request input stream
     * @param res the ouput stream
     * @throws    ServletException
     * @throws    IOException
     */
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException{
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
	String log_out = 
	    "<a href="+url+"RHLogout?username=" +
	    username+">Log Out </a>";
	//
	// Browsing the records
	//
	out.println("<head><title>Browsing </title>     ");
	out.println(" <script language=Javascript>      ");
	out.println("  function validateInteger(x){     ");            
	out.println("	if( x >= 0 || x <= 9 ){         ");  
	out.println("	            return true;        ");
	out.println(" 	        }                       ");
 	out.println("	       return false;	        ");
	out.println(" 	   }                            ");
	out.println("  function validateDay(x){         ");            
	out.println("  if((x.length > 0)){              ");  
	out.println("   if(x.length==1){                ");  
	out.println("    if(!validateInteger(x)){       ");
	out.println("       alert(\"Invalid Day date \"+x+\" \");");
	out.println("	     return false;	       	  ");
	out.println(" 	    }                             ");
	out.println(" 	  }                               "); 
	out.println("   else {                            ");// two digit
	out.println("      if(!(x.charAt(0) >= 0 &&       "); 
	out.println("        x.charAt(0) <= 3 )) {        ");
	out.println("         alert(\"Invalid Day date \"+x+\" \");");
	out.println("	     return false;		  ");
	out.println(" 	    }                             ");
	out.println("      if(!validateInteger(x.charAt(1))){ "); 
	out.println("         alert(\"Invalid Day date \"+x+\" \");");
	out.println("	     return false;		  ");
	out.println(" 	    }                             ");
	out.println("      if(x.charAt(0) == 0 &&    "); // for 0x format
	out.println("         !(x.charAt(1) > 0 &&       "); 
	out.println("         x.charAt(1) <= 9 )){       ");
	out.println("         alert(\"Invalid Day date \"+x+\" \");");
	out.println("	     return false;		  ");
	out.println(" 	    }                             ");
	out.println("      if(x.charAt(0) == 3 &&    ");  // case 30, 31
	out.println("         x.charAt(1) > 1 ){          ");
	out.println("         alert(\"Invalid Day date \"+x+\" \");");
	out.println("	     return false;		   ");
	out.println(" 	     }                             ");
	out.println("      }                               ");
	out.println("    }                                 ");
	out.println("   return true;                       ");
	out.println("   }                                  ");
	out.println("  function validateMonth(x){          ");            
	out.println("  if((x.length > 0)){                 ");  
	// out.println("       alert(\" Month date \"+x+\" \");");
	out.println("   if(x.length==1){                   ");  
	out.println("    if(!validateInteger(x)){          ");
	out.println("       alert(\"Invalid Month date \"+x+\" \");");
	out.println("	     return false;	       	   ");
	out.println(" 	    } }                            ");
	out.println("   else {                            ");// two chars
	out.println("      if( x.charAt(0) == 0 &&  "); // for 0x format
	out.println("          !x.charAt(1) >= 1) {      ");
	out.println("       alert(\"Invalid Month date:\"+x+\" \");");
	out.println("	     return false;	       	 ");
	out.println("	    }                            ");
	out.println("      if( x.charAt(0) > 1 &&        ");
	out.println("          x.charAt(1) >= 0) {       ");
	out.println("       alert(\"Invalid Month date:\"+x+\" \");");
	out.println("	     return false;	         ");
	out.println("      }}}                           ");
	out.println("    return true;                    ");
	out.println("	}	                         ");
	out.println("  function validateYear(x){         "); 
   	out.println("  if((x.length > 0)){               ");  
	out.println("   if(!x.length==4){                ");  
	out.println("       alert(\"Invalid Year \"+x+\" \");");
	out.println("	     return false;	       	  ");
	out.println("    }                                ");
	out.println("    if(!validateInteger(x.charAt(0)) || ");
	out.println("       !validateInteger(x.charAt(1)) || ");
	out.println("       !validateInteger(x.charAt(2)) || ");
	out.println("       !validateInteger(x.charAt(3))){ ");
	out.println("         alert(\"Invalid Year \"+x+\" \");");
	out.println("	     return false;	       	   ");
	out.println(" 	     }                             ");
	out.println(" 	   }                               "); 
	out.println("	   return true;	         	   ");
	out.println(" 	 }                                 ");
	out.println("  function validateForm(){            ");            
	out.println(" if(!validateDay(document.myForm.dayFrom.value)||");
	out.println("  !validateDay(document.myForm.dayTo.value)||");
	out.println("  !validateMonth(document.myForm.monthFrom.value)||");
	out.println("  !validateMonth(document.myForm.monthTo.value)||");
	out.println("  !validateYear(document.myForm.yearFrom.value)||");
	out.println("  !validateYear(document.myForm.yearTo.value)){");
	out.println("	   return false;	      	  ");
	out.println("	  }                               ");
	out.println(" if((!document.myForm.dayFrom.value.legth==0 ||");
	out.println("  !document.myForm.monthFrom.value.length==0) && ");
	out.println("  document.myForm.yearFrom.value.length==0){  ");
	out.println("         alert(\"From Year need to be set \");");
	out.println("	   return false;	      	  ");
	out.println("	  }                               ");
	out.println(" if((!document.myForm.dayTo.value.legth==0 ||");
	out.println("  !document.myForm.monthTo.value.length==0) && ");
	out.println("  document.myForm.yearTo.value.length==0){  ");
	out.println("         alert(\"To Year need to be set \");");
	out.println("	   return false;	      	  ");
	out.println("	  }                               ");
	out.println("    return true;                     ");
	out.println("	}	                          ");
	out.println(" </script>                           ");   
	out.println("</head><body>");
	out.println("<center><h2>Deed Browsing</h2></center>");
	out.println("<form name=myForm method=POST onsubmit=\"return validateForm();\">");
	out.println("<input type=hidden name=username value=" + username + 
		    "></input>");
	out.println("<center><table align=center border=1 "+
		    "cellpadding=2 cellspacing=1>");
	out.println("<tr bgcolor=#CDC9A3><td align=right>");
	out.println("<b>Sort by </b></td><td align=left>");
	out.println("<select name=sortby>");
	out.println("<option selected>Name 1, Name 2");
	out.println("<option>Section, Names");
	out.println("<option>Date of Issue");
	out.println("<option>Name 2, Name 1");

	out.println("</select></td></tr>");
	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Show only "
		    +"</b></td><td><left>");
	out.println("<input type=hidden name=minRecords value=0>");
	out.println("<input type=text name=maxRecords value=100 size=6>"+
		    "Records per page.</td></tr>");
	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Section: </b>");
	out.println("</td><left><td>");
	out.println("<input type=text name=sec size=5>");
	out.println("<b>Lot: </b>");
	out.println("<input type=text name=lot size=5></left></td></tr>");

	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Last Name 1</b>"+
		    "</td><td><left>");
	out.println("<input type=text name=lname size=20>");
	out.println("<b>First Name 1</b>");
	out.println("<input type=text name=fname size=20>");
	out.println("<b>MI </b>");
	out.println("<input type=text name=mi size=5></left></td></tr>");

	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Last Name 2</b>"+
		    "</td><td><left>");
	out.println("<input type=text name=lname2 size=20>");
	out.println("<b>First Name 2</b>");
	out.println("<input type=text name=fname2 size=20>");
	out.println("<b>MI 2</b>");
	out.println("<input type=text name=mi2 size=5></left></td></tr>");

	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Date of Issue "+
		    "</b></td><td>"); 
	/*
	out.println("<td><table border=0>"); 
	out.println("<tr><td align=left><b>From </b>&nbsp;<font "+
		    "size=1>mm/dd/yyyy</font></td>");
	out.println("<td align=left><b>To </b>&nbsp;<font "+
		    "size=1>mm/dd/yyyy</font></td></tr>");
;	out.println("<tr><td alighn=left><input type=text name=issueFrom "+
		    "size=11></td>");
;	out.println("<td alighn=left><input type=text name=issueTo "+
		    "size=11></td></tr></table></td></tr>");
	*/
	// one row table 
	out.println("<table border=0><tr><td valign=center>");

	// table for from
	out.println("<b>From:</b></td><td><table border=0>"); 
	out.println("<tr><td align=left>mm </td>"+
		    "<td align=left>dd </td>"+
		    "<td align=left>yyyy </td></tr>");
	out.println("<tr><td alighn=left><input type=text name=monthFrom "+
		    "size=2 maxlength=2>/</td>");
	out.println("<td alighn=left><input type=text name=dayFrom "+
		    "size=2 maxlength=2>/</td>");
	out.println("<td alighn=left><input type=text name=yearFrom "+
		    "size=4 maxtlength=4></td></tr></table></td><td>");
	// table for to
	out.println("<td valign=center><b>To: </b></td><td><table border=0>"); 
	out.println("<tr><td align=left>mm "+
		    "</td><td align=left>dd </td><td align=left>yyyy</tr>");
	out.println("<tr><td alighn=left><input type=text name=monthTo "+
		    "size=2 maxlength=2>/</td>");
	out.println("<td alighn=left><input type=text name=dayTo "+
		    "size=2 maxlength=2>/</td>");
	out.println("<td alighn=left><input type=text name=yearTo "+
	          "size=4 maxtlength=4></td></tr></table></td></tr></table>");
	out.println("</td></tr>");
	//
	//
	out.println("<tr bgcolor=#CDC9A3><td align=right><b>Output Type "+
		    "</b></td><td><b>"); 
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
	out.println("<td align=right><input type=submit value=Browse "+
		    "></td></tr></table>");
	out.println("<br>");
	out.println(log_out);
	out.println("</center>");
	out.println("</form>");

	out.println("</body>\n</html>");
	out.close();

    }
   /**
     * Presents the output of the search to the user in case there is a match. 
     * @param req the request input stream
     * @param res the ouput stream
     * @throws    ServletException
     * @throws    IOException
     */
    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException{
	String[] repItem = {"Section: ","Lot: ","Deed Owner(s): ", " "," ",
			    " ", " "," ", // second name
			    "Date of Issue: ",
			    "Notes: "}; 
	String[] tableItem = {"Id","Section","Lot","Name 1", "Name 2",
			      "Date of Issue ",
			      "&nbsp;Notes&nbsp;&nbsp;&nbsp;&nbsp; "}; 
    
	res.setStatus(HttpServletResponse.SC_OK);
	res.setContentType("text/html");
	os = res.getWriter();
	int maxRecords = 100, minRecords = 0, row;
	Enumeration values = req.getParameterNames();
	//obtainProperties(os);
	String name, value, wt="table";
	boolean rangeFlag = true, showNext=true;
	int incr = 0;
	String id = "";
	String sec = "";
	String lot = "";
	String book = "";
	String issueFrom = "";
	String issueTo = "";
	String lname = "";
	String fname = "";
	String mi = "";
	String lname2 = "";
	String fname2 = "";
	String mi2 = "";
	String date_issue="";
	String monthFrom = "", dayFrom = "", yearFrom = "";
	String monthTo = "", dayTo = "", yearTo = "";

	os.println("<html>");

	while (values.hasMoreElements()){
	    name = ((String)values.nextElement()).trim();
	    value = (req.getParameter(name)).trim();
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
		sec = value.toUpperCase() ;
	    }
	    else if (name.equals("lot")){
		lot = value.toUpperCase();
	    }
	    else if (name.equals("lname")){
		lname = value.toUpperCase();
	    }
	    else if (name.equals("fname")){
		fname = value.toUpperCase();
	    }
	    else if (name.equals("mi")){
		mi = value.toUpperCase();
	    }
	    else if (name.equals("lname2")){
		lname2 = value.toUpperCase();
	    }
	    else if (name.equals("fname2")){
		fname2 = value.toUpperCase();
	    }
	    else if (name.equals("mi2")){
		mi2 = value.toUpperCase();
	    }
	    else if (name.equals("monthFrom")){
		monthFrom = value;
	    }
	    else if (name.equals("dayFrom")){
		dayFrom = value;
	    }
	    else if (name.equals("yearFrom")){
		yearFrom = value;
	    }
	    else if (name.equals("monthTo")){
		monthTo = value;
	    }
	    else if (name.equals("dayTo")){
		dayTo = value;
	    }
	    else if (name.equals("yearTo")){
		yearTo = value;
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
	    os.println("<head><title>Browsing Deed Records" + 
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
		"<a href="+url+"DeedBrowse?username="+
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
	    if(!lname.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(lname.indexOf("%") > -1)
		    str += "lname like '"+lname+"'";
		else
		    str += "lname1='"+lname+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }
	    if(!fname.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(fname.indexOf("%") > -1)
		    str += "fname1 like '"+fname+"'";
		else
		    str += "fname1='"+fname+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	  
	    if(!mi.equals("")){
		String str="";
		if(andFlag) str = " and ";
		str += "mi1='"+mi+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	
	    if(!lname2.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(lname2.indexOf("%") > -1)
		    str += "lname2 like '"+lname2+"'";
		else
		    str += "lname2='"+lname2+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	  
	    if(!fname2.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(fname2.indexOf("%") > -1)
		    str += "fname2 like '"+fname2+"'";
		else
		    str += "fname2='"+fname2+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	  
	    if(!mi2.equals("")){
		String str="";
		if(andFlag) str = " and ";
		str += "mi2='"+mi2+"'";
		wherecases.addElement(str);
		andFlag = true;
	    }	
	    if(!issueFrom.equals("")){
		String str="";
		if(andFlag) str = " and ";
		str += "to_date('"+ issueFrom + 
		    "','MM/DD/YYYY') <= date_issue";
		wherecases.addElement(str);
		andFlag = true;
	    }
	    if(!issueTo.equals("")){
		String str="";
		if(andFlag) str = " and ";
		str += "to_date('"+issueTo+
		    "','MM/DD/YYYY') >= date_issue";
		wherecases.addElement(str);
		andFlag = true;
	    }
	    if(!dayFrom.equals("") || !monthFrom.equals("") || 
	       !yearFrom.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(!dayFrom.equals("") && !monthFrom.equals("") && 
		   !yearFrom.equals("")){
		    str += "to_date('"+ monthFrom+"/"+dayFrom+"/"+ 
			yearFrom+ "','MM/DD/YYYY') <= date_issue";
		}
		else if(!monthFrom.equals("") && !yearFrom.equals("")){
		    str += "to_date('"+ monthFrom+"/"+yearFrom+ 
			"','MM/YYYY') <= date_issue";
		} 
		else if(!yearFrom.equals("")){
		    str += "to_date('"+ yearFrom +
			"','yyyy') <= date_issue";
		} 
		if(!str.equals("")){
		    wherecases.addElement(str);
		    andFlag = true;
		}
	    }
	    if(!dayTo.equals("") || !monthTo.equals("") || 
	       !yearTo.equals("")){
		String str="";
		if(andFlag) str = " and ";
		if(!dayTo.equals("") && !monthTo.equals("") && 
		   !yearTo.equals("")){
		    str += "to_date('"+ monthTo+"/"+dayTo+"/"+ 
			yearTo+ "','MM/DD/YYYY') >= date_issue";
		}
		else if(!monthTo.equals("") && !yearTo.equals("")){
		    str += "to_date('"+ monthTo+"/"+yearTo+ 
			"','MM/YYYY') >= date_issue";
		} 
		else if(!yearTo.equals("")){
		    str += "to_date('"+ yearTo+
			"','yyyy') >= date_issue";
		} 
		if(!str.equals("")){
		    wherecases.addElement(str);
		    andFlag = true;
		}
	    }
	    String added = "lname1, fname1,lname2,fname2";
	    String query2 = "select count(*) from deed "; 
	    sortby = sortby.toLowerCase();// id, lot,sec, mi1, mi2
	    if(sortby.startsWith("name 1")) sortby = added+",sec,lot";
	    else if(sortby.startsWith("name 2")) 
		sortby = "lname2,fname2,lname1,fname1,sec,lot";
	    else if(sortby.startsWith("section")) 
		sortby ="sec,"+added;
	    else if(sortby.startsWith("date")) 
		sortby ="date_issue,"+added+",sec,lot";
	    else if(sortby.equals("id"))sortby = added + ",sec,lot";
	    //
	    // Table presentation first
	    //
	    if(wt.equals("table")){
		String query = "select id,sec,lot,initcap(lname1) ||', '||"+
		    "initcap(fname1) ||' '|| mi1,"+
		    "initcap(lname2) ||', '|| initcap(fname2) ||' '|| mi2,"+
		    "to_char(date_issue,'MM/DD/YYYY'),"+
		    "notes from deed ";
	    
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
     		    showNext= false;
		}
		else if(total > maxRecords && total > minRecords){
		    os.println("Showing the records from:"+ minRecords +
			       " to " + maxRecords+ "<br>");
     		    showNext= true;
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
		    os.println("<table border=0 cellspacing=1 "+
			       "cellpadding=1>");
		    
		    int colcnt = rs.getMetaData().getColumnCount();
		    // System.err.println(colcnt);
		    row = 0;
		    while (rs.next()){
			if(row >= minRecords && row <= maxRecords){
			    if(row%20 == 0){
				os.println("<tr BGCOLOR=#666666>");
				for (int c = 0; c < tableItem.length; c++) {
				    os.println("<th><FONT Size=-1>");
				    os.println(tableItem[c]);
				    os.println("</FONT></th>");
				}
				os.println("</tr>");
			    }
			    if(row%2 == 0)
				os.println("<tr bgcolor=white>");
			    else
				os.println("<tr bgcolor=#c1c1a1>");
			    for (int c = 0; c < colcnt; c++){
				os.println("<td>");
				String that = rs.getString(c+1);
				if(that != null) that = that.trim();
				//  System.err.print(that+" ");
				if (c == 0){
				    os.println("<a href="+url + 
					       "DeedZoom?id=" 
					       + that + 
					       "&username="+username+">"
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
				    if(that == null || that.equals(",")) 
					os.println("&nbsp;&nbsp;");
				    else 
					os.println(that);
				    os.println("</td>");
				}
			    }
			    os.println("</tr>");
			}
			row++;
			if(row > maxRecords) break;
		    }
		    os.println("</table>");
		}
	    }
	    else{ //report
		//
		// Report presentation
		//
		String query = "select sec,lot,initcap(lname1) lname1,"+
		    "initcap(fname1) fname1,mi1,"+ 
		    "initcap(lname2) lname2,initcap(fname2) fname2,mi2,"+
		    "to_char(date_issue,'Mon DD/YYYY'),"+
		    "notes from deed ";
	    
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
		}
		rs = stmt.executeQuery(query2);
		int total = 0;
		if(rs.next()){
		    total = rs.getInt(1);
		}
		os.println("Matching total records :"+ total + 
			   "<br>");
		if(total < maxRecords && minRecords == 0) 
		os.println("Showing all the: "+ total + 
			   " records <br>");
		else if(total <= maxRecords && total > minRecords)
		os.println("Showing the records from:"+ minRecords +
			   " to " + total+ "<br>");
		else if(total > maxRecords && total > minRecords)
		os.println("Showing the records from:"+ minRecords +
			   " to " + maxRecords+ "<br>");
		else if(total < minRecords){
		    os.println("Error in setting the \"From\" field in "+
			       "\"Show Records\", go back and "+
			       "reset this field. <br>");
		    rangeFlag = false;
		}
		// System.err.println("total: "+total);
		// System.err.println("query: "+query);
		
		if(rangeFlag){
		    rs = stmt.executeQuery(query);
		    int colcnt = rs.getMetaData().getColumnCount();
		    // System.err.println("column count: "+colcnt);
		    //  os.println("<center><Font size=+2>Rose Hill Cemetary"+
		    //       "</Font><br>");
		    os.println("<center><Font size=+1>Deed Report</Font><br>");
		    os.println("<hr></center>");

		    //  System.out.println("After rs.next");
		    row = 0;
		    while (rs.next()){
			if(row >= minRecords && row <= maxRecords){
			    os.println("<table border=0 cellspacing=1 "+
				       "cellpadding=1>");
			    // section
			    int c = 0;
			    String that = rs.getString(c+1);
			    if(that != null) 
				writeItem(that, repItem[c]);
			    // lot
			    c++;
			    that = rs.getString(c+1);
			    if(that != null)
				writeItem(that, repItem[c]);
			    // name
			    c++;
			    that = rs.getString(c+1);
			    name = "";
 			    if(that != null) name = that;
			    that = rs.getString(c+2);
 			    if(that != null) name += ", "+that;
			    that = rs.getString(c+3);
 			    if(that != null) name += ", "+that;
			    that = rs.getString(c+4);
			    if(!name.equals("")){
				if(that != null) name += " & "+that;
			    }
			    else{
				if(that != null) name = that;
			    }
			    that = rs.getString(c+5);
 			    if(that != null) name += ", "+that;
			    that = rs.getString(c+6);
 			    if(that != null) name += ", "+that;

			    if(!name.equals(""))
				writeItem(name, repItem[c]);
			    c+=6;
			    // age
			    for(int i=c; i<colcnt;i++){
				that = rs.getString(i+1);
				if(that != null){
				    //System.err.println(" "+i+" "+repItem[i]);
				    writeItem(that, repItem[i]);
				}
			    }
			    os.println("</table>");
			    os.println("<br><br><hr>");
			}
			row++;
			if(row > maxRecords) break;
		    }
		}
	    }
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

		if(!sec.equals("")){
		    os.println("<input type=hidden name=sec value=" + 
			       sec + "></input>");
		}
		if(!lot.equals("")){
		    os.println("<input type=hidden name=lot value=" + 
			       lot + "></input>");
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
		if(!lname2.equals("")){
		    os.println("<input type=hidden name=lname2 value=" + 
			       lname2+"></input>");
		}
		if(!fname2.equals("")){
		    os.println("<input type=hidden name=fname2 value=" + 
			       fname2+"></input>");
		}
		if(!mi2.equals("")){
		    os.println("<input type=hidden name=mi2 value=" + 
			       mi2+"></input>");
		}
		if(!monthFrom.equals("")){
		    os.println("<input type=hidden name=monthFrom value=" + 
			       monthFrom+"></input>");
		}
		if(!dayFrom.equals("")){
		    os.println("<input type=hidden name=dayFrom value=" + 
			       dayFrom+"></input>");
		}
		if(!yearFrom.equals("")){
		    os.println("<input type=hidden name=yearFrom value=" + 
			       yearFrom+"></input>");
		}
		if(!monthTo.equals("")){
		    os.println("<input type=hidden name=monthTo value=" + 
			       monthTo+"></input>");
		}
		if(!dayTo.equals("")){
		    os.println("<input type=hidden name=dayTo value=" + 
			       dayTo+"></input>");
		}
		if(!yearTo.equals("")){
		    os.println("<input type=hidden name=yearTo value=" + 
			       yearTo+"></input>");
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
    void writeItem(String that, String title){
	os.println("<tr><td align=right><strong>");
	os.println(title+"</strong></td><td align=left>");
	os.println(that+"</td></tr>");
    }
    /**
     * Connects to the rosehill database.
     */
    public void databaseConnect() {
	//obtainProperties();
	try {
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    con = DriverManager.
		getConnection(
			      Deed.dbConnectStr,
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
    /*
 
   public void obtainProperties(PrintWriter out){

	try {
	    FileInputStream fis = new FileInputStream(new File(iniFile));
	    Properties pr = new Properties();
	    pr.load(fis);
	    accessUser = pr.getProperty("accessUser");
	    accessPass = pr.getProperty("accessPass");

	} catch(Exception ee){ 
	    out.println(ee.toString());}
   }
    */

}























































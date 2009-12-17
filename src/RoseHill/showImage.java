package RoseHill;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import RoseHill.*;
/**
 * Shows an image of a location in Rose Hill based on lot and section. 
 * @author Walid Sibo
 * @version %I%,%G%
 */

public class showImage extends HttpServlet{

    /**
     * To add deed records to RoseHill database.
     * @author Walid Sibo
     * 
     */
    Connection con;
    Statement stmt;
    ResultSet rs;
    final int baseNumber = 10000;
    PrintWriter os;
    String unicID ="";
    String Section[] = { "","A","B","C","D","E",
			 "F","G","H","I","J","K",
			 "L","M","N","O","U.P.","SP-AD"};
    boolean imgRhAval[] = {false, true, true, true, true, true,
			   true, true, true, false, true, true,
			   true, true, true, true, false, false};

    boolean imgSecAval[] = {false, false, false, false, false,false,
			    false, false, false, false, true, false,
			    false, false, false, false, false, false};

    //
    String accessUser = Deed.accessUser;
    String accessPass = Deed.accessPass;

    String url=Deed.url;
    String url2=Deed.url2;
    String url3=Deed.url3;
    String imagePath = url3+"images/rosehill/";
    String roseAll = url3+"images/rosehill/ROSEHILL.gif";
    String roseAlls = url3+"images/rosehill/roseAlls.gif";

    boolean debug = Deed.debug;

    String username = "", password = "";
    /**
     * Shows the image of the location of selected lot and section.
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
	String id="", type="", sec="",lot="";
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
	    else if(name.equals("sec")){
		sec = value;
	    }
	    else if(name.equals("lot")){
		lot = value;
	    }
	    else if(name.equals("id")){
		id = value;
	    }
	    else if(name.equals("type")){
		type = value;
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
	out.println("    return true;                   ");
	out.println("	}	                        ");
	out.println(" </script>                         ");   
	out.println("</head><body>                      ");
	out.println("<center><h2><center>Parks and Recreation</center></h2>");
	out.println("<font color=blue size=+2>Rose Hill Overall Map"+
		    "</font><br>");
	//box it in 
	out.println("<br><table border>");
	if(!type.equals("large")){
	    out.println("<tr><td align=center><img src=\""+
			imagePath+"roseEast.gif"+
			"\"  alt=\"Rose Hill East Map\"><br>East Rose Hill");
	    out.println("<br><br></td></tr></table>");
	    out.println("<br><br>");
	    out.println("<br><table border>");
	    out.println("<td align=center><img src=\""+
			imagePath+"roseWest.gif"+
			"\"  alt=\"Rose Hill East Map\"><br>West Rose Hill");
	    out.println("</td></tr></table><br>");
	    out.println("<br><br><br><br><br><br><br>");
	}
	else{
	    out.println("<tr><td><img src=\""+roseAll+
			"\" alt=\"Rose Hill Map\">");
	    out.println("</td></tr></table><br>");
	    out.println("<br><br>");
	}
	// 
	// section image relative to other sections (if any)
	//
	if(imgRhAval[getSectId(sec)]){

	    out.println("<br><br>");
	    out.println("<center><font color=red size=+2>High "+
			"Lighted Section in Rose Hill Map</font>");
	    out.println("<br><br><table border>");
	    out.println("<tr><td>");
	    if(!type.equals("large")){
		out.println("<img src=\""+imagePath+"rhss"+sec+
			    ".gif\" alt=\"Section image\">");
		//  System.err.println("<img src=\""+imagePath+
		// "rh"+sec+".gif\" alt=\"Section image\">");
	    }
	    else{
		out.println("<img src=\""+imagePath+"rh"+sec+
			    ".gif\" alt=\"Section image\">");
	    }
	    out.println("</td></tr></table><br>");
	    out.println("<center><font size=+1>Section: "+sec+
			"</font></center><br>");
	}
	//
	// if we have an image for this section, it will be shown
	//
	if(imgSecAval[getSectId(sec)]){
	   out.println("<center><font color=red size=+2>Section Map</font>");
	   out.println("<br>");
	   out.println("<br><table border>");
	   out.println("<tr><td>");
	   out.println("<img src=\""+imagePath+"/"+sec+
		       ".gif\" alt=\"Section image\">");
	   //System.err.println("<img src=\""+imagePath+sec+
	   // ".gif\" alt=\"Section image\">");
	   out.println("</td></tr></table><br>");
	   out.println("<center><font size=+1>Section: "+sec+
		       " Lot: "+lot+"</font></center><br>");
	}
	out.println("<HR>");
	out.println("<center><font size=+1><a href="+url+"showImage?"+
		    "username="+username+"&sec="+sec+"&lot="+lot+
		    "&type=large>Show Large Size Maps</a>");
	out.println("</font></center>");
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
    //
    int getSectId(String sec){
	for(int i=0; i< Section.length; ++i){
	    if(sec.equals(Section[i])) return i;
	}
	return 0;
    }
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

}























































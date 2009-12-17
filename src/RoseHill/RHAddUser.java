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

public class RHAddUser extends HttpServlet{

    /**
     * Interface to RoseHill data base.
     * @author Walid Sibo
     * Date: sept. 2001
     */
    String WEBserver = "";
    String url = Deed.url;
    String url2 = Deed.url2;
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
     * Shows a form to input his username.
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
	out.println("<HTML><HEAD><TITLE>Add a New User to RoseHill " +
		    "Authorized Users</TITLE>");
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
	out.println("<center><h2> Add a New User to Authorized "+
		    "RoseHill Users</h2>");
	out.println("<center><h3> Enter the user name for the new user "+
		    "and then press return.</h3>");
	out.println("<FORM NAME=\"useridForm\" method=\"post\" "+
		    "onSubmit=\"return submit1()\"> ");
	out.println("<table border=0><tr><td>Username</td><td><INPUT "+
		    "NAME=\"useridField\" TYPE=text></td><tr>");
	out.println(" </table> </FORM>");	
   	out.println("</BODY></HTML>");
	out.close();

    }				   
    /**
     * Saves the username in the authorzied table of users to this database.
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
	    sendError(os, "Add user failed, Unauthorized User");
	    os.close();
	    return;
	}
	else{
	    String skey = (String) hash.get(key);
	    //  System.err.println("skey="+skey);
	    if(!skey.equals(secretKey)){
		sendError(os, "Add user failed, Unauthorized User");
		os.close();
		return;
	    }
	}
	while (values.hasMoreElements()){
	    name = ((String)values.nextElement()).trim();
	    value = (req.getParameter(name)).trim();
	    if(name.equals("useridField")){
		username = value.toLowerCase();
	    }
	}
	try{
	    //
	    // add the user to the data base
	    //
	    databaseConnect();
	    // 
	    // check if the table exist
	    //
	    try{
		stmt.executeQuery("select count(*) from "+
				  "rosehill_authorized");
	    } 
	    catch(Exception ex){
		//
		// if we get here means that
		// this table does not exist yet
		// this would happen only for the
		// first time use
		//
		//System.err.println("about to create the table");
		stmt.executeUpdate("create table "+
				   "rosehill_authorized ( userid "+
				   "varchar(20) not null )");
	    }
	    //  System.err.println("insert user in table");
	    stmt.executeUpdate("insert into rosehill_authorized "+
			       " values ('"+username+"')");
	    databaseDisconnect();
	    os.println("<HTML><HEAD><TITLE>Add a new User to RoseHill "+
		    "Authorized Users</TITLE>");
	    os.println("<body>");
	    os.println("<br>");
	    os.println("<center><h2>New user added successfully </h2>");
	    os.println("<FORM ></centar>");
	    os.println("<br>");
	    os.println("<center><table border=0><tr><td><LI>To Add Another User "+
		       "</td><td><INPUT "+
		       "NAME=\"submit\" TYPE=button Value=\"Click Here\" "+
		       "onclick=\"document.location='"+url+
		       "RHAddUser'\">"+
		       "</td></tr>");
	    os.println("<tr><td><LI> Go back to main menu </td> <td> "+
			"<INPUT NAME=\"back\" TYPE=button Value="+
		       "\"Click Here\" "+
			"onclick=\"document.location='"+url2+
		       "index.html'\">"+
			"</td></tr><h3>");
	    os.println(" </table></center> </FORM>");	
	    os.println("</body>");
	    os.println("</html>");
	}
    	catch (Exception ex) {
	    os.println(ex);
	}
	os.flush();
	os.close();

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


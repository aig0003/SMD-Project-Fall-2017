import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;

class Query{
	public static void main(String args[]) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(
		  	"jdbc:mysql://localhost:3306/mysql","username","password");
		  	//example database name, root is username & password
		  	Statement stmt = con.createStatement();
		  	ResultSet rs = stmt.executeQuery("SELECT * FROM product");
		  	while(rs.next())
		    System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getDouble(3));
		  	con.close();
		} catch(Exception e) {
			System.out.println("Thatchdawg" + e);
		}
	}
}
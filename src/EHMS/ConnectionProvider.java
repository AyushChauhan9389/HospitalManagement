package EHMS;
import java.sql.*;
public class ConnectionProvider {
public static Connection getCon()
{
	try {
		String url="jdbc:mysql://localhost:3306/HealthcareMangaementSystem";
		
		String uname="root";
		String pass="abcd1234";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(url,uname,pass);
		return con;
	}catch(Exception e) {
		return null;
	}
}

}

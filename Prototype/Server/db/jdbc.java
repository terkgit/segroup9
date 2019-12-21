package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.net.ssl.SSLException;

public class jdbc {
	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	// update USER, PASS and DB URL according to credentials provided by the website:
	// https://remotemysql.com/
	// in future move these hard coded strings into separated config file or even better env variables
	static private final String DB = "P0KAg0apUE";
	static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
	static private final String USER = "P0KAg0apUE";
	static private final String PASS = "e0IHtpz2Kx";

	public static String listCatalog() throws SSLException {
		Connection conn = null;
		Statement stmt = null;
		String result="";
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			//id,name,price,shop,amount
			String sql = "SELECT * FROM `Catalog`";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id =  rs.getInt("id"); 
				String name = rs.getString("name");
				String shop = rs.getString("shop");
				int price = rs.getInt("price");
				int amount = rs.getInt("amount");
				System.out.format("%d. %-16s price:%d - %4d items available at %s\n", id, name, price, amount, shop);
				result=result.concat(name+": price-"+price+"$\n");
			}
			rs.close();
			stmt.close();
			conn.close();
		} 
		catch (SQLException se) {
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return result;
	}

	public static String setPriceId(String str) throws SSLException {
		String result = "setPriceId: unknown error!\n";
		int id=-1;
		int price=-1;
		int arg1=-1;
		int arg2=-1;
		
		arg1=str.indexOf(' ')+1;
		if(arg1<0) {
			result = "param error! use !spi <id> <price>\n";
		}
		else {
			arg2=str.substring(arg1).indexOf(' ');
			if(arg2<0) {
				result = "param error! use !spi <id> <price>\n";
			}
			else {
				arg2=arg2+arg1+1;
				System.out.println("arg1["+arg1+"] arg2["+arg2+"]");
				String sarg1=str.substring(arg1,arg2-1);
				String sarg2=str.substring(arg2);
				result="set price to: "+sarg1+" for id: "+sarg2;
				price=Integer.parseInt(sarg1);
				id=Integer.parseInt(sarg2);
				System.out.println("price:" + price + " id:" + id);
				System.out.println(result);
				updatePriceId(price, id);
			}
		}
		return result;
	}

	private static String updatePriceId(int price, int id) {
		Connection conn = null;
		Statement stmt = null;
		String result="";
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			//id,name,price,shop,amount

			// update flight 387 price to 2019
			String sql = "SELECT id, price FROM Catalog WHERE id = "+id;
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			rs.updateInt("price", price);
			rs.updateRow();
			result="";
			
			stmt.close();
			conn.close();
		} 
		catch (SQLException se) {
			se.printStackTrace();
			System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return result;
	}

	private static String getNextArgument(String str) {
		int i=str.indexOf(' ');
		int j=0;
		if(i>0){
			if(str.substring(i).indexOf(' ')>0) {
				return str.substring(i,j);
			}
			else 
				return str.substring(i);
		}
		return null;
	}

	public static String setPriceName(String str) throws SSLException {
		String result="setPriceName "+str;
		System.out.println(result);		
		return result;
	}
}

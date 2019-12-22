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
				String line = String.format("[%d] %-16s price: %4d - %4d items available at \"%s\" shop\n", id, name, price, amount, shop);
				System.out.print(line);
				result=result.concat(line);
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

	public static String setPriceId(String[] args) throws SSLException {
		return "";
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


	public static String setPriceName(String str) throws SSLException {
		String result="setPriceName "+str;
		System.out.println(result);		
		return result;
	}

	public static String updatePrice(String[] args) {
		String result = "setPriceId: unknown error!\n";
		int id=-1;
		int price=-1;
		
		if(args.length!=3)
			return "illegal params (usage: !updatePrice <ID> <PRICE>)";
		
		result="set price to: "+args[1]+" for id: "+args[2];
		id=Integer.parseInt(args[1]);
		price=Integer.parseInt(args[2]);
		if(id<=0||price<=0)
			return "illegal params (usage: !updatePrice <ID> <PRICE>)";
		
		System.out.println("price:" + price + " id:" + id);
		result = updatePriceId(price, id);
		System.out.println("updatePriceId result: "+result);
		
		return result;
	}
}

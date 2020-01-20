package db;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.net.ssl.SSLException;

import classes.*;

public class jdbc {
	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	// update USER, PASS and DB URL according to credentials provided by the website:
	// https://remotemysql.com/
	// in future move these hard coded strings into separated config file or even better env variables
	static private final String DB = "P0KAg0apUE";
	static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
	static private final String USER = "P0KAg0apUE";
	static private final String PASS = "e0IHtpz2Kx";

	public static Command listCatalog() throws SSLException {
		Connection conn = null;
		Statement stmt = null;
		Catalog ctlg = new Catalog();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			//id,name,price,shop,amount
			String sql = "SELECT * FROM `Catalog`";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Item itm = new Item();
				itm.setId(rs.getInt("id")); 
				itm.setName(rs.getString("name"));
				itm.setAmount(rs.getInt("amount"));
				itm.setPrice(rs.getInt("price"));
				itm.setShop(rs.getString("shop"));
				
				ctlg.itemList.add(itm);
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

		Command reply = new Command("Catalog list created", ctlg);
		return reply;
	}
	
	public static void addNewObjectToDataBase(Object obj) throws SSLException {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = null;
			if(obj instanceof Item) {
				String sql = "SELECT * FROM `Catalog`";
				Item newItem = (Item) obj;
				rs = stmt.executeQuery(sql);
				rs.moveToInsertRow();
				rs.updateInt("id", newItem.getId());
				rs.updateString("name", newItem.getName());
				rs.updateDouble("price", newItem.getPrice());
				rs.updateString("shop", newItem.getShop());
				rs.updateInt("amount", newItem.getAmount());
				rs.insertRow();
				
				
			}
			if(obj instanceof User) {
				String sql = "SELECT * FROM `Users`";
				signedUser sUser = new signedUser();
				sUser=(signedUser) obj;
				rs = stmt.executeQuery(sql);
				rs.moveToInsertRow();
				rs.updateInt("id",sUser.getId());
				rs.updateString("name", sUser.getUserNane());
				rs.updateString("password", sUser.getPassword());
//			  	rs.updateObject("permission", sUser.getPermissionLevel());
				rs.updateString("phone", sUser.getPhone());
				rs.insertRow();
			}
			rs.close();
			stmt.close();
			conn.close();
		} 
		catch (SQLException se) {
//			se.printStackTrace();
//			System.out.println("SQLException: " + se.getMessage());
//            System.out.println("SQLState: " + se.getSQLState());
//            System.out.println("VendorError: " + se.getErrorCode());
			System.out.println("Object alredy exist in DATABASE");
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
		//return obj;
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

			String sql = "SELECT id, price FROM Catalog WHERE id = "+id;
			ResultSet rs = stmt.executeQuery(sql);
			if(!rs.last()) {
				result = String.format("product %d not found",id);
			}
			else {
				rs.updateInt("price", price);
				rs.updateRow();	
				result= String.format("product %d price updated to %d",id,price);;
			}
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

	public static Command updatePrice(String[] args) {
		String result = "setPriceId: unknown error!\n";
		int id=-1;
		int price=-1;
		
		if(args.length!=3)
			return new Command("illegal params (usage: !updatePrice <ID> <PRICE>)");
		
		result="set price to: "+args[1]+" for id: "+args[2];
		id=Integer.parseInt(args[1]);
		price=Integer.parseInt(args[2]);
		if(id<=0||price<=0)
			return new Command("illegal params (usage: !updatePrice <ID> <PRICE>)");
		
		System.out.println("price:" + price + " id:" + id);
		result = updatePriceId(price, id);
		System.out.println("updatePriceId result: "+result);
		return new Command(result);
	}
}

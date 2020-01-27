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
	
	public static Command addNewObjectToDataBase(Command cmd){
		Connection conn = null;
		Statement stmt = null;
		String result = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = null;
			String sql;
//			*********ADD NEW ITEM TO CATALOG*********
			if(cmd.obj instanceof Item) {
				Item item=(Item)cmd.obj;
				sql = "SELECT * FROM `Catalog` WHERE `name` LIKE '"+item.getName()+"' AND `shop` LIKE '"+item.getShop()+"'";
				rs = stmt.executeQuery(sql);
				if(rs.last())
					result = "alredy exists";
				else {
					sql = "SELECT * FROM `Catalog`";
					rs = stmt.executeQuery(sql);
					rs.moveToInsertRow();
					rs.updateInt("id", item.getId());
					rs.updateString("name", item.getName());
					rs.updateDouble("price", item.getPrice());
					rs.updateString("shop", item.getShop());
					rs.updateInt("amount", item.getAmount());
					rs.insertRow();
					result = "Success";
				}
			}
//			*********ADD NEW USER TO DATABASE*********
			if(cmd.obj instanceof User) {
				User user = (User)cmd.obj;
				sql = "SELECT * FROM Users WHERE username LIKE '" + user.getUserName()+"'";
				rs = stmt.executeQuery(sql);
				if(rs.last()) 		//check if user name already exist
					result = "alredy exists";
				else{
					rs.moveToInsertRow();
					rs.updateString("username", user.getUserName());
					rs.updateString("password", user.getPassword());
					rs.insertRow();
					result = "Success";
					
				}
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
			//System.out.println("Object already exist in DATABASE");
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
		return new Command(result);
	}

	public static Command updateItemInDataBase(Command cmd) {
		Connection conn = null;
		Statement stmt = null;
		Command result=new Command("");
		result.obj=cmd.obj;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = null;
			
//			*********VALIDATE USER*********
			if(cmd.obj instanceof signedUser) {
				signedUser sUser = (signedUser)cmd.obj;
				String sql = "SELECT * FROM Users WHERE username LIKE '" + sUser.getUserName()+"'";
				rs = stmt.executeQuery(sql);
				rs.last();
				String pass=rs.getString("password");
				if(!pass.equals(sUser.getPassword()))
					result.msg = "wrong password";
				else {
					rs.updateString("name", sUser.getName());
					rs.updateInt("id", sUser.getId());
					rs.updateInt("credit card", sUser.getCreditCard());
					rs.updateRow();
					result.msg = "validated";
				}
			}
//			*********UPDATE ITEM IN CATALOG*********
			if(cmd.obj instanceof Item) {
				Item item = (Item) cmd.obj;
				String sql = "SELECT * FROM `Catalog` WHERE `name` LIKE '"+item.getName()+"' AND `shop` LIKE '"+item.getShop()+"'";
				rs = stmt.executeQuery(sql);
				rs.last();
				rs.updateDouble("price", item.getPrice());
				rs.updateInt("amount", item.getAmount());
				rs.updateRow();
				result.msg = "updated";
			}
//			*********SIGN IN*********
			if(cmd.obj instanceof User) {
				User user = (User) cmd.obj;
				String sql = "SELECT * FROM Users WHERE username LIKE '" + user.getUserName()+"'";
				rs = stmt.executeQuery(sql);
				if(rs.last()) {
					result.msg = "wrong username";
					return result;
				}
				String pass=rs.getString("password");
				if(!pass.equals(user.getPassword()))
					result.msg = "wrong password";
				else {
					signedUser sUser = new signedUser();
					sUser.setUserName(user.getUserName());
					sUser.setPassword(user.getPassword());
					result.obj=sUser;
					result.msg="Log In Success";
				}
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
	
//	public static Command signUp(Command cmd) {	
//		return addNewObjectToDataBase(cmd);
//	}
//	
//	public static Command signIn(Command cmd) {
//		return updateItemInDataBase(cmd);
//	}
//	
//	public static Command validate(Command cmd) {
//		return updateItemInDataBase(cmd);
//	}
	

}

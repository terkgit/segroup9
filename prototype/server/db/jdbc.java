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
	
	public static String addNewObjectToDataBase(Object obj){
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
			if(obj instanceof Item) {
				sql = "SELECT * FROM `Catalog` WHERE `name` LIKE '"+((Item) obj).getName()+"' AND `shop` LIKE '"+((Item) obj).getShop()+"'";
				rs = stmt.executeQuery(sql);
				if(rs.last())
					result = "alredy exists";
				else {
					sql = "SELECT * FROM `Catalog`";
					rs = stmt.executeQuery(sql);
					rs.moveToInsertRow();
					rs.updateInt("id", ((Item) obj).getId());
					rs.updateString("name", ((Item) obj).getName());
					rs.updateDouble("price", ((Item) obj).getPrice());
					rs.updateString("shop", ((Item) obj).getShop());
					rs.updateInt("amount", ((Item) obj).getAmount());
					rs.insertRow();
					result = "succes";
				}
			}
//			*********ADD NEW USER TO DATABASE*********
			if(obj instanceof User) {
				sql = "SELECT * FROM Users WHERE username LIKE '" + ((User) obj).getUserName()+"'";
				rs = stmt.executeQuery(sql);
				if(rs.last()) 		//check if user name already exist
					result = "alredy exists";
				else{
					rs.moveToInsertRow();
					rs.updateString("username", ((User) obj).getUserName());
					rs.updateString("password", ((User) obj).getPassword());
					rs.insertRow();
					result = "succes";
					
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
		return result;
	}

	public static Command updateItemInDataBase(Object obj) {
		Connection conn = null;
		Statement stmt = null;
		Command result=new Command();
		result.obj=obj;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = null;
			
//			*********VALIDATE USER*********
			if(obj instanceof signedUser) {;
				String sql = "SELECT * FROM Users WHERE username LIKE '" + ((signedUser) obj).getUserName()+"'";
				rs = stmt.executeQuery(sql);
				rs.last();
				String pass=rs.getString("password");
				if(!pass.equals(((signedUser) obj).getPassword()))
					result.msg = "wrong password";
				else {
					rs.updateString("name", ((signedUser) obj).getName());
					rs.updateInt("id", ((signedUser) obj).getId());
					rs.updateInt("credit card", ((signedUser) obj).getCreditCard());
					rs.updateRow();
					result.msg = "validated";
				}
			}
//			*********UPDATE ITEM IN CATALOG*********
			if(obj instanceof Item) {
				String sql = "SELECT * FROM `Catalog` WHERE `name` LIKE '"+((Item) obj).getName()+"' AND `shop` LIKE '"+((Item) obj).getShop()+"'";
				rs = stmt.executeQuery(sql);
				rs.last();
				rs.updateDouble("price", ((Item) obj).getPrice());
				rs.updateInt("amount", ((Item) obj).getAmount());
				rs.updateRow();
				result.msg = "updated";
			}
//			*********SIGN IN*********
			if(obj instanceof User) {
				String sql = "SELECT * FROM Users WHERE username LIKE '" + ((User) obj).getUserName()+"'";
				rs = stmt.executeQuery(sql);
				if(rs.last()) {
					result.msg = "wrong username";
					return result;
				}
				String pass=rs.getString("password");
				if(!pass.equals(((User) obj).getPassword()))
					result.msg = "wrong password";
				else {
					signedUser sUser = new signedUser();
					sUser.setUserName(((User) obj).getUserName());
					sUser.setPassword(((User) obj).getPassword());
					result.obj=sUser;
					result.msg="Log In Succes";
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
	
	public static Command signUp(Command cmd) {
		Command flag = new Command(cmd.msg, cmd.obj);
		flag.msg = addNewObjectToDataBase(cmd.obj);
		return flag;
	}
	
	public static Command signIn(Command cmd) {
		return updateItemInDataBase(cmd.obj);
	}
	
	public static Command validate(Command cmd) {
		return updateItemInDataBase(cmd.obj);
	}
	

}

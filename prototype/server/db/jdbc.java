package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.net.ssl.SSLException;

import classes.*;

public class jdbc {
	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
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
	
	public static Command listUserOrders(Command cmd) throws SSLException {
		Connection conn = null;
		Statement stmt = null;
		LinkedList<Order> orderList = new LinkedList<Order>();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT * FROM `Orders` WHERE `username` LIKE '"+(String)cmd.obj+"'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setUserName(rs.getString("username"));
				order.fromString(rs.getString("details"));
				order.setCard(rs.getString("card"));
				order.setAddress(rs.getString("address"));
				order.setPhone(rs.getString("phone"));
				order.setPrice(rs.getDouble("price"));
				order.setOrderDate(rs.getString("orderDate"));
				order.setDeliveryDate(rs.getString("deliveryDate"));
				order.setStatus(rs.getString("status"));
				orderList.add(order);
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

		Command reply = new Command("User Order List Created", orderList);
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
					rs.updateString("name", item.getName());
					rs.updateDouble("price", item.getPrice());
					rs.updateString("shop", item.getShop());
					rs.updateInt("amount", item.getAmount());
					rs.updateString("color", item.getColor());
					rs.insertRow();
					result = "Success";
				}
			}
//			*********SIGN UP - ADD NEW USER TO DATABASE*********
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
					rs.updateString("permissionLevel", user.getPermLevel());
					rs.insertRow();
					result = "Success";
					
				}
			}
//			*********ADD NEW ORDER TO DATABASE*********
			if(cmd.obj instanceof Order) {
				Order order = new Order();
				order=(Order) cmd.obj;
				order.calculateTotalPrice();
				sql="SELECT * FROM `Orders`";
				rs = stmt.executeQuery(sql);
				rs.moveToInsertRow();
				rs.updateString("username", order.getUserName());
				rs.updateString("details", order.toString());
				rs.updateString("card", order.getCard());
				rs.updateDouble("price", order.getPrice());
				rs.updateString("orderDate",order.getOrderDate());
				rs.updateString("deliveryDate",order.getDeliveryDate());
				rs.updateString("status", order.getStatus());
				rs.updateString("phone", order.getPhone());
				rs.updateString("address", order.getAddress());
				rs.insertRow();
				result = "Success";
				
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
			if(cmd.obj instanceof User && ((User)cmd.obj).getPermLevel().equals("SignedUser")) {
				User sUser = (User)cmd.obj;
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
					((User) result.obj).setPermLevel("Validated");
					result.msg = "Validated";
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
				result.msg = "Item Updated";
			}
			
//			*********LOG IN*********
			if(cmd.obj instanceof User && ((User)cmd.obj).getPermLevel().equals("Guest")) {
				User user = (User) cmd.obj;
				String sql = "SELECT * FROM Users WHERE username LIKE '" + user.getUserName()+"'";
				rs = stmt.executeQuery(sql);
				if(!rs.last()) {
					result.msg = "Wrong Username";
					return result;
				}
				String pass=rs.getString("password");
				if(!pass.equals(user.getPassword()))
					result.msg = "Wrong Password";
				else {
					user.setPermLevel("SignedUser");
					result.obj=user;
					result.msg="Log In Success";
				}
			}
			
//			*********UPDATE ORDER'S STATUS*********
			if(cmd.obj instanceof Order) {
				Order order = new Order();
				order=(Order) cmd.obj;
				String sql = "SELECT * FROM `Orders` WHERE `username` LIKE '"+order.getUserName()+"' AND `orderDate` = '"+order.getOrderDate()+"'";
				rs = stmt.executeQuery(sql);
				rs.last();
				rs.updateString("status", order.getStatus());
				rs.updateRow();
				result.msg = "Status Updated";
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
		return addNewObjectToDataBase(cmd);
	}
	
	public static Command logIn(Command cmd) {
		return updateItemInDataBase(cmd);
	}
	
	public static Command validate(Command cmd) {
		return updateItemInDataBase(cmd);
	}

	public static Command editItem(Command cmd) {
		return updateItemInDataBase(cmd);
	}

	public static Object addItem(Command cmd) {
		return addNewObjectToDataBase(cmd);
	}

	public static Object order(Command cmd) {
		return addNewObjectToDataBase(cmd);
	}

	public static Object cancelOrder(Command cmd) {
		Order order = new Order();
		order=(Order) cmd.obj;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
		String deliverDate = formatter.format(order.getDeliveryDate());
		String now = formatter.format(new Date());
		String[] delArgs = deliverDate.split("[/ :]");
		String[] nowArgs = now.split("[/ :]");
		
		return  updateItemInDataBase(cmd);
	}
	

}

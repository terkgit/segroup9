package classes;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 3701232644453341817L;
	
	private String userName;
	private String password;
	private String permLevel;
	
	public User(String _name, String _pass) {
		userName=_name;
		password=_pass;
		setPermLevel("Guest");
	}

	
	public User() {
		userName="";
		password="";
		setPermLevel("Guest");
	}

	
	//public int getId() {return id;}
	public String getUserName() {return userName;}
	public String getPassword() {return password;}
	///public permissionLevel getPermissionLevel() {return permission;}
	//public String getPhone() {return phone;}
	
	//public void setId(int _id) {id=_id;}
	public void setUserName(String _userName) {userName=_userName;}
	public void setPassword(String _password) {password=_password;}
	//public void setPermissionLevel(Object _permission) {permission=(permissionLevel) _permission;}
	//public void setPhone(String _phone) {phone=_phone;};


	public void printUser() {
		System.out.println("name: "+userName);
		
	}


	public String getPermLevel() {
		return permLevel;
	}


	public void setPermLevel(String permLevel) {
		this.permLevel = permLevel;
	}
		
}



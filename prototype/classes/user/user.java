package user;

import java.io.Serializable;

public class user implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	enum permissionLevel {
			USER,
			BOSS,
			ADMIN
	}
	
	private int id;
	private String userName;
	private String password;
	private permissionLevel permission;
	private String phone;
	
	public int getId() {return id;}
	public String getUserNane() {return userName;}
	public String getPassword() {return password;}
	public permissionLevel getPermissionLevel() {return permission;}
	public String getPhone() {return phone;}
	
	public void setId(int _id) {id=_id;}
	public void setUserName(String _userName) {userName=_userName;}
	public void setPassword(String _password) {password=_password;}
	public void setPermissionLevel(Object _permission) {permission=(permissionLevel) _permission;}
	public void setPhone(String _phone) {phone=_phone;};
		
}



package classes;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 3701232644453341817L;
	
	private String userName;
	private String password;
	private String permLevel;	
	private String name;
	private int id;
	private int creditCard;
	
	public User(String _name, String _pass) {
		setUserName(_name);
		setPassword(_pass);
		setPermLevel("Guest");
	}

	
	public User() {
		setUserName("Guest");
		setPassword("");
		setPermLevel("Guest");
	}


	public void printUser() {
		System.out.println("name: "+getUserName());
		
	}


	public String getPermLevel() {
		return permLevel;
	}


	public void setPermLevel(String permLevel) {
		this.permLevel = permLevel;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getCreditCard() {
		return creditCard;
	}


	public void setCreditCard(int creditCard) {
		this.creditCard = creditCard;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

		
}



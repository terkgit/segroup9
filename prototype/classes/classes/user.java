package classes;


public class user {
	
	enum permissionLevel {
			USER,
			BOSS
	}
	
	private int id;
	private String userName;
	private String password;
	private permissionLevel permission;
}



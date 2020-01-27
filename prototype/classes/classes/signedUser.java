package classes;


public class signedUser extends User{


	private static final long serialVersionUID = 1L;
	private String name;
	private int id;
	private int creditCard;
	
	public signedUser() {
		
	}
	
	public void setName(String _name) {name=_name;}
	public void setId(int _id) {id=_id;}
	public void setCreditCard(int _creditCard) {creditCard=_creditCard;}
	
	public String getName() {return name;}
	public int getId() {return id;}
	public int getCreditCard() {return creditCard;}
	
	
	
}

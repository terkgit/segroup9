package classes;

public class Item {

	public Item(String _name, double _price, int _amount, String _pic, int _id, String _shop) {
		name=_name;
		price=_price;
		amount=_amount;
		pic=_pic;
		id=_id;
		shop=_shop;
	}
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	public int getAmount() {
		return amount;
	}
	public String getPic() {
		return pic;
	}
	private String name; 
	private double price;
	private int amount;
	private String pic;
	private int id;
	private String shop;
	
}

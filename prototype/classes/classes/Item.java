package classes;

import java.io.Serializable;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private double price;
	private String name; 
	private String shop;
	private int amount;
	
	public Item(String _name, double _price, int _amount, String _pic, int _id, String _shop) {
		name=_name;
		price=_price;
		amount=_amount;
		id=_id;
		shop=_shop;
	}
	
	public Item() {
		
	}

	public void setId(int _id) {id=_id;	}
	public void setPrice(double _price) {price=_price;	}
	public void setName(String _name) {name=_name;}
	public void setShop(String _shop) {shop=_shop;}
	public void setAmount(int _amount) {amount=_amount;}
	
	public int getId() {return id;}
	public double getPrice() {return price;}
	public String getName() {return name;}
	public String getShop() {return shop;}
	public int getAmount() {return amount;}
	
	public void printItem() {
		String line = String.format("%-4d | %-16s | %6g |  %4d  | \"%s\"\n", id, name, price, amount, shop);
		System.out.print(line);
	}
}

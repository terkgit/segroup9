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
	private String color;
	private String shop;
	private int amount;
	private String pic;

	
	public Item(String _name, double _price,String _color, int _amount, String _pic, int _id, String _shop) {
		name=_name;
		price=_price;
		color=_color;
		amount=_amount;
		id=_id;
		shop=_shop;
		pic=_pic;
	}
	
	public Item(String _name, double _price, String _color, int _amount, String _pic) {
		name=_name;
		price=_price;
		color=_color;
		amount=_amount;
		id=-1;
		shop="";
	}
	
	public Item() {
		
	}
	
	public int getId() {return id;}

	public double getPrice() {return price;}

	public String getName() {return name;}

	public String getShop() {return shop;}

	public int getAmount() {return amount;}

	public String getPic() {return pic;	}

	public String getColor() {return color;	}

	public String getDetails() {
		return name + " Price: " +price + "\n";
	}

	public void setId(int _id) {id=_id;	}
	public void setPrice(double _price) {price=_price;	}
	public void setName(String _name) {name=_name;}
	public void setShop(String _shop) {shop=_shop;}
	public void setAmount(int _amount) {amount=_amount;}
	public void setPic(String pic) {this.pic = pic;	}
	public void setColor(String color) {this.color = color;}
	
	public void printItem() {
		String line = String.format("%-4d | %-16s | %6g |  %4d  | \"%s\"\n", id, name, price, amount, shop);
		System.out.print(line);
	}
	
	public String stringItem() {
		String line = String.format("%-4d | %-16s | %6g |  %4d  | \"%s\"\n", id, name, price, amount, shop);
		return line;
	}
	
	public String toString() {
		return id+"#"+name+"#"+price+"#"+color+"#"+shop+"#"+amount+"#"+pic+"*";
	}
	public Item fromString(String str) {
		Item item=new Item();
		String[] args = str.split("[* #]+");
		int _id=Integer.parseInt(args[0]);
		double _price=Double.parseDouble(args[2]);
		int _amount = Integer.parseInt(args[5]);
		item.setId(_id);
		item.setName(args[1]);
		item.setPrice(_price);
		item.setColor(args[3]);
		item.setShop(args[4]);
		item.setAmount(_amount);
		item.setPic(args[6]);
		
		return item;
	}

	public static void main(String args[]) {
		Item item = new Item();
		item.setId(1);
		item.setAmount(10);
		item.setColor("red");
		item.setName("pikachu");
		item.setPic("pic1");
		item.setPrice(11.2);
		item.setShop("shop1");
		
		String str = item.toString();
		item = item.fromString(str);
	}
}

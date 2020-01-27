package classes;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import javax.net.ssl.SSLException;

import db.jdbc;

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private signedUser user;
	private LinkedList<Item> orderList;
	private DateTimeFormatter orderDate;
	private DateTimeFormatter deliveryDate;
	private String card;
	private static String details ;
	private static  double price;
	
	public Order() {
		user=null;
		orderList=new LinkedList<Item>();
		card="";
	}
	
	public LinkedList<Item> getOrderList(){
		return orderList;
	}
	public void setOrdreList(LinkedList<Item> _list) {
		orderList=_list;
	}
	
	public String getCard() {
		return card;
	}
	public void setCard(String _card) {
		card=_card;
	}
	
	public signedUser getUser() {
		return user;
	}
	public void setUser(signedUser _user) {
		user=_user;
	}
	
	
	public String toString() {
		int size = orderList.size();
		String str="";
		for(int i=0; i<size;i++)
			str+=orderList.get(i).toString();
		return str;
	}
	
	public Order fromString(String str) {
		String[] args = str.split("[*]");
		int size = args.length;
		Order order = new Order();
		int i=0;
		while(i<size) {
			Item item = new Item();
			order.orderList.add(item.fromString(args[i]));
		}
		return order;
	}
	
	public String getDetails() {
		price=0;
		details = "Order Details\n\n Cart:\n";
		orderList.forEach((item)->{
			details += item.getDetails();
			price+=item.getPrice();
		});
		details+= "Total Price is: "+price+"\n";
		if(!card.equals(""))
			details+= card;
		return details;
		
	}
	
	public static void main(String args[]) {
		Item item1 = new Item("f1",11.2,"red",10,"pic1",1,"shop1");
		Item item2 = new Item("f2",12.2,"blue",20,"pic2",2,"shop2");
		Item item3 = new Item("f3",13.2,"green",30,"pic3",3,"shop3");
		Order order = new Order();
		order.orderList.add(item1);
		order.orderList.add(item2);
		order.orderList.add(item3);
		
		System.out.println(order.getDetails());
		
		String str = order.toString();
		
		order.fromString(str);
	}

	public DateTimeFormatter getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(DateTimeFormatter orderDate) {
		this.orderDate = orderDate;
	}

	public DateTimeFormatter getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(DateTimeFormatter deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

}

package classes;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date; 
import java.util.LinkedList;

import javax.net.ssl.SSLException;

import db.jdbc;

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	private String userName;
	private LinkedList<Item> orderList;
	private String orderDate;
	private String deliveryDate;
	private String card;
	private String status;
	private String details ;
	private double price;
	private String phone;
	private String address;
	
	public Order() {
		userName="";
		orderList=new LinkedList<Item>();
		card="";
		status="Pending";
		
	}
	
	public LinkedList<Item> getOrderList(){
		return orderList;
		}
	public String getCard() {
	return card;
	}

	public String getUserName() {
	return userName;
	}



	public double getPrice() {
		return price;
	}

	public String getDetails() {
		price=0;
		details = "Order Details\n\n Cart:\n";
		orderList.forEach((item)->{
			details += item.getDetails();
		});
		this.calculateTotalPrice();
		details+= "Total Price is: "+price+"\n";
		if(!card.equals(""))
			details+= card;
		return details;
		
	}

	public String getOrderDate() {
		return orderDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public String getStatus() {
		return status;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public void setOrdreList(LinkedList<Item> _list) {
		orderList=_list;
		}
	
	public void setCard(String _card) {
		card=_card;
		}
	
	public void setUserName(String _user) {
		userName=_user;
		}
	
	public void setPrice(double _price) {
		price=_price;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public void setDeliveryDate(String date)  {
		this.deliveryDate = date;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void calculateTotalPrice() {
		this.price=0;
		int i=0;
		while(i<orderList.size())
			this.price+=orderList.get(i++).getPrice();
		this.price=Double.parseDouble(df.format(price));
	}
	public String toString() {
		int size = orderList.size();
		String str="";
		for(int i=0; i<size;i++)
			str+=orderList.get(i).toString();
		return str;
	}
	public void fromString(String str) {
		String[] args = str.split("[*]");
		int size = args.length;
		int i=0;
		while(i<size) {
			Item item = new Item();
			this.orderList.add(item.fromString(args[i++]));
		}
	}
	
	public static void main(String args[]) throws ParseException, SSLException {
		Item item1 = new Item("f1",11.2,"red",10,"pic1",1,"shop1");
		Item item2 = new Item("f2",12.2,"blue",20,"pic2",2,"shop2");
		Item item3 = new Item("f3",13.2,"green",30,"pic3",3,"shop3");
		Order order = new Order();
		order.orderList.add(item1);
		order.orderList.add(item1);
		order.orderList.add(item1);
//		order.orderList.add(item2);
//		order.orderList.add(item3);
//		order.orderList.add(item3);
//		order.orderList.add(item3);
		
//		System.out.println(order.getDetails());
//		String str = order.toString();	
//		order.fromString(str);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date now = new Date();
		order.setOrderDate(formatter.format(now));
		order.setDeliveryDate(formatter.format(now));
		order.setUserName("terkel");
		order.setPhone("0551234569");
		order.setAddress("Rotshild 30");
		order.setCard("????????GZ!!!!!!");
		order.calculateTotalPrice();
		//order.setStatus("Pending");
		
//		Date dt= formatter.parse("28/01/2020 19:00");
//		System.out.println(formatter.format(dt));
		
		
		Command cmd = new Command("!User's orders");
		cmd.obj=order;
		jdbc.order(cmd);
		LinkedList<Order> userOrders = new LinkedList<Order>();
		cmd.obj=(String) "terkel";
		cmd=jdbc.listUserOrders(cmd);
		
		System.out.println("END!");
//		
//		System.out.println(formatter.format(order.getDeliveryDate()));
//		System.out.println(formatter.format(order.getOrderDate()));
//		
//		String dateStr = formatter.format(order.getDeliveryDate());
//		System.out.println(dateStr);
//		String dateArgs[] = dateStr.split("[/ :]");
//		System.out.println(Integer.parseInt(dateArgs[4]));
//		
//		Date firstdate = new Date();
//		Date seconddate = formatter.parse("28/01/2020 15:00");
//		
//		System.out.println(formatter.format(seconddate));
//		jdbc.cancelOrder(cmd);
	}

}

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
	
	public SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private int id;
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
	private double refund;
	
	
	public Order() {
		userName="";
		orderList=new LinkedList<Item>();
		card="";
		status="Pending";
		phone="";
		address="";
		id=-1;
		
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

	public int getId() {
		return id;
	}

	public double getRefund() {
		return refund;
	}

	public SimpleDateFormat getFormatter() {
		return formatter;
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

	public void setId(int id) {
		this.id = id;
	}

	public void setRefund(double refund) {
		this.refund = refund;
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
		
		
		Command cmd = new Command("!User's orders");
		cmd.obj=order;
//		jdbc.order(cmd);
		LinkedList<Order> userOrders = new LinkedList<Order>();
		cmd.obj=(String) "terkel";
		cmd=jdbc.listUserOrders(cmd);
		userOrders=(LinkedList<Order>) cmd.obj;
		int i = 0;
		while(i<userOrders.size()) {
			Command cmd2 = new Command("!cancel");
			cmd2.obj=userOrders.get(i++);
			cmd2=(Command) jdbc.cancelOrder(cmd2);
		}
		
		
//		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		
//		
//		System.out.println(formatter.format(order.getDeliveryDate()));
//		System.out.println(formatter.format(order.getOrderDate()));
//		
//		String dateStr = formatter.format(order.getDeliveryDate());
//		System.out.println(dateStr);
//		String dateArgs[] = dateStr.split("[/ :]");
//		System.out.println(Integer.parseInt(dateArgs[4]));
//		
//		Date now = new Date();
//		Date seconddate = formatter.parse("30/01/2020 15:00");
//		
//		System.out.println(formatter.format(now));
//		System.out.println(formatter.format(seconddate));
//		System.out.println(now.toString());
//		System.out.println(seconddate.toString());
//		long l1 = now.getTime();
//		long l2 = seconddate.getTime();
//		long diff = l2-l1;
//		long diff1 = l1-l2;
//		System.out.println(diff);
//		System.out.println(diff1);
//		double hours = ((double)diff)/1000/60/60;
//		
//		System.out.println(hours);
		
		
//		jdbc.cancelOrder(cmd);
		
		
		System.out.println("END!");
	}

}

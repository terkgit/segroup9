package classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;  
import java.util.Date; 
import java.util.LinkedList;

import db.jdbc;

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	private LinkedList<Item> orderList;
	private  Date orderDate;
	private  Date deliveryDate;
	private String adress;
	private String phone;
	private String card;
	private static String details ;
	private static  double price;
	private String status;
	
	public Order() {
		user=null;
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

	public User getUser() {
	return user;
	}



	public double getPrice() {
		return price;
	}

	public String getDetails() {
		price=0;
		details = "Order Details\n\n Cart:\n";
		orderList.forEach((item)->{
			details += item.getDetails();
			price+=item.getPrice();
		});
		price = Math.round(price);
		details+= "Total Price is: "+price+"\n";
		if(!card.equals(""))
			details+= card;
		return details;
		
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setOrdreList(LinkedList<Item> _list) {
		orderList=_list;
		}
	
	public void setCard(String _card) {
		card=_card;
		}
	
	public void setUser(User _user) {
		user=_user;
		}
	
	public void setPrice(double _price) {
		price=_price;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setStatus(String status) {
		this.status = status;
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
			order.orderList.add(item.fromString(args[i++]));
		}
		return order;
	}
	
	public static void main(String args[]) {
		Item item1 = new Item("f1",11.2,"red",10,"pic1",1,"shop1");
		Item item2 = new Item("f2",12.2,"blue",20,"pic2",2,"shop2");
//		Item item3 = new Item("f3",13.2,"green",30,"pic3",3,"shop3");
		Order order = new Order();
		order.orderList.add(item1);
		order.orderList.add(item2);
//		order.orderList.add(item3);
		
		System.out.println(order.getDetails());
		String str = order.toString();	
		order.fromString(str);
		
//		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
		order.setOrderDate(new Date());
		order.setDeliveryDate(new Date());
		order.setUser(new User());
		order.user.setUserName("malki");
		order.setStatus("Canceled");
		
		Command cmd = new Command("!order");
		cmd.obj=order;
		jdbc.cancelOrder(cmd);
		
		
		
		
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}

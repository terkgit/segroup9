package classes;

import java.util.ArrayList;

public class Catalog {
	private ArrayList<Item> itemList;
	
	public Catalog() {
		itemList = new ArrayList<Item>();
	}
	
	public void addItem(Item item) {
		itemList.add(item);
	}
}

package classes;

import java.io.Serializable;
import java.util.LinkedList;


public class Catalog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LinkedList<Item> itemList;
	
	public Catalog() {
		itemList = new LinkedList<Item>();
	}
	
	public void printCatalog() {
		String result=String.format("ID %3s name%14s price%4s Shop%9s shop\n"," |"," |"," |"," |");
		result=result.concat("-----------------------------------------------------------------\n");
		System.out.print(result);
		
		int size = itemList.size();
		int i=0;
		
		while(i<size) {
			itemList.get(i++).printItem();
		}	
	}
	
	public LinkedList<Item> search(Filter filter) {
		LinkedList<Item> searchList = new LinkedList<Item>();
		itemList.forEach((item)->{
			if(filter.inRange(item))
				searchList.add(item);
    	});
		return searchList;
	}
}

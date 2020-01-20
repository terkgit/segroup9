package catalog;

import java.io.Serializable;
import java.util.LinkedList;

import item.*;

public class catalog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LinkedList<item> itemList;
	
	public catalog() {
		itemList = new LinkedList<item>();
	}
	
	public void printCatalog() {
		String result=String.format("ID %3s name%14s price%3s amount%s shop\n"," |"," |"," |"," |");
		result=result.concat("-----------------------------------------------------------------\n");
		System.out.print(result);
		
		int size = itemList.size();
		int i=0;
		
		while(i<size) {
			itemList.get(i++).printItem();
		}	
	}
}

package classes;

public class Filter {
	
	double minPrice;
	double maxPrice;
	
	String shop;
	
	String color;
	
	public Filter() {
		minPrice=-1;
		maxPrice=-1;
		shop = "any";
		color="any";
	}
	
	
	public void setFilterPrice(double _min, double _max) {
		minPrice=_min;
		maxPrice=_max;
	}
	
	public void setFilterShop(String _shop){
		shop=_shop;
	}
	
	public void setFilterColor(String _color){
		color=_color;
	}

	public boolean inRange(Item item) {
		return (priceInRange(item.getPrice()) 
				&& shopinRange(item.getShop())
//				&& colorInRange(item.getColor())
				);
	}

	private boolean shopinRange(String _shop) {
		if(shop.equals("any"))
			return true;
		if(shop.equals(_shop))
			return true;
		return false;
	}
	

	private boolean priceInRange(double price) {
		if(price>=minPrice) {
			if(maxPrice==-1 || price <= maxPrice) {
				System.out.println(" true");
				return true;
			}
		}
		return false;
	}
}

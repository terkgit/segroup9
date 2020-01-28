package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;

import classes.Catalog;
import classes.Command;
import classes.Filter;
import classes.Item;
import classes.Order;
import classes.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GUIController {

	/** Client Static Variables  **/
	private static Catalog localCatalog;
	private static Order localOrder;
	private static User localUser;
	private static LinkedList<Item> cart;
	private static LinkedList<Item> searchList;
	private static String clientMsg;
	private static String userTxtStr;	
	private static int waitLock=0;
	private static Command reply;

	public static void initStatics(){
		userTxtStr="Guest";
		clientMsg="";		
		client.ClientConsole.send(new Command("!list"));
		localCatalog=null;
		localUser=(User)(new User());
		cart=new LinkedList<Item>();
	    localOrder=new Order();
	}

    /**** Catalog ****/
	@FXML private TableView<Item> catalogTable;
    @FXML private TableColumn<Item, String> catalogTableName;
    @FXML private TableColumn<Item, Double> catalogTablePrice;
    @FXML private TableColumn<Item, Integer> catalogTableAmount;
    @FXML private TableColumn<Item, String> catalogTablePic;
    @FXML private Text catalogTxt;
    @FXML private TextField catalogMinPriceTF;
    @FXML private TextField catalogMaxPriceTF;
    @FXML private ChoiceBox<String> catalogColorChoice;
    @FXML private TextField catalogShopTF;

    /**** CatalogM ****/
    @FXML private TextField catalogMName;
    @FXML private TextField catalogMPrice;
    @FXML private TextField catalogMAmount;
    @FXML private TextField catalogMPic;
    
    /**** Cart ****/
	@FXML private TableView<Item> cartTable;
    @FXML private TableColumn<Item, String> cartTableName;
    @FXML private TableColumn<Item, Double> cartTablePrice;

    
    
    /**** Welcome ****/    
    @FXML private Button welcomeLoginBtn;
    @FXML private Button welcomeOrdersBtn;
    @FXML private Button welcomeValidateBtn;
    @FXML private Text generalMsg;

    /**** Login ****/ 
    @FXML private TextField loginPassTxt;
    @FXML private TextField loginUserTxt;
//    @FXML private Button loginLoginBtn; 
    
    /**** SingUp ****/     
    @FXML private TextField signupTxt;
    
    /**** Welcome/Login ****/
//    @FXML private Button catalogBtn;
    @FXML private Text userTxt;
//	@FXML private ResourceBundle resources;
//  @FXML private URL location;

    /**** Order ****/
    @FXML private TextArea orderDetailsTA;
    @FXML private TextField orderAdressTF;
    @FXML private CheckBox  orderCardCB;
    @FXML private TextArea orderCardTA;
 
    
    /**** debug ****/
    @FXML private TextField debugCommandTxt;
    @FXML private TextArea debugObjectTxt;
//    @FXML private Button debugSendBtn;

    @FXML void initialize() {
        if(userTxt!=null)
        	userTxt.setText("Hi "+userTxtStr);
        if(welcomeLoginBtn!=null) {
        	if(localUser.getPermLevel().equals("SignedUser")) {
        		welcomeOrdersBtn.setVisible(true);
        		if( !(localUser.getPermLevel().equals("ValidatedUser")) )
        			welcomeValidateBtn.setVisible(true);
        	}
        }
        if(catalogTable!=null) {
	        catalogTableName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
	        catalogTablePrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
	        catalogTableAmount.setCellValueFactory(new PropertyValueFactory<Item, Integer>("amount"));
	        catalogTablePic.setCellValueFactory(new PropertyValueFactory<Item, String>("pic"));
	        catalogTableFill();
        }
        if(cartTable!=null) {
        	cartTableName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        	cartTablePrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
	        cartTableFill();
        }
        if(catalogColorChoice!=null)
        {
        	catalogColorChoice.getItems().add("Red");
        	catalogColorChoice.getItems().add("Blue");
        	catalogColorChoice.getItems().add("Green");
        }
        if(orderDetailsTA!=null) {
        	orderDetailsTA.setText(localOrder.getDetails());
        }
    }
    
    @SuppressWarnings("unused")private void _Cart_() {}
    
    void cartTableFill() {
    	if(cart==null)
    		System.out.println("cart is unavailable");
    	else {
        	ObservableList<Item> ctl = cartTable.getItems();
        	cart.forEach((item)->{
        		ctl.add(item);
        	});
    	}
    } // catalogTableFill
    
    @FXML void gotoCart(ActionEvent event) throws IOException {
	    URL url = getClass().getResource("Cart.fxml");
	    AnchorPane pane = FXMLLoader.load( url );
	    Scene scene = new Scene( pane );
	    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    stage.setTitle("Cart");
	    stage.setScene(scene);
	}
    
    @FXML void gotoOrder(ActionEvent event) throws IOException {
	    if(cart.size()<=0) {
	    	System.out.println("cart is empty");
	    	generalMsg.setText("cart is empty");
	    	return;
	    }
	    
	    if( !(localUser.getPermLevel().equals("SignedUser")) ) {
	    	System.out.println("permission: "+localUser.getPermLevel()+" expected: SignedUser");
	    	generalMsg.setText(localUser.getUserName()+", please login first");
	    	return;
	    }
	    
	    localOrder.setOrdreList(cart);
	    localOrder.setUser(localUser);
	    localOrder.setOrderDate(new Date());
	    
	    
	    
    	URL url = getClass().getResource("Order.fxml");
	    AnchorPane pane = FXMLLoader.load( url );
	    Scene scene = new Scene( pane );
	    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    stage.setTitle("Order");
	    stage.setScene(scene);
	}
    
    @FXML void orderPurchace(ActionEvent event) throws IOException {
    	if(localUser.getPermLevel().equals("validatedUser")) {
    		System.out.println("{---\nsending !order"+localOrder.getDetails()+"---}");
    		client.ClientConsole.send(new Command("!order",localOrder));
    	}
    	else {
    		gotoSignup(event);
    	}
    	
    }

	@SuppressWarnings("unused")private void _Catalog_() {}
    
    @FXML void addMyItem(ActionEvent event) {
    	catalogTableFill();
//    	        catalogTableName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
//    	Item myItem= new Item("my item",0.0,0,"pic.jpg",0,"shop");
//    	ObservableList<Item> ctl = catalogTable.getItems();
//    	ctl.add(myItem);
    }   
    
    void catalogTableFill() {
    	if(localCatalog==null)
    		System.out.println("catalog unavaileable");
    	else {
        	ObservableList<Item> ctl = catalogTable.getItems();
        	localCatalog.itemList.forEach((item)->{
        		ctl.add(item);
        	});
    	}
    } // catalogTableFill
    
  
    void catalogTableFillSearch() {
    	if(searchList==null)
    		System.out.println("search unavaileable");
    	else {
    		catalogTable.getItems().clear();
        	ObservableList<Item> ctl = catalogTable.getItems();
        	searchList.forEach((item)->{
        		ctl.add(item);
        	});
    	}
    } // catalogTableFill
    
    @FXML void catalogSearch() {
		Filter filter = new Filter();
		double min=-1;
		double max=-1;
		if( !(catalogMinPriceTF.getText().equals("")) ) {
			min=Double.valueOf(catalogMinPriceTF.getText());
		}
		if( !(catalogMaxPriceTF.getText().equals("")) ) {
			max=Double.valueOf(catalogMaxPriceTF.getText());
		}
		
		System.out.println("price: "+min+"-"+max);
	    System.out.println("color: "+catalogColorChoice.getValue());
		System.out.println("shop: "+catalogShopTF.getText());
	
		filter.setFilterPrice(min,max);
		if( !(catalogShopTF.getText().equals("")) )
			filter.setFilterShop(catalogShopTF.getText());
	
		if( catalogShopTF.getText()!=null )
			filter.setFilterColor(catalogColorChoice.getValue());
		
		searchList=localCatalog.search(filter);
		searchList.forEach((item)->{
			item.printItem();
		});
		catalogTableFillSearch();
	}

	@FXML void addToCart(ActionEvent event) throws IOException {
    	Item selected=catalogTable.getSelectionModel().getSelectedItem();
    	if (selected==null) {
    		System.out.println("select an item");
    		catalogTxt.setText("select an item");
    	}
    	else {
    		System.out.println(selected.getName());
    		catalogTxt.setText(selected.getName());
    		cart.add(selected);
    	}
    }

    @FXML void gotoCatalog(ActionEvent event) throws IOException {
	    URL url = getClass().getResource("Catalog.fxml");
	    AnchorPane pane = FXMLLoader.load( url );
	    Scene scene = new Scene( pane );
	    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    stage.setTitle("Catalog");
	    stage.setScene(scene);
	}

    @FXML void gotoCatalogM(ActionEvent event) throws IOException {
	    URL url = getClass().getResource("CatalogM.fxml");
	    AnchorPane pane = FXMLLoader.load( url );
	    Scene scene = new Scene( pane );
	    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    stage.setTitle("Catalog Maneger");
	    stage.setScene(scene);
	}
    
    @FXML void catalogEditItem(ActionEvent event) throws IOException {
    	Item selected=catalogTable.getSelectionModel().getSelectedItem();
		String _name=catalogMName.getText();
		double _price=Double.valueOf(catalogMPrice.getText());
		int _amount=Integer.parseInt(catalogMAmount.getText());
		String _pic=catalogMPic.getText();
    	Item item = new Item(_name,_price,"color",_amount,_pic);
    	if (selected==null) {
    		System.out.println("select an item");
    		catalogTxt.setText("select an item");
    	}
    	else {
    		System.out.println(selected.getName()+" update request\n"+"to: "+item.stringItem());
    		catalogTxt.setText(selected.getName()+" update request\n"+"to: "+item.stringItem());
    		client.ClientConsole.send(new Command("!editItem",item));
    	}
    }
    
    @FXML void catalogAddItem(ActionEvent event) throws IOException, InterruptedException {
		String _name=catalogMName.getText();
		double _price=Double.valueOf(catalogMPrice.getText());
		int _amount=Integer.parseInt(catalogMAmount.getText());
		String _pic=catalogMPic.getText();
		
    	Item item = new Item(_name,_price,"color",_amount,_pic);
	    
    	System.out.println("add item: "+item.stringItem());
		catalogTxt.setText("add request to item: "+item.stringItem());
		client.ClientConsole.send(new Command("!addItem",item));
		int status = replyWait();
		System.out.println("reply recieved: "+(status!=0));
    }
    
    @SuppressWarnings("unused")private void __Login__() {}
	
	@FXML void handleLogin(ActionEvent event) throws IOException, InterruptedException {
		String usertxt=loginUserTxt.getText();
	    userTxtStr=usertxt;
		if(usertxt.equals("admin")) {    	
	    	URL url = getClass().getResource("debug.fxml");
	        AnchorPane pane = FXMLLoader.load( url );
	        Scene scene = new Scene( pane );
	        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        return;
	    }
		else if(usertxt.equals("Manager")) {    	
	    	gotoCatalogM(event);
	        return;
		}
		else {
			Command cmd = new Command("!login");
			cmd.obj=new User(loginUserTxt.getText(),loginPassTxt.getText());
			client.ClientConsole.send(cmd);
			int status = replyWait();
			System.out.println("reply recieved: "+(status!=0));
			if(reply.msg.equals("Log In Success")) {
				localUser=(User)reply.obj;
				gotoWelcome(event);
			}
			else
				generalMsg.setText(reply.msg);
		}
	}

	@FXML void gotoLogin(ActionEvent event) throws IOException{
		URL url = getClass().getResource("Login.fxml");
	    AnchorPane pane = FXMLLoader.load( url );
	    Scene scene = new Scene( pane );
	    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    stage.setTitle("Login");
	    stage.setScene(scene);
	}
	
	@SuppressWarnings("unused")private void __Signup__() {}


	@FXML void gotoSignup(ActionEvent event) throws IOException{
		URL url = getClass().getResource("Signup.fxml");
	    AnchorPane pane = FXMLLoader.load( url );
	    Scene scene = new Scene( pane );
	    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    stage.setTitle("Signup");
	    stage.setScene(scene);
	}
	
	@FXML void handleSignUp(ActionEvent event) throws IOException, InterruptedException {
		Command cmd = new Command("!signUp");
		cmd.obj=new User(loginUserTxt.getText(),loginPassTxt.getText());
		client.ClientConsole.send(cmd);
		int status = replyWait();
		System.out.println("reply recieved: "+(status!=0));
		if(reply.msg.contentEquals("Success")) {
			generalMsg.setText("User: "+((User)cmd.obj).getUserName()+" successfuly Signed up, please Login");
		}
		else {
			System.out.println("signUp failed, "+reply.msg);
			generalMsg.setText("signUp failed, "+reply.msg);
		}
	}

	@FXML void signupValidate(ActionEvent event) throws InterruptedException, IOException{
		client.ClientConsole.send(new Command("!validate"));
		int status = replyWait();
		System.out.println("reply recieved: "+(status!=0));
		if(reply.msg.equals("User Valid")) {
			System.out.println("User Validated");
			gotoCatalog(event);
		}
		else {
			System.out.println("validation failed");
		}
		
	}
	
	@SuppressWarnings("unused")private void __Welcome__() {}
    
    @FXML void gotoWelcome(ActionEvent event) throws IOException{
		URL url = getClass().getResource("Welcome.fxml");
	    AnchorPane pane = FXMLLoader.load( url );
	    Scene scene = new Scene( pane );
	    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    stage.setTitle("Welcome");
	    stage.setScene(scene);
	    stage.show();
	}

	@SuppressWarnings("unused")private void _DEBUG_() {}

    @FXML void debugRefresh(ActionEvent event) {
		System.out.println("msg after send"+clientMsg);
		System.out.println("--------------------");
		debugObjectTxt.setText(clientMsg);
    }

    @FXML void debugSend(ActionEvent event) throws InterruptedException {
		client.ClientConsole.send(new Command(debugCommandTxt.getText()));
		int status = replyWait();
		System.out.println("reply recieved: "+(status!=0));
		debugObjectTxt.setText(clientMsg);
    }

	private int replyWait() throws InterruptedException {
		int i;
		waitLock=1;
		// wait 5 seconds for reply 
		for( i=10; i>0 && waitLock==1;i--) {
            Thread.sleep(500);
            System.out.print(".");
		}
		return i;
	}

	public static void display(Command cmd) {
		// TODO Auto-generated method stub
		reply=cmd;
		if(cmd.obj instanceof Catalog) {
			System.out.println("recieved catalog from server");
			localCatalog=((Catalog)reply.obj);
			localCatalog.printCatalog();
		}
		else {
			System.out.println("recieved: <"+reply.msg+"> from server, obj-"+reply.obj.toString());
		}
		
		waitLock=0;
	}
	
}

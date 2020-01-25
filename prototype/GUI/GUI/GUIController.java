package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import classes.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;


public class GUIController {

	/** Client Static Variables  **/
	private static Catalog localCatalog;
	private static LinkedList<Item> cart;
	private static String clientMsg;
	private static String userTxtStr;	
	private static int waitLock=0;

	public static void initStatics(){
		userTxtStr="Guest";
		clientMsg="";		
		client.ClientConsole.send(new Command("!list"));
		localCatalog=null;
		cart=new LinkedList<Item>();
	}

    /**** Catalog ****/
	@FXML private TableView<Item> catalogTable;
    @FXML private TableColumn<Item, String> catalogTableName;
    @FXML private TableColumn<Item, Double> catalogTablePrice;
    @FXML private TableColumn<Item, Integer> catalogTableAmount;
    @FXML private TableColumn<Item, String> catalogTablePic;
    @FXML private Text catalogTxt;

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
//    @FXML private Button loginBtn;

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

 
    
    /**** debug ****/
    @FXML private TextField debugCommandTxt;
    @FXML private TextArea debugObjectTxt;
//    @FXML private Button debugSendBtn;

    @FXML void initialize() {
        if(userTxt!=null)
        	userTxt.setText("Hi "+userTxtStr);
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
    	Item item = new Item(_name,_price,_amount,_pic);
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
    
    @FXML void catalogAddItem(ActionEvent event) throws IOException {
		String _name=catalogMName.getText();
		double _price=Double.valueOf(catalogMPrice.getText());
		int _amount=Integer.parseInt(catalogMAmount.getText());
		String _pic=catalogMPic.getText();
		
    	Item item = new Item(_name,_price,_amount,_pic);
	    
    	System.out.println("add item: "+item.stringItem());
		catalogTxt.setText("add request to item: "+item.stringItem());
		client.ClientConsole.send(new Command("!addItem",item));
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
		else if(usertxt.equals("manager")) {    	
	    	gotoCatalogM(event);
	        return;
		}
		else {
			Command cmd = new Command("!login");
			cmd.obj=new User(loginUserTxt.getText(),loginPassTxt.getText());
			client.ClientConsole.send(cmd);
			int status = replyWait();
			System.out.println("reply status: "+status);
		}
		gotoWelcome(event);
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
		System.out.println("reply status: "+status);
		gotoSignup(event);
	}

	@FXML void signupVarify(ActionEvent event){
		System.out.println("TODO: signupVarify implement");
//		signupTxt.setText("varify unavailable");
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
		System.out.println("reply status: "+status);
		debugObjectTxt.setText(clientMsg);
    }

	private int replyWait() throws InterruptedException {
		int i;
		waitLock=1;
		// wait 3 seconds for reply 
		for( i=10; i>0 && waitLock==1;i--) {
            Thread.sleep(300);
            System.out.print(".");
		}
		return i;
	}

	public static void display(Command cmd) {
		// TODO Auto-generated method stub
		clientMsg=cmd.msg;
		if(cmd.obj instanceof Catalog) {
			System.out.println("recieved catalog from server");
			localCatalog=((Catalog)cmd.obj);
			localCatalog.printCatalog();
		}
		else {
			System.out.println("recieved: <"+cmd.msg+"> from server, obj-"+cmd.obj.toString());
		}
		
		waitLock=0;
	}
	
}

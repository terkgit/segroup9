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
import java.util.ResourceBundle;

import classes.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;


public class GUIController {

	private static Catalog localCatalog;
	private static String clientMsg;
	public static String userTxtStr;	
	public GUIController instance;
	private static int waitLock=0;

	public static void initStatics(){
		userTxtStr="Guest";
		clientMsg="";
//		localCatalog;
	}

    /**** Catalog ****/
	@FXML private TableView<Item> catalogTable;
    @FXML private TableColumn<Item, String> catalogTableName;
    @FXML private TableColumn<Item, Double> catalogTablePrice;
    @FXML private TableColumn<Item, Integer> catalogTableAmount;
    @FXML private TableColumn<Item, String> catalogTablePic;
    @FXML private Button catalogNextBtn; 
    @FXML private Button catalogPrevBtn; 
    @FXML private Button catalogSearchBtn; 

    
    
    /**** Welcome ****/    
    @FXML private Button loginBtn;

    /**** Login ****/ 
    @FXML private TextField loginPassTxt;
    @FXML private TextField loginUserTxt;
    @FXML private Button loginLoginBtn; 
    
    /**** Welcome/Login ****/
    @FXML private Button catalogBtn;
    @FXML private Text userTxt;
//	@FXML private ResourceBundle resources;
//  @FXML private URL location;

 
    
    /**** debug ****/
    @FXML private TextField debugCommandTxt;
    @FXML private TextArea debugObjectTxt;
    @FXML private Button debugSendBtn;

    @FXML void initialize() {
        if(userTxt!=null)
        	userTxt.setText("Hi "+userTxtStr);
    }
    
    @FXML void addMyItem(ActionEvent event) {
        catalogTableName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
    	Item myItem= new Item("my item",0.0,0,"pic.jpg",0,"shop");
    	ObservableList<Item> ctl = catalogTable.getItems();
    	ctl.add(myItem);
    }
    
    @FXML void gotoCatalog(ActionEvent event) throws IOException {
        URL url = getClass().getResource("Catalog.fxml");
        AnchorPane pane = FXMLLoader.load( url );
        Scene scene = new Scene( pane );
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Catalog");
        stage.setScene(scene);
    }

    @FXML void gotoLogin(ActionEvent event) throws IOException{
    	URL url = getClass().getResource("Login.fxml");
        AnchorPane pane = FXMLLoader.load( url );
        Scene scene = new Scene( pane );
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
    }
    
    @FXML void handleLogin(ActionEvent event) throws IOException {
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
    	gotoWelcome(event);
    }

    @FXML void gotoWelcome(ActionEvent event) throws IOException{
    	URL url = getClass().getResource("Welcome.fxml");
        AnchorPane pane = FXMLLoader.load( url );
        Scene scene = new Scene( pane );
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
    }
    


    @FXML void debugRefresh(ActionEvent event) {
		System.out.println("msg after send"+clientMsg);
		System.out.println("--------------------");
		debugObjectTxt.setText(clientMsg);
    }

    @FXML void debugSend(ActionEvent event) throws InterruptedException {
		String msg[]= {debugCommandTxt.getText(),debugObjectTxt.getText()};
    	System.out.println("command: "+msg[0]);
    	System.out.println("Object: "+msg[1]);
		client.ClientConsole.send(msg);
		int status = replyWait();
		System.out.println("reply status: "+status);
		debugObjectTxt.setText(clientMsg);
    }

	private int replyWait() throws InterruptedException {
		int i;
		waitLock=1;
		// wait 10 seconds for reply 
		for( i=10; i>0 && waitLock==1;i--) {
            Thread.sleep(100);
            System.out.print(".");
		}
		return i;
	}

	public static void display(Command cmd) {
		// TODO Auto-generated method stub
		clientMsg=cmd.msg;
		if(cmd.obj instanceof Catalog) {
			System.out.println("recieved catalog from server");
			((Catalog)cmd.obj).printCatalog();
		}
		
		waitLock=0;
	}
	
}

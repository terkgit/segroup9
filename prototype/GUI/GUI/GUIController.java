package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;


public class GUIController {
	
	private static String clientMsg="";
	public static String userTxtStr="_Guest";	
	public GUIController instance;
	private static int waitLock=0;
	
	public class cont{
		
	}

	
    
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text userTxt;

    @FXML
    private Button loginBtn;

    @FXML
    private Button catalogBtn;
    
    @FXML
    private TextField loginPassTxt;

    @FXML
    private TextField loginUserTxt;

    @FXML
    private Button loginLogin;

    @FXML
    private TextField debugCommandTxt;

    @FXML
    private TextArea debugObjectTxt;

    @FXML
    private Button debugSendBtn;

    @FXML
    void initialize() {
        userTxt.setText("Hi "+userTxtStr);
        assert catalogBtn != null : "fx:id=\"catalogBtn\" was not injected: check your FXML file 'Login.fxml'.";
        assert loginPassTxt != null : "fx:id=\"loginPassTxt\" was not injected: check your FXML file 'Login.fxml'.";
        assert loginUserTxt != null : "fx:id=\"loginUserTxt\" was not injected: check your FXML file 'Login.fxml'.";
        assert loginLogin != null : "fx:id=\"loginLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert debugCommandTxt != null : "fx:id=\"debugCommandTxt\" was not injected: check your FXML file 'debug.fxml'.";
        assert debugObjectTxt != null : "fx:id=\"debugObjectTxt\" was not injected: check your FXML file 'debug.fxml'.";
        assert debugSendBtn != null : "fx:id=\"debugSendBtn\" was not injected: check your FXML file 'debug.fxml'.";

    }
    
    @FXML
    void gotoCatalog(ActionEvent event) throws IOException {
    	userTxt.setText("N/A");
//        URL url = getClass().getResource("Catalog.fxml");
//        AnchorPane pane = FXMLLoader.load( url );
//        Scene scene = new Scene( pane );
//        Stage stage = (Stage) catalogBtn.getScene().getWindow();
//        stage.setTitle("Catalog");
//        stage.setScene(scene);
    }

    @FXML
    void gotoLogin(ActionEvent event) throws IOException{
    	URL url = getClass().getResource("Login.fxml");
        AnchorPane pane = FXMLLoader.load( url );
        Scene scene = new Scene( pane );
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
    }
    

    @FXML
    void gotoWelcome(ActionEvent event) throws IOException{
        String usertxt=loginUserTxt.getText();
        userTxtStr=usertxt;
    	if(usertxt.equals("admin")) {    	
        	URL url = getClass().getResource("debug.fxml");
            AnchorPane pane = FXMLLoader.load( url );
            Scene scene = new Scene( pane );
            Stage stage = (Stage) loginLogin.getScene().getWindow();
            stage.setScene(scene);
        }
    	else {
	    	URL url = getClass().getResource("Welcome.fxml");
	        AnchorPane pane = FXMLLoader.load( url );
	        Scene scene = new Scene( pane );
	        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        stage.setTitle("Welcome "+userTxtStr);
	        System.out.println("user: "+userTxtStr);
	        userTxt.setText("user: "+userTxtStr);
	        stage.setScene(scene);
	        stage.show();
    	}
    }
    


    @FXML
    void debugRefresh(ActionEvent event) {
		System.out.println("msg after send"+clientMsg);
		System.out.println("--------------------");
		debugObjectTxt.setText(clientMsg);
    }

    @FXML
    void debugSend(ActionEvent event) throws InterruptedException {
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

	public static void display(String message) {
		// TODO Auto-generated method stub
		clientMsg=message;
		waitLock=0;
	}
	
}

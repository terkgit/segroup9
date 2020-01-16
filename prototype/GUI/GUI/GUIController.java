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


public class GUIController {
	
	private static int clientActive=0;
	private static String clientMsg="";
	public String userTxtStr=".userTxtStr.";
	
    
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
        assert userTxt != null : "fx:id=\"userTxt\" was not injected: check your FXML file 'Login.fxml'.";
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
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        userTxt.setText("Who are you??");
    }
    

    @FXML
    void gotoWelcome(ActionEvent event) throws IOException{
        String usertxt=loginUserTxt.getText();
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
	        Stage stage = (Stage) loginLogin.getScene().getWindow();
	        stage.setTitle("Welcome "+loginUserTxt.getText());
	        userTxt.setText("hi "+loginUserTxt.getText());
	        stage.setScene(scene);
	        userTxt.setText("hi "+loginUserTxt.getText());
    	}
    }
    


    @FXML
    void debugRefresh(ActionEvent event) {
		System.out.println("msg after send"+clientMsg);
		System.out.println("--------------------");
		debugObjectTxt.setText(clientMsg);
    }

    @FXML
    void debugSend(ActionEvent event) {
		String msg[]= {debugCommandTxt.getText(),debugObjectTxt.getText()};
    	System.out.println("command: "+msg[0]);
    	System.out.println("Object: "+msg[1]);
    	//  local 127.0.0.1 5555
    	String[] args = {"GUI","127.0.0.1","5555"};
    	if(clientActive==0) {
    		clientActive=1;
    		System.out.println("starting client...");
    		client.ClientConsole.start(args);
    	}
		client.ClientConsole.send(msg);
    }

	public static void display(String message) {
		// TODO Auto-generated method stub
		clientMsg=message;
	}
	
}

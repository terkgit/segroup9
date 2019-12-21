package calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class Controller {

	String str="";
	
	@FXML // fx:id="BTClear"
	private Button BTClear; // Value injected by FXMLLoader

    @FXML // fx:id="Bt1"
    private Button Bt1; // Value injected by FXMLLoader

    @FXML // fx:id="exam"
    private TextField exam; // Value injected by FXMLLoader

    @FXML // fx:id="Bt2"
    private Button Bt2; // Value injected by FXMLLoader

    @FXML // fx:id="Bt3"
    private Button Bt3; // Value injected by FXMLLoader

    @FXML // fx:id="Bt4"
    private Button Bt4; // Value injected by FXMLLoader

    @FXML // fx:id="Bt5"
    private Button Bt5; // Value injected by FXMLLoader

    @FXML // fx:id="Bt6"
    private Button Bt6; // Value injected by FXMLLoader

    @FXML // fx:id="Bt7"
    private Button Bt7; // Value injected by FXMLLoader

    @FXML // fx:id="Bt8"
    private Button Bt8; // Value injected by FXMLLoader

    @FXML // fx:id="Bt9"
    private Button Bt9; // Value injected by FXMLLoader

    @FXML // fx:id="BtL"
    private Button BtL; // Value injected by FXMLLoader

    @FXML // fx:id="Bt0"
    private Button Bt0; // Value injected by FXMLLoader

    @FXML // fx:id="BtR"
    private Button BtR; // Value injected by FXMLLoader

    @FXML // fx:id="BtP"
    private Button BtP; // Value injected by FXMLLoader

    @FXML // fx:id="BtM"
    private Button BtM; // Value injected by FXMLLoader

    @FXML // fx:id="Btx"
    private Button Btx; // Value injected by FXMLLoader

    @FXML // fx:id="BtH"
    private Button BtH; // Value injected by FXMLLoader

    @FXML // fx:id="BtCalc"
    private Button BtCalc; // Value injected by FXMLLoader
    
    @FXML
    void SetClear(ActionEvent event) {
		str=exam.getText();
    	str="";
    	exam.setText(str);
    }
    
    @FXML
    void SetText(ActionEvent event) {
    	str=exam.getText();
    	String txt=((Button)event.getSource()).getText();
		str=str+txt;
    	exam.setText(str);
    }   
    
    @FXML
    void SetDiv(ActionEvent event) {
    		str=exam.getText();
    		str=str+"/";
        	exam.setText(str);
    }

    @FXML
    void SetMul(ActionEvent event) {
		str=exam.getText();
		str=str+"*";
    	exam.setText(str);
    }
    
    @FXML
    void SetCalc(ActionEvent event) {
		str=exam.getText();
    	double d=ArithmeticApp.evaluate(str,0);
    	str=Double.toString(d);
    	exam.setText(str);
    }
}
package GUI;

/*
 * in order to execute GUI, set eclipse run configuration to:
 * 
 * 		VM arguments:
 * 		--module-path C:\javafx-sdk-13.0.1\lib --add-modules=javafx.controls,javafx.fxml
 * 
 * */

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage stage) throws IOException { 
    	
    	
    	
		GUIController.initStatics();
		
		//
        URL url = getClass().getResource("Welcome.fxml");
        AnchorPane pane = FXMLLoader.load( url );
        Scene scene = new Scene( pane );
        
        //
        stage.setScene(scene);
        stage.setTitle("Welcome");
        stage.show();

    }

    public static void main(String[] args) {
    	
    	// expected args: GUI 127.0.0.1 5555
		System.out.println("starting client...");
		client.ClientConsole.start(args);
    	
        launch();
    }
}


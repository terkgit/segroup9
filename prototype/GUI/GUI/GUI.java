package GUI;

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
        launch();
    }
}

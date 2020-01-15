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
        URL url = getClass().getResource("GUI.fxml");
        AnchorPane pane = FXMLLoader.load( url );
        Scene scene = new Scene( pane );
        
        //
        stage.setScene(scene);
        stage.setTitle("GUI");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


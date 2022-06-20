package shop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Assignment1 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();      
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Shop Simulator");
        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}

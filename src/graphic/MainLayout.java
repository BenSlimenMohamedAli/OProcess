package graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainLayout extends Application {
    public static void main(String args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main_layout.fxml"));

            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
    }
}

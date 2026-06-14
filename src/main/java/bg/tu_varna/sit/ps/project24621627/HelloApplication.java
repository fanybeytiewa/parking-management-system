package bg.tu_varna.sit.ps.project24621627;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));;
        Scene scene = new Scene(fxmlLoader.load(), 620, 540);
        stage.setTitle("Управление на паркинг места");
        stage.setScene(scene);
        stage.show();
    }
}

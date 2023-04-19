package MainModel;

import View.RealtimeController;
import View.SimulationController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Aktienkurs");
        RealtimeController realtimeController = new RealtimeController();
        Scene scene = realtimeController.getScene();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }




}

package MainModel;

import View.GroundView;
import View.RealtimeController;
import View.Simulation;
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

import static View.GroundView.change;

public class Main extends Application {
    Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Aktienkurs");

        if (change){
            RealtimeController realtimeController = new RealtimeController();
            scene = realtimeController.getScene();
            stage.setTitle("Realtime");
        } else {
            SimulationController simulationController = new SimulationController();
            scene = simulationController.getScene();
            stage.setTitle("Simulation");
        }

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }




}

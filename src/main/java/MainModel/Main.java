package MainModel;

import View.SimulationController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class Main extends Application {
    Scene scene;

    /**
     * Enum, um zu unterscheiden was angezeigt werden soll
     * standby: damit in der TimeLine nicht immer aktualisiert wird, obwohl keine Änderung war
     */
    public enum status {realtime, simulation, standby}
    public static status mode = status.realtime;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Aktienkurs");

        mode = status.realtime;
        RealtimeController realtimeController = new RealtimeController();
        SimulationController simulationController = new SimulationController();
        Timeline run = new Timeline(new KeyFrame(new Duration(10), actionEvent -> {
            //System.out.println(mode.toString());
            if (mode == status.realtime){
                scene = realtimeController.getScene();
                stage.setTitle("Realtime");
                stage.setScene(scene);
                stage.show();
                mode = status.standby;
            } else if(mode == status.simulation){
                scene = simulationController.getScene();
                stage.setTitle("Simulation");
                stage.setScene(scene);
                stage.show();
                mode = status.standby;
            }
        }));

        run.setCycleCount(Animation.INDEFINITE);
        run.play();

        //haelt die Timeline an, damit sie nicht staendig neu gestartet wird
        stage.setOnCloseRequest(windowEvent -> {
            run.stop();
        });

    }

    public static void main(String[] args) {
        launch();
    }




}

package MainModel;

import Utils.SimulationUtils;
import View.Controller;
import View.SimulationController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;



public class Main extends Application {
    Scene scene;

    /**
     * Enum, um zu unterscheiden was angezeigt werden soll
     * standby: damit in der TimeLine nicht immer aktualisiert wird, obwohl keine Änderung war
     */
    public enum status {realtime, simulation, standby}
    public static status mode = status.realtime;

    @Override
    public void start(Stage stage)  {

        stage.setTitle("Aktienkurs");

        mode = status.realtime;
        Controller realtimeController = new Controller();
        SimulationController simulationController = new SimulationController();
        SimulationUtils simulationUtils = new SimulationUtils(simulationController);
        Timeline run = new Timeline(new KeyFrame(new Duration(50), actionEvent -> {
            //System.out.println(mode.toString());
            if (mode == status.realtime){
                scene = realtimeController.getScene();

                //+15 und +30 weil scene nicht genau gelcih groß ist wie die stage
                stage.setWidth(simulationController.getScene().getWidth() + 15);
                stage.setHeight(simulationController.getScene().getHeight() + 30);
                /*
                realtimeController.getGroundView().getWindow().setPrefWidth(simulationController.getScene().getWidth());
                realtimeController.getGroundView().getWindow().setPrefHeight(simulationController.getScene().getHeight());

                 */

                realtimeController.adjustWindowSize(simulationController.getScene().getWidth(), simulationController.getScene().getHeight());

                stage.setTitle("Realtime");
                stage.setScene(scene);
                stage.show();
                mode = status.standby;
            } else if(mode == status.simulation){
                scene = simulationController.getScene();


                stage.setWidth(realtimeController.getScene().getWidth() + 15);
                stage.setHeight(realtimeController.getScene().getHeight() + 30);



                /*
                simulationController.getGroundView().getWindow().setPrefWidth(realtimeController.getScene().getWidth());
                simulationController.getGroundView().getWindow().setPrefHeight(realtimeController.getScene().getHeight());
                 */

                simulationController.adjustWindowSize(realtimeController.getScene().getWidth(), realtimeController.getScene().getHeight());

                stage.setTitle("Simulation");
                stage.setScene(scene);
                stage.show();
                mode = status.standby;
            }
        }));

        run.setCycleCount(Animation.INDEFINITE);
        run.play();

        stage.setMinWidth(800);
        stage.setMinHeight(500);

        //haelt die Timeline an, damit sie nicht staendig neu gestartet wird
        stage.setOnCloseRequest(windowEvent -> {
            run.stop();
        });

    }

    public static void main(String[] args) {
        launch();
    }
}

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

    /**
     * Enum, um zu unterscheiden was angezeigt werden soll
     * standby: damit in der TimeLine nicht immer aktualisiert wird, obwohl keine Änderung war
     */
    public enum status {realtime, simulation}
    public static status mode = status.realtime;

    public static Scene scene;

    public static Controller realtimeController = new Controller();
    public static SimulationController simulationController = new SimulationController();
    public static Stage primaryStage;
    @Override
    public void start(Stage stage)  {
        primaryStage = stage;

        stage.setTitle("Aktienkurs");

        mode = status.realtime;
        //Aufruf, um beim Start die Stage anzuzeigen
        changeBetweenModes();
        stage.setMinWidth(800);
        stage.setMinHeight(500);
    }

    /**
     * Methode, die beim Unschalten zwischen den Modi jeweils vom anderen Modus die SCene holt und diese anzeigen lässt
     */
    public static void changeBetweenModes(){
        if (mode == status.realtime){
            scene = realtimeController.getScene();

            //Groesse anpassen
            //+15 und +30 weil scene nicht genau gelcih groß ist wie die stage
            primaryStage.setWidth(simulationController.getScene().getWidth() + 15);
            primaryStage.setHeight(simulationController.getScene().getHeight() + 30);
            realtimeController.adjustWindowSize(simulationController.getScene().getWidth(), simulationController.getScene().getHeight());

            primaryStage.setTitle("Realtime");
            primaryStage.setScene(scene);
            primaryStage.show();
        } else if(mode == status.simulation){
            scene = simulationController.getScene();

            //Groesse anpassen
            primaryStage.setWidth(realtimeController.getScene().getWidth() + 15);
            primaryStage.setHeight(realtimeController.getScene().getHeight() + 30);
            simulationController.adjustWindowSize(realtimeController.getScene().getWidth(), realtimeController.getScene().getHeight());

            primaryStage.setTitle("Simulation");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

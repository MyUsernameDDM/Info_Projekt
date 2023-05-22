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

    public static Controller realtimeController;
    public static SimulationController simulationController;
    public static double windowWidth = 800;
    public static double windowHeight = 500;
    public static Stage primaryStage;
    @Override
    public void start(Stage stage)  {
        primaryStage = stage;
        realtimeController = new Controller();
        simulationController = new SimulationController();


        mode = status.realtime;
        //Aufruf, um beim Start die Stage anzuzeigen
        changeBetweenModes();
        //Mindestgröße festlegen
        stage.setMinWidth(900);
        stage.setMinHeight(550);
    }

    /**
     * Methode, die beim Unschalten zwischen den Modi jeweils vom anderen Modus die SCene holt und diese anzeigen lässt
     */
    public static void changeBetweenModes(){
        if (mode == status.realtime){
            //die Scene des realtimeController holen
            scene = realtimeController.getScene();

            //Groesse anpassen
            primaryStage.setWidth(windowWidth);
            primaryStage.setHeight(windowHeight);
            realtimeController.adjustWindowSize(windowWidth, windowHeight);

            primaryStage.setTitle("Realtime");
            primaryStage.setScene(scene);
            primaryStage.show();
        } else if(mode == status.simulation){

            //die Scene des simulationController holen
            scene = simulationController.getScene();

            //Groesse anpassen
            primaryStage.setWidth(windowWidth);
            primaryStage.setHeight(windowHeight);
            simulationController.adjustWindowSize(windowWidth, windowHeight);
            primaryStage.setTitle("Simulation");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

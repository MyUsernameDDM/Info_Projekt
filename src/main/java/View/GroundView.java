package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;


public class GroundView {
    private int sceneWith = 800;
    private int sceneHeight = 500;
    private double old_width = sceneWith;
    private double old_height = sceneHeight;

    Group root = new Group();
    BorderPane window = new BorderPane();
    Scene scene;



    //Menu erstellen
    HBox menu = new HBox();
    //Button login = new Button();
    Button simulationMode = new Button();
    Button wallet = new Button();

    Rectangle graph = new Rectangle();
    Button simulationModeButton = new Button();
    Button changeStateButton = new Button("Normal");
    VBox rightGroupBox = new VBox();
    HBox timeBox = new HBox();


    Controller controller;

    //Button Array für die Zeitspanne
    Button[]timeButtons = new Button[7];
    String[]timeButtonsName = {
            "1D",
            "1M",
            "3M",
            "6M",
            "1Y",
            "5Y",
            "MAX"
    };


    public GroundView(Controller controller) {
        this.controller = controller;
        displayGraphic();


        window.setTop(menu);
        window.getTop().setStyle("-fx-background-color: #e6e8ec;");
        window.setBottom(timeBox);
        window.setCenter(graph);
        root.getChildren().add(window);
    }


    /*

    Alle größen setzen (Button, etc.)
    Mingröße

     */

    /**
     * Methode um die GroundView grafisch darzustellen
     */
    public void displayGraphic(){
        //Menu
        wallet.setText("WALLET");
        wallet.getStyleClass().add("menuButton");
        wallet.setPrefWidth(150);
        wallet.setPrefHeight(30);

        simulationMode.setText("MODE");
        simulationMode.getStyleClass().add("menuButton");
        simulationMode.setPrefWidth(150);
        simulationMode.setPrefHeight(30);

        menu.getChildren().addAll(simulationMode, wallet);

        menu.setMargin(wallet, new Insets(10, 10, 10, 10));
        menu.setMargin(simulationMode, new Insets(10, 10, 10, 10));




        changeStateButton.setPadding(new Insets(10, 10, 10, 10));
        changeStateButton.setPrefWidth(60);
        timeBox.getChildren().add(changeStateButton);

        for (int i = 0; i < timeButtons.length; i++){
            timeButtons[i] = new Button();
            //timeButtons[i].setPadding(new Insets(10, 10, 10, 10));
            timeButtons[i].setText(timeButtonsName[i]);
            timeButtons[i].getStyleClass().add("button");
            timeBox.getChildren().add(timeButtons[i]);
        }

        graph.setHeight(300);
        graph.setWidth(300);
        graph.setFill(Color.GREEN);

        simulationModeButton.setText("Mode");
        simulationModeButton.setLayoutY(200);
        simulationModeButton.setLayoutX(200);


        InfoView infoView = new InfoView();

        for (int i = 0; i < timeButtons.length; i++){
            infoView.showInfoView(timeButtons[i]);
        }
        //vielleicht dech tim Controller mochn, dass man theoretisch lei des in der Mitte unpasst
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                graph.setX(graph.getX() / old_width * scene.getWidth());
                graph.setY(graph.getY() / old_height * scene.getHeight());

                old_width = scene.getWidth();
                old_height = scene.getHeight();
                /*olls ungepasst werden*/
            }
        };


        scene = new Scene(root, sceneWith, sceneHeight);
        scene.heightProperty().addListener(changeListener);
        scene.widthProperty().addListener(changeListener);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("ProgramStyle.css")).toExternalForm());
    }

    public void setMenu(Node node){
        menu.getChildren().add(node);
    }


}



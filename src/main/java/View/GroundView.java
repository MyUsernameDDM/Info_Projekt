package View;

import MainModel.Article;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.Objects;


public class GroundView {
    private int sceneWith = 800;
    private int sceneHeight = 500;
    double oldSceneWidth = sceneWith;
    double oldSceneHeight = sceneHeight;

    Group root = new Group();
    BorderPane window = new BorderPane();
    Scene scene;

    //Menu erstellen
    HBox menu = new HBox();
    Button modeButton = new Button();
    Button wallet = new Button();

    Button simulationModeButton = new Button();
    Button changeStateButton = new Button("Normal");
    HBox timeBox = new HBox();


    Controller controller;

    //Button Array für die Zeitspanne
    Button[] timeButtons = new Button[7];
    String[] timeButtonsName = {"1D", "1M", "3M", "6M", "1Y", "5Y", "MAX"};


    public GroundView(Controller controller) {
        this.controller = controller;
        displayGraphic();

        window.setTop(menu);
        window.getTop().setStyle("-fx-background-color: #3f6cc9;");
        window.setBottom(timeBox);
        root.getChildren().add(window);
    }


    /*

    Alle größen setzen (Button, etc.)
    Mingröße

     */

    /**
     * Methode um die GroundView grafisch darzustellen
     */
    public void displayGraphic() {
        //Menu
        wallet.setText("WALLET");
        wallet.getStyleClass().add("menuButton");
        wallet.setPrefWidth(150);
        wallet.setPrefHeight(30);
        wallet.setTextFill(Color.WHITE);

        modeButton.setText("MODE");
        modeButton.getStyleClass().add("menuButton");
        modeButton.setPrefWidth(150);
        modeButton.setPrefHeight(30);
        modeButton.setTextFill(Color.WHITE);

        menu.getChildren().addAll(modeButton, wallet);
        menu.setMargin(wallet, new Insets(1, 1, 1, 1));
        menu.setMargin(modeButton, new Insets(1, 1, 1, 1));
        menu.setPrefHeight(70);


        changeStateButton.setPrefWidth(60);
        changeStateButton.setPrefHeight(40);
        changeStateButton.getStyleClass().add("buttonTime");
        timeBox.getChildren().add(changeStateButton);
        timeBox.setMargin(changeStateButton, new Insets(2, 2, 2, 2));
        timeBox.setPrefHeight(40);

        for (int i = 0; i < timeButtons.length; i++) {
            timeButtons[i] = new Button();
            timeButtons[i].setText(timeButtonsName[i]);
            timeButtons[i].getStyleClass().add("buttonTime");
            timeButtons[i].setPrefHeight(40);
            timeButtons[i].setPrefWidth(40);
            timeBox.getChildren().add(timeButtons[i]);
            timeBox.setMargin(timeButtons[i], new Insets(2, 2, 2, 2));
        }

        timeButtons[6].setPrefWidth(60);

        simulationModeButton.setText("Mode");
        simulationModeButton.setLayoutY(200);
        simulationModeButton.setLayoutX(200);

        InfoView infoView = new InfoView();

        for (int i = 0; i < timeButtons.length; i++) {
            infoView.showInfoView(timeButtons[i], controller);
        }


        scene = new Scene(root, sceneWith, sceneHeight);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("ProgramStyle.css")).toExternalForm());
    }


    public void simulationModeHover(Boolean status) {
        if (status) {
            modeButton.setTextFill(Color.LIGHTBLUE);
        } else {
            modeButton.setTextFill(Color.WHITE);
        }
    }

    public void walletHover(Boolean status) {
        if (status) {
            wallet.setTextFill(Color.LIGHTBLUE);
        } else {
            wallet.setTextFill(Color.WHITE);
        }
    }

    public void timeButtonsHover(int i, Boolean status) {
        if (status) {
            timeButtons[i].setTextFill(Color.RED);
        } else {
            timeButtons[i].setTextFill(Color.BLACK);
        }
    }

    public void timeButtonsOnMouseClicked(int i) {
        for (int u = 0; u < timeButtons.length; u++) {
            timeButtons[u].getStyleClass().remove("buttonTimeClicked");
            timeButtons[u].getStyleClass().add("buttonTime");
        }
        timeButtons[i].getStyleClass().remove("buttonTime");
        timeButtons[i].getStyleClass().add("buttonTimeClicked");
    }

}



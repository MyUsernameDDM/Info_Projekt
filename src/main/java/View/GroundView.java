package View;

import MainModel.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GroundView {

    private int MAX = 500;
    private double old_height = MAX;

    private double old_width = MAX;

    //Border Pane erstellen
    BorderPane root = new BorderPane();

    //Scene erstellen
    Scene scene;

    Rectangle graph = new Rectangle();

    //Buttons erstellen
    Button simulationModeButton = new Button();
    Button oneDayButton = new Button();
    Button fiveDayButton = new Button();
    Button oneMonthButton = new Button();
    Button threeMonthButton = new Button();
    Button sixMonthButton = new Button();
    Button ytdButton = new Button();
    Button oneYearButton = new Button();
    Button fiveYearButton = new Button();
    Button searchButton = new Button();

    //Input (Textfeld) erstellen
    TextField searchInputTextField = new TextField();

    //Ausgabe Textfeld erstellen
    Text shareNameText = new Text();
    Text watchlistText = new Text();
    Text sharePriceText = new Text();
    Text sharePercentText = new Text();

    VBox rightGroupBox = new VBox();

    HBox timeBox = new HBox();

    HBox searchBox = new HBox();

    HBox changeBox = new HBox();
    Controller controller;

    public GroundView(Controller controller) {
        displayGraphic();
        root.setRight(changeBox);
        root.setTop(searchBox);
        root.setBottom(timeBox);
        root.setCenter(graph);
        this.controller = controller;

    }
    /*

    Alle größen setzen (Button, etc.)

    Mingröße


     */



    public void displayGraphic(){
//
        graph.setHeight(300);
        graph.setWidth(300);
        graph.setFill(Color.GREEN);

        int buttonX = 100;
        int buttonY = 100;

        /*
        searchInputTextField.setText("Search");
        searchButton.setText("Search");
        searchButton.setLayoutX(150);
        searchButton.setLayoutY(20);

        Kimp in Searchview inne
         */

        oneDayButton.setText("1D");
        oneDayButton.setLayoutX(buttonX);
        oneDayButton.setLayoutY(buttonY);

        fiveDayButton.setText("5D");
        fiveDayButton.setLayoutX(buttonX);
        fiveDayButton.setLayoutY(buttonY);

        oneMonthButton.setText("1M");
        oneMonthButton.setLayoutX(buttonX);
        oneMonthButton.setLayoutY(buttonY);

        threeMonthButton.setText("3M");
        threeMonthButton.setLayoutX(buttonX);
        threeMonthButton.setLayoutY(buttonY);

        sixMonthButton.setText("6M");
        sixMonthButton.setLayoutX(buttonX);
        sixMonthButton.setLayoutY(buttonY);

        oneYearButton.setText("1Y");
        oneYearButton.setLayoutX(buttonX);
        oneYearButton.setLayoutY(buttonY);

        fiveYearButton.setText("5Y");
        fiveYearButton.setLayoutX(buttonX);
        fiveYearButton.setLayoutY(buttonY);

        ytdButton.setLayoutX(buttonX);
        ytdButton.setLayoutY(buttonY);

        simulationModeButton.setText("Simulation");
        simulationModeButton.setLayoutY(200);
        simulationModeButton.setLayoutX(200);

        timeBox = new HBox(oneDayButton, fiveDayButton, oneMonthButton, sixMonthButton, oneYearButton, fiveYearButton, ytdButton);

        searchBox = new HBox(searchInputTextField, searchButton);

        changeBox = new HBox(simulationModeButton);
        scene = new Scene(root, MAX, MAX);




        //vielleicht dech tim Controller mochn, dass man theoretisch lei des in der Mitte unpasst
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                graph.setX(graph.getX() / old_width * scene.getWidth());
                graph.setY(graph.getY() / old_height * scene.getHeight());

                old_width = scene.getWidth();
                old_height = scene.getHeight();
            }
        };
        //Um View mit Model zu trennen immer mit Lambda die entsprechenden Methoden aufrufen siehe oben

        simulationModeButton.setOnAction(actionEvent -> {
            System.out.println("Test");
            controller.changeMode();});

        scene.heightProperty().addListener(changeListener);
        scene.widthProperty().addListener(changeListener);
    }
}

package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class GroundView {

    //CourseUtils courseUtils = new CourseUtils();

    private int MAX = 500;
    private double old_height = MAX;
    private double old_width = MAX;

    public enum menus {};
    MenuBar topMenuBar = new MenuBar();
    Menu menu_1 = new Menu("Login");
    Menu menu_2 = new Menu("Mode");
    Menu menu_3 = new Menu("Wallet");
    Menu menu_4 = new Menu("Search");
    MenuItem submenu_2_1 = new MenuItem("Switch");

    BorderPane window = new BorderPane();
    Scene scene;
    Rectangle graph = new Rectangle();
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
    Button[]buttonTime = new Button[7];
    String[]buttonTimeName = {
            "1D",
            "1M",
            "3M",
            "6M",
            "1Y",
            "5Y",
            "MAX"
    };

    public GroundView(Controller controller) {
        displayGraphic();

        topMenuBar.getMenus().addAll(menu_1, menu_2, menu_3, menu_4);

        topMenuBar.setUseSystemMenuBar(true);



        menu_2.getItems().add(submenu_2_1);
        submenu_2_1.setOnAction(actionEvent -> {
            controller.changeMode();
        });

        menu_3.setOnAction(actionEvent -> {
            System.out.println("Wallet");
        });

        menu_4.setOnAction(actionEvent -> {

        });

        window.setTop(topMenuBar);
        //window.setRight(changeBox);
        //window.setTop(searchBox);
        window.setBottom(timeBox);
        window.setCenter(graph);
        this.controller = controller;
    }
    /*

    Alle größen setzen (Button, etc.)

    Mingröße


     */



    public void displayGraphic(){

        int buttonX = 100;
        int buttonY = 100;

        /*
        buttonTime[0] ==> 1D
        buttonTime[1] ==> 5D
        buttonTime[2] ==> 1M
        buttonTime[3] ==> 3M
        buttonTime[4] ==> 6M
        buttonTime[5] ==> 1Y
        buttonTime[6] ==> 5Y
         */

        for (int i = 0; i < buttonTime.length; i++){
            buttonTime[i] = new Button();
            buttonTime[i].setPadding(new Insets(10, 10, 10, 10));
            buttonTime[i].setText(buttonTimeName[i]);
            buttonTime[i].setLayoutX(buttonX);
            buttonTime[i].setLayoutY(buttonY);
        }

        graph.setHeight(300);
        graph.setWidth(300);
        graph.setFill(Color.GREEN);

        /*
        searchInputTextField.setText("Search");
        searchButton.setText("Search");
        searchButton.setLayoutX(150);
        searchButton.setLayoutY(20);

        Kimp in Searchview inne
         */

        ytdButton.setLayoutX(buttonX);
        ytdButton.setLayoutY(buttonY);

        simulationModeButton.setText("Mode");
        simulationModeButton.setLayoutY(200);
        simulationModeButton.setLayoutX(200);

        for (int i = 0; i < buttonTime.length; i++){
            timeBox.getChildren().add(buttonTime[i]);
        }

        /*
        timeBox = new HBox();
         */

        searchBox = new HBox(searchInputTextField, searchButton);
        scene = new Scene(window, MAX, MAX);




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


        //isch changeWIndow lei zum Testen?
        for (int i = 0; i < buttonTime.length; i++) {
            int finalI = i;
            buttonTime[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(finalI + "D");
                    controller.changeTimeMode(finalI);
                    changeWindow(finalI);
                }
            });
        }

        scene.heightProperty().addListener(changeListener);
        scene.widthProperty().addListener(changeListener);
    }

    private void changeWindow(int i) {
        ///changeShowInterval();
    }
}


/*
Buttons:


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



 */
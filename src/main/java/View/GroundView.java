package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
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

    private int sceneWith = 800;
    private int sceneHeight = 500;

    private double old_width = sceneWith;
    private double old_height = sceneHeight;



    public enum menus {};


    //Menü Leiste mit Items erstellen
    MenuBar topMenuBar = new MenuBar();
    Menu menu_1 = new Menu("Login");
    Menu menu_2 = new Menu("Mode");
    Menu menu_3 = new Menu("Wallet");
    Menu menu_4 = new Menu("Search");
    MenuItem submenu_2_1 = new MenuItem("Switch");

    Group root = new Group();
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
    Button changeStateButton = new Button("Normal");

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

        //MenuBar: MenuItems hinzugefügen
        topMenuBar.getMenus().addAll(menu_1, menu_2, menu_3, menu_4);

        topMenuBar.setUseSystemMenuBar(true);


        menu_2.getItems().add(submenu_2_1);
        submenu_2_1.setOnAction(actionEvent -> {
            controller.changeMode();
        });

        menu_3.setOnAction(actionEvent -> {
            System.out.println("Wallet");
        });

        window.setTop(topMenuBar);
        //window.setRight(changeBox);
        //window.setTop(searchBox);
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
        changeStateButton.setPadding(new Insets(10, 10, 10, 10));
        changeStateButton.setPrefWidth(60);
        timeBox.getChildren().add(changeStateButton);

        for (int i = 0; i < timeButtons.length; i++){
            timeButtons[i] = new Button();
            timeButtons[i].setPadding(new Insets(10, 10, 10, 10));
            timeButtons[i].setText(timeButtonsName[i]);
            timeBox.getChildren().add(timeButtons[i]);
        }

        graph.setHeight(300);
        graph.setWidth(300);
        graph.setFill(Color.GREEN);

        simulationModeButton.setText("Mode");
        simulationModeButton.setLayoutY(200);
        simulationModeButton.setLayoutX(200);

        searchBox = new HBox(searchInputTextField, searchButton);

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
    }
}

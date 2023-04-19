package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GroundView {
    //Border Pane erstellen
    BorderPane root = new BorderPane();

    //Scene erstellen
    Scene scene;

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

    HBox Timeline = new HBox();

    HBox Searchline = new HBox();

    public GroundView() {
        root.setRight(rightGroupBox);
        root.setTop(Searchline);
        root.setBottom(Timeline);
    }





    /*
    new Scene(controller.view.root)
     */


    /**
     * Zeigt den Realtime Modus auf dem Bildschirm.
     *
     */


    /*

    Alle größen setzen (Button, etc.)

    Mingröße


     */



    public void displayGraphic(Stage stage){

       /*
        LineChart<?,?> lineChart = null;

        XYChart.Series series = new XYChart.Series();
        series.setName("No of schools in an year");

        series.getData().add(new XYChart.Data(1970, 15));
        series.getData().add(new XYChart.Data(1980, 30));
        series.getData().add(new XYChart.Data(1990, 60));
        series.getData().add(new XYChart.Data(2000, 120));
        series.getData().add(new XYChart.Data(2013, 240));
        series.getData().add(new XYChart.Data(2014, 300));

        lineChart.getData().addAll(series);

        root.setCenter(lineChart);
        */

        searchInputTextField.setText("Search");
        searchButton.setLayoutX(150);
        searchButton.setLayoutY(20);

        oneDayButton.setLayoutX(50);
        oneDayButton.setLayoutY(50);

        fiveDayButton.setLayoutX(50);
        fiveDayButton.setLayoutY(50);

        oneMonthButton.setLayoutX(50);
        oneMonthButton.setLayoutY(50);

        threeMonthButton.setLayoutX(50);
        threeMonthButton.setLayoutY(50);

        sixMonthButton.setLayoutX(50);
        sixMonthButton.setLayoutY(50);

        ytdButton.setLayoutX(50);
        ytdButton.setLayoutY(50);

        oneYearButton.setLayoutX(50);
        oneYearButton.setLayoutY(50);

        fiveYearButton.setLayoutX(50);
        fiveYearButton.setLayoutY(50);

        Timeline = new HBox(oneDayButton, fiveDayButton, oneMonthButton, threeMonthButton, sixMonthButton, ytdButton, oneYearButton, fiveDayButton);

        Searchline = new HBox(searchInputTextField, searchButton);

        root.getChildren().add(simulationModeButton);
        scene = new Scene(root, 500, 500);
        stage.setTitle("Realtime Mode");
        stage.setScene(scene);
        stage.show();
    }
}

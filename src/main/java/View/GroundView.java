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

    HBox TimeBox = new HBox();

    HBox SearchBox = new HBox();

    public GroundView() {

        displayGraphic();
        root.setRight(rightGroupBox);
        root.setTop(SearchBox);
        root.setBottom(TimeBox);
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



    public void displayGraphic(){
//
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
        int buttonX = 100;
        int buttonY = 100;

        searchInputTextField.setText("Search");
        searchButton.setText("Search");
        searchButton.setLayoutX(150);
        searchButton.setLayoutY(20);

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

        TimeBox.getChildren().add(oneDayButton);
        TimeBox.getChildren().add(fiveDayButton);
        TimeBox.getChildren().add(oneMonthButton);
        TimeBox.getChildren().add(sixMonthButton);
        TimeBox.getChildren().add(oneYearButton);
        TimeBox.getChildren().add(fiveYearButton);
        TimeBox.getChildren().add(ytdButton);

        SearchBox = new HBox(searchInputTextField, searchButton);

        root.getChildren().add(simulationModeButton);
        scene = new Scene(root, 500, 500);
    }
}

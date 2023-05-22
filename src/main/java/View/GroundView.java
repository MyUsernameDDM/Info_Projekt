package View;

import MainModel.Main;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * GroundView Klasse: Diese Klasse bildet
 */
public class GroundView {
    /*
     * Das Attribut root: Diese Group wird verwendet, um unabhängig von der BorderPane window, Elemente auf dem Bildschirm
     * anzuzeigen. Dies wird z.B. bei der Liste für die gesuchten Artikelvorschläge oder bei den Buttons für das Laden, Speicher
     * und Erstellen einer SImulation verwendet.
     */
    Group root = new Group();
    /*
    Window ist die Borderpane, die die grundsätzliche Struktur der Anzeige bildet, in sie werden anshcließend die einzelnen Elemente
    eingefügt.
     */
    BorderPane window = new BorderPane();
    Scene scene;


    //Menu erstellen
    HBox menu = new HBox();
    Button modeButton = new Button();       //Button zum Umschalten in den jeweiligen anderen Modus
    Button changeStateButton = new Button("Normal");        //Button zum Umschalten zwischen normaler Kursanzeige und Chart-Kursanzeige

    HBox timeBox = new HBox();      //HBox für die changeStateButton und die timeBUttons
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

        //Inhalte der BorderPane setzen und dann in die root-Group geben
        window.setTop(menu);
        window.getTop().setStyle("-fx-background-color: #3f6cc9;");     //TOP mit blauen Hintergrund
        window.setBottom(timeBox);
        root.getChildren().add(window);
    }

    /**
     * Methode um die GroundView grafisch darzustellen
     */
    public void displayGraphic(){
        //Menu
        modeButton.setText("MODE");
        modeButton.getStyleClass().add("menuButton");
        modeButton.setPrefWidth(150);
        modeButton.setPrefHeight(30);
        modeButton.setTextFill(Color.WHITE);
        menu.getChildren().add(modeButton);
        menu.setMargin(modeButton, new Insets(1, 1, 1, 1));
        menu.setPrefHeight(70);

        //UmschalteButton in andere Ansicht Groesse setzen
        changeStateButton.setPrefWidth(60);
        changeStateButton.setPrefHeight(40);
        changeStateButton.getStyleClass().add("buttonTime");
        timeBox.getChildren().add(changeStateButton);
        timeBox.setMargin(changeStateButton, new Insets(2, 2, 2, 2));
        timeBox.setPrefHeight(40);

        //Style und Groesse der Zeitbuttons einstellen
        for (int i = 0; i < timeButtons.length; i++){
            timeButtons[i] = new Button();
            timeButtons[i].setText(timeButtonsName[i]);
            timeButtons[i].getStyleClass().add("buttonTime");
            timeButtons[i].setPrefHeight(40);
            timeButtons[i].setPrefWidth(40);
            timeBox.getChildren().add(timeButtons[i]);
            timeBox.setMargin(timeButtons[i], new Insets(2,2,2,2));
        }

        timeButtons[6].setPrefWidth(60);

        scene = new Scene(root, Main.windowWidth, Main.windowHeight);
        //CSS-Datei einbinden
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("ProgramStyle.css")).toExternalForm());
    }


    /**
     *Methode um die Schriftfarbe des AnzeigeModusButton verändern
     * @param status
     */
    public void simulationModeHover(Boolean status){
        if(status){
            modeButton.setTextFill(Color.LIGHTBLUE);
        } else {
            modeButton.setTextFill(Color.WHITE);
        }
    }

    /**
     * Bei Überhovern, über die TimeButtons die Schriftfare ändern
     * @param i
     * @param status
     */
    public void timeButtonsHover(int i, Boolean status){
        if(status){
            timeButtons[i].setTextFill(Color.RED);
        } else {
            timeButtons[i].setTextFill(Color.BLACK);
        }
    }

    /**
     * Style für die ZeitUmstellungs-Buttons bei Anklicken setzen
     * @param i
     */
    public void timeButtonsOnMouseClicked(int i){
        for(int u = 0; u < timeButtons.length; u++) {
            timeButtons[u].getStyleClass().remove("buttonTimeClicked");
            timeButtons[u].getStyleClass().add("buttonTime");
        }
        timeButtons[i].getStyleClass().remove("buttonTime");
        timeButtons[i].getStyleClass().add("buttonTimeClicked");
    }

}



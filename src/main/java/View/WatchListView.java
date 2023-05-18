package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.time.chrono.IsoChronology;
import java.util.ArrayList;

public class WatchListView {
    Controller controller;
    VBox wlRoot = new VBox();
    ScrollPane scrollPane = new ScrollPane();
    Button addButton = new Button("Artikel hinzufügen");
    Button removeButton = new Button("Artikel entfernen");
    ArrayList<Button> buttonList = new ArrayList<>();
    Label titel = new Label("Watchlist");
    VBox vBox = new VBox();

    public WatchListView(Controller controller) {
        this.controller = controller;
        setButtons();
        titel.setStyle("-fx-font-size: 35px;");

        scrollPane.prefHeight(500);
        scrollPane.setContent(vBox);
        wlRoot.setAlignment(Pos.TOP_CENTER);
        scrollPane.setStyle("-fx-background-color: transparent");

        wlRoot.getChildren().addAll(titel,addButton, removeButton, scrollPane);
        wlRoot.setPrefWidth(200);
    }

    //Methode, um das Layout und den Style des Add- und Removebuttons zu setzen
    private void setButtons() {
        addButton.setPrefWidth(150);
        addButton.setPrefHeight(30);
        addButton.getStyleClass().add("addAndRemoveButtonNormal");
        removeButton.setPrefWidth(150);
        removeButton.setPrefHeight(30);
        removeButton.getStyleClass().add("addAndRemoveButtonNormal");


        wlRoot.setMargin(addButton, new Insets(10, 10, 10, 10));
        wlRoot.setMargin(removeButton, new Insets(10, 10, 10, 10));
    }



    public void setAddButtonHover(Boolean status){
        if(status){
            addButton.getStyleClass().remove("addAndRemoveButtonNormal");
            addButton.getStyleClass().add("addAndRemhoverButtonHover");
        } else {
            addButton.getStyleClass().remove("addAndRemhoverButtonHover");
            addButton.getStyleClass().add("addAndRemoveButtonNormal");
        }
    }

    /**
     *
     * @param status
     */
    public void setRemoveButtonHover(Boolean status){
        if(status){
            removeButton.getStyleClass().remove("addAndRemoveButtonNormal");
            removeButton.getStyleClass().add("addAndRemhoverButtonHover");
        } else {
            removeButton.getStyleClass().remove("addAndRemhoverButtonHover");
            removeButton.getStyleClass().add("addAndRemoveButtonNormal");
        }
    }

    /**
     * Methode, die bei Überhovern durch die Maus den Style ändert
     * @param status wenn true, dann ist die Maus über dem Button
     */
    public void setButtonHover(Boolean status, Button button){
        if(status){
            button.getStyleClass().remove("addAndRemoveButtonNormal");
            button.getStyleClass().add("addAndRemhoverButtonHover");
        } else {
            button.getStyleClass().remove("addAndRemhoverButtonHover");
            button.getStyleClass().add("addAndRemoveButtonNormal");
        }
    }
}

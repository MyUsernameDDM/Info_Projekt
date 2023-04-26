package View;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.nio.Buffer;
import java.util.ArrayList;

public class WatchListView {
    Controller controller;
    ScrollPane wlRoot = new ScrollPane();
    Button addButton = new Button("Artikel hinzufügen");
    Button removeButton = new Button("Artikel entfernen");
    ArrayList<Button> buttonList = new ArrayList<>();
    Label titel = new Label("Watchlist");
    VBox vBox = new VBox();



    public WatchListView(Controller controller) {
        this.controller = controller;
        setButtons();
        vBox.getChildren().addAll(titel,addButton, removeButton);
        wlRoot.setContent(vBox);
        wlRoot.setPrefWidth(500);


    }

    private void setButtons() {
        addButton.setStyle(String.valueOf(Color.ALICEBLUE));
        addButton.setPrefWidth(150);
        addButton.setPrefHeight(30);
        removeButton.setStyle(String.valueOf(Color.ALICEBLUE));
        removeButton.setPrefWidth(150);
        removeButton.setPrefHeight(30);
    }

    /**
     * Methode zum setzen der Artikel-Namen in der buttonList, mehrere auf einmal
     * @param articleNames Enthält die Namen von den Artikeln die angezeigt werden sollen
     *
     */
    public void addArticles(ArrayList<String> articleNames){
        for (String str :articleNames) {
            Button temp = new Button(str);
            temp.setPrefHeight(30);
            temp.setPrefWidth(100);
            buttonList.add(temp);
            vBox.getChildren().add(temp);
        }
    }

}

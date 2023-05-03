package View;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
        scrollPane.setContent(vBox);
        wlRoot.getChildren().addAll(titel,addButton, removeButton, scrollPane);
        wlRoot.setPrefWidth(500);
    }


    private void setButtons() {
        //addButton.setStyle(String.valueOf(Color.LIGHTGREEN));
        addButton.setPrefWidth(150);
        addButton.setPrefHeight(30);
        //addButton.setStyle("-fx-padding: 10, 10, 10, 10;");

        addButton.setStyle("-fx-background-radius: 20;" +
                "-fx-font-size: 14px;" +
                "-fx-border-radius: 500px;" +
                "-fx-spacing: 2px;" +
                "-fx-text-alignment: center;" +
                "-fx-background-color: #add8e6;" +
                "-fx-border-color: #72bcd4;");

        removeButton.setStyle(String.valueOf(Color.ALICEBLUE));
        removeButton.setPrefWidth(150);
        removeButton.setPrefHeight(30);
        removeButton.setStyle("-fx-background-radius: 20;" +
                "-fx-font-size: 14px;" +
                "-fx-border-radius: 500px;" +
                "-fx-spacing: 2px;" +
                "-fx-text-alignment: center;" +
                "-fx-background-color: #add8e6;" +
                "-fx-border-color: #72bcd4;");
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

    public void setAddButtonHover(Boolean status){
        if(status){
            //addButton.getStyleClass().removeAll();
            addButton.getStyleClass().add("hoverButton");
            /*
            addButton.setStyle("-fx-background-radius: 20;" +
                    "-fx-font-size: 14px;" +
                    "-fx-border-radius: 500px;" +
                    "-fx-spacing: 2px;" +
                    "-fx-text-alignment: center;" +
                    "-fx-background-color: #72bcd4;" +
                    "-fx-border-color: #72bcd4;");

             */
        } else {
            addButton.setStyle("-fx-background-radius: 20;" +
                    "-fx-font-size: 14px;" +
                    "-fx-border-radius: 500px;" +
                    "-fx-spacing: 2px;" +
                    "-fx-text-alignment: center;" +
                    "-fx-background-color: #add8e6;" +
                    "-fx-border-color: #72bcd4;");
        }
    }

    public void setRemoveButtonHover(Boolean status){
        if(status){
            removeButton.setStyle("-fx-background-radius: 20;" +
                    "-fx-font-size: 14px;" +
                    "-fx-border-radius: 500px;" +
                    "-fx-spacing: 2px;" +
                    "-fx-text-alignment: center;" +
                    "-fx-background-color: #72bcd4;" +
                    "-fx-border-color: #72bcd4;");
        } else {
            removeButton.setStyle("-fx-background-radius: 20;" +
                    "-fx-font-size: 14px;" +
                    "-fx-border-radius: 500px;" +
                    "-fx-spacing: 2px;" +
                    "-fx-text-alignment: center;" +
                    "-fx-background-color: #add8e6;" +
                    "-fx-border-color: #72bcd4;");
        }
    }
}

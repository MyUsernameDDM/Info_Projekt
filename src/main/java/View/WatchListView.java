package View;

import javafx.geometry.Insets;
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
        setTitel();
        scrollPane.setContent(vBox);
        wlRoot.getChildren().addAll(titel,addButton, removeButton, scrollPane);
        wlRoot.setPrefWidth(500);
    }

    public void setTitel(){
        titel.setStyle("-fx-font-size: 35px;");
    }


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
            addButton.getStyleClass().remove("addAndRemoveButtonNormal");
            addButton.getStyleClass().add("addAndRemhoverButtonHover");
        } else {
            addButton.getStyleClass().remove("addAndRemhoverButtonHover");
            addButton.getStyleClass().add("addAndRemoveButtonNormal");
        }
    }

    public void setRemoveButtonHover(Boolean status){
        if(status){
            removeButton.getStyleClass().remove("addAndRemoveButtonNormal");
            removeButton.getStyleClass().add("addAndRemhoverButtonHover");
        } else {
            removeButton.getStyleClass().remove("addAndRemhoverButtonHover");
            removeButton.getStyleClass().add("addAndRemoveButtonNormal");
        }
    }
}

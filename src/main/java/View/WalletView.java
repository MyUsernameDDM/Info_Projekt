package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class WalletView {
    String selectedArticle;
    ArrayList<Button> buttonList = new ArrayList<>();
    Label titel = new Label("Wallet");
    Button sellButton = new Button("Verkaufen");
    VBox wlRoot = new VBox();



    public WalletView() {
        wlRoot.getChildren().add(titel);
    }

    /**
     * Methode zum setzen der Artikel-Namen in der buttonList
     *
     * @param articleNames Enthält die Namen von den Artikeln die angezeigt werden sollen
     */
    public void setArticles(ArrayList<String> articleNames) {
        for (String str : articleNames) {
            Button temp = new Button(str);
            temp.setPrefHeight(30);
            temp.setPrefWidth(100);
            buttonList.add(temp);
            wlRoot.getChildren().add(temp);
        }
    }

    public void addArticle(String articleName) {
        buttonList.add(new Button(articleName));
    }

    public void removeArticle(String articleName) {
        for (Button b : buttonList) {
            if (b.getText().equals(articleName)) {
                buttonList.remove(b);
            }
        }
    }

}
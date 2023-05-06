package View;

import MainModel.MatchUnits;
import Utils.SearchUtils;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SearchView extends Thread {
    Controller controller;
    HBox root = new HBox();

    ScrollPane outputSearchView = new ScrollPane();
    TextField searchInputTextField = new TextField();
    Button searchButton = new Button();

    ArrayList<String> searchResult = new ArrayList<>();
    VBox recommendsBox = new VBox();

    final Button[] recommends = new Button[10];
    //GroundView groundView = new GroundView(controller);


    /**
     * Konstruktor zum Erstellen der SearchView mit allen Konfigurationen und Elementen
     */
    public SearchView(Controller controller) {
        this.controller = controller;
        setButtonsSearchView();

        //Styleklassen setzen
        outputSearchView.getStyleClass().add("searchListElements");
        searchInputTextField.getStyleClass().add("searchMenuElements");
        searchButton.getStyleClass().add("searchMenuElements");

        //Layout der vBox root
        root.setAlignment(Pos.CENTER);

        //groundView.setMenu(searchBox);
        //groundView.setMenu(searchButton);
        //GroundView.menu.getChildren().addAll(searchBox, searchButton);
        //root.getChildren().addAll(searchBox, searchButton);
    }
    public void clearScrollPane(){
        outputSearchView.setVisible(false);
    }

    /**
     * Methode zum Setzen der Elemente der SearchView
     */
    public void setButtonsSearchView() {
        outputSearchView.setVisible(false);

        searchInputTextField.setPrefHeight(30);
        searchInputTextField.setPrefWidth(150);

        searchButton.setText("Search");
        searchButton.setPrefHeight(30);
        searchButton.setPrefWidth(80);
        for (int i = 0; i < 10; ++i) {
            recommends[i] = new Button();
        }
        if (root.getChildren().size() == 0)
            root.getChildren().addAll(searchInputTextField, searchButton);
    }

    public void clearSearching() {
        searchInputTextField.setText("");
        outputSearchView.setVisible(false);
    }

    /**
     * Methode um den Namen der vorgeschlagenen Aktien zurückgibt
     *
     * @return Name von Aktien
     */
    public ArrayList<String> getSearchResult() {
        return searchResult;
    }


    /**
     * Methode um den Inhalt des Input Search Textfeld zurückzugeben
     *
     * @return Inhalt des Textfeldes
     */
    public String getInputText() {
        return searchInputTextField.getText();
    }


}

package View;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SearchView {
    Controller controller;
    VBox root = new VBox();
    HBox inputBox = new HBox();

    ScrollPane outputSearchView = new ScrollPane();
    VBox vBoxSearchResults = new VBox();
    TextField searchInputTextField = new TextField();
    Button searchButton = new Button();

    ComboBox<Button> searchBox = new ComboBox<>();

    ArrayList<String> searchResult = new ArrayList<>();

    //GroundView groundView = new GroundView(controller);


    /**
     * Konstruktor zum Erstellen der SearchView mit allen Konfigurationen und Elementen
     */
    public SearchView(Controller controller) {
        this.controller = controller;
        setButtonsSearchView();
        //groundView.setMenu(searchBox);
        //groundView.setMenu(searchButton);
        //GroundView.menu.getChildren().addAll(searchBox, searchButton);
        //root.getChildren().addAll(searchBox, searchButton);
    }

    /**
     * Methode zum Setzen der Elemente der SearchView
     */
    public void setButtonsSearchView(){
        searchBox.setEditable(true);
        searchBox.setPrefHeight(30);
        searchBox.setPrefWidth(150);

        searchButton.setText("Search");
        searchButton.setPrefHeight(30);
        searchButton.setPrefWidth(60);
    }


    /**
     * Methode um Suchergebnisse anzuzeigen
     * @param resultStr ArrayList wo alle Ergenisse Übergeben werden, die dann angezeigt werden
     */
    public void showSearchResults(String[] resultStr){
        //searchResult = resultStr;
        for(String str : searchResult){
            Button tmp = new Button();
            tmp.setText(str);
            tmp.setMaxWidth(100);
            tmp.setPrefHeight(30);
            vBoxSearchResults.getChildren().add(tmp);
        }
        outputSearchView.setPrefWidth(searchButton.getWidth());
        outputSearchView.setPrefHeight(100);
        outputSearchView.setContent(vBoxSearchResults);
        outputSearchView.setVisible(true);
        //root.getChildren().add(outputSearchView);
    }


    /**
     * Methode um den Namen der vorgeschlagenen Aktien zurückgibt
     * @return Name von Aktien
     */
    public ArrayList<String> getSearchResult(){
        return searchResult;
    }


    /**
     * Methode um den Inhalt des Input Search Textfeld zurückzugeben
     * @return Inhalt des Textfeldes
     */
    public String getInputText(){
        return searchInputTextField.getText();
    }


}

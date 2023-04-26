package View;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SearchView {
    Controller controller;
    VBox root = new VBox();
    HBox inputBox = new HBox();

    ScrollPane outputSearchView = new ScrollPane();
    TextField searchInputTextField = new TextField();
    Button searchButton = new Button();

    ArrayList<Button> buttonList = new ArrayList<>();

    ComboBox<Button> searchBox = new ComboBox<>();

    /**
     * Konstruktor zum Erstellen der SearchView mit allen Konfigurationen und Elementen
     */
    public SearchView(Controller controller) {
        this.controller = controller;
        setButtonsSearchView();
        inputBox.getChildren().addAll(searchBox, searchButton);
        root.getChildren().add(inputBox);
    }

    /**
     * Methode sum Setzen der Elemente der SearchView
     */
    public void setButtonsSearchView(){
        searchBox.setEditable(true);
        searchBox.setStyle(String.valueOf(Color.ALICEBLUE));
        searchBox.setPrefHeight(30);
        searchBox.setPrefWidth(150);

        /*
        searchInputTextField.setPrefHeight(30);
        searchInputTextField.setPrefWidth(100);
        searchInputTextField.setStyle(String.valueOf(Color.ALICEBLUE));
         */

        searchButton.setText("Search");
        searchButton.setPrefHeight(30);
        searchButton.setPrefWidth(60);
        searchButton.setStyle(String.valueOf(Color.ALICEBLUE));

        inputBox.setSpacing(5);
    }

    /**
     * Methode um Suchergebnisse anzuzeigen
     * @param searchArticles ArrayList wo alle Ergenisse Übergeben werden, die dann angezeigt werden
     */
    public void addSearchArticle(ArrayList<String> searchArticles){
        for(String str : searchArticles){
            Button tmp = new Button();
            tmp.setText(str);
            tmp.setPrefHeight(30);
            tmp.setPrefWidth(root.getWidth() - 10);
            root.getChildren().add(tmp);
        }
    }


    public void showSearchResults(ArrayList<String> searchResults){
        for(String str : searchResults){
            Button tmp = new Button();
            tmp.setText(str);
            tmp.setMaxWidth(100);
            tmp.setPrefHeight(30);
            searchBox.getItems().add(tmp);
        }
        //root.getChildren().add(searchBox);
    }


    /**
     * Methode um den Inhalt des Input Search Textfeld zurückzugeben
     * @return Inhalt des Textfeldes
     */
    public String getInputText(){
        return searchInputTextField.getText();
    }


}

package View;

import MainModel.MatchUnits;
import Utils.SearchUtils;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SearchView extends Thread {
    Controller controller;
    VBox root = new VBox();
    ScrollPane outputSearchView = new ScrollPane();
    TextField searchInputTextField = new TextField();
    Button searchButton = new Button();
    ArrayList<String> searchResult = new ArrayList<>();
    VBox recommendsBox = new VBox();

    private final Label[] recommends = new Label[10];
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
    public void setButtonsSearchView() {
        outputSearchView.setVisible(false);

        searchInputTextField.setPrefHeight(30);
        searchInputTextField.setPrefWidth(150);

        searchButton.setText("Search");
        searchButton.setPrefHeight(30);
        searchButton.setPrefWidth(60);
        for (int i = 0; i < 10; ++i) {
            recommends[i] = new Label();
        }
        if (root.getChildren().size() == 0)
            root.getChildren().addAll(searchInputTextField, searchButton, outputSearchView);
    }





    /*

        /**
         * Methode um Suchergebnisse anzuzeigen
         * @param resultStr ArrayList wo alle Ergenisse Übergeben werden, die dann angezeigt werden

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
        }*/

    public void showSearchResults(Controller controller) {
        outputSearchView.setVisible(true);
        outputSearchView.setLayoutX(searchInputTextField.getLayoutX());
        outputSearchView.setLayoutY(searchInputTextField.getLayoutY() + searchInputTextField.getHeight());
        MatchUnits[] result = SearchUtils.getMatchings(searchInputTextField.getText());
        if (result == null) {
            return;
        }
        int count = 0;
        if (recommendsBox.getChildren().size() > 0)
            recommendsBox.getChildren().clear();
        for (int i = 9; i >= 0; --i) {
            if (result[i] == null)
                continue;
            recommends[count].setText(result[i].getName());
            int finalI = i;
            recommends[count].setOnMouseClicked(e -> {
                SearchUtils.labelclicked(result[finalI], controller, this);
            });
            recommendsBox.getChildren().add(recommends[count]);
            count++;
        }
        outputSearchView.setContent(recommendsBox);
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

package View;

import MainModel.*;
import Utils.SearchUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.util.ArrayList;

import static MainModel.Main.*;
import static View.CourseUtils.intervals.*;

public class RealtimeController extends Controller{
    GroundView groundView = new GroundView(this);
    SearchView searchView = new SearchView(this);
    WatchListView watchListView = new WatchListView(this);
    ArrayList<Article> watchListArticles = new ArrayList<>();
    Article watchLCurrentArticle = null;
    Article currentArticle;
    CourseView courseView = new CourseView();
    CourseUtils courseUtils = new CourseUtils(CourseUtils.intervals.fiveY, CourseUtils.courseStatus.normalCourse, courseView, currentArticle);

    SearchUtils searchUtils = new SearchUtils();



    //Lei zum Testen
    ArrayList<Article> shares = new ArrayList<>();


    public GroundView getGroundView() {
        return groundView;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public Scene getScene(){
        return groundView.scene;
    }

    public RealtimeController() {
        groundView.window.setRight(watchListView.wlRoot);
        groundView.window.setLeft(searchView.root);
        setUpWatchList();
        setSearchList();

        //Handler fuer die Buttons zum setzen des Intervals
        for (int i = 0; i < groundView.timeButtons.length; i++) {
            int finalI = i;
            groundView.timeButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    switch (groundView.timeButtons[finalI].getText()){
                        case "1D": courseUtils.changeShowInterval(oneD); break;
                        case "1M": courseUtils.changeShowInterval(oneM); break;
                        case "3M": courseUtils.changeShowInterval(threeM); break;
                        case "6M": courseUtils.changeShowInterval(sixM); break;
                        case "1Y": courseUtils.changeShowInterval(oneY); break;
                        case "5Y": courseUtils.changeShowInterval(fiveY); break;
                        case "MAX": courseUtils.changeShowInterval(max); break;
                    }
                }
            });
        }

        //Handler für den Button zum Veraendern der Ansicht
        groundView.changeStateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(groundView.changeStateButton.getText().equals("Normal")){
                    groundView.changeStateButton.setText("Charts");
                    courseUtils.courseState = CourseUtils.courseStatus.chartCourse;
                    courseUtils.showNormalCourse();
                }else{
                    groundView.changeStateButton.setText("Normal");
                    courseUtils.courseState = CourseUtils.courseStatus.normalCourse;
                    courseUtils.showCharts();
                }
            }
        });
    }

    private void setSearchList() {
        ArrayList<String> searchHelp = searchUtils.search(String.valueOf(searchView.searchBox.getValue()), shares);
        searchView.showSearchResults(searchHelp);

        //On Action für "Search" Button
        searchView.searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Lei zum Testen
                for(int i = 0; i < 5; i++){
                    Article tmp = new Article("Autoreifen" + i);
                    shares.add(tmp);
                }
                for(int i = 0; i < 3; i++){
                    Article tmp = new Article("Traktorreifen" + i);
                    shares.add(tmp);
                }
            }
        });
    }


    /**
     *
     * @param articleName Name des aktuell angezeigten Artikels
     */
    public void wlAddArticle(String articleName){
        //nicht hinzufuegen, falls der Artikel bereits enthalten ist
        for (Button b:watchListView.buttonList) {
            String name = b.getText();
            if(name.equals(articleName)){
                return;
            }
        }
        Button temp = new Button(articleName);
        temp.setOnAction(actionEvent -> {
            /*Mothe aufrufen zur Anzeige des GRaphs*/
            wlSafeCurrentArticle(articleName);
        });
        watchListView.buttonList.add(temp);
        watchListView.vBox.getChildren().add(temp);
    }

                ArrayList<String> searchHelp = searchUtils.search(String.valueOf(searchView.searchBox.getValue()), shares);
                searchView.showSearchResults(searchHelp);
            }
        });
    }


    /**
     * setzt die ActionHandler
     */
    private void setUpWatchList() {
        watchListView.removeButton.setOnAction(actionEvent -> {
            wlRemoveCurrentArticle();
        });

        //Handler fuer den addButton in der Watchlist
        watchListView.addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (currentArticle != null) {
                    wlAddArticle(currentArticle.getName());

                }
            }
        });

        //Probe
        for (int i = 0; i < 20; i++) {
            wlAddArticle("Article " + i);
            watchListArticles.add(new Article("Article " + i));
        }
    }




    /**
     * Wechsel auf den SimulationController und somit auf den SimulationMode
     */
    @Override
    public void changeMode() {
        mode = Main.status.simulation;
    }


    /**
     *
     * @param articleName Name des neuen, aktuell ausgewählten Elements in der WatchList
     */
    @Override
    public void wlSafeCurrentArticle(String articleName) {
        //vorher ausgewählten Button wieder zuruecksetzen
        if(watchLCurrentArticle != null){
            for (int i = 0; i < watchListView.buttonList.size(); i++) {
                if(watchLCurrentArticle.getName().equals(watchListView.buttonList.get(i).getText())){
                    watchListView.buttonList.get(i).setStyle("");
                }
            }
        }

        //neuen Artikel als aktuellen Artikel erstellen
        for (Article article:watchListArticles) {
            if(articleName.equals(article.getName())){
                watchLCurrentArticle = article;
                for (int i = 0; i < watchListView.buttonList.size(); i++) {
                    if(articleName.equals(watchListView.buttonList.get(i).getText())){
                        setButtonStyle(watchListView.buttonList.get(i));
                    }
                }
            }
        }
    }

    /**
     * Die Methode löscht das aktuell ausgewählte Element der Watchlist aus dieser
     */
    @Override
    public void wlRemoveCurrentArticle() {
        for (Button b :watchListView.buttonList) {
            if(b.getText().equals(watchLCurrentArticle.getName())){
                watchListView.vBox.getChildren().remove(b);
                watchListView.buttonList.remove(b);
                return;
            }
        }
        watchListArticles.remove(watchLCurrentArticle);
    }

    /**
     * Diese Methode kann häufiger aufgerufen werden um einen einheitlichen Stil zu haben
     * @param button Button der bearbeitet wird
     *
     */
    public void setButtonStyle(Button button){
        button.setStyle("-fx-border-insets: 5");
        button.setStyle("-fx-border-color: #1970d2");
    }


}

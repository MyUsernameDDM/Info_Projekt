package View;

import MainModel.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.util.ArrayList;

import static MainModel.Main.*;

public class RealtimeController extends Controller{
    GroundView groundView = new GroundView(this);
    SearchView searchView = new SearchView(this);
    WatchListView watchListView = new WatchListView(this);
    ArrayList<Article> watchListArticles = new ArrayList<>();
    Article watchLCurrentArticle = null;

    public RealtimeController() {
        groundView.window.setRight(watchListView.wlRoot);
        setUpWatchList();
    }



    public GroundView getGroundView() {
        return groundView;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public Scene getScene(){
        return groundView.scene;
    }

    /**
     * setzt die ActionHandler
     */
    private void setUpWatchList() {
        watchListView.removeButton.setOnAction(actionEvent -> {
            wlRemoveCurrentArticle();
        });
        for (int i = 0; i < 20; i++) {
            watchListView.addArticle("Article " + i);
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

    @Override
    public void changeTimeMode(int ID){
        Main.buttonTime[ID] = ID;
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
        watchListView.removeArticle(watchLCurrentArticle.getName());
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

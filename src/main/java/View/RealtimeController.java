package View;

import MainModel.*;
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
        setUpWatchList();

        //isch changeWIndow lei zum Testen?
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

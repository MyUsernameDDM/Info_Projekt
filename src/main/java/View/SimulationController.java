package View;

import MainModel.Article;
import MainModel.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;

import static MainModel.Main.mode;

public class SimulationController extends Controller {
    Simulation simulation = new Simulation();
    WalletView walletView = new WalletView(this);
    Article walletCurrentArticle = null; // Aktueller Artikle
    ArrayList<Article> walletListArticles = new ArrayList<>();
    ArrayList<Button> walletList = new ArrayList<>();
    Timeline timeline;
    static Label labelAv = new Label();
    public SimulationController() {
        super();
        setWalletView();
        courseUtils.adjustCourseSize(
                groundView.scene.getWidth() - watchListView.wlRoot.getPrefWidth() - walletView.walletRoot.getPrefWidth(),
                groundView.scene.getHeight() - groundView.timeBox.getPrefHeight() - groundView.menu.getPrefHeight());



        timeline = new Timeline(new KeyFrame(new Duration(1000), event -> {
            //System.out.println("Update");

            labelAv.setText(String.valueOf(Simulation.moneyAv)); // Update A. M.

            for (int i = 0; i < walletList.size(); i++){
                //simulation.moneyInv *=
            }
            //System.out.println(walletView.buttonList.size());

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public GroundView getGroundView() {
        return groundView;
    }

    public Scene getScene(){
        return groundView.scene;
    }

    /**
     * Methode zum Anpassen der Inhalte an die Fentergroesse
     * Unterschied zum Controller nur beim Aufruf courseUtils.adjustCourseSize() weil dort auch die Walletbreite weggerechnet werden muss
     */
    @Override
    protected void setWindowAdjustment(){
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                double newSceneWidth = groundView.scene.getWidth();
                double newSceneHeight = groundView.scene.getHeight();

                //Hintergrund
                groundView.window.setPrefWidth(newSceneWidth);
                groundView.window.setPrefHeight(newSceneHeight);

                double widthRatio = newSceneWidth / groundView.oldSceneWidth;
                double heightRatio = newSceneHeight / groundView.oldSceneHeight;

                //folgende Zeile ist zum normalen Controller unterschiedlich
                //courseUtils.adjustCourseSize(groundView.scene.getWidth() - SimulationController.this.walletView.wlRoot.getPrefWidth() - walletView.walletRoot.getPrefWidth(), groundView.scene.getHeight() - groundView.timeBox.getPrefHeight() - groundView.menu.getPrefHeight());
                groundView.oldSceneWidth = newSceneWidth;
                groundView.oldSceneHeight = newSceneHeight;

            }
        };

        groundView.scene.heightProperty().addListener(changeListener);
        groundView.scene.widthProperty().addListener(changeListener);
    }

    /**
     * Methode die aufgerufen wird, um in den RealtimeModus zu schalten
     */
    //@Override
    public void changeMode() { // Simulation - Realtime
        mode = Main.status.realtime;
    }

    private void setWalletView() {
        groundView.window.setLeft(walletView.walletRoot);
    }

    public void walletAddArticle() {
        walletListArticles.add(currentArticle);

        Button temp = new Button(currentArticle.getName());
        temp.setPrefWidth(210);
        temp.getStyleClass().add("walletArticle");

        walletList.add(temp);

        VBox.setMargin(temp, new Insets(5, 5, 5, 10));

        walletView.vBox.getChildren().add(temp);

        temp.setOnAction(actionEvent -> {
            //Daten aus Datei oder von API holen: TimeSpan dieselbe von Artikel, das davor angezeigt wurde
            while (!currentArticle.setValues(currentArticle.getTimeSpan())) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            courseUtils.showCourse();
            walletSafeCurrentArticle(currentArticle.getName());
        });
    }

    /**
     * @param articleName Name des neuen, aktuell ausgewählten Elements in der WatchList
     */
    public void walletSafeCurrentArticle(String articleName) {
        //vorher ausgewählten Button wieder zuruecksetzen
        if (walletCurrentArticle != null) {
            for (int i = 0; i < walletList.size(); i++) {
                if (walletCurrentArticle.getName().equals(walletList.get(i).getText())) {
                    walletList.get(i).setStyle("");
                }
            }
        }

        //neuen Artikel als aktuellen Artikel erstellen
        for (Article article : walletListArticles) {
            if (articleName.equals(article.getName())) {
                walletCurrentArticle = article;
                for (int i = 0; i < walletList.size(); i++) {
                    if (articleName.equals(walletList.get(i).getText())) {
                        setButtonStyle(walletList.get(i));
                    }
                }
            }
        }
    }

    /**
     * Die Methode löscht das aktuell ausgewählte Element der Watchlist aus dieser
     */
    public void walletRemoveCurrentArticle() {
        if (walletCurrentArticle != null) {
            for (Button b : walletList) {
                if (b.getText().equals(walletCurrentArticle.getName())) {
                    walletView.vBox.getChildren().remove(b);
                    walletList.remove(b);
                    return;
                }
            }
            walletListArticles.remove(walletCurrentArticle);
        }
    }


    public void modeSceneChanger(){
        groundView.modeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                changeMode();
            }
        });{
        }
    }


}


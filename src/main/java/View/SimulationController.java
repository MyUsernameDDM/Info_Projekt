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
import javafx.util.Duration;

import static MainModel.Main.mode;

public class SimulationController extends Controller {
    Simulation simulation = new Simulation();
    WalletView walletView = new WalletView(this);
    Article walletCurrentArticle = null; // Aktueller Artikle

    Timeline timeline;
    static Label labelAv = new Label();
    public SimulationController() {
        super();
        setWalletView();
        courseUtils.adjustCourseSize(
                groundView.scene.getWidth() - watchListView.wlRoot.getPrefWidth() - walletView.walletRoot.getPrefWidth(),
                groundView.scene.getHeight() - groundView.timeBox.getPrefHeight() - groundView.menu.getPrefHeight());



        timeline = new Timeline(new KeyFrame(new Duration(1000), event -> {
            System.out.println("Update");

            labelAv.setText(String.valueOf(Simulation.moneyAv)); // Update A. M.

            for (int i = 0; i < walletView.buttonList.size(); i++){
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
                courseUtils.adjustCourseSize(groundView.scene.getWidth() - watchListView.wlRoot.getPrefWidth() - walletView.walletRoot.getPrefWidth(), groundView.scene.getHeight() - groundView.timeBox.getPrefHeight() - groundView.menu.getPrefHeight());
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

    public void walletSafeCurrentArticle(String articleName){ // CurrentArticle Umändern
        //vorher ausgewählten Button wieder zuruecksetzen
        if (walletCurrentArticle != null) {
            for (int i = 0; i < walletView.buttonList.size(); i++) {
                if (walletCurrentArticle.getName().equals(walletView.buttonList.get(i).getText())) {
                    walletView.buttonList.get(i).setStyle("");
                }
            }
        }

        //neuen Artikel als aktuellen Artikel erstellen
        for (Article article : simulation.walletArticles) {
            if (articleName.equals(article.getName())) {
                walletCurrentArticle = article;
                for (int i = 0; i < walletView.buttonList.size(); i++) {
                    if (articleName.equals(walletView.buttonList.get(i).getText())) {
                        setButtonStyle(walletView.buttonList.get(i));
                    }
                }
            }
        }
    }

    /**
     * @param articleName Name des aktuell angezeigten Artikels
     */
    public void walletAddArticle(String articleName) { // Kaufen
        //nicht hinzufuegen, falls der Artikel bereits enthalten ist
        for (Button b : walletView.buttonList) {
            String name = b.getText();
            if (name.equals(articleName)) {
                return;
            }
        }
        Button temp = new Button(articleName);
        temp.setOnAction(actionEvent -> {
            /*Mothe aufrufen zur Anzeige des GRaphs*/
            wlSafeCurrentArticle(articleName);
        });
        walletView.buttonList.add(temp);

        temp.getStyleClass().add("walletArticle");
        temp.setPrefWidth(210);
        walletView.vBox.setMargin(temp, new Insets(2, 2, 2, 10));

        walletView.vBox.getChildren().add(temp);
    }

    /**
     * Die Methode löscht das aktuell ausgewählte Element dem Wallet aus dieser
     */
    public void walletRemoveCurrentArticle() { // Verkaufen
        if(walletCurrentArticle != null){
            for (Button b : walletView.buttonList) {
                if (b.getText().equals(walletCurrentArticle.getName())) {
                    walletView.vBox.getChildren().remove(b);
                    walletView.buttonList.remove(b);
                    return;
                }
            }
            simulation.walletArticles.remove(watchLCurrentArticle);
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

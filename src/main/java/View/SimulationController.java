package View;

import MainModel.Article;
import MainModel.Main;
import MainModel.Unit;
import Utils.SimulationUtils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static MainModel.Main.mode;

public class SimulationController extends Controller {

    InfoView infoView = new InfoView();
    Simulation simulation = new Simulation();
    WalletView walletView = new WalletView(this);
    Article walletCurrentArticle = null; // Aktueller Artikle
    ArrayList<Article> walletListArticles = new ArrayList<>();
    ArrayList<Button> walletList = new ArrayList<>();
    Timeline timeline;
    SimulationUtils simulationUtils = new SimulationUtils(this);
    int moneyInvest;

    public SimulationController() {
        super();
        setWalletView();
        courseController.adjustCourseSize(
                groundView.scene.getWidth() - watchListView.wlRoot.getPrefWidth() - walletView.walletRoot.getPrefWidth(),
                groundView.scene.getHeight() - groundView.timeBox.getPrefHeight() - groundView.menu.getPrefHeight());


    }

    public GroundView getGroundView() {
        return groundView;
    }

    public Scene getScene(){
        return groundView.scene;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Methode zum Anpassen der Inhalte an die Fentergroesse
     * Unterschied zum Controller nur beim Aufruf courseController.adjustCourseSize() weil dort auch die Walletbreite weggerechnet werden muss
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
                courseController.adjustCourseSize(groundView.scene.getWidth() - walletView.walletRoot.getPrefWidth() - watchListView.wlRoot.getPrefWidth(), groundView.scene.getHeight() - groundView.timeBox.getPrefHeight() - groundView.menu.getPrefHeight());
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
        //Liste für die Buttons anzeigen
        groundView.root.getChildren().add(walletView.simulationButtonVBox);
        walletView.simulationButtonVBox.setVisible(false);

        groundView.menu.getChildren().add(walletView.simulationCoverInMenu);

        groundView.window.setLeft(walletView.walletRoot);
        walletView.simulationCoverInMenu.setOnAction(actionEvent -> {
            walletView.simulationButtonVBox.setLayoutX(walletView.simulationCoverInMenu.getLayoutX() + 20);
            walletView.simulationButtonVBox.setLayoutY(walletView.simulationCoverInMenu.getLayoutY() + walletView.simulationCoverInMenu.getHeight());
            walletView.simulationButtonVBox.setVisible(true);
        });

        walletView.newSimButton.setOnAction(actionEvent -> {

        });
        walletView.loadSimButton.setOnAction(actionEvent -> {

        });
        walletView.saveSimButton.setOnAction(actionEvent -> {
            simulationUtils.saveCurrentSimulation();
        });

        //wenn außerhalb geklickt, wird, dann soll die Liste weggehen
        groundView.root.setOnMouseClicked(e -> {
            walletView.removeSimulationOptions();
        });
    }

    public void walletAddArticle(int money) {

        moneyInvest = money;

        Button temp = simulationArticle(money);

        temp.setOnAction(actionEvent -> {
            //Daten aus Datei oder von API holen: TimeSpan dieselbe von Artikel, das davor angezeigt wurde
            while (!currentArticle.setValues(currentArticle.getTimeSpan())) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            courseController.showCourse();
            walletSafeCurrentArticle(currentArticle.getName());
        });

        simulation.openShares += 1;
        simulation.moneyAv -= moneyInvest;

        timeline = new Timeline(new KeyFrame(new Duration(1000), event -> {
            for (int i = 0; i < simulation.walletListArticles.size(); i++){
                refreshArticle(i);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void refreshArticle(int i) {
        Article article = simulation.walletListArticles.get(i);
        Unit lastUnit = article.getValues().get(article.getValues().size() - 1);

        if (lastUnit.getClose() < moneyInvest){
            simulation.cash.setFill(Color.GREEN);
        } else {
            simulation.cash.setFill(Color.RED);
        }

        double result = ((moneyInvest - (moneyInvest * simulation.fee)) / lastUnit.getClose());
        String formattedResult = String.format("%.4f", result);
        simulation.shares.setText(formattedResult);

        double cashText = (result * lastUnit.getClose());
        String formattedResult2 = String.format("%.2f", cashText);
        simulation.cash.setText(formattedResult2);
        simulation.priceArticle.setText(String.valueOf(lastUnit.getClose()));
    }

    @NotNull
    private Button simulationArticle(int money) {

        Button temp = new Button(currentArticle.getName());
        temp.setPrefWidth(50);
        temp.getStyleClass().add("walletArticle");

        simulation.priceArticle = new Text(String.valueOf(money));
        simulation.currency = new Text("€");
        simulation.shares = new Text("0");
        simulation.cash = new Text("0");
        simulation.priceArticle.getStyleClass().add("priceArticle");
        simulation.currency.getStyleClass().add("priceArticle");
        simulation.shares.getStyleClass().add("priceArticle");
        simulation.cash.getStyleClass().add("priceArticle");

        simulation.walletListArticles.add(currentArticle);
        infoView.showInfoView(temp, walletView.controller);
        walletList.add(temp);

        VBox.setMargin(temp, new Insets(5, 5, 5, 10));
        HBox.setMargin(simulation.priceArticle, new Insets(5, 5, 5, 10));
        HBox.setMargin(simulation.cash, new Insets(5, 5, 5, 5));
        HBox.setMargin(simulation.currency, new Insets(5, 5, 5, 0));
        HBox addArticle = new HBox(temp, simulation.priceArticle, simulation.shares, simulation.cash, simulation.currency);

        addArticle.getStyleClass().add("frameHBox");
        addArticle.setAlignment(Pos.CENTER);

        walletView.vBox.getChildren().add(addArticle);
        return temp;
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
     * Die Methode löscht das aktuell ausgewählte Element der Walletlist aus dieser
     */
    public void walletRemoveCurrentArticle() {
        if (walletCurrentArticle != null) {
            for (Node node : walletView.vBox.getChildren()) {
                if (node instanceof HBox) {
                    HBox hbox = (HBox) node;
                    Button b = (Button) hbox.getChildren().get(0);
                    if (b.getText().equals(walletCurrentArticle.getName())) {
                        walletView.vBox.getChildren().remove(hbox);
                        walletList.remove(hbox);

                        // get the price Text node and print its text content
                        Text priceText = (Text) hbox.getChildren().get(1);
                        String price = priceText.getText();
                        System.out.println("Removed article: " + walletCurrentArticle.getName() + ", price: " + price);

                        simulation.openShares -= 1;
                        simulation.moneyAv += Double.valueOf(price);
                        walletView.labelAv.setText(String.valueOf(simulation.moneyAv));
                        break;
                    }
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

    public void walletRemoveAllArticles(){
        // Lösche alle Artikel aus der View
        walletView.vBox.getChildren().clear();
        walletList.clear();
        // Setze den aktuellen Artikel zurück
        walletCurrentArticle = null;
        // Lösche alle Artikel aus der Simulation
        simulation.walletListArticles.clear();
        simulation.openShares = 0;
        simulation.moneyAv = 1000;

        // Setze die Label zurück
        simulation.labelAv.setText(String.valueOf(simulation.moneyAv));
    }
}


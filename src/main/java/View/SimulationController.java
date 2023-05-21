package View;

import MainModel.Article;
import MainModel.Main;
import MainModel.Simulation;
import Utils.SimulationUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static MainModel.Main.mode;

/**
 * SimulationsController dient als Controller für die Simulationsanzeige, in der der User selbst Aktien und Artikel kaufen
 * kann.
 */
public class SimulationController extends Controller {

    InfoView infoView = new InfoView();
    Simulation simulation = null;
    WalletView walletView = new WalletView(this);
    Article walletCurrentArticle = null; // Aktueller Artikle

    SimulationUtils simulationUtils = new SimulationUtils(this);
    Double moneyInvest;

    public SimulationController() {
        //Aufruf des Konstruktors der Superklasse Controller
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

    /**
     * Sie Simulatin wird auf die übergebene Simulaiton gesetzt. Dies passiert, wenn eine neue Simulaiton begonnen wird oder eine Simulation aus einer Serialization-Datei geladen wird.
     * @param simulation
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Methode zum zurückgeben der aktuellen Simulation
     * @return Simulation, die aktuell verwendet wird
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Getter fuer die Walletview
     * @return die View des Wallets wird zurueckgegeben
     */
    public WalletView getWalletView() {
        return walletView;
    }

    /**
     * Methode zum Anpassen der Inhalte an die Fenstergroesse
     * Unterschied zum Controller nur beim Aufruf courseController.adjustCourseSize() weil dort auch die Walletbreite weggerechnet werden muss
     *
     */
    public void adjustWindowSize(double newSceneWidth, double newSceneHeight){
        //Hintergrund
        groundView.window.setPrefWidth(newSceneWidth);
        groundView.window.setPrefHeight(newSceneHeight);

        groundView.oldSceneWidth = newSceneWidth;
        groundView.oldSceneHeight = newSceneHeight;

        //folgende Zeilen sind zum normalen Controller unterschiedlich
        courseController.adjustCourseSize(groundView.scene.getWidth() - walletView.walletRoot.getPrefWidth() - watchListView.wlRoot.getPrefWidth(), groundView.scene.getHeight() - groundView.timeBox.getPrefHeight() - groundView.menu.getPrefHeight());
        walletView.articlesScrollPane.setPrefHeight(walletView.articlesScrollPane.getPrefHeight());
    }

    /**
     * Methode die aufgerufen wird, um in den RealtimeModus zu schalten
     */
    //@Override
    public void changeMode() { // Simulation - Realtime
        mode = Main.status.realtime;
    }

    /**
     * setWalletView fügt die WalletView der GroundView hinzu und setzt Actionhandler
     */
    private void setWalletView() {

        //Liste für die Buttons anzeigen
        groundView.root.getChildren().add(walletView.simulationButtonVBox);
        walletView.simulationButtonVBox.setVisible(false);

        groundView.menu.getChildren().add(walletView.simulationCoverInMenu);

        groundView.window.setLeft(walletView.walletRoot);

        //Button für das Laden, Erstellen und Speichern der Simulation
        walletView.simulationCoverInMenu.setOnAction(actionEvent -> {
            walletView.simulationButtonVBox.setLayoutX(walletView.simulationCoverInMenu.getLayoutX() + 20);
            walletView.simulationButtonVBox.setLayoutY(walletView.simulationCoverInMenu.getLayoutY() + walletView.simulationCoverInMenu.getHeight());
            walletView.simulationButtonVBox.setVisible(true);
        });

        //setzen der Handler fuer die SimulationsButtons
        walletView.newSimButton.setOnAction(actionEvent -> {
            simulationUtils.newSimulation();
        });
        walletView.loadSimButton.setOnAction(actionEvent -> {
            try {
                simulationUtils.openSimulation();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        walletView.saveSimButton.setOnAction(actionEvent -> {
            simulationUtils.saveCurrentSimulation();
        });

        //wenn außerhalb geklickt, wird, dann soll die Liste weggehen
        groundView.root.setOnMouseClicked(e -> {
            walletView.removeSimulationOptions();
        });
    }

    public void walletAddArticle(Double money) {

        moneyInvest = money;

        //View fuer den Artikel in der Wallet erstellen
        ArticleInWalletView temp = new ArticleInWalletView(currentArticle, this);
        //Werte setzen
        temp.articlePrice.setText("" + (currentArticle.getValues().get(0).getOpen()));
        temp.article.setSharesAmount(money/(currentArticle.getValues().get(0).getOpen()));
        temp.sharesAmountText.setText(String.format("%.2f", temp.article.getSharesAmount()));
        //todo Currency eintragen

        //Artikel hinzufuegen zur Walletliste
        if(!(simulation.getWalletListArticles().contains(currentArticle))){
            simulation.getWalletListArticles().add(currentArticle);
        }
        walletView.articlesInWalletView.add(temp);

        temp.articleButton.setOnAction(actionEvent -> {
            if (!(temp.articleButton.getText().equals(currentArticle.getName()))) {
                //Daten aus dem Zwischenspeicher holen. TimeSpan dieselbe von Artikel, das davor angezeigt wurde
                for (Article s : safeArticle.getSafedArticles()) {
                    if (s.getTimeSpan() == currentTimeSpan && s.getName().equals(temp.articleButton.getText())) {
                        currentArticle = s;
                        break;
                    }
                }
                courseController.showCourse();
            }
            courseController.showCourse();
            walletSafeCurrentArticle(currentArticle.getName());
        });
        simulation.setMoneyAv(simulation.getMoneyAv()-moneyInvest);
        walletView.articlesVBox.getChildren().add(temp.root);


    }


    /**
     * @param articleName Name des neuen, aktuell ausgewählten Elements in der WatchList
     */
    public void walletSafeCurrentArticle(String articleName) {
        //vorher ausgewählten Button wieder zuruecksetzen
        if (walletCurrentArticle != null) {
            for (int i = 0; i < walletView.articlesInWalletView.size(); i++) {
                if (walletCurrentArticle.getName().equals(walletView.articlesInWalletView.get(i).articleButton.getText())) {
                    walletView.articlesInWalletView.get(i).articleButton.getStyleClass().remove("walletArticleClicked");
                    walletView.articlesInWalletView.get(i).articleButton.getStyleClass().add("walletArticle");
                }
            }
        }

        //neuen Artikel als aktuellen Artikel erstellen
        for (Article article : simulation.getWalletListArticles()) {
            if (articleName.equals(article.getName())) {
                walletCurrentArticle = article;
                for (int i = 0; i < walletView.articlesInWalletView.size(); i++) {
                    if (articleName.equals(walletView.articlesInWalletView.get(i).articleButton.getText())) {
                        walletView.articlesInWalletView.get(i).articleButton.getStyleClass().remove("walletArticle");
                        walletView.articlesInWalletView.get(i).articleButton.getStyleClass().add("walletArticleClicked");
                    }
                }
            }
        }
    }

    /**
     * Die Methode löscht das aktuell ausgewählte Element der Walletlist aus dieser
     */
    public void walletRemoveCurrentArticle(double amount) {
        if (walletCurrentArticle != null) {
            for (ArticleInWalletView articleInWalletView : walletView.articlesInWalletView) {
                if (articleInWalletView.articleButton.getText().equals(walletCurrentArticle.getName())) {

                    //wenn mehr als man hat oder genau so viel eingegeben wird, dann werden die Artikel verkauft und entfernt aus der Waleltliste
                    if(amount >= articleInWalletView.article.getSharesAmount()){
                        //aus WalletArticleListe und aus Anzeige entfernen
                        simulation.getWalletListArticles().remove(walletCurrentArticle);
                        walletView.articlesVBox.getChildren().remove(articleInWalletView.root);
                        simulation.setMoneyAv(articleInWalletView.article.getSharesAmount() * articleInWalletView.article.getLastUnit().getOpen());
                    }else{
                        //wenn nur ein Teil verkauft wird
                        simulation.setMoneyAv(simulation.getMoneyAv() + amount * articleInWalletView.article.getLastUnit().getOpen());

                    }
                    walletView.walletMoneyDisplay.avMoneyButton().setText(String.valueOf(simulation.getMoneyAv()));
                    break;
                }
            }
        }
    }

    public void modeSceneChanger(){
        groundView.modeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                changeMode();
            }
        });
    }

}


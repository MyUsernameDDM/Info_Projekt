package View;

import MainModel.Article;
import MainModel.Main;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import static MainModel.Main.mode;

public class SimulationController extends Controller {
    Simulation simulation = new Simulation();
    WalletView walletView = new WalletView(this);
    Article walletCurrentArticle = null; // Aktueller Artikle

    public SimulationController() {
        super();
        setWalletView();
    }

    public GroundView getGroundView() {
        return groundView;
    }

    public Scene getScene(){
        return groundView.scene;
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

package View;

import MainModel.Article;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WalletView{
    SimulationController controller;
    VBox walletRoot = new VBox();       //unterstes Element in der WalletView
    ScrollPane articlesScrollPane = new ScrollPane();
    VBox articlesVBox = new VBox();
    ArrayList<ArticleInWalletView> articlesInWalletView = new ArrayList<>();        //Liste der Artikel im WalletView
    VBox confirmWindow = new VBox();        //View über den Artikeln

    //Buttons und Elemente für die View der Wallet
    Button sellButton = new Button("SELL");
    Button buyButton = new Button("BUY");
    Rectangle buySettings = new Rectangle(230, 50);
    TextField textFieldUserAmount = new TextField("0");
    Label startMoneyLabel = new Label();
    Label note = new Label("Enter Amount in Currency:");

    //Elemente zum Laden etc. einer Simulation
    VBox simulationButtonVBox = new VBox();
    Button simulationCoverInMenu = new Button("SIMULATION");
    Button newSimButton = new Button();
    Button loadSimButton = new Button();
    Button saveSimButton = new Button();

    //Elemente für die Verkaufsansicht
    BorderPane sellArticleView = new BorderPane();
    Label sellQuestionLabel = new Label("Enter Amount of Articles to sell:");
    TextField textFieldSellAmount = new TextField();
    HBox sellHBox = new HBox();
    Button sellConfirmButton = new Button("CONFIRM");
    Button sellCancelButton = new Button("CANCEL");
    windowSettingUp walletMoneyDisplay;
    boolean open = false;       //Hilfsattribut


    /**
     * Konstruktor von WalletView
     * @param controller SimulationController
     */
    public WalletView(SimulationController controller) {
        setSimulationElements();
        setSellArticleView();
        this.controller = controller;

        walletMoneyDisplay = getWindowSettingUp();

        //ID setzen und Infoview für das StartGeldLabel und moneyAv setzen
        startMoneyLabel.setId("Start Money");
        walletMoneyDisplay.avMoneyButton().setId("Available Money");
        controller.infoView.showIDofNodeInfoView(startMoneyLabel);
        controller.infoView.showIDofNodeInfoView(walletMoneyDisplay.avMoneyButton());


        //HBox für Kauf- und Verkaufbutton
        HBox hBox = new HBox(buyButton, sellButton);
        //Handler für Kaufbutton
        buyButton.setOnAction(event -> {

            if (!open){
                BorderPane window = windowSettingDown(walletMoneyDisplay.confirmBuyButton(), walletMoneyDisplay.confirmCancelButton());

                walletMoneyDisplay.confirmBuyButton().setOnAction(event1 -> {
                    confirmWindow.getChildren().remove(window);

                    if (controller.simulation.getMoneyAv() > 0 && Integer.valueOf(textFieldUserAmount.getText()) <= controller.simulation.getMoneyAv()) {
                        controller.simulation.setMoneyAv(controller.simulation.getMoneyAv() - Integer.valueOf(textFieldUserAmount.getText()));
                        walletMoneyDisplay.avMoneyButton().setText(String.valueOf(controller.simulation.getMoneyAv()));

                        if (!(controller.simulation.getWalletListArticles().contains(controller.currentArticle))) {
                            controller.walletAddArticle(Double.valueOf(textFieldUserAmount.getText()));
                        }else{
                            for (ArticleInWalletView articleView :articlesInWalletView) {
                                if(articleView.article.getName().equals(controller.currentArticle.getName())){
                                    //die Artikelmenge um so viel erweitern, wie viel gekauft wurde
                                    articleView.article.setSharesAmount(articleView.article.getSharesAmount() + Double.parseDouble(textFieldUserAmount.getText())/controller.currentArticle.getLastUnit().getOpen());
                                    articleView.sharesAmountText.setText(String.format("%.2f", articleView.article.getSharesAmount()));
                                }
                            }
                        }

                    }

                    open = false;
                });

                walletMoneyDisplay.confirmCancelButton().setOnAction(event1 -> {

                    confirmWindow.getChildren().remove(window);

                    open = false;
                });

                open = true;
            }
        });


        VBox upperwallervbox = walletViewSetting(walletMoneyDisplay.avMoneyButton(), hBox);

        walletRoot.getChildren().addAll(upperwallervbox, articlesScrollPane);
        walletRoot.setPrefWidth(270);
    }

    /**
     * Methode, um die Elemente beim Verkaufen eines Artikels zu setzen. Es ist eine Abfrage, wie viele Elemente verkauft werden sollen.
     */
    private void setSellArticleView() {
        //Elemente hinzufügen
        sellArticleView.setTop(sellQuestionLabel);
        sellArticleView.setCenter(textFieldSellAmount);
        sellHBox.getChildren().addAll(sellConfirmButton, sellCancelButton);
        sellArticleView.setBottom(sellHBox);

        //Layout
        sellArticleView.setMargin(sellQuestionLabel, new Insets(5, 5, 5, 10));
        sellHBox.setMargin(sellConfirmButton, new Insets(5, 5, 5, 10));
        sellHBox.setMargin(sellCancelButton, new Insets(5, 5, 5, 10));
        sellArticleView.setMargin(note, new Insets(5,5,5,10));
        sellArticleView.setMargin(textFieldSellAmount, new Insets(5,5,5,10));
        textFieldSellAmount.setAlignment(Pos.CENTER_LEFT);
        textFieldSellAmount.setPrefWidth(100);

        //Style setzen
        sellConfirmButton.getStyleClass().add("confirmBuyButton");
        sellCancelButton.getStyleClass().add("cancelButton");


    }

    /**
     * Methode, um eine neue Simulation zu starten
     * @param startMoney Startgeld
     */
    public void setNewSimulation(String startMoney){
        controller.simulation.setStartMoney(Double.parseDouble(startMoney));
        startMoneyLabel.setText(startMoney);
        walletMoneyDisplay.avMoneyButton.setText(startMoney);
        articlesVBox.getChildren().clear();
    }

    @NotNull
    private windowSettingUp getWindowSettingUp() {
        buyButton.setPrefWidth(100);
        sellButton.setPrefWidth(100);
        Button money = new Button("0");
        Button confirmBuyButton = new Button("CONFIRM");
        Button confirmCancelButton = new Button("CANCEL");

        money.setStyle("-fx-background-color: #ffffff");
        money.setPrefHeight(75);
        money.setPrefWidth(230);
        money.getStyleClass().add("moneyButton");

        buyButton.getStyleClass().add("buyButton");
        sellButton.getStyleClass().add("sellButton");
        windowSettingUp result = new windowSettingUp(money, confirmBuyButton, confirmCancelButton);
        return result;
    }

    public record windowSettingUp(Button avMoneyButton, Button confirmBuyButton, Button confirmCancelButton) {}

    @NotNull
    private VBox walletViewSetting(Button money, HBox hBox) {
        hBox.setMargin(buyButton, new Insets(10, 10, 10, 10));
        hBox.setMargin(sellButton, new Insets(10, 10, 10, 10));

        VBox upperwallervbox = new VBox(money, startMoneyLabel, hBox, confirmWindow);

        upperwallervbox.setMargin(startMoneyLabel, new Insets(5,5,5,10));

        articlesScrollPane.prefHeight(200);
        articlesScrollPane.setMaxWidth(240);
        articlesScrollPane.setContent(articlesVBox);
        articlesScrollPane.setBackground(Background.fill(Color.WHITE));

        VBox.setMargin(articlesScrollPane, new Insets(5,5,5,10));
        return upperwallervbox;
    }

    /**
     * Diese Funktion gibt die View für das Angeben der zu kaufenden Artikel zurueck
     * @param confirmBuyButton
     * @param confirmCancelButton
     * @return BorderPane: View, die angezeigt wird, um anzugeben, wie viele Artikel gekauft werden sollen
     */
    @NotNull
    private BorderPane windowSettingDown(Button confirmBuyButton, Button confirmCancelButton) {
        BorderPane window = new BorderPane();

        buySettings.setFill(Color.WHITESMOKE);
        buySettings.setWidth(270);
        buySettings.setHeight(100);
        window.getChildren().add(buySettings);

        confirmCancelButton.getStyleClass().add("cancelButton");
        confirmBuyButton.getStyleClass().add("confirmBuyButton");

        HBox barConfirm = new HBox(confirmBuyButton, confirmCancelButton);
        VBox labelTextfieldVbox = new VBox(textFieldUserAmount);
        textFieldUserAmount.setPrefWidth(170);
        textFieldUserAmount.setMaxWidth(200);

        window.setTop(note);
        window.setCenter(labelTextfieldVbox);
        window.setBottom(barConfirm);

        barConfirm.setMargin(confirmBuyButton, new Insets(5, 5, 5, 10));
        barConfirm.setMargin(confirmCancelButton, new Insets(5, 5, 5, 10));
        window.setMargin(note, new Insets(5,5,5,10));
        window.setMargin(labelTextfieldVbox, new Insets(5,5,5,10));
        window.setMargin(textFieldUserAmount, new Insets(5,5,5,10));

        confirmWindow.getChildren().add(window);
        return window;
    }

    /**
     * Methode, die eine Combobox mit Elementen befuellt, damit verschiedene Simulationen geladen werden koennen
     */
    private void setSimulationElements() {
        simulationCoverInMenu.getStyleClass().add("menuButton");

        simulationButtonVBox.getChildren().addAll(newSimButton,loadSimButton, saveSimButton);

        //Namen und Breite setzen
        newSimButton.setText("New");
        newSimButton.setPrefWidth(80);
        newSimButton.getStyleClass().add("buttonInList");
        loadSimButton.setText("Load");
        loadSimButton.setPrefWidth(80);
        loadSimButton.getStyleClass().add("buttonInList");
        saveSimButton.setText("save");
        saveSimButton.setPrefWidth(80);
        saveSimButton.getStyleClass().add("buttonInList");
    }

    /**
     * Methode, die die Optionen zum laden, speichern und erstellen einer Simulation ausblendet
     */
    public void removeSimulationOptions() {
        simulationButtonVBox.setVisible(false);
    }

    /**
     * Methode wird aufgerufen, um eine neu geladene Simulation anzuzeigen
     */
    public void reloadSimulation(){
        startMoneyLabel.setText(controller.simulation.getStartMoney() + "");
        articlesVBox.getChildren().clear();
        walletMoneyDisplay.avMoneyButton.setText(controller.simulation.getMoneyAv()+"");
        for (Article article: controller.simulation.getWalletListArticles()) {
            controller.currentArticle = article;
            //zurueckrechnen mit money, weil ja nicht aktuelisiert, kommt es wieder auf die selben Shareamount
            controller.walletAddArticle(article.getSharesAmount() * article.getLastUnit().getOpen());
        }

    }
}
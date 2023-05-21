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

    boolean isAv = true;
    SimulationController controller;
    VBox walletRoot = new VBox();
    ScrollPane articlesScrollPane = new ScrollPane();
    VBox articlesVBox = new VBox();
    ArrayList<ArticleInWalletView> articlesInWalletView = new ArrayList<>();

    VBox confirmWindow = new VBox();
    Button sellButton = new Button("SELL");
    Button sellAllButton = new Button("SELL EVERYTHING");
    Button buyButton = new Button("BUY");
    Rectangle buySettings = new Rectangle(230, 50);
    TextField textFieldUserAmount = new TextField("0");
    Label startMoneyLabel = new Label();
    Label note = new Label("Enter Amount:");
    VBox upperwalletvbox = new VBox();
    VBox simulationButtonVBox = new VBox();
    Button simulationCoverInMenu = new Button("Simulation");
    Button newSimButton = new Button();
    Button loadSimButton = new Button();
    Button saveSimButton = new Button();

    //Artikel Verkaufsansicht
    BorderPane sellArticleView = new BorderPane();
    Label sellQuestionLabel = new Label("Enter Amount to sell:");
    TextField textFieldSellAmount = new TextField();
    HBox sellHBox = new HBox();
    Button sellConfirmButton = new Button("CONFIRM");
    Button sellCancelButton = new Button("CANCEL");
    windowSettingUp walletMoneyDisplay;
    boolean open = false;


    public Label getStartMoneyLabel() {
        return startMoneyLabel;
    }

    public WalletView(SimulationController controller) {
        setSimulationElements();
        setSellArticleView();
        this.controller = controller;

        walletMoneyDisplay = getWindowSettingUp();

        //ID setzen und Infoview für das StartGeldLabel setzen
        startMoneyLabel.setId("Start Money");
        controller.infoView.showIDofNodeInfoView(startMoneyLabel);


        walletMoneyDisplay.avMoneyButton().setOnAction(event -> {
            if (isAv == true){
                walletMoneyDisplay.avMoneyButton().setText(String.valueOf(controller.simulation.getMoneyAv()));
                isAv = false;
            } else {
                walletMoneyDisplay.avMoneyButton().setText("*****");
                isAv = true;
            }
        });

        HBox hBox = new HBox(buyButton, sellButton);

        buyButton.setOnAction(event -> {

            if (!open){
                BorderPane window = windowSettingDown(walletMoneyDisplay.confirmBuyButton(), walletMoneyDisplay.confirmCancelButton());

                walletMoneyDisplay.confirmBuyButton().setOnAction(event1 -> {
                    confirmWindow.getChildren().remove(window);

                    if (controller.simulation.getMoneyAv() > 0 && Integer.valueOf(textFieldUserAmount.getText()) <= controller.simulation.getMoneyAv()) {
                        System.out.println("Test!");
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

        //Handler fuer das Angeben der Anzahl der zu verkaufenden Artikel
        sellButton.setOnAction(event -> {
            if(!(confirmWindow.getChildren().contains(sellArticleView))){
                confirmWindow.getChildren().add(sellArticleView);
            }

        });
        sellConfirmButton.setOnAction(actionEvent -> {
            controller.walletRemoveCurrentArticle(Double.parseDouble(textFieldSellAmount.getText()));
            confirmWindow.getChildren().remove(sellArticleView);
        });
        sellCancelButton.setOnAction(actionEvent -> {
            confirmWindow.getChildren().remove(sellArticleView);
        });


        VBox upperwallervbox = walletViewSetting(walletMoneyDisplay.avMoneyButton(), hBox);

        walletRoot.getChildren().addAll(upperwallervbox, articlesScrollPane);
        walletRoot.setPrefWidth(270);
    }

    private void setSellArticleView() {
        sellArticleView.setTop(sellQuestionLabel);
        sellArticleView.setCenter(textFieldSellAmount);
        sellHBox.getChildren().addAll(sellConfirmButton, sellCancelButton);
        sellArticleView.setBottom(sellHBox);

        sellArticleView.setMargin(sellQuestionLabel, new Insets(5, 5, 5, 10));
        sellHBox.setMargin(sellConfirmButton, new Insets(5, 5, 5, 10));
        sellHBox.setMargin(sellCancelButton, new Insets(5, 5, 5, 10));
        sellArticleView.setMargin(note, new Insets(5,5,5,10));
        sellArticleView.setMargin(textFieldSellAmount, new Insets(5,5,5,10));
        textFieldSellAmount.setAlignment(Pos.CENTER_LEFT);
        textFieldSellAmount.setPrefWidth(100);


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
        sellAllButton.setPrefWidth(210);
        Button money = new Button("0");
        Button confirmBuyButton = new Button("CONFIRM");
        Button confirmCancelButton = new Button("CANCEL");

        money.setStyle("-fx-background-color: #ffffff;");
        money.setPrefHeight(75);
        money.setPrefWidth(230);
        money.getStyleClass().add("moneyButton");

        buyButton.getStyleClass().add("buyButton");
        sellButton.getStyleClass().add("sellButton");
        sellAllButton.getStyleClass().add("sellAllButton");
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
        upperwallervbox.setMargin(sellAllButton, new Insets(5, 5, 10, 10));

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
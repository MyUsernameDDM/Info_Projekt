package View;

import MainModel.Article;
import javafx.geometry.Insets;
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
    Button confirmButton = new Button("Confirm");
    Button cancelButton = new Button("Cancel");
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
                walletMoneyDisplay.avMoneyButton().setText(String.valueOf(controller.simulation.moneyAv));
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

                    if (controller.simulation.moneyAv > 0 && Integer.valueOf(textFieldUserAmount.getText()) <= controller.simulation.moneyAv) {
                        System.out.println("Test!");
                        controller.simulation.moneyAv -= Integer.valueOf(textFieldUserAmount.getText());
                        controller.simulation.moneyInv += Integer.valueOf(textFieldUserAmount.getText());
                        walletMoneyDisplay.avMoneyButton().setText(String.valueOf(controller.simulation.moneyAv));

                        if (!(controller.simulation.walletListArticles.contains(controller.currentArticle))) {
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
            confirmWindow.getChildren().add(sellArticleView);

        });
        confirmButton.setOnAction(actionEvent -> {
            controller.walletRemoveCurrentArticle(Double.parseDouble(textFieldSellAmount.getText()));
            confirmWindow.getChildren().remove(sellArticleView);
        });
        cancelButton.setOnAction(actionEvent -> {
            confirmWindow.getChildren().remove(sellArticleView);
        });


        VBox upperwallervbox = walletViewSetting(walletMoneyDisplay.avMoneyButton(), hBox);

        walletRoot.getChildren().addAll(upperwallervbox, articlesScrollPane);
        walletRoot.setPrefWidth(270);
    }

    private void setSellArticleView() {
        sellArticleView.setTop(sellQuestionLabel);
        sellArticleView.setCenter(textFieldSellAmount);
        sellHBox.getChildren().addAll(confirmButton, cancelButton);
        sellArticleView.setBottom(sellHBox);

        sellHBox.setMargin(confirmButton, new Insets(5, 5, 5, 10));
        sellHBox.setMargin(cancelButton, new Insets(5, 5, 5, 10));
        sellArticleView.setMargin(note, new Insets(5,5,5,10));
        sellArticleView.setMargin(textFieldSellAmount, new Insets(5,5,5,10));

        confirmButton.getStyleClass().add("confirmBuyButton");
        cancelButton.getStyleClass().add("cancelButton");


    }

    /**
     * Methode, um eine neue Simulation zu starten
     * @param startMoney Startgeld
     */
    public void setNewSimulation(String startMoney){
        controller.simulation.startMoney = Double.parseDouble(startMoney);
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

        newSimButton.setText("New");
        newSimButton.getStyleClass().add("buttonInList");
        loadSimButton.setText("Load");
        loadSimButton.getStyleClass().add("buttonInList");
        saveSimButton.setText("save");
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
        startMoneyLabel.setText(controller.simulation.startMoney + "");
        articlesVBox.getChildren().clear();
        walletMoneyDisplay.avMoneyButton.setText(controller.simulation.getMoneyAv()+"");
        for (Article article: controller.simulation.walletListArticles) {
            controller.currentArticle = article;
            //zurueckrechnen mit money, weil ja nicht aktuelisiert, kommt es wieder auf die selben Shareamount
            controller.walletAddArticle(article.getSharesAmount() * article.getLastUnit().getOpen());
        }

    }
}
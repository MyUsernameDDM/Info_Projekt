package View;

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
    windowSettingUp walletMoneyDisplay;
    boolean open = false;


    public Label getStartMoneyLabel() {
        return startMoneyLabel;
    }

    public WalletView(SimulationController controller) {
        setSimulationElements();

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
                                    System.out.println("Test");
                                    //die Artikelmenge um so viel erweitern, wie viel gekauft wurde
                                    articleView.sharesAmountText.setText(String.format("%.2f", articleView.sharesAmount + Double.parseDouble(textFieldUserAmount.getText())/controller.currentArticle.getValues().get(0).getOpen()));
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

        sellButton.setOnAction(event -> {
            controller.walletRemoveCurrentArticle();
        });

        sellAllButton.setOnAction(event -> { // nicht d.
            //controller.sellAllButton(controller.simulation.walletListArticles);
            //controller.walletRemoveAllArticles();
        });

        VBox upperwallervbox = walletViewSetting(walletMoneyDisplay.avMoneyButton(), hBox);

        walletRoot.getChildren().addAll(upperwallervbox, articlesScrollPane);
        walletRoot.setPrefWidth(270);
    }

    public void setNewSimulation(String startMoney){
        startMoneyLabel.setText(startMoney);
        walletMoneyDisplay.avMoneyButton.setText(startMoney);

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

    public record windowSettingUp(Button avMoneyButton, Button confirmBuyButton, Button confirmCancelButton) {
    }

    @NotNull
    private VBox walletViewSetting(Button money, HBox hBox) {
        hBox.setMargin(buyButton, new Insets(10, 10, 10, 10));
        hBox.setMargin(sellButton, new Insets(10, 10, 10, 10));

        VBox upperwallervbox = new VBox(money, startMoneyLabel, hBox, confirmWindow);

        upperwallervbox.setMargin(startMoneyLabel, new Insets(5,5,5,10));
        upperwallervbox.setMargin(sellAllButton, new Insets(5, 5, 10, 10));

        articlesScrollPane.prefHeight(500);
        articlesScrollPane.setMinHeight(200);
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

    public void removeSimulationOptions() {
        simulationButtonVBox.setVisible(false);
    }

    /**
     * Methode wird aufgerufen, um eine neu geladene Simulation anzuzeigen
     */
    public void reloadSimulation(){
        startMoneyLabel.setText(controller.simulation.getMoneyAv() + "");
        //todo do miaßat man olle Artikel fa dor neuen SImulation usw. lodn

    }
}
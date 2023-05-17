package View;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

public class WalletView{

    boolean isAv = true;
    SimulationController controller;
    VBox walletRoot = new VBox();
    ScrollPane scrollPane = new ScrollPane();
    VBox vBox = new VBox();

    VBox confirmWindow = new VBox();
    Button sellButton = new Button("SELL");
    Button sellAllButton = new Button("SELL EVERYTHING");
    Button buyButton = new Button("BUY");
    Rectangle buySettings = new Rectangle(230, 50);
    TextField textFieldUserAmount = new TextField("0");
    Label moneyShow = new Label();
    Label note = new Label("Enter Amount:");
    VBox upperwalletvbox = new VBox();
    Label labelAv = new Label();
    VBox simulationButtonVBox = new VBox();
    Button simulationCoverInMenu = new Button("Simulation");
    Button newSimButton = new Button();
    Button loadSimButton = new Button();
    Button saveSimButton = new Button();
    boolean open = false;
    Text currency;
    Text cash;
    Text priceArticle;
    Text shares;

    public Label getMoneyShow() {
        return moneyShow;
    }

    public WalletView(SimulationController controller) {
        setSimulationElements();

        this.controller = controller;

        windowSettingUp result = getWindowSettingUp();

        result.money().setOnAction(event -> {
            if (isAv == true){
                result.money().setText(String.valueOf(controller.simulation.moneyInv));
                isAv = false;
            } else {
                result.money().setText("*****");
                isAv = true;
            }
        });

        HBox hBox = new HBox(buyButton, sellButton);

        buyButton.setOnAction(event -> {

            if (!open){
                BorderPane window = windowSettingDown(result.confirmBuyButton(), result.confirmCancelButton());

                result.confirmBuyButton().setOnAction(event1 -> {
                    confirmWindow.getChildren().remove(window);

                    if (controller.simulation.moneyAv > 0 && Integer.valueOf(textFieldUserAmount.getText()) <= controller.simulation.moneyAv) {
                        controller.simulation.moneyAv -= Integer.valueOf(textFieldUserAmount.getText());
                        controller.simulation.moneyInv += Integer.valueOf(textFieldUserAmount.getText());

                        controller.walletAddArticle(Integer.valueOf(textFieldUserAmount.getText()));
                    }

                    open = false;
                });

                result.confirmCancelButton().setOnAction(event1 -> {
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
            controller.walletRemoveAllArticles();
        });

        VBox upperwallervbox = walletViewSetting(result.money(), hBox);

        walletRoot.getChildren().addAll(upperwallervbox, scrollPane);
        walletRoot.setPrefWidth(270);
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

    private record windowSettingUp(Button money, Button confirmBuyButton, Button confirmCancelButton) {
    }

    @NotNull
    private VBox walletViewSetting(Button money, HBox hBox) {
        hBox.setMargin(buyButton, new Insets(10, 10, 10, 10));
        hBox.setMargin(sellButton, new Insets(10, 10, 10, 10));

        VBox upperwallervbox = new VBox(money, moneyShow, hBox, confirmWindow);

        upperwallervbox.setMargin(moneyShow, new Insets(5,5,5,10));
        upperwallervbox.setMargin(sellAllButton, new Insets(5, 5, 10, 10));

        scrollPane.prefHeight(500);
        scrollPane.setHmin(500);
        scrollPane.setMaxWidth(240);
        scrollPane.setContent(vBox);
        scrollPane.setBackground(Background.fill(Color.WHITE));

        VBox.setMargin(scrollPane, new Insets(5,5,5,10));
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
        moneyShow.setText(controller.simulation.getMoneyAv() + "");
        //todo do miaßat man olle Artikel fa dor neuen SImulation usw. lodn

    }
}
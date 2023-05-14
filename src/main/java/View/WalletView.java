package View;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WalletView{

    boolean isAv = true;
    Simulation simulation = new Simulation();
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

    Label moneyAv = new Label();

    Label note = new Label("Enter Amount:");
    VBox upperwalletvbox = new VBox();
    Label labelAv = new Label();
    VBox simulationButtonVBox = new VBox();
    Button simulationCoverInMenu = new Button("Simulation");
    Button newSimButton = new Button();
    Button loadSimButton = new Button();
    Button saveSimButton = new Button();

    boolean open = false;
    public WalletView(SimulationController controller) {
        setSimulationElements();

        this.controller = controller;

        buyButton.setPrefWidth(100);
        sellButton.setPrefWidth(100);
        sellAllButton.setPrefWidth(210);
        moneyAv.setText(String.valueOf(Simulation.moneyAv));
        Button money = new Button(String.valueOf(Simulation.moneyInv));
        Button confirmBuyButton = new Button("CONFIRM");
        Button confirmCancelButton = new Button("CANCEL");

        money.setStyle("-fx-background-color: #ffffff;");
        money.setPrefHeight(75);
        money.setPrefWidth(230);
        money.getStyleClass().add("moneyButton");

        buyButton.getStyleClass().add("buyButton");
        sellButton.getStyleClass().add("sellButton");
        sellAllButton.getStyleClass().add("sellAllButton");


        money.setOnAction(event -> {
            if (isAv == true){
                money.setText(String.valueOf(Simulation.moneyInv));
                isAv = false;
            } else {
                money.setText("*****");
                isAv = true;
            }
        });

        HBox hBox = new HBox(buyButton, sellButton);

        buyButton.setOnAction(event -> {

            if (!open){
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

                confirmBuyButton.setOnAction(event1 -> {
                    confirmWindow.getChildren().remove(window);

                    if (Simulation.moneyAv >= 0 && Integer.valueOf(textFieldUserAmount.getText()) <= Simulation.moneyAv) {
                        Simulation.moneyAv -= Integer.valueOf(textFieldUserAmount.getText());
                        Simulation.moneyInv += Integer.valueOf(textFieldUserAmount.getText());

                        controller.walletAddArticle(Integer.valueOf(textFieldUserAmount.getText()));
                    }

                    open = false;
                });

                confirmCancelButton.setOnAction(event1 -> {
                    System.out.println("CANCEL");
                    vBox.getChildren().remove(window);
                    open = false;
                });

                open = true;
            }
        });

        sellButton.setOnAction(event -> {
            controller.walletRemoveCurrentArticle();
        });

        sellAllButton.setOnAction(event -> {
            System.out.println("Sell all");
        });


        hBox.setMargin(buyButton, new Insets(10, 10, 10, 10));
        hBox.setMargin(sellButton, new Insets(10, 10, 10, 10));


        upperwalletvbox.getChildren().addAll(money, labelAv, hBox, sellAllButton, confirmWindow);

        upperwalletvbox.setMargin(labelAv, new Insets(5,5,5,10));
        upperwalletvbox.setMargin(sellAllButton, new Insets(5, 5, 10, 10));

        scrollPane.prefHeight(500);
        scrollPane.setHmin(500);
        scrollPane.setMaxWidth(240);
        scrollPane.setContent(vBox);
        scrollPane.setBackground(Background.fill(Color.WHITE));

        VBox.setMargin(scrollPane, new Insets(5,5,5,10));


        walletRoot.getChildren().addAll(upperwalletvbox, scrollPane);
        walletRoot.setPrefWidth(270);
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
}
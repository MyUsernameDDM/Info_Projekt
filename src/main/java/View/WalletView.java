package View;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class WalletView{

    boolean isAv = true;
    Simulation simulation = new Simulation();
    SimulationController controller;
    VBox walletRoot = new VBox();
    ScrollPane scrollPane = new ScrollPane();
    VBox vBox = new VBox();

    ArrayList<Button> buttonList = new ArrayList<>();
    Label titel = new Label("Wallet");
    Button sellButton = new Button("SELL");
    Button sellAllButton = new Button("SELL EVERYTHING");
    Button buyButton = new Button("BUY");
    Rectangle buySettings = new Rectangle(230, 50);
    TextField textFieldUserAmount = new TextField("0");

    Label moneyAv = new Label();

    boolean open = false;
    public WalletView(SimulationController controller) {

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
                buySettings.setWidth(230);
                buySettings.setHeight(75);

                window.getChildren().add(buySettings);
                confirmCancelButton.getStyleClass().add("cancelButton");
                confirmBuyButton.getStyleClass().add("confirmBuyButton");

                HBox barConfirm = new HBox(confirmBuyButton, confirmCancelButton);

                window.setTop(new Label("Enter Amount:"));
                VBox labelTextfieldVbox = new VBox(textFieldUserAmount);
                window.setCenter(labelTextfieldVbox);
                window.setBottom(barConfirm);

                vBox.getChildren().add(window);

                confirmBuyButton.setOnAction(event1 -> {
                    System.out.println("BUY:");
                    vBox.getChildren().remove(window);

                    if (Simulation.moneyAv >= 0 && Integer.valueOf(textFieldUserAmount.getText()) <= Simulation.moneyAv) {
                        Simulation.moneyAv -= Integer.valueOf(textFieldUserAmount.getText());
                        Simulation.moneyInv += Integer.valueOf(textFieldUserAmount.getText());

                        System.out.println(Simulation.moneyAv);
                        controller.walletAddArticle(controller.currentArticle.getName() + " : " + textFieldUserAmount.getText());
                        controller.walletSafeCurrentArticle(controller.currentArticle.getName());
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
            controller.walletSafeCurrentArticle(controller.currentArticle.getName());
            controller.walletRemoveCurrentArticle();
        });

        sellAllButton.setOnAction(event -> {
            System.out.println("Sell all");
        });

        hBox.setMargin(buyButton, new Insets(10, 10, 10, 10));
        hBox.setMargin(sellButton, new Insets(10, 10, 10, 10));

        VBox upperwallervbox = new VBox(money, SimulationController.labelAv, hBox, sellAllButton);

        upperwallervbox.setMargin(SimulationController.labelAv, new Insets(5,5,5,10));
        upperwallervbox.setMargin(sellAllButton, new Insets(5, 5, 10, 10));

        walletRoot.getChildren().addAll(upperwallervbox, vBox);
        walletRoot.setPrefWidth(230);
    }
}
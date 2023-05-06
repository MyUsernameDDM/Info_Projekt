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
    Button sellButton = new Button("Verkaufen");
    Button sellAllButton = new Button("Alle verkaufen");
    Button buyButton = new Button("Kaufen");

    Rectangle buySettings = new Rectangle(230, 50);
    TextField textFieldUserAmount = new TextField("0");

    boolean open = false;

    Label labelAv = new Label();
    public WalletView(SimulationController controller) {

        buyButton.setOnAction(event -> {
            System.out.println("Test");
            controller.walletSafeCurrentArticle("IBM");
            //controller.walletAddArticle();
            //simulation.walletArticles.add(controller.currentArticle);
            //controller.walletAddArticle(controller.currentArticle.getName());
        });

        sellAllButton.setOnAction(event -> {
            controller.walletRemoveCurrentArticle();
        });


        this.controller = controller;
        buyButton.setPrefWidth(100);
        sellButton.setPrefWidth(100);
        sellAllButton.setPrefWidth(210);

        Button money = new Button(String.valueOf(Simulation.moneyInc));
        Button confirmBuyButton = new Button("BUY");
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
                money.setText(String.valueOf(Simulation.moneyInc));
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


                HBox barConfirm = new HBox(confirmBuyButton, confirmCancelButton);

                window.setTop(new Label("Enter Amount:"));
                labelAv.setText(String.valueOf(simulation.moneyAv));
                VBox labelTextfieldVbox = new VBox(labelAv, textFieldUserAmount);
                window.setCenter(labelTextfieldVbox);
                window.setBottom(barConfirm);

                vBox.getChildren().add(window);

                confirmBuyButton.setOnAction(event1 -> {
                    System.out.println("BUY:");
                    vBox.getChildren().remove(window);

                    simulation.moneyAv -= Integer.valueOf(textFieldUserAmount.getText());
                    simulation.moneyInc += Integer.valueOf(textFieldUserAmount.getText());
                    System.out.println(simulation.moneyAv);

                    controller.walletAddArticle(controller.currentArticle.getName() + " : " + textFieldUserAmount.getText());

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

        hBox.setMargin(buyButton, new Insets(10, 10, 10, 10));
        hBox.setMargin(sellButton, new Insets(10, 10, 10, 10));

        VBox vBox1 = new VBox(money, hBox, sellAllButton);

        vBox1.setMargin(sellAllButton, new Insets(5, 5, 10, 10));

        walletRoot.getChildren().addAll(vBox1, vBox);
        walletRoot.setPrefWidth(230);
    }
}
package View;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class WalletView{

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




    public WalletView(SimulationController controller) {

        buyButton.setOnAction(event -> {
            System.out.println("Test");
            controller.walletSafeCurrentArticle("IBM");
            //controller.walletAddArticle();
            //simulation.walletArticles.add(controller.currentArticle);
            //controller.walletAddArticle(controller.currentArticle.getName());
        });

       /*
        sellAllButton.setOnAction(event -> {
            controller.wallet
        });
        */

        this.controller = controller;
        buyButton.setPrefWidth(100);
        sellButton.setPrefWidth(100);
        sellAllButton.setPrefWidth(100);

        walletRoot.getChildren().addAll(titel, buyButton, sellButton, sellAllButton);
    }




}
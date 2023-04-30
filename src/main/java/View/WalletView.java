package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class WalletView{
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
        this.controller = controller;
        buyButton.setPrefWidth(100);
        sellButton.setPrefWidth(100);
        sellAllButton.setPrefWidth(100);

        walletRoot.getChildren().addAll(titel, buyButton, sellButton, sellAllButton);
    }

}
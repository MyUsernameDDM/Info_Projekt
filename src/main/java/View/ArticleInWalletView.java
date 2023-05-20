package View;

import MainModel.Article;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ArticleInWalletView {
    SimulationController controller;
    Article article;
    HBox root = new HBox();
    Button articleButton = new Button();
    Text articlePrice = new Text("0");
    Text sharesAmountText = new Text("0");
    double sharesAmount;

    Text currency = new Text("€");


    public ArticleInWalletView(Article article, SimulationController controller) {
        this.controller = controller;
        this.article = article;
        articleButton.setText(article.getName());
        root.getChildren().addAll(articleButton, sharesAmountText, articlePrice, currency);
        setStyle();
        setInfoViews();


        //Infoview setzen
        controller.infoView.showInfoView(articleButton, controller);
    }

    private void setInfoViews() {
        //IDs für die Wertanzeige eines Artikels setzen
        articlePrice.setId("Articleprice");
        sharesAmountText.setId("Amount of Articles");
        controller.infoView.showIDofNodeInfoView(articlePrice);
        controller.infoView.showIDofNodeInfoView(sharesAmountText);

    }

    private void setStyle() {
        articleButton.setPrefWidth(50);
        articleButton.getStyleClass().add("walletArticle");

        articlePrice.getStyleClass().add("priceArticle");
        currency.getStyleClass().add("priceArticle");
        sharesAmountText.getStyleClass().add("priceArticle");

        VBox.setMargin(articleButton, new Insets(5, 5, 5, 10));
        HBox.setMargin(articlePrice, new Insets(5, 5, 5, 10));
        HBox.setMargin(sharesAmountText, new Insets(5, 5, 5, 10));
        HBox.setMargin(currency, new Insets(5, 5, 5, 0));

        root.getStyleClass().add("frameHBox");
        root.setAlignment(Pos.CENTER);
    }

}
